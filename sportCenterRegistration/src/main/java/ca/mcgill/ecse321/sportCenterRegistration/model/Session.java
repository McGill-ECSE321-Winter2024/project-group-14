package ca.mcgill.ecse321.sportCenterRegistration.model;

import java.sql.Time;
import java.sql.Date;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;

@Entity
public class Session
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Session Attributes
  @Id
  @GeneratedValue
  private int id;
  private Time startTime;
  private Time endTime;
  private String location;
  private Date date;

  //Session Associations
  @ManyToOne
  private Instructor instructor;
  @ManyToOne
  private SportClass sportClass;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  @SuppressWarnings("unused")
  private Session(){}
  public Session(Time aStartTime, Time aEndTime, String aLocation, Date aDate, Instructor aInstructor, SportClass aSportClass)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    location = aLocation;
    date = aDate;
    if (!setInstructor(aInstructor))
    {
      throw new RuntimeException("Unable to create Session due to aInstructor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setSportClass(aSportClass))
    {
      throw new RuntimeException("Unable to create Session due to aSportClass. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public boolean setLocation(String aLocation)
  {
    boolean wasSet = false;
    location = aLocation;
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

  public String getLocation()
  {
    return location;
  }

  public Date getDate()
  {
    return date;
  }
  /* Code from template association_GetOne */
  public Instructor getInstructor()
  {
    return instructor;
  }
  /* Code from template association_GetOne */
  public SportClass getSportClass()
  {
    return sportClass;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setInstructor(Instructor aNewInstructor)
  {
    boolean wasSet = false;
    if (aNewInstructor != null)
    {
      instructor = aNewInstructor;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setSportClass(SportClass aNewSportClass)
  {
    boolean wasSet = false;
    if (aNewSportClass != null)
    {
      sportClass = aNewSportClass;
      wasSet = true;
    }
    return wasSet;
  }

}