package ca.mcgill.ecse321.sportCenterRegistration.model;

import jakarta.persistence.Entity;

@Entity
public class Owner extends Staff {

  @SuppressWarnings("unused")
  private String email;
  @SuppressWarnings("unused")
  private String password;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public Owner(String aUsername, String aEmail, String aPassword) {
    super(aUsername, aEmail, aPassword);
    this.email = "admin@sportcenter.com";
    this.password = "12345678999";
  }

  public String toString() {
    return super.toString() + "[" +
        "email" + ":" + getEmail() + "," +
        "password" + ":" + getPassword() + "]";
  }
}
