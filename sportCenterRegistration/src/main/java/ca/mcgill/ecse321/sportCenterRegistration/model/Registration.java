/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/
package ca.mcgill.ecse321.sportCenterRegistration.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;

@Entity
public class Registration
{


  @Id
  @GeneratedValue
  //Registration Attributes
  private int id;
  private Date date;

  //Registration Associations
  @ManyToOne
  private Account account;
  @ManyToOne
  private Session session;


  @SuppressWarnings("unused")
  private Registration(){}
  public Registration(Date aDate, Account aAccount, Session aSession)
  {
    date = aDate;
    if (!setAccount(aAccount))
    {
      throw new RuntimeException("Unable to create Registration due to aAccount. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setSession(aSession))
    {
      throw new RuntimeException("Unable to create Registration due to aSession. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }


  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
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

  public Date getDate()
  {
    return date;
  }
  /* Code from template association_GetOne */
  public Account getAccount()
  {
    return account;
  }
  /* Code from template association_GetOne */
  public Session getSession()
  {
    return session;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setAccount(Account aNewAccount)
  {
    boolean wasSet = false;
    if (aNewAccount != null)
    {
      account = aNewAccount;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setSession(Session aNewSession)
  {
    boolean wasSet = false;
    if (aNewSession != null)
    {
      session = aNewSession;
      wasSet = true;
    }
    return wasSet;
  }


}