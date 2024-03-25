package ca.mcgill.ecse321.sportCenterRegistration.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.sportCenterRegistration.dao.InstructorRepository;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;

@ExtendWith(MockitoExtension.class)
public class TestInstructorService {

	@Mock
	private InstructorRepository InstructorDao;

	@InjectMocks
	private InstructorService service;

	private static final String Instructor_USERNAME = "TestInstructorUsername";
	private static final String Instructor_EMAIL = "TestInstructorEmail@gmail.com";
	private static final String Instructor_PASSWORD = "TestInstructorPassword";

	private static final String NONEXISTING_Instructor_USERNAME = "NotAnInstructorUsername";
	private static final String NONEXISTING_Instructor_EMAIL = "NotAnInstructorEmail";
	private static final String NONEXISTING_Instructor_PASSWORD = "NotAnInstructorPassword";

	@BeforeEach
	public void setMockOutput() {
		lenient().when(InstructorDao.findInstructorByUsername(anyString()))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(Instructor_USERNAME)) {
						Instructor Instructor = new Instructor(Instructor_USERNAME, Instructor_EMAIL,
								Instructor_PASSWORD);
						return Instructor;
					} else {
						return null;
					}
				});
	}

	/*
	 * @author Muhammad Hammad
	 * Method that tests getting an existing Instructor
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
	 * Method that tests if there is an error frmo getting a non existing Instructor
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
	 * Method that tests of creating Instructor
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
	 * Method that tests if there is an error in creating a null username Instructor
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
	 * Method that tests if there is an error in creating a null email Instructor
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
	 * Method that tests if there is an error in creating a null password Instructor
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
	 * Method that tests if there is an error in creating an invalid email
	 * Instructor
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
	 * Method that tests if there is an error in creating an invalid username
	 * Instructor
	 * 
	 */ @Test
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
	 * Method that tests if there is an error in creating an empty username
	 * Instructor
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
	 * Method that tests if there is an error in creating an empty email Instructor
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
	 * Method that tests if there is an error in creating an empty password
	 * Instructor
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
	 * Method that tests deleting a Instructor
	 * 
	 */ @Test
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
	 * Method that tests if there is an error in deleting a null username Instructor
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

		assertEquals("Instructor name cannot be empty!", error);

	}

	/*
	 * @author Muhammad Hammad
	 * Method that tests if there is an error in deleting an invalid username
	 * Instructor
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

		assertEquals("Instructor name cannot be empty!", error);

	}

	/*
	 * @author Muhammad Hammad
	 * Method that tests if there is an error in deleting a non existing username
	 * Instructor
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
	/*
	 * @author Muhammad Hammad
	 * 
	 * The below method tests getting all Instructors
	 */

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

	}
	/*
	 * @author Muhammad Hammad
	 * 
	 * checks to see if a Instructors information can be updated sucessfully
	 */

	@Test
	public void testUpdateInstructor() {
		Instructor updatedInstructor = null;
		String error = null;

		String newUsername = "newUsername";
		String newEmail = "newEmail@gmail.com";
		String newPassword = "newPassword";

		try {
			updatedInstructor = service.updateInstructor(Instructor_USERNAME, newUsername, newEmail, newPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(newUsername, updatedInstructor.getUsername());
		assertEquals(newEmail, updatedInstructor.getEmail());
		assertEquals(newPassword, updatedInstructor.getPassword());

	}

	/*
	 * @author Muhammad Hammad
	 * 
	 * checks to see if an error is thrown when an invalid suername is provided
	 */
	@Test
	public void testUpdateInstructorInvalidUsername() {
		Instructor updatedInstructor = null;
		String error = null;

		String newUsername = "";
		String newEmail = "newEmail@gmail.com";
		String newPassword = "newPassword";

		try {
			updatedInstructor = service.updateInstructor(Instructor_USERNAME, newUsername, newEmail, newPassword);
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
	public void testUpdateInstructorInvalidUsernameNull() {
		Instructor updatedInstructor = null;
		String error = null;

		String newUsername = null;
		String newEmail = "newEmail@gmail.com";
		String newPassword = "newPassword";

		try {
			updatedInstructor = service.updateInstructor(Instructor_USERNAME, newUsername, newEmail, newPassword);
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
	public void testUpdateInstructorInvalidEmail() {
		Instructor updatedInstructor = null;
		String error = null;

		String newUsername = "username";
		String newEmail = "";
		String newPassword = "newPassword";

		try {
			updatedInstructor = service.updateInstructor(Instructor_USERNAME, newUsername, newEmail, newPassword);
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
	public void testUpdateInstructorInvalidEmailNull() {
		Instructor updatedInstructor = null;
		String error = null;

		String newUsername = "username";
		String newEmail = null;
		String newPassword = "newPassword";

		try {
			updatedInstructor = service.updateInstructor(Instructor_USERNAME, newUsername, newEmail, newPassword);
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
	public void testUpdateInstructorInvalidEmailFromat() {
		Instructor updatedInstructor = null;
		String error = null;

		String newUsername = "username";
		String newEmail = "notAValidEmail";
		String newPassword = "newPassword";

		try {
			updatedInstructor = service.updateInstructor(Instructor_USERNAME, newUsername, newEmail, newPassword);
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
	public void testUpdateInstructorInvalidPassword() {
		Instructor updatedInstructor = null;
		String error = null;

		String newUsername = "username";
		String newEmail = "newEmail@gmail.com";
		String newPassword = "";

		try {
			updatedInstructor = service.updateInstructor(Instructor_USERNAME, newUsername, newEmail, newPassword);
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
	public void testUpdateInstructorInvalidPasswordNull() {
		Instructor updatedInstructor = null;
		String error = null;

		String newUsername = "username";
		String newEmail = "newEmail@gmail.com";
		String newPassword = null;

		try {
			updatedInstructor = service.updateInstructor(Instructor_USERNAME, newUsername, newEmail, newPassword);
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
	public void testUpdateInstructorNonUniqueUsername() {
		Instructor updatedInstructor = null;
		String error = null;

		String newEmail = "email@gmail.com";
		String newPassword = "newPassword";

		try {
			updatedInstructor = service.updateInstructor(Instructor_USERNAME, Instructor_USERNAME, newEmail,
					newPassword);
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
	public void testInstructorLoginINvalidUsernameOrEmail() {
		String error = null;
		Instructor InstructorLogin = null;

		try {
			InstructorLogin = service.InstructorLogin(NONEXISTING_Instructor_USERNAME, Instructor_PASSWORD);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(error, "Either the username or password is invalid!");

	}

	/*
	 * @author Muhammad Hammad
	 * 
	 * checks to see if an error is thrown when an invalid username or email
	 * combination is provided
	 */
	@Test
	public void testInstructorLoginInvalidCombination() {
		String error = null;
		Instructor InstructorLogin = null;

		try {
			InstructorLogin = service.InstructorLogin(Instructor_USERNAME, NONEXISTING_Instructor_PASSWORD);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(error, "Either the username or password is invalid!");

	}

	/*
	 * @author Muhammad Hammad
	 * 
	 * checks to see if an error is thrown when an invalid username or email is
	 * provided
	 */
	@Test
	public void testInstructorLogin() {
		String error = null;
		Instructor InstructorLogin = null;

		try {
			InstructorLogin = service.InstructorLogin(Instructor_USERNAME, Instructor_PASSWORD);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(InstructorLogin.getUsername(), Instructor_USERNAME);
		assertEquals(InstructorLogin.getEmail(), Instructor_EMAIL);
		assertEquals(InstructorLogin.getPassword(), Instructor_PASSWORD);

	}

}
