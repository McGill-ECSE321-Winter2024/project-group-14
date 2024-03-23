package test.java.ca.mcgill.ecse321.sportCenterRegistration.service;

import org.junit.jupiter.api.extension.ExtendWith;

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
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportCenterRegistration.dao.*;
import ca.mcgill.ecse321.sportCenterRegistration.model.*;
import ca.mcgill.ecse321.sportCenterRegistration.service.LoginService;
import ca.mcgill.ecse321.sportCenterRegistration.service.SessionService;

@SpringBootTest
public class LoginServiceTests {
    @Mock
	private CustomerRepository customerRepo;

	@Mock
	private InstructorRepository employeeRepo;

	@Mock
	private OwnerRepository ownerRepo;

	@Mock
	private SessionRepository sessionRepo;

	@InjectMocks
	private LoginService loginservice;

	@InjectMocks
	private SessionService sessionService;

    private static final String CUSTOMER_KEY = "testcustomer@mail.ca";
	private static final String CUSTOMER_NAME = "Test";
	private static final String CUSTOMER_PASSWORD = "2222";

	private static final String INSTRUCTOR_KEY = "testemployee@mail.ca";
	private static final String INSTRUCTOR_NAME = "Test";
	private static final String INSTRUCTOR_PASSWORD = "1234";

	private static final String OWNER_KEY = "testowner@mail.ca";
	private static final String OWNER_NAME = "Test";
	private static final String OWNER_PASSWORD = "1234";

	static final long SESSION_KEY = (long) 321;
	private static final Time SESSION_STARTTIME = Time.valueOf("08:00:00");
	private static final Time SESSION_ENDTIME = Time.valueOf("20:00:00");
	private static final Date SESSION_DATE = Date.Monday;

	static final long SESSION_KEY2 = (long) 123;
	private static final Time SESSION_STARTTIME2 = Time.valueOf("07:00:00");
	private static final Time SESSION_ENDTIME2 = Time.valueOf("21:00:00");
	private static final Date SESSION_DATE2 = Date.Tuesday;

    @BeforeEach
	public void setMockOutput() {
		lenient().when(customerRepo.findByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(CUSTOMER_KEY)) {
				Customer customer = new Customer();
				customer.setEmail(CUSTOMER_KEY);
				customer.setName(CUSTOMER_NAME);
				customer.setPassword(CUSTOMER_PASSWORD);
				return customer;
			} else {
				return null;
			}
		});
        lenient().when(customerRepo.existsByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(CUSTOMER_KEY)) {
				return true;
			} else {
				return false;
			}

		});
        lenient().when(instructorRepo.existsByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(INSTRUCTOR_KEY)) {
				return true;
			} else {
				return false;
			}

		});
        lenient().when(ownerRepo.existsByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(OWNER_KEY)) {
				return true;
			} else {
				return false;
			}
		});
        lenient().when(instructorRepo.findByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(INSTRUCTOR_KEY)) {

				Session session = sessionRepo.findSessionById(SESSION_KEY);
				List<Session> session = new ArrayList<Session>();

				session.add(session);
				Instructor instructor = new Instructor();
				instructor.setEmail(EMPLOYEE_KEY);
				instructor.setName(EMPLOYEE_NAME);
				instructor.setPassword(EMPLOYEE_PASSWORD);
                instructor.setSession(session);
                return instructor;
			} else {
				return null;
			}
		});

        lenient().when(sessionRepo.findSessionById(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(SESSION_KEY)) {

				Session session = new Session();
				session.setDate(SESSION_DATE);
				session.setStartTime(SESSION_STARTTIME);
				session.setEndTime(SESSION_ENDTIME);
				session.setId(SESSION_KEY);

				return session;

			} else if (invocation.getArgument(0).equals(SESSION_KEY2)) {
				Session session = new DailySession();
				session.setDate(SESSION_DATE2);
				session.setStartTime(SESSION_STARTTIME2);
				session.setEndTime(SESSION_ENDTIME2);
				session.setId(SESSION_KEY2);
				return session;
			} else {
				return null;
			}
		});
        
        lenient().when(ownerDao.findByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(OWNER_KEY)) {
				Owner owner = new Owner();
				owner.setName(OWNER_NAME);
				owner.setPassword(OWNER_PASSWORD);
				owner.setEmail(OWNER_KEY);
				return owner;
			} else {
				return null;
			}
		});

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};

		lenient().when(customerRepo.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(instructorRepo.save(any(Instrutor.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(sessionRepo.save(any(Session.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(ownerRepo.save(any(Owner.class))).thenAnswer(returnParameterAsAnswer);
	}

    @Test
	public void testLoginCustomer() {
		Customer customer = null;
		try {
			customer = (Customer) service.login(CUSTOMER_KEY, CUSTOMER_PASSWORD);
		} catch (IllegalArgumentException e) {
			fail();
		}

		// Check the arguments of the created customer match the ones we passed as
		// argument.
		assertNotNull(customer);
		assertEquals(CUSTOMER_KEY, customer.getEmail());
		assertEquals(CUSTOMER_PASSWORD, customer.getPassword());
		assertEquals(CUSTOMER_NAME, customer.getName());
	}

    @Test
	public void testLoginInstructor() {
		Instructor instructor = null;
		try {
			instructor = (Instructor) service.login(INSTRUCTOR_KEY, INSTRUCTOR_PASSWORD);
		} catch (IllegalArgumentException e) {
			fail();
		}

		// Check the arguments of the created customer match the ones we passed as
		// argument.
		assertNotNull(instructor);
		assertEquals(INSTRUCTOR_KEY, instructor.getEmail());
		assertEquals(INSTRUCTOR_PASSWORD, instructor.getName());
	}

    @Test
	public void testLoginOwner() {
		Owner owner = null;
		try {
			owner = (Owner) service.login(OWNER_KEY, OWNER_PASSWORD);
		} catch (IllegalArgumentException e) {
			fail();
		}

		// Check the arguments of the created customer match the ones we passed as
		// argument.
		assertNotNull(owner);
		assertEquals(OWNER_KEY, owner.getEmail());
		assertEquals(OWNER_PASSWORD, owner.getPassword());
		assertEquals(OWNER_NAME, owner.getName());
	}

    @Test
	public void testLoginNotFound() {
		Account test = null;
		String error = null;
		try {
			test = (Owner) service.login("doesnotexist", "1234");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// Check the person instance is null
		assertNull(test);
		assertEquals(error, "Invalid email");
	}

    @Test
	public void testLoginWrongPassword() {
		Account test = null;
		String error = null;
		try {
			test = (Owner) service.login(CUSTOMER_KEY, "wrong");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// Check the person instance is null
		assertNull(test);
		assertEquals("Incorrect password", error);

	}















}
