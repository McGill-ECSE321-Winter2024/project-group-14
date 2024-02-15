/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/
package ca.mcgill.ecse321.sportCenterRegistration.model;

import java.sql.Time;
import java.sql.Date;

import jakarta.persistence.*;
// line 31 "model.ump"
// line 93 "model.ump"
@Entity
public class Shift
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Shift Attributes
  @Id
  @GeneratedValue
  private int id;
  private Time startTime;
  private Time endTime;
  private Date date;

  //Shift Associations
  private Staff staff;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  @SuppressWarnings("unused")
  private Shift(){}
  public Shift(Time aStartTime, Time aEndTime, Date aDate, Staff aStaff)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    date = aDate;
    if (!setStaff(aStaff))
    {
      throw new RuntimeException("Unable to create Shift due to aStaff. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }

  public Date getDate()
  {
    return date;
  }
  /* Code from template association_GetOne */
  public Staff getStaff()
  {
    return staff;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setStaff(Staff aNewStaff)
  {
    boolean wasSet = false;
    if (aNewStaff != null)
    {
      staff = aNewStaff;
      wasSet = true;
    }
    return wasSet;
  }

//  public void delete()
//  {
//    staff = null;
//  }


//  public String toString()
//  {
//    return super.toString() + "["+
//            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
//            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
//            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
//            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
//            "  " + "staff = "+(getStaff()!=null?Integer.toHexString(System.identityHashCode(getStaff())):"null");
//  }
}