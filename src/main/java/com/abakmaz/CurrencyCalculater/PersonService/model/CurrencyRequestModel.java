package com.abakmaz.CurrencyCalculater.PersonService.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CurrencyRequestModel {
  private String token;
  private long amount;
  private String from;
  private String to;
}
