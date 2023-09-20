package com.abakmaz.CurrencyCalculater.Services.service;

import com.abakmaz.CurrencyCalculater.Services.model.CurrencyRequestModel;
import com.abakmaz.CurrencyCalculater.Services.model.CurrencyResponseModel;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class CurrencyCalculaterServiceImpl implements CurrencyCalculaterService {

  private ConcurrentMap<String, Double> conversionRatesCache = new ConcurrentHashMap<>();
  String[] currencyPairs = {"USDTRY", "EURUSD", "GBPUSD", "USDJPY", "USDCHF", "AUDUSD"};

  public void cacheConversionRates(Vertx vertx) {
    WebClient client = WebClient.create(vertx);

    String authorizationHeader = "Basic c2FtZXRia216VGVzdDoxMjM0NTY=";

    AtomicInteger counter = new AtomicInteger(0);

    for (String currencyPair : currencyPairs) {
      vertx.setTimer(15000 * counter.incrementAndGet(), timerId -> {
        String url = String.format("https://service.foreks.com/feed/snapshot/?f=Code%%2CLast&Code=%s", currencyPair);

        client.getAbs(url)
          .ssl(true)
          .putHeader("Authorization", authorizationHeader)
          .send(ar -> {
            if (ar.succeeded()) {
              HttpResponse<Buffer> response = ar.result();
              String responseBody = response.bodyAsString();
              System.out.println(responseBody);
              try {
                JsonArray jsonArray = new JsonArray(responseBody);
                for (Object obj : jsonArray) {
                  if (obj instanceof JsonObject) {
                    JsonObject json = (JsonObject) obj;
                    double conversionRate = json.getDouble("Last");
                    String code = json.getString("Code");
                    conversionRatesCache.put(code, conversionRate);
                  }
                }
              } catch (Exception e) {
                System.out.println("An error occurred while caching conversion rates: " + e.getMessage());
              }
            } else {
              System.out.println("Failed to fetch conversion rates: " + ar.cause().getMessage());
            }
          });
      });
    }

    vertx.setTimer(180000, timerId -> {
      cacheConversionRates(vertx);
    });
  }

  public CurrencyResponseModel calculateCurrency(CurrencyRequestModel requestModel) {
    String from = requestModel.getFrom();
    String to = requestModel.getTo();
    Double conversionRate = getConversionRate(from , to);

    if (conversionRate == null) {
      try {
        Double exchangeRate = calculateExchangeRate(from, to);

        if (exchangeRate == null) {
          throw new RuntimeException("Conversion rate not available");
        }


        double convertedAmount = Math.round(exchangeRate * requestModel.getAmount() * 100000.0) / 100000.0;
        return new CurrencyResponseModel(convertedAmount, to);

      } catch (Exception e) {
        throw new RuntimeException("Error occurred during calculation: " + e.getMessage());
      }
    } else {

      double convertedAmount = Math.round(conversionRate * requestModel.getAmount() * 100000.0) / 100000.0;
      return new CurrencyResponseModel(convertedAmount, to);
    }
  }
  private Double getConversionRate(String base, String target) {
    Double conversionRate = conversionRatesCache.get(base+target);

    if (conversionRate == null ) {
      return 1 / conversionRatesCache.get(target+base);
    }

    return conversionRatesCache.get(base+target);
  }

  public Double calculateExchangeRate(String baseCurrency, String targetCurrency) {
    Double baseToUSDRate = conversionRatesCache.getOrDefault(baseCurrency + "USD", null);
    Double targetToUSDRate = conversionRatesCache.getOrDefault(targetCurrency + "USD", null);

    Double baseToTargetRate = conversionRatesCache.getOrDefault("USD" + targetCurrency, null);
    Double targetToBaseRate = conversionRatesCache.getOrDefault("USD" + baseCurrency, null);

    if (baseToUSDRate != null && targetToUSDRate != null) {
      return Math.round((targetToUSDRate / baseToUSDRate) * 100000.0) / 100000.0;
    } else if (baseToUSDRate != null && baseToTargetRate != null) {
      return Math.round((baseToTargetRate / baseToUSDRate) * 100000.0) / 100000.0;
    } else if (targetToBaseRate != null && targetToUSDRate != null) {
      return Math.round((targetToBaseRate / targetToUSDRate) * 100000.0) / 100000.0;
    } else if (targetToBaseRate != null && baseToUSDRate != null) {
      return Math.round((baseToUSDRate / targetToBaseRate) * 100000.0) / 100000.0;
    }

    return null;
  }
}
