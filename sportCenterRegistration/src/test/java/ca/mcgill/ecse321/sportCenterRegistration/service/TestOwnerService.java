package ca.mcgill.ecse321.sportCenterRegistration.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.CustomSQLExceptionTranslatorRegistrar;

import ca.mcgill.ecse321.sportCenterRegistration.dao.OwnerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.model.Owner;



@ExtendWith(MockitoExtension.class)
public class TestOwnerService {

@Mock
private OwnerRepository OwnerDao;

@InjectMocks
private OwnerService service;

private static final String Owner_USERNAME = "TestOwnerUsername";
private static final String Owner_EMAIL = "admin@gmail.com";
private static final String Owner_PASSWORD = "admin123";

private static final String NONEXISTING_Owner_USERNAME = "NotAnOwnerUsername";
private static final String NONEXISTING_Owner_EMAIL = "NotAnOwnerEmail";
private static final String NONEXISTING_Owner_PASSWORD = "NotAnOwnerPassword";

@BeforeEach
public void setMockOutput() {
    lenient().when(OwnerDao.findOwnerByUsername(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
        if(invocation.getArgument(0).equals(Owner_USERNAME)) {
            Owner Owner = new Owner(Owner_USERNAME, Owner_EMAIL, Owner_PASSWORD);
            return Owner;
        } else {
            return null;
        }
    });
}

/*
 * @author Muhammad Hammad
 *  Method that tests getting an existing Owner
 * 
 */
@Test
public void testGetExistingOwner() {
    assertEquals(Owner_USERNAME, service.getOwner(Owner_USERNAME).getUsername());
    assertEquals("admin@gmail.com", service.getOwner(Owner_USERNAME).getEmail());
    assertEquals("admin123", service.getOwner(Owner_USERNAME).getPassword());
}

/*
 * @author Muhammad Hammad
 *  Method that tests if there is an error frmo getting a non existing Owner
 * 
 */
@Test
public void testGetNonExistingOwner() {

    assertThrows(IllegalArgumentException.class, () -> {
        service.getOwner(NONEXISTING_Owner_USERNAME);
    });

}
/*
 * @author Muhammad Hammad
 *  Method that tests of creating Owner
 * 
 */
@Test
public void testCreateOwner() {
		assertEquals(0, service.getAllOwners().size());

		String username = "Muhammad";
        String email = "admin@gmail.com";
        String password = "admin123";
		Owner Owner = null;
		try {
			Owner = service.createOwner(username, email, password);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(Owner);
		assertEquals(username, Owner.getUsername());
        assertEquals("admin@gmail.com", Owner.getEmail());
        assertEquals("admin123", Owner.getPassword());
    }
/*
 * @author Muhammad Hammad
 *  Method that tests if there is an error in creating a null username Owner
 * 
 */
    @Test
	public void testCreateOwnerNullUsername() {
		String username = null;
		String email = "email@gmail.com";
		String password = "password";
        Owner Owner = null;
        String error = null;
		try {
            Owner = service.createOwner(username, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(Owner);
		
		assertEquals("Username cannot be empty!", error);
	}
/*
 * @author Muhammad Hammad
 *  Method that tests if there is an error in creating a null email Owner
 * 
 */
    @Test
	public void testCreateOwnerNullEmail() {
		String username = "username";
		String email = null;
		String password = "password";
        Owner Owner = null;
        String error = null;
		try {
            Owner = service.createOwner(username, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(Owner);
		
		assertEquals("Email cannot be empty!", error);
	}
/*
 * @author Muhammad Hammad
 *  Method that tests if there is an error in creating a null password Owner
 * 
 */
    @Test
	public void testCreateOwnerNullPassword() {
		String username = "username";
		String email = "email@gmail.com";
        String password = null;
        Owner Owner = null;
        String error = null;
		try {
            Owner = service.createOwner(username, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(Owner);
		
		assertEquals("Password cannot be empty!", error);
	}
/*
 * @author Muhammad Hammad
 *  Method that tests if there is an error in creating an invalid email Owner
 * 
 */
	@Test
	public void testCreateOwnerInvalidEmail() {
		String username = "username";
		String email = "email";
        String password = "password";
        Owner Owner = null;
        String error = null;
		try {
            Owner = service.createOwner(username, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(Owner);
		
		assertEquals("Email is invalid!", error);
	}

/*
 * @author Muhammad Hammad
 *  Method that tests if there is an error in creating an invalid  username Owner
 * 
 */	@Test
	public void testCreateOwnerInvalidUsername() {
		String username = "username";
		String email = "email";
        String password = "password";
        Owner Owner = null;
        String error = null;
		try {
            Owner = service.createOwner(username, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(Owner);
		
		assertEquals("Email is invalid!", error);
	}

/*
 * @author Muhammad Hammad
 *  Method that tests if there is an error in creating an empty username Owner
 * 
 */
    @Test
	public void testCreateOwnerEmptyUsername() {
		String username = "";
		String email = "email@gmail.com";
		String password = "password";
        Owner Owner = null;
        String error = null;
		try {
            Owner = service.createOwner(username, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(Owner);
		
		assertEquals("Username cannot be empty!", error);
	}
/*
 * @author Muhammad Hammad
 *  Method that tests if there is an error in creating an empty email Owner
 * 
 */
    @Test
	public void testCreateOwnerEmptyEmail() {
		String username = "username";
		String email = "";
		String password = "password";
        Owner Owner = null;
        String error = null;
		try {
            Owner = service.createOwner(username, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(Owner);
		
		assertEquals("Email cannot be empty!", error);
	}

/*
 * @author Muhammad Hammad
 *  Method that tests if there is an error in creating an empty password Owner
 * 
 */
    @Test
	public void testCreateOwnerEmptyPassword() {
		String username = "username";
		String email = "email@gmail.com";
		String password = "";
        Owner Owner = null;
        String error = null;
		try {
            Owner = service.createOwner(username, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(Owner);
		
		assertEquals("Password cannot be empty!", error);
	}
    

/*
 * @author Muhammad Hammad
 *  Method that tests deleting a Owner
 * 
 */    @Test
	public void testDeleteOwner() {
		

		Owner OwnerDelete = null;

		String error = null;
		try {
        	OwnerDelete = service.deleteOwner(Owner_USERNAME);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(Owner_USERNAME, OwnerDelete.getUsername());
		assertEquals(Owner_EMAIL, OwnerDelete.getEmail());
		assertEquals(Owner_PASSWORD, OwnerDelete.getPassword());

	}

/*
 * @author Muhammad Hammad
 *  Method that tests if there is an error in deleting a null username Owner
 * 
 */
	@Test
	public void testDeleteOwnerNullUsername() {
		
		Owner OwnerDelete = null;

		String error = null;
		try {
        	OwnerDelete = service.deleteOwner(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Owner name cannot be empty!", error);

	}
/*
 * @author Muhammad Hammad
 *  Method that tests if there is an error in deleting an invalid username Owner
 * 
 */
	@Test
	public void testDeleteOwnerInvalidUsername() {
		
		Owner OwnerDelete = null;

		String error = null;
		try {
        	OwnerDelete = service.deleteOwner("");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Owner name cannot be empty!", error);

	}
/*
 * @author Muhammad Hammad
 *  Method that tests if there is an error in deleting a non existing username Owner
 * 
 */
	@Test
	public void testDeleteOwnerNonExistingdUsername() {
		
		Owner OwnerDelete = null;

		String error = null;
		try {
        	OwnerDelete = service.deleteOwner("nonexistingusername");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Owner name is invalid", error);

	}
/*
 * @author Muhammad Hammad
 * 
 * The below method tests getting all Owners
 */

	@Test
	public void testGetAllOwners() {
		List<Owner> OwnerList = null;
		String error = null;
		String username = "username";
		String email = "email@gmail.com";
		String password = "password";
		
		try {
			service.createOwner(username, email, password);
        	OwnerList = service.getAllOwners();
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		

	}
	/*
	 * @author Muhammad Hammad
	 * 
	 * checks to see if a Owners information can be updated sucessfully
	 */
	
	@Test
	public void testUpdateOwner() {
		Owner updatedOwner = null;
		String error = null;

		String newUsername = "newUsername";
		String newEmail = "newEmail@gmail.com";
		String newPassword = "newPassword";
		
		try {
        	updatedOwner = service.updateOwner(Owner_USERNAME, newUsername, newEmail, newPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(newUsername, updatedOwner.getUsername());
		assertEquals(newEmail, updatedOwner.getEmail());
		assertEquals(newPassword, updatedOwner.getPassword());

	}

	/*
	 * @author Muhammad Hammad
	 * 
	 * checks to see if an error is thrown when an invalid suername is provided
	 */
	@Test
	public void testUpdateOwnerInvalidUsername() {
		Owner updatedOwner = null;
		String error = null;

		String newUsername = "";
		String newEmail = "newEmail@gmail.com";
		String newPassword = "newPassword";
		
		try {
        	updatedOwner = service.updateOwner(Owner_USERNAME, newUsername, newEmail, newPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Username cannot be empty!");

 	 }


	 	/*
	 * @author Muhammad Hammad
	 * 
	 * checks to see if an error is thrown when an invalid suername is provided
	 */
	@Test
	public void testUpdateOwnerInvalidUsernameNull() {
		Owner updatedOwner = null;
		String error = null;

		String newUsername = null;
		String newEmail = "newEmail@gmail.com";
		String newPassword = "newPassword";
		
		try {
        	updatedOwner = service.updateOwner(Owner_USERNAME, newUsername, newEmail, newPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Username cannot be empty!");

 	 }



	 	/*
	 * @author Muhammad Hammad
	 * 
	 * checks to see if an error is thrown when an invalid email is provided
	 */
	@Test
	public void testUpdateOwnerInvalidEmail() {
		Owner updatedOwner = null;
		String error = null;

		String newUsername = "username";
		String newEmail = "";
		String newPassword = "newPassword";
		
		try {
        	updatedOwner = service.updateOwner(Owner_USERNAME, newUsername, newEmail, newPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Email cannot be empty!");

 	 }





	 	/*
	 * @author Muhammad Hammad
	 * 
	 * checks to see if an error is thrown when an invalid suername is provided
	 */
	@Test
	public void testUpdateOwnerInvalidEmailNull() {
		Owner updatedOwner = null;
		String error = null;

		String newUsername = "username";
		String newEmail = null;
		String newPassword = "newPassword";
		
		try {
        	updatedOwner = service.updateOwner(Owner_USERNAME, newUsername, newEmail, newPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Email cannot be empty!");

 	 }
	 	 	/*
	 * @author Muhammad Hammad
	 * 
	 * checks to see if an error is thrown when an invalid suername is provided
	 */
	@Test
	public void testUpdateOwnerInvalidEmailFromat() {
		Owner updatedOwner = null;
		String error = null;

		String newUsername = "username";
		String newEmail = "notAValidEmail";
		String newPassword = "newPassword";
		
		try {
        	updatedOwner = service.updateOwner(Owner_USERNAME, newUsername, newEmail, newPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Email is invalid!");

 	 }
	 	/*
	 * @author Muhammad Hammad
	 * 
	 * checks to see if an error is thrown when an invalid password is provided
	 */
	@Test
	public void testUpdateOwnerInvalidPassword() {
		Owner updatedOwner = null;
		String error = null;

		String newUsername = "username";
		String newEmail = "newEmail@gmail.com";
		String newPassword = "";
		
		try {
        	updatedOwner = service.updateOwner(Owner_USERNAME, newUsername, newEmail, newPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Password cannot be empty!");

 	 }
	 	/*
	 * @author Muhammad Hammad
	 * 
	 * checks to see if an error is thrown when an invalid suername is provided
	 */
	@Test
	public void testUpdateOwnerInvalidPasswordNull() {
		Owner updatedOwner = null;
		String error = null;

		String newUsername = "username";
		String newEmail = "newEmail@gmail.com";
		String newPassword = null;
		
		try {
        	updatedOwner = service.updateOwner(Owner_USERNAME, newUsername, newEmail, newPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Password cannot be empty!");

 	 }
	 	 	/*
	 * @author Muhammad Hammad
	 * 
	 * checks to see if an error is thrown when a non unique username is provided
	 */
	@Test
	public void testUpdateOwnerNonUniqueUsername() {
		Owner updatedOwner = null;
		String error = null;

		String newEmail = "email@gmail.com";
		String newPassword = "newPassword";
		
		try {
        	updatedOwner = service.updateOwner(Owner_USERNAME, Owner_USERNAME, newEmail, newPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Username is not unique!");

 	 }

	/*
	 * @author Muhammad Hammad
	 * 
	 * checks to see if an error is thrown when an invalid username is provided
	 */
	
	  @Test
	  public void testOwnerLoginINvalidUsernameOrEmail() {
		  String error = null;
		  Owner OwnerLogin = null;
		  
		  try {
			  OwnerLogin = service.OwnerLogin(NONEXISTING_Owner_USERNAME, Owner_PASSWORD);
		  } catch (IllegalArgumentException e) {
			  error = e.getMessage();
		  }
		  
		  assertEquals(error, "Either the username or password is invalid!");
  
	  }
/*
	 * @author Muhammad Hammad
	 * 
	 * checks to see if an error is thrown when an invalid username or email combination is provided
	 */
	  @Test
	  public void testOwnerLoginInvalidCombination() {
		  String error = null;
		  Owner OwnerLogin = null;
		  
		  try {
			  OwnerLogin = service.OwnerLogin(Owner_USERNAME, NONEXISTING_Owner_PASSWORD);
		  } catch (IllegalArgumentException e) {
			  error = e.getMessage();
		  }
		  
		  assertEquals(error, "Either the username or password is invalid!");
  
	  }


	  /*
	 * @author Muhammad Hammad
	 * 
	 * checks to see if an error is thrown when an invalid username or email is provided
	 */
	@Test
	  public void testOwnerLogin() {
		  String error = null;
		  Owner OwnerLogin = null;
		  
		  try {
			  OwnerLogin = service.OwnerLogin(Owner_USERNAME, Owner_PASSWORD);
		  } catch (IllegalArgumentException e) {
			  error = e.getMessage();
		  }
		  
		  assertEquals(OwnerLogin.getUsername(), Owner_USERNAME);
		  assertEquals(OwnerLogin.getEmail(), Owner_EMAIL);
		  assertEquals(OwnerLogin.getPassword(), Owner_PASSWORD);

  
	  }

	  	/*
	 * @author Muhammad Hammad
	 * 
	 * Method tests to see if it correctly identitfies a valid
	 */
	@Test
	public void testEmailIsValid() {
	  String error = null;
	  Boolean response = null;
		try {
			response = service.emailIsValid(Owner_EMAIL);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
	}
	assertEquals(response, true);

  }

	/*
   * @author Muhammad Hammad
   * 
   * Method tests to see if it correctly identitfies a unique username
   */
  @Test
  public void testUniqueUsernam() {
	String error = null;
	Boolean response = null;
	  try {
		  response = service.usernameIsUnique(Owner_USERNAME);
	  } catch (IllegalArgumentException e) {
		  error = e.getMessage();
	  }
	  
	  assertEquals(response, false);

  


	

   
  }




	 
	}


  

