package com.abakmaz.CurrencyCalculater.Services.repository;

import com.abakmaz.CurrencyCalculater.Services.model.PersonEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository {

  private final SessionFactory sessionFactory;

  @Override
  public void addPerson(PersonEntity person) {
    Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();
    session.save(person);
    transaction.commit();
    session.close();
  }

  @Override
  public void updatePerson(PersonEntity person) {
    Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();
    session.update(person);
    transaction.commit();
    session.close();
  }

  @Override
  public void deletePerson(Long id) {
    Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();
    PersonEntity person = session.get(PersonEntity.class, id);
    session.delete(person);
    transaction.commit();
    session.close();
  }

  @Override
  public PersonEntity getPersonById(Long id) {
    Session session = sessionFactory.openSession();
    PersonEntity person = session.get(PersonEntity.class, id);
    session.close();
    return person;
  }

  @Override
  public List<PersonEntity> getAllPersons() {
    Session session = sessionFactory.openSession();
    List<PersonEntity> personList = session.createQuery("FROM PersonEntity", PersonEntity.class).list();
    session.close();
    return personList;
  }
}
