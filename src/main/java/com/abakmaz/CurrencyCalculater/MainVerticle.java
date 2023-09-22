package com.abakmaz.CurrencyCalculater;

import com.abakmaz.CurrencyCalculater.Services.service.CurrencyCalculaterService; // Yeni eklenen satır
import com.abakmaz.CurrencyCalculater.Services.service.CurrencyCalculaterServiceImpl; // Yeni eklenen satır
import com.abakmaz.CurrencyCalculater.Services.web.CurrencyCalculaterVerticle; // Yeni eklenen satır
import io.vertx.core.AbstractVerticle;


public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(){

    // Yeni eklenen CurrencyCalculator ile ilgili yapıları ekliyoruz
    CurrencyCalculaterService currencyService = new CurrencyCalculaterServiceImpl();
    CurrencyCalculaterVerticle currencyVerticle = new CurrencyCalculaterVerticle(currencyService);

    vertx.deployVerticle(currencyVerticle);
  }
}
