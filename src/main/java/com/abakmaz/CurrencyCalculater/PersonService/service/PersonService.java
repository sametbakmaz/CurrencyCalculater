package com.abakmaz.CurrencyCalculater.PersonService.service;

import com.abakmaz.CurrencyCalculater.PersonService.model.PersonEntity;

import java.util.List;

public interface PersonService {
  void addPerson(PersonEntity person);

  void updatePerson(PersonEntity person);

  void deletePerson(Long id);

  PersonEntity getPersonById(Long id);

  List<PersonEntity> getAllPersons();
}
