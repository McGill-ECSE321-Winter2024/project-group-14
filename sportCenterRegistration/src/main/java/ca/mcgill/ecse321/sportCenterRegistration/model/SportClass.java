/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/
package ca.mcgill.ecse321.sportCenterRegistration.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;

@Entity
public class SportClass
{

  //SportClass Attributes
  @Id
  @GeneratedValue
  private int id;
  private String name;
  private boolean approved;


  @SuppressWarnings("unused")
  private SportClass(){}
  public SportClass(String aName)
  {
    name = aName;
    approved = false;
  }


  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public void setApproved(boolean aApproved){
    approved = aApproved;
  }

  public int getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

  public boolean getApproved()
  {
    return approved;
  }

}