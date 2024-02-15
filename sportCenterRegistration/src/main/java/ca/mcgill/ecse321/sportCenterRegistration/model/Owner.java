/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/
package ca.mcgill.ecse321.sportCenterRegistration.model;

import jakarta.persistence.*;
// line 19 "model.ump"
// line 83 "model.ump"
@Entity
public class Owner extends Staff
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------
  @SuppressWarnings("unused")
  private Owner(){}
  public Owner(String aUsername, String aEmail, String aPassword)
  {
    super(aUsername, aEmail, aPassword);
  }

  //------------------------
  // INTERFACE
  //------------------------

//  public void delete()
//  {
//    super.delete();
//  }

}