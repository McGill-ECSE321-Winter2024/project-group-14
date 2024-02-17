package ca.mcgill.ecse321.sportCenterRegistration.model;

import jakarta.persistence.Entity;
@Entity
public class Instructor extends Staff
{
  @SuppressWarnings("unused")
  private  Instructor(){}
  public Instructor(String aUsername, String aEmail, String aPassword)
  {
    super(aUsername, aEmail, aPassword);
  }

}