package com.abakmaz.CurrencyCalculater.Services.repository;

import com.abakmaz.CurrencyCalculater.Services.model.PersonEntity;

import java.util.List;

public interface PersonRepository {

  void addPerson(PersonEntity person);

  void updatePerson(PersonEntity person);

  void deletePerson(Long id);

  PersonEntity getPersonById(Long id);

  List<PersonEntity> getAllPersons();
}
