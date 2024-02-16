package ca.mcgill.ecse321.sportCenterRegistration.model;

import java.sql.Time;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
@Entity
public class Shift
{

  //Shift Attributes
  @Id
  @GeneratedValue
  private int id;
  private Time startTime;
  private Time endTime;
  private Date date;

  //Shift Associations
  @ManyToOne
  private Staff staff;


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

}