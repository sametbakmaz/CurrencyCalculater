package com.abakmaz.CurrencyCalculater.PersonService.service;

import com.abakmaz.CurrencyCalculater.PersonService.model.CurrencyRequestModel;
import com.abakmaz.CurrencyCalculater.PersonService.model.CurrencyResponseModel;
import io.vertx.core.Vertx;

public interface CurrencyCalculaterService {
  CurrencyResponseModel calculateCurrency(CurrencyRequestModel requestModel);
  void cacheConversionRates(Vertx vertx);
  Double calculateExchangeRate(String baseCurrency, String targetCurrency);
}
