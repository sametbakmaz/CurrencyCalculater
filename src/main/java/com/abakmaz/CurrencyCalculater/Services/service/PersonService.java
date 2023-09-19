package com.abakmaz.CurrencyCalculater.Services.service;

import com.abakmaz.CurrencyCalculater.Services.model.PersonEntity;

import java.util.List;

public interface PersonService {
  void addPerson(PersonEntity person);

  void updatePerson(PersonEntity person);

  void deletePerson(Long id);

  PersonEntity getPersonById(Long id);

  List<PersonEntity> getAllPersons();
}
