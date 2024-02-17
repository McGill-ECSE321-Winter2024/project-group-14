package ca.mcgill.ecse321.sportCenterRegistration.model;

import jakarta.persistence.Entity;

@Entity
public class Customer extends Account
{

  @SuppressWarnings("unused")
  private Customer(){}
  public Customer(String aUsername, String aEmail, String aPassword)
  {
    super(aUsername, aEmail, aPassword);
  }


}