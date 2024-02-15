/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/
package ca.mcgill.ecse321.sportCenterRegistration.model;


// line 25 "model.ump"
// line 88 "model.ump"
@Entity
public class Customer extends Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------
  @SuppressWarnings("unused")
  private Customer(){}
  public Customer(String aUsername, String aEmail, String aPassword)
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