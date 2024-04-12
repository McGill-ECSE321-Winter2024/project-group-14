package ca.mcgill.ecse321.sportCenterRegistration.model;

import jakarta.persistence.Entity;

@Entity
public class Owner extends Staff {

  // @SuppressWarnings("unused")
  // private String email;
  // @SuppressWarnings("unused")
  // private String password;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  @SuppressWarnings("unused")
  private Owner() {
  }

  public Owner(String aUsername, String aEmail, String aPassword) {
    super(aUsername, aEmail, aPassword);
  }

  public String toString() {
    return super.toString() + "[" +
        "email" + ":" + getEmail() + "," +
        "password" + ":" + getPassword() + "]";
  }
}
