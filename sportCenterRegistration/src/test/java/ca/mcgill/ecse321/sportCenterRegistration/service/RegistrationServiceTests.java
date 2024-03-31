package ca.mcgill.ecse321.sportCenterRegistration.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.sportCenterRegistration.dao.AccountRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.CustomerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.InstructorRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.SessionRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.SportClassRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.StaffRepository;
import ca.mcgill.ecse321.sportCenterRegistration.model.Account;
import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.Registration;
import ca.mcgill.ecse321.sportCenterRegistration.model.Session;
import ca.mcgill.ecse321.sportCenterRegistration.model.SportClass;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTests {

	@Mock
	private RegistrationRepository registrationRepository;
	// making them mock so i can save to them without actually saving to the repos
	@Mock
	private AccountRepository accountRepository;
	@Mock
	private SportClassRepository sportClassRepository;
	@Mock
	private InstructorRepository instructorRepository;
	@Mock
	private SessionRepository sessionRepository;
	@Mock
	private CustomerRepository customerRepository;
	@Mock
	private StaffRepository staffRepository;

	@InjectMocks
	private RegistrationService registrationService;

	private static final String Customer_USERNAME = "TestCustomerUsername";
	private static final String Customer_EMAIL = "TestCustomerEmail";
	private static final String Customer_PASSWORD = "TestCustomerPassword";

	private static final String Instructor_USERNAME = "TestInstructorUsername";
	private static final String Instructor_EMAIL = "TestInstructorEmail";
	private static final String Instructor_PASSWORD = "TestInstructorPassword";

	private static final Date Registration_DATE = new Date(1230580000L);

	private static final Time Session_START = Time.valueOf("13:00:00");
	private static final Time Session_END = Time.valueOf("14:00:00");
	private static final String Session_LOCATION = "TestSessionLocation";
	private static final Date Session_DATE = new Date(1230560800000L);
	private static final String SportClass_NAME = "TestSportclassName";

	private static final String NONEXISTING_Customer_USERNAME = "NotACustomerUsername";
	private static final String NONEXISTING_Customer_EMAIL = "NotACustomerEmail";
	private static final String NONEXISTING_Customer_PASSWORD = "NotACustomerPassword";

	private static final String NONEXISTING_Instructor_USERNAME = "NotAnInstructorUsername";
	private static final String NONEXISTING_Instructor_EMAIL = "NotAnInstructorEmail";
	private static final String NONEXISTING_Instructor_PASSWORD = "NotAnInstructorPassword";

	private static final Time NONEXISTING_Session_START = Time.valueOf("15:30:00");
	private static final String NONEXISTING_SportClass_NAME = "NotASportClassName";

	private static Instructor instructor;

	private static Instructor NONEXISTING_instructor;

	@BeforeEach
	public void setMockOutput() {

		// Mockito.clearAllCaches();
		Mockito.reset(registrationRepository);
		Mockito.reset(accountRepository);
		Mockito.reset(instructorRepository);
		Mockito.reset(staffRepository);
		Mockito.reset(customerRepository);
		Mockito.reset(sessionRepository);
		Mockito.reset(sportClassRepository);

		instructor = new Instructor(Instructor_USERNAME, Instructor_EMAIL, Instructor_PASSWORD);
		NONEXISTING_instructor = new Instructor(NONEXISTING_Instructor_USERNAME, NONEXISTING_Instructor_EMAIL,
				NONEXISTING_Instructor_PASSWORD);

		lenient().when(accountRepository.findAccountByUsername(anyString()))
				.thenAnswer((InvocationOnMock invocation) -> {
					if ((invocation.getArgument(0)).equals(Customer_USERNAME)) {
						Customer customer = new Customer(Customer_USERNAME, Customer_EMAIL, Customer_PASSWORD);
						return customer;
					} else if ((invocation.getArgument(0)).equals(Instructor_USERNAME)) {
						Instructor customer = new Instructor(Instructor_USERNAME, Instructor_EMAIL,
								Instructor_PASSWORD);
						return customer;
					} else {
						return null;
					}
				});

		lenient().when(instructorRepository.findInstructorByUsername(anyString()))
				.thenAnswer((InvocationOnMock invocation) -> {
					if ((invocation.getArgument(0)).equals(Instructor_USERNAME)) {
						Instructor instructor = new Instructor(Instructor_USERNAME, Instructor_EMAIL,
								Instructor_PASSWORD);
						return instructor;
					} else {
						return null;
					}
				});

		lenient().when(sportClassRepository.findSportClassByName(anyString()))
				.thenAnswer((InvocationOnMock invocation) -> {
					if ((invocation.getArgument(0)).equals(SportClass_NAME)) {
						SportClass sportClass = new SportClass(SportClass_NAME);
						return sportClass;
					} else {
						return null;
					}
				});

		lenient().when(sessionRepository.findSessionByStartTimeAndInstructorAndSportClass(any(Time.class),
				any(Instructor.class), any(SportClass.class))).thenAnswer((InvocationOnMock invocation) -> {
					if ((invocation.getArgument(0)).equals(Session_START)) {
						Instructor sessionInstructor = instructorRepository
								.findInstructorByUsername(Instructor_USERNAME);
						SportClass sportClass = sportClassRepository.findSportClassByName(SportClass_NAME);
						Session session = new Session(Session_START, Session_END, Session_LOCATION, Session_DATE,
								sessionInstructor, sportClass);
						return session;
					} else {
						return null;
					}
				});

		lenient().when(registrationRepository.save(any(Registration.class)))
				.thenAnswer((InvocationOnMock invocation) -> {
					return invocation.getArgument(0);
				});

		lenient().doAnswer(invocation -> {
			return invocation.getArgument(0);
		}).when(registrationRepository).delete(any(Registration.class));
	}

	// @SuppressWarnings("null")
	@Test
	public void createValidRegistration() {
		// Set up

		Account customer = new Customer(Customer_USERNAME, Customer_EMAIL, Customer_PASSWORD);
		Account instructor = new Instructor(Instructor_USERNAME, Instructor_EMAIL, Instructor_PASSWORD);
		SportClass sportClass = new SportClass(SportClass_NAME);
		Session session = new Session(Session_START, Session_END, Session_LOCATION, Session_DATE,
				(Instructor) instructor, sportClass);

		Registration registration = new Registration(Registration_DATE, customer, session);
		when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

		Registration registrationFromService = registrationService.createRegistration(Registration_DATE,
				Customer_USERNAME, Instructor_USERNAME, SportClass_NAME, Session_START);

		assertNotNull(registrationFromService);
		assertEquals((Account) customer, registration.getAccount());
		assertEquals(session, registration.getSession());
		assertEquals(Registration_DATE, registration.getDate());
	}

	@Test
	public void createRegistrationDateNull() {
		// Set up

		Account customer = new Customer(Customer_USERNAME, Customer_EMAIL, Customer_PASSWORD);
		Account instructor = new Instructor(Instructor_USERNAME, Instructor_EMAIL, Instructor_PASSWORD);
		SportClass sportClass = new SportClass(SportClass_NAME);
		Session session = new Session(Session_START, Session_END, Session_LOCATION, Session_DATE,
				(Instructor) instructor, sportClass);

		Registration registration = null;
		try {
			registration = registrationService.createRegistration(null, Customer_USERNAME, Instructor_USERNAME,
					SportClass_NAME, Session_START);
			// doesnt cause an error so it fails
			fail("did not throw an error even though date is null");
		} catch (Exception e) {
			assertEquals("Date can not be null", e.getMessage());
		}
		assertNull(registration);
	}

	@Test
	public void createRegistrationNoneExistingAccountUsername() {
		// Set up
		Account customer = new Customer(Customer_USERNAME, Customer_EMAIL, Customer_PASSWORD);
		Account instructor = new Instructor(Instructor_USERNAME, Instructor_EMAIL, Instructor_PASSWORD);
		SportClass sportClass = new SportClass(SportClass_NAME);
		Session session = new Session(Session_START, Session_END, Session_LOCATION, Session_DATE,
				(Instructor) instructor, sportClass);

		Registration registration = null;
		try {
			registration = registrationService.createRegistration(Registration_DATE, NONEXISTING_Customer_USERNAME,
					Instructor_USERNAME, SportClass_NAME, Session_START);
			// doesnt cause an error so it fails
			fail("did not throw an error even though account does not exist");
		} catch (Exception e) {
			assertEquals("No account with the given username exists.", e.getMessage());
		}
		assertNull(registration);
	}

	@Test
	public void createRegistrationNoneExistingInstructorUsername() {
		// Set up

		Registration registration = null;
		try {
			registration = registrationService.createRegistration(Registration_DATE, Customer_USERNAME,
					NONEXISTING_Instructor_USERNAME, SportClass_NAME, Session_START);
			// doesnt cause an error so it fails
			fail("did not throw an error even though instructor does not exist");
		} catch (Exception e) {
			assertEquals("No instructor with the given username exists.", e.getMessage());
		}
		assertNull(registration);
	}

	@Test
	public void createRegistrationInstructorAttemptsToRegisterForHisOwnClass() {
		// Set up

		Registration registration = null;
		try {
			registration = registrationService.createRegistration(Registration_DATE, Instructor_USERNAME,
					Instructor_USERNAME, SportClass_NAME, Session_START);
			// doesnt cause an error so it fails
			fail("did not throw an error even though instructor tried to register to their own session");
		} catch (Exception e) {
			assertEquals("Instructor can not register to their own session!", e.getMessage());
		}
		assertNull(registration);
	}

	@Test
	public void createRegistrationWithNoneExistingSession() {
		// Set up

		Registration registration = null;
		try {
			registration = registrationService.createRegistration(Registration_DATE, Customer_USERNAME,
					Instructor_USERNAME, SportClass_NAME, NONEXISTING_Session_START);
			// doesnt cause an error so it fails
			fail("did not throw an error even though no session existed");
		} catch (Exception e) {
			assertEquals("No such session exists.", e.getMessage());
		}
		assertNull(registration);
	}

	@Test
	public void createRegistrationAfterClassEnded() {
		// Set up
		Account instructor = new Instructor(Instructor_USERNAME, Instructor_EMAIL, Instructor_PASSWORD);
		SportClass sportClass = new SportClass(SportClass_NAME);
		Session alreadyEndedSession = new Session(Session_START, Session_END, Session_LOCATION, new Date(0L),
				(Instructor) instructor, sportClass);
		when(sessionRepository.findSessionByStartTimeAndInstructorAndSportClass(any(Time.class), any(Instructor.class),
				any(SportClass.class))).thenReturn(alreadyEndedSession);

		Registration registration = null;
		try {
			registration = registrationService.createRegistration(Registration_DATE, Customer_USERNAME,
					Instructor_USERNAME, SportClass_NAME, NONEXISTING_Session_START);
			// doesnt cause an error so it fails
			fail("did not throw an error even though session already ended");
		} catch (Exception e) {
			assertEquals("Can not register to a class that has already ended.", e.getMessage());
		}
		assertNull(registration);
	}

	@Test
	public void createRegistrationWhenItAlreadyExists() {
		// Set up
		Account customer = new Customer(Customer_USERNAME, Customer_EMAIL, Customer_PASSWORD);
		Account instructor = new Instructor(Instructor_USERNAME, Instructor_EMAIL, Instructor_PASSWORD);
		SportClass sportClass = new SportClass(SportClass_NAME);
		Session session = new Session(Session_START, Session_END, Session_LOCATION, Session_DATE,
				(Instructor) instructor, sportClass);

		Registration alreadyExistsRegistration = new Registration(Registration_DATE, customer, session);
		when(registrationRepository.findRegistrationByAccountAndSession(any(Account.class), any(Session.class)))
				.thenReturn(alreadyExistsRegistration);

		Registration registration = null;
		try {
			registration = registrationService.createRegistration(Registration_DATE, Customer_USERNAME,
					Instructor_USERNAME, SportClass_NAME, Session_START);
			// doesnt cause an error so it fails
			fail("did not throw an error even though no session existed");
		} catch (Exception e) {
			assertEquals("Registration already exists.", e.getMessage());
		}
		assertNull(registration);
	}

	@Test
	public void getRegistrationWithNullCustomerUsername() {
		// Set up

		Account customer = accountRepository.findAccountByUsername(Customer_USERNAME);
		Account sessionInstructor = instructorRepository.findInstructorByUsername(Instructor_USERNAME);
		SportClass sportClass = sportClassRepository.findSportClassByName(SportClass_NAME);
		Session session = sessionRepository.findSessionByStartTimeAndInstructorAndSportClass(Session_START,
				(Instructor) sessionInstructor, sportClass);

		Registration registration = null;
		try {
			registration = registrationService.getRegistration(null, Session_START, instructor.getUsername(),
					sportClass.getName());
			// doesnt cause an error
			fail();
		} catch (Exception e) {
			assertEquals("Account username can not be null or empty", e.getMessage());
		}
	}

	@Test
	public void getRegistrationWithEmptyCustomerUsername() {
		// Set up

		Account customer = accountRepository.findAccountByUsername(Customer_USERNAME);
		Account sessionInstructor = instructorRepository.findInstructorByUsername(Instructor_USERNAME);
		SportClass sportClass = sportClassRepository.findSportClassByName(SportClass_NAME);
		Session session = sessionRepository.findSessionByStartTimeAndInstructorAndSportClass(Session_START,
				(Instructor) sessionInstructor, sportClass);

		Registration registration = null;
		try {
			registration = registrationService.getRegistration("", Session_START, instructor.getUsername(),
					sportClass.getName());
			// doesnt cause an error
			fail();
		} catch (Exception e) {
			assertEquals("Account username can not be null or empty", e.getMessage());
		}
	}

	@Test
	public void getRegistrationWithNullInstructorUsername() {
		// Set up

		Account customer = accountRepository.findAccountByUsername(Customer_USERNAME);
		Account sessionInstructor = instructorRepository.findInstructorByUsername(Instructor_USERNAME);
		SportClass sportClass = sportClassRepository.findSportClassByName(SportClass_NAME);
		Session session = sessionRepository.findSessionByStartTimeAndInstructorAndSportClass(Session_START,
				(Instructor) sessionInstructor, sportClass);

		Registration registration = null;
		try {
			registration = registrationService.getRegistration(customer.getUsername(), Session_START, null,
					sportClass.getName());
			// doesnt cause an error
			fail();
		} catch (Exception e) {
			assertEquals("Instructor username can not be null or empty", e.getMessage());
		}
	}

	@Test
	public void getRegistrationWithEmptyInstructorUsername() {
		// Set up

		Account customer = accountRepository.findAccountByUsername(Customer_USERNAME);
		Account sessionInstructor = instructorRepository.findInstructorByUsername(Instructor_USERNAME);
		SportClass sportClass = sportClassRepository.findSportClassByName(SportClass_NAME);
		Session session = sessionRepository.findSessionByStartTimeAndInstructorAndSportClass(Session_START,
				(Instructor) sessionInstructor, sportClass);

		Registration registration = null;
		try {
			registration = registrationService.getRegistration(customer.getUsername(), Session_START, null,
					sportClass.getName());
			// doesnt cause an error
			fail();
		} catch (Exception e) {
			assertEquals("Instructor username can not be null or empty", e.getMessage());
		}
	}

	@Test
	public void getRegistrationWithNullSportClassName() {
		// Set up

		Account customer = accountRepository.findAccountByUsername(Customer_USERNAME);
		Account sessionInstructor = instructorRepository.findInstructorByUsername(Instructor_USERNAME);
		SportClass sportClass = sportClassRepository.findSportClassByName(SportClass_NAME);
		Session session = sessionRepository.findSessionByStartTimeAndInstructorAndSportClass(Session_START,
				(Instructor) sessionInstructor, sportClass);

		Registration registration = null;
		try {
			registration = registrationService.getRegistration(customer.getUsername(), Session_START,
					instructor.getUsername(), null);
			// doesnt cause an error
			fail();
		} catch (Exception e) {
			assertEquals("Sport Class name can not be null or empty", e.getMessage());
		}
	}

	@Test
	public void getRegistrationWithEmptySportClassName() {
		// Set up

		Account customer = accountRepository.findAccountByUsername(Customer_USERNAME);
		Account sessionInstructor = instructorRepository.findInstructorByUsername(Instructor_USERNAME);
		SportClass sportClass = sportClassRepository.findSportClassByName(SportClass_NAME);
		Session session = sessionRepository.findSessionByStartTimeAndInstructorAndSportClass(Session_START,
				(Instructor) sessionInstructor, sportClass);

		Registration registration = null;
		try {
			registration = registrationService.getRegistration(customer.getUsername(), Session_START,
					instructor.getUsername(), "");
			// doesnt cause an error
			fail();
		} catch (Exception e) {
			assertEquals("Sport Class name can not be null or empty", e.getMessage());
		}
	}

	@Test
	public void getValidRegistration() {
		// Set up

		Account customer = accountRepository.findAccountByUsername(Customer_USERNAME);
		Account sessionInstructor = instructorRepository.findInstructorByUsername(Instructor_USERNAME);
		SportClass sportClass = sportClassRepository.findSportClassByName(SportClass_NAME);
		Session session = sessionRepository.findSessionByStartTimeAndInstructorAndSportClass(Session_START,
				(Instructor) sessionInstructor, sportClass);

		Registration testRegistration = new Registration(Registration_DATE, customer, session);
		when(registrationRepository.findRegistrationByAccountAndSession(any(Account.class), any(Session.class)))
				.thenReturn(testRegistration);

		Registration registration = null;
		try {
			registration = registrationService.getRegistration(customer.getUsername(), Session_START,
					instructor.getUsername(), sportClass.getName());
		} catch (Exception e) {
			fail(e.getMessage());
		}

		assertNotNull(registration);
		assertEquals(customer.getUsername(), registration.getAccount().getUsername());
		assertEquals(session.getInstructor().getUsername(), registration.getSession().getInstructor().getUsername());
		assertEquals(Registration_DATE, registration.getDate());
	}

	@Test
	public void deleteValidRegistration() {
		// Set up
		Account customer = new Customer(Customer_USERNAME, Customer_EMAIL, Customer_PASSWORD);
		Account instructor = new Instructor(Instructor_USERNAME, Instructor_EMAIL, Instructor_PASSWORD);
		SportClass sportClass = new SportClass(SportClass_NAME);
		Session session = new Session(Session_START, Session_END, Session_LOCATION, Session_DATE,
				(Instructor) instructor, sportClass);

		Registration alreadyExistsRegistration = new Registration(Registration_DATE, customer, session);
		// when(registrationRepository.findRegistrationByAccountAndSession(any(Account.class),
		// any(Session.class))).thenReturn(alreadyExistsRegistration);

		try {
			registrationService.deleteRegistration(Customer_USERNAME, Instructor_USERNAME, SportClass_NAME,
					Session_START);
		} catch (Exception e) {
			fail("registration exists so it should be deleted.");
		}
	}
}
