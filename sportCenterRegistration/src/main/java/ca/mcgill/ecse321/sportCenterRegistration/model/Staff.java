package ca.mcgill.ecse321.sportCenterRegistration.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
// line 58 "model.ump"
// line 118 "model.ump"
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Staff extends Account
{

  protected Staff(){

  }
  public Staff(String aUsername, String aEmail, String aPassword)
  {
    super(aUsername, aEmail, aPassword);
  }


}