package com.abakmaz.CurrencyCalculater.Services.service;

import com.abakmaz.CurrencyCalculater.Services.model.PersonEntity;
import com.abakmaz.CurrencyCalculater.Services.repository.PersonRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

  private final PersonRepository personRepository;

  @Override
  public void addPerson(PersonEntity person) {
    personRepository.addPerson(person);
  }

  @Override
  public void updatePerson(PersonEntity person) {
    personRepository.updatePerson(person);
  }

  @Override
  public void deletePerson(Long id) {
    personRepository.deletePerson(id);
  }

  @Override
  public PersonEntity getPersonById(Long id) {
    return personRepository.getPersonById(id);
  }

  @Override
  public List<PersonEntity> getAllPersons() {
    return personRepository.getAllPersons();
  }
}