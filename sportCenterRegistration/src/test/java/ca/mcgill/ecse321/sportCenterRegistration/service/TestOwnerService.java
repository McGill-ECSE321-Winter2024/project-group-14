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

import ca.mcgill.ecse321.sportCenterRegistration.dao.OwnerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.model.Owner;



@ExtendWith(MockitoExtension.class)
public class TestOwnerService {

@Mock
private OwnerRepository OwnerDao;

@InjectMocks
private OwnerService service;

private static final String Owner_USERNAME = "TestOwnerUsername";
private static final String Owner_EMAIL = "TestOwnerEmail";
private static final String Owner_PASSWORD = "TestOwnerPassword";

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
    assertEquals(Owner_EMAIL, service.getOwner(Owner_USERNAME).getEmail());
    assertEquals(Owner_PASSWORD, service.getOwner(Owner_USERNAME).getPassword());
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
        String email = "Memail@gmail.com";
        String password = "Mpass";
		Owner Owner = null;
		try {
			Owner = service.createOwner(username, email, password);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(Owner);
		assertEquals(username, Owner.getUsername());
        assertEquals(email, Owner.getEmail());
        assertEquals(password, Owner.getPassword());
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
		
		assertEquals("Username cannot be empty!", error);

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
		
		assertEquals("Username cannot be empty!", error);

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
	//the below isnt working
	@Test
	public void toList() {
		String myString = "iterable";
		List<Character> charList = new ArrayList<Character>();
		String error = null;


		for (char c: myString.toCharArray()){
			charList.add(c);
		}
		
		try {
        	List<Character> OwnerList = service.toList(charList);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

	}
//the below isnt working
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
		
		assertEquals(Owner_USERNAME, OwnerList.get(0).getUsername());
		assertEquals(Owner_EMAIL, OwnerList.get(0).getEmail());
		assertEquals(Owner_PASSWORD, OwnerList.get(0).getPassword());

	}



  }

