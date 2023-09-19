package com.abakmaz.CurrencyCalculater;

import com.abakmaz.CurrencyCalculater.PersonService.repository.PersonRepository;
import com.abakmaz.CurrencyCalculater.PersonService.repository.PersonRepositoryImpl;
import com.abakmaz.CurrencyCalculater.PersonService.service.PersonService;
import com.abakmaz.CurrencyCalculater.PersonService.service.PersonServiceImpl;
import com.abakmaz.CurrencyCalculater.PersonService.web.PersonServiceVerticle;
import com.abakmaz.CurrencyCalculater.PersonService.service.CurrencyCalculaterService; // Yeni eklenen satır
import com.abakmaz.CurrencyCalculater.PersonService.service.CurrencyCalculaterServiceImpl; // Yeni eklenen satır
import com.abakmaz.CurrencyCalculater.PersonService.web.CurrencyCalculaterVerticle; // Yeni eklenen satır
import io.vertx.core.AbstractVerticle;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(){
    Configuration configuration = new Configuration();
    configuration.configure();

    // Your Hibernate configurations here (if any)

    SessionFactory sessionFactory = configuration.buildSessionFactory();
    PersonRepository personRepository = new PersonRepositoryImpl(sessionFactory); // PersonRepository'yi oluşturun
    PersonService personService = new PersonServiceImpl(personRepository); // PersonService'e geçirin

    PersonServiceVerticle personServiceVerticle = new PersonServiceVerticle(personService);

    vertx.deployVerticle(personServiceVerticle);

    // Yeni eklenen CurrencyCalculator ile ilgili yapıları ekliyoruz
    CurrencyCalculaterService currencyService = new CurrencyCalculaterServiceImpl();
    CurrencyCalculaterVerticle currencyVerticle = new CurrencyCalculaterVerticle(currencyService);

    vertx.deployVerticle(currencyVerticle);
  }
}
