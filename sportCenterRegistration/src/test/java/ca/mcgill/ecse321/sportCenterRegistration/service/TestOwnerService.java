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


@Test
public void testGetExistingOwner() {
    assertEquals(Owner_USERNAME, service.getOwner(Owner_USERNAME).getUsername());
    assertEquals(Owner_EMAIL, service.getOwner(Owner_USERNAME).getEmail());
    assertEquals(Owner_PASSWORD, service.getOwner(Owner_USERNAME).getPassword());
}


@Test
public void testGetNonExistingOwner() {

    assertThrows(IllegalArgumentException.class, () -> {
        service.getOwner(NONEXISTING_Owner_USERNAME);
    });

}

@Test
public void testCreateOwner() {
		assertEquals(0, service.getAllOwners().size());

		String username = "Muhammad";
        String email = "Memail";
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

    @Test
	public void testCreateOwnerNullUsername() {
		String username = null;
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
		
		assertEquals("Username cannot be empty!", error);
	}

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

    @Test
	public void testCreateOwnerNullPassword() {
		String username = "username";
		String email = "email";
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


    @Test
	public void testCreateOwnerEmptyUsername() {
		String username = "";
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
		
		assertEquals("Username cannot be empty!", error);
	}

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


    @Test
	public void testCreateOwnerEmptyPassword() {
		String username = "username";
		String email = "email";
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
    

    //the below test probably isnt working because the projcet isnt compiling properly
    @Test
    public void deleteOwner() {
       // String username = "Muhammad";
       // String email = "Memail";
       // String password = "Mpass";
        assertEquals(Owner_USERNAME,  OwnerDao.findOwnerByUsername(Owner_USERNAME).getUsername());
		service.deleteOwner(Owner_USERNAME);
		assertNull(OwnerDao.findOwnerByUsername(Owner_USERNAME));









    }
}

    
