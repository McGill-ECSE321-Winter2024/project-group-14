package ca.mcgill.ecse321.sportCenterRegistration.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Staff extends Account
{

  protected Staff(){

  }
  public Staff(String aUsername, String aEmail, String aPassword)
  {
    super(aUsername, aEmail, aPassword);
  }


}