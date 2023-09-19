package com.abakmaz.CurrencyCalculater.Services.service;

import com.abakmaz.CurrencyCalculater.Services.model.CurrencyRequestModel;
import com.abakmaz.CurrencyCalculater.Services.model.CurrencyResponseModel;
import io.vertx.core.Vertx;

public interface CurrencyCalculaterService {
  CurrencyResponseModel calculateCurrency(CurrencyRequestModel requestModel);
  void cacheConversionRates(Vertx vertx);
  Double calculateExchangeRate(String baseCurrency, String targetCurrency);
}
