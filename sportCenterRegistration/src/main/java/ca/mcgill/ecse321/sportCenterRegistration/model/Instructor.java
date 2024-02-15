/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/
package ca.mcgill.ecse321.sportCenterRegistration.model;


// line 12 "model.ump"
// line 112 "model.ump"
@Entity
public class Instructor extends Staff
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------
  @SuppressWarnings("unused")
  private  Instructor(){}
  public Instructor(String aUsername, String aEmail, String aPassword)
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