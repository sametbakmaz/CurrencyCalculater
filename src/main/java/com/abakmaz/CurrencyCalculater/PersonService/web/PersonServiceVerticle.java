package com.abakmaz.CurrencyCalculater.PersonService.web;

import com.abakmaz.CurrencyCalculater.PersonService.model.PersonEntity;
import com.abakmaz.CurrencyCalculater.PersonService.service.PersonService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PersonServiceVerticle extends AbstractVerticle {

  private final PersonService personService;

  @Override
  public void start() {
    Router router = Router.router(vertx);

    // POST /addPerson
    router.post("/addPerson/:firstName/:lastName").handler(this::handleAddPerson);

    // PUT /updatePerson/:id
    router.put( "/updatePerson/:id/:firstName/:lastName").handler(this::handleUpdatePerson);

    // DELETE /deletePerson/:id
    router.delete("/deletePerson/:id").handler(this::handleDeletePerson);

    // GET /getPerson/:id
    router.get( "/getPerson/:id").handler(this::handleGetPersonById);

    // GET /getAllPersons
    router.get( "/getAllPersons").handler(this::handleGetAllPersons);

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(7000);
  }


  private void handleAddPerson(RoutingContext routingContext) {
    try {
      String firstName = routingContext.request().getParam("firstName");
      String lastName = routingContext.request().getParam("lastName");

      if (firstName == null || lastName == null) {
        routingContext.response().setStatusCode(400).end("Missing firstName or lastName in URL");
        return;
      }

      System.out.println(firstName + " " + lastName);
      PersonEntity person = new PersonEntity();
      person.setFirstName(firstName);
      person.setLastName(lastName);

      personService.addPerson(person);

      JsonObject responseJson = new JsonObject()
        .put("message", "Person added successfully");

      routingContext.response()
        .putHeader("content-type", "application/json; charset=utf-8")
        .setStatusCode(200)
        .end(responseJson.encode());
    } catch (Exception e) {
      routingContext.response().setStatusCode(500).end("An error occurred: " + e.getMessage());
    }
  }
  private void handleUpdatePerson(RoutingContext routingContext) {
    try {


      Long id = Long.valueOf(routingContext.request().getParam("id"));
      String firstName = routingContext.request().getParam("firstName");
      String lastName = routingContext.request().getParam("lastName");
      System.out.println(firstName + " " + lastName);
      PersonEntity person = new PersonEntity();
      person.setId(id);
      person.setFirstName(firstName);
      person.setLastName(lastName);

      personService.updatePerson(person);

      routingContext.response().setStatusCode(200).end("Person updated successfully");
    } catch (Exception e) {
      routingContext.response().setStatusCode(500).end("An error occurred: " + e.getMessage());
    }
  }

  private void handleDeletePerson(RoutingContext routingContext) {
    try {
      Long id = Long.parseLong(routingContext.request().getParam("id"));
      personService.deletePerson(id);

      routingContext.response().setStatusCode(200).end("Person deleted successfully");
    } catch (Exception e) {
      routingContext.response().setStatusCode(500).end("An error occurred: " + e.getMessage());
    }
  }

  private void handleGetPersonById(RoutingContext routingContext) {
    try {
      String idString = routingContext.pathParam("id");
      Long id = Long.parseLong(idString);

      PersonEntity person = personService.getPersonById(id);

      if (person != null) {
        JsonObject jsonResponse = new JsonObject()
          .put("id", person.getId())
          .put("firstName", person.getFirstName())
          .put("lastName", person.getLastName());

        routingContext.response()
          .putHeader("content-type", "application/json; charset=utf-8")
          .setStatusCode(200)
          .end(jsonResponse.toString());
      } else {
        routingContext.response().setStatusCode(404).end("Person not found");
      }
    } catch (NumberFormatException e) {
      routingContext.response().setStatusCode(400).end("Invalid id format");
    } catch (Exception e) {
      routingContext.response().setStatusCode(500).end("An error occurred: " + e.getMessage());
    }
  }

  private void handleGetAllPersons(RoutingContext routingContext) {
    try {
      List<PersonEntity> persons = personService.getAllPersons();

      JsonArray jsonArray = new JsonArray();
      for (PersonEntity person : persons) {
        JsonObject jsonPerson = new JsonObject()
          .put("id", person.getId())
          .put("firstName", person.getFirstName())
          .put("lastName", person.getLastName());
        jsonArray.add(jsonPerson);
      }

      routingContext.response()
        .putHeader("content-type", "application/json; charset=utf-8")
        .setStatusCode(200)
        .end(jsonArray.encode());
    } catch (Exception e) {
      routingContext.response().setStatusCode(500).end("An error occurred: " + e.getMessage());
    }
  }
}
