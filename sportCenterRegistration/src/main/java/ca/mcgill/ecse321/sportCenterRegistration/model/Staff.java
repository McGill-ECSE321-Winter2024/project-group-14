/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/
package ca.mcgill.ecse321.sportCenterRegistration.model;

import jakarta.persistence.*;
// line 58 "model.ump"
// line 118 "model.ump"
@MappedSuperclass
public class Staff extends Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------
  @SuppressWarnings("unused")
  private Staff(){}
  public Staff(String aUsername, String aEmail, String aPassword)
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