package com.abakmaz.CurrencyCalculater.Services.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CurrencyRequestModel {

  private long amount;
  private String from;
  private String to;
}
