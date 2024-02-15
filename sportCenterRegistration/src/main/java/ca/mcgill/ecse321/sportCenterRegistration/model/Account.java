/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/
package ca.mcgill.ecse321.sportCenterRegistration.model;

//import jakarta.persistence.Entity;
//import jakarta.persistence.generatedValue;
//import jakarta.persistence.Id;
//import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.*;

// line 2 "model.ump"
// line 75 "model.ump"
@MappedSuperclass
public abstract class Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Account Attributes
  @Id
  @GeneratedValue
  private int id;
  private String username;
  private String email;
  private String password;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  @SuppressWarnings("unused")
  private Account(){}
  public Account(String aUsername, String aEmail, String aPassword)
  {
    username = aUsername;
    email = aEmail;
    password = aPassword;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    username = aUsername;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public String getUsername()
  {
    return username;
  }

  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }

//  public void delete()
//  {}


//  public String toString()
//  {
//    return super.toString() + "["+
//            "id" + ":" + getId()+ "," +
//            "username" + ":" + getUsername()+ "," +
//            "email" + ":" + getEmail()+ "," +
//            "password" + ":" + getPassword()+ "]";
//  }
}