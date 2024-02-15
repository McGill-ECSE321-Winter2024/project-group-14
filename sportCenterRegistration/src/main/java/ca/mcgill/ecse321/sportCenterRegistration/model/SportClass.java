/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/
package ca.mcgill.ecse321.sportCenterRegistration.model;


// line 63 "model.ump"
// line 124 "model.ump"
@Entity
public class SportClass
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SportClass Attributes
  @id
  @GeneratedValue
  private int id;
  private String name;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  @SuppressWarnings("unused")
  private SportClass(){}
  public SportClass(String aName)
  {
    name = aName;
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

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

//  public void delete()
//  {}


//  public String toString()
//  {
//    return super.toString() + "["+
//            "id" + ":" + getId()+ "," +
//            "name" + ":" + getName()+ "]";
//  }
}