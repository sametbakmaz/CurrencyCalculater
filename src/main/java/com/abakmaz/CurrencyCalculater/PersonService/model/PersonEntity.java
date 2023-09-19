package com.abakmaz.CurrencyCalculater.PersonService.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "person")
@Data
public class PersonEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;
}
