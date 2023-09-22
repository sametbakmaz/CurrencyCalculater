package com.abakmaz.CurrencyCalculater.Services.web;

import com.abakmaz.CurrencyCalculater.Services.model.CurrencyRequestModel;
import com.abakmaz.CurrencyCalculater.Services.model.CurrencyResponseModel;
import com.abakmaz.CurrencyCalculater.Services.service.CurrencyCalculaterService;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.core.json.JsonObject;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CurrencyCalculaterVerticle extends AbstractVerticle {

  private final CurrencyCalculaterService currencyService;



  @Override
  public void start() {
    Router router = Router.router(vertx);

    router.get("/currencyCalculate/:amount/:from/:to").handler(this::handleGetCurrency);

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(7010);
    currencyService.cacheConversionRates(vertx);
  }

  private void handleGetCurrency(RoutingContext routingContext) {
    CurrencyRequestModel currencyRequestModel = new CurrencyRequestModel();
    currencyRequestModel.setAmount(Long.parseLong(routingContext.request().getParam("amount")));
    currencyRequestModel.setFrom(routingContext.request().getParam("from"));
    currencyRequestModel.setTo(routingContext.request().getParam("to"));

    try {
      CurrencyResponseModel response = currencyService.calculateCurrency(currencyRequestModel);

      JsonObject jsonResponse = new JsonObject()
        .put("result", response.getResult())
        .put("resultCurrency", response.getResultCurrency());

      routingContext.response()
        .putHeader("content-type", "application/json; charset=utf-8")
        .setStatusCode(200)
        .end(jsonResponse.encode());
    } catch (RuntimeException e) {
      routingContext.response().setStatusCode(500).end("An error occurred: " + e.getMessage());
    }
  }
}

