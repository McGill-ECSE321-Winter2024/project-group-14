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

import ca.mcgill.ecse321.sportCenterRegistration.dao.InstructorRepository;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;



@ExtendWith(MockitoExtension.class)
public class TestInstructorService {

@Mock
private InstructorRepository InstructorDao;

@InjectMocks
private InstructorService service;

private static final String Instructor_USERNAME = "TestInstructorUsername";
private static final String Instructor_EMAIL = "TestInstructorEmail";
private static final String Instructor_PASSWORD = "TestInstructorPassword";

private static final String NONEXISTING_Instructor_USERNAME = "NotAnInstructorUsername";
private static final String NONEXISTING_Instructor_EMAIL = "NotAnInstructorEmail";
private static final String NONEXISTING_Instructor_PASSWORD = "NotAnInstructorPassword";

@BeforeEach
public void setMockOutput() {
    lenient().when(InstructorDao.findInstructorByUsername(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
        if(invocation.getArgument(0).equals(Instructor_USERNAME)) {
            Instructor Instructor = new Instructor(Instructor_USERNAME, Instructor_EMAIL, Instructor_PASSWORD);
            return Instructor;
        } else {
            return null;
        }
    });
}

/*
 * @author Muhammad Hammad
 *  Method that tests getting an existing Instructor
 * 
 */
@Test
public void testGetExistingInstructor() {
    assertEquals(Instructor_USERNAME, service.getInstructor(Instructor_USERNAME).getUsername());
    assertEquals(Instructor_EMAIL, service.getInstructor(Instructor_USERNAME).getEmail());
    assertEquals(Instructor_PASSWORD, service.getInstructor(Instructor_USERNAME).getPassword());
}

/*
 * @author Muhammad Hammad
 *  Method that tests if there is an error frmo getting a non existing Instructor
 * 
 */
@Test
public void testGetNonExistingInstructor() {

    assertThrows(IllegalArgumentException.class, () -> {
        service.getInstructor(NONEXISTING_Instructor_USERNAME);
    });

}
/*
 * @author Muhammad Hammad
 *  Method that tests of creating Instructor
 * 
 */
@Test
public void testCreateInstructor() {
		assertEquals(0, service.getAllInstructors().size());

		String username = "Muhammad";
        String email = "Memail@gmail.com";
        String password = "Mpass";
		Instructor Instructor = null;
		try {
			Instructor = service.createInstructor(username, email, password);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(Instructor);
		assertEquals(username, Instructor.getUsername());
        assertEquals(email, Instructor.getEmail());
        assertEquals(password, Instructor.getPassword());
    }
/*
 * @author Muhammad Hammad
 *  Method that tests if there is an error in creating a null username Instructor
 * 
 */
    @Test
	public void testCreateInstructorNullUsername() {
		String username = null;
		String email = "email@gmail.com";
		String password = "password";
        Instructor Instructor = null;
        String error = null;
		try {
            Instructor = service.createInstructor(username, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(Instructor);
		
		assertEquals("Username cannot be empty!", error);
	}
/*
 * @author Muhammad Hammad
 *  Method that tests if there is an error in creating a null email Instructor
 * 
 */
    @Test
	public void testCreateInstructorNullEmail() {
		String username = "username";
		String email = null;
		String password = "password";
        Instructor Instructor = null;
        String error = null;
		try {
            Instructor = service.createInstructor(username, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(Instructor);
		
		assertEquals("Email cannot be empty!", error);
	}
/*
 * @author Muhammad Hammad
 *  Method that tests if there is an error in creating a null password Instructor
 * 
 */
    @Test
	public void testCreateInstructorNullPassword() {
		String username = "username";
		String email = "email@gmail.com";
        String password = null;
        Instructor Instructor = null;
        String error = null;
		try {
            Instructor = service.createInstructor(username, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(Instructor);
		
		assertEquals("Password cannot be empty!", error);
	}
/*
 * @author Muhammad Hammad
 *  Method that tests if there is an error in creating an invalid email Instructor
 * 
 */
	@Test
	public void testCreateInstructorInvalidEmail() {
		String username = "username";
		String email = "email";
        String password = "password";
        Instructor Instructor = null;
        String error = null;
		try {
            Instructor = service.createInstructor(username, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(Instructor);
		
		assertEquals("Email is invalid!", error);
	}

/*
 * @author Muhammad Hammad
 *  Method that tests if there is an error in creating an invalid  username Instructor
 * 
 */	@Test
	public void testCreateInstructorInvalidUsername() {
		String username = "username";
		String email = "email";
        String password = "password";
        Instructor Instructor = null;
        String error = null;
		try {
            Instructor = service.createInstructor(username, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(Instructor);
		
		assertEquals("Email is invalid!", error);
	}

/*
 * @author Muhammad Hammad
 *  Method that tests if there is an error in creating an empty username Instructor
 * 
 */
    @Test
	public void testCreateInstructorEmptyUsername() {
		String username = "";
		String email = "email@gmail.com";
		String password = "password";
        Instructor Instructor = null;
        String error = null;
		try {
            Instructor = service.createInstructor(username, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(Instructor);
		
		assertEquals("Username cannot be empty!", error);
	}
/*
 * @author Muhammad Hammad
 *  Method that tests if there is an error in creating an empty email Instructor
 * 
 */
    @Test
	public void testCreateInstructorEmptyEmail() {
		String username = "username";
		String email = "";
		String password = "password";
        Instructor Instructor = null;
        String error = null;
		try {
            Instructor = service.createInstructor(username, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(Instructor);
		
		assertEquals("Email cannot be empty!", error);
	}

/*
 * @author Muhammad Hammad
 *  Method that tests if there is an error in creating an empty password Instructor
 * 
 */
    @Test
	public void testCreateInstructorEmptyPassword() {
		String username = "username";
		String email = "email@gmail.com";
		String password = "";
        Instructor Instructor = null;
        String error = null;
		try {
            Instructor = service.createInstructor(username, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(Instructor);
		
		assertEquals("Password cannot be empty!", error);
	}
    

/*
 * @author Muhammad Hammad
 *  Method that tests deleting a Instructor
 * 
 */    @Test
	public void testDeleteInstructor() {
		

		Instructor InstructorDelete = null;

		String error = null;
		try {
        	InstructorDelete = service.deleteInstructor(Instructor_USERNAME);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(Instructor_USERNAME, InstructorDelete.getUsername());
		assertEquals(Instructor_EMAIL, InstructorDelete.getEmail());
		assertEquals(Instructor_PASSWORD, InstructorDelete.getPassword());

	}

/*
 * @author Muhammad Hammad
 *  Method that tests if there is an error in deleting a null username Instructor
 * 
 */
	@Test
	public void testDeleteInstructorNullUsername() {
		
		Instructor InstructorDelete = null;

		String error = null;
		try {
        	InstructorDelete = service.deleteInstructor(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Username cannot be empty!", error);

	}
/*
 * @author Muhammad Hammad
 *  Method that tests if there is an error in deleting an invalid username Instructor
 * 
 */
	@Test
	public void testDeleteInstructorInvalidUsername() {
		
		Instructor InstructorDelete = null;

		String error = null;
		try {
        	InstructorDelete = service.deleteInstructor("");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Username cannot be empty!", error);

	}
/*
 * @author Muhammad Hammad
 *  Method that tests if there is an error in deleting a non existing username Instructor
 * 
 */
	@Test
	public void testDeleteInstructorNonExistingdUsername() {
		
		Instructor InstructorDelete = null;

		String error = null;
		try {
        	InstructorDelete = service.deleteInstructor("nonexistingusername");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Instructor name is invalid", error);

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
        	List<Character> InstructorList = service.toList(charList);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

	}
//the below isnt working
	@Test
	public void testGetAllInstructors() {
		List<Instructor> InstructorList = null;
		String error = null;
		String username = "username";
		String email = "email@gmail.com";
		String password = "password";
		
		try {
			service.createInstructor(username, email, password);
        	InstructorList = service.getAllInstructors();
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(Instructor_USERNAME, InstructorList.get(0).getUsername());
		assertEquals(Instructor_EMAIL, InstructorList.get(0).getEmail());
		assertEquals(Instructor_PASSWORD, InstructorList.get(0).getPassword());

	}



  }

