package ca.mcgill.ecse321.sportCenterRegistration.service; 


// import ca.mcgill.ecse321.sportCenterRegistration.model.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
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
import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.Registration;
import ca.mcgill.ecse321.sportCenterRegistration.model.Session;
import ca.mcgill.ecse321.sportCenterRegistration.model.SportClass;



@ExtendWith(MockitoExtension.class)
public class TestRegistrationService  {

	@Mock
	private RegistrationRepository registrationRepository;
	//making them mock so i can save to them without actually saving to the repos
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

	@InjectMocks
	private RegistrationService registrationService;

	private static final String Customer_USERNAME = "TestCustomerUsername";
	private static final String Customer_EMAIL = "TestCustomerEmail";
	private static final String Customer_PASSWORD = "TestCustomerPassword";

	private static final String Instructor_USERNAME = "TestInstructorUsername";
	private static final String Instructor_EMAIL = "TestInstructorEmail";
	private static final String Instructor_PASSWORD = "TestInstructorPassword";

	private static final Date Registration_DATE = new Date(1230760800000L);
	
	private static final Time Session_START = Time.valueOf("13:00:00");
	private static final Time Session_END = Time.valueOf("14:00:00");
	private static final String Session_LOCATION = "TestSessionLocation";
	private static final Date Session_DATE = new Date(1230560800000L);
	private static final String SportClass_NAME = "TestSportclassName";


	private static final String NONEXISTING_Customer_USERNAME = "NotACustomerUsername";

	private static final String NONEXISTING_Instructor_USERNAME = "NotAnInstructorUsername";

	private static final Time NONEXISTING_Session_START = Time.valueOf("15:30:00");
	private static final String NONEXISTING_SportClass_NAME = "NotASportClassName";
	

	@BeforeEach
	public void setMockOutput() {
		lenient().when(registrationRepository.findRegistrationById(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(Registration_DATE)) {

				Customer customer = new Customer(Customer_USERNAME, Customer_EMAIL, Customer_PASSWORD);
				accountRepository.save(customer);
				customerRepository.save(customer);
				Instructor instructor = new Instructor(Instructor_USERNAME, Instructor_EMAIL, Instructor_PASSWORD);
				accountRepository.save(instructor);
				instructorRepository.save(instructor);
				SportClass sportclass = new SportClass(SportClass_NAME);
				sportClassRepository.save(sportclass);
				Session session = new Session(Session_START, Session_END, Session_LOCATION, Session_DATE, instructor,sportclass);
				sessionRepository.save(session);
				Registration registration = new Registration(Registration_DATE, customer, session);

				return registration;
			} else {
				return null;
			}
		});
		
	}

	@AfterEach
	public void cleanUp(){
		Mockito.reset(accountRepository);
		Mockito.reset(sportClassRepository);
		Mockito.reset(registrationRepository);
		Mockito.reset(instructorRepository);
		Mockito.reset(sessionRepository);
		Mockito.reset(customerRepository);
	}
	
	@Test
	public void testGetExistingRegistration() {
		assertEquals(Registration_DATE, registrationService.getRegistration(Customer_USERNAME, Session_START, Instructor_USERNAME, SportClass_NAME).getDate());
		assertEquals(sessionRepository.findSessionByStartTimeAndInstructorAndSportClass(Session_START, instructorRepository.findInstructorByUsername(Instructor_USERNAME), sportClassRepository.findSportClassByName(SportClass_NAME)), registrationService.getRegistration(Customer_USERNAME, Session_START, Instructor_USERNAME, SportClass_NAME).getSession());
	}


	@Test
	public void testGetNonExistingRegistration() {
		Registration registration = null;
		try{
			registration = registrationService.getRegistration(NONEXISTING_Customer_USERNAME, NONEXISTING_Session_START, NONEXISTING_Instructor_USERNAME, NONEXISTING_SportClass_NAME);
		}
		catch(Exception e){
			assertEquals("No such user exists" ,e.getMessage());
			assertNull(registration);
		}

		try{
			//testing if it finds the registration when we use correct customer username
			registration = registrationService.getRegistration(Customer_USERNAME, NONEXISTING_Session_START, NONEXISTING_Instructor_USERNAME, NONEXISTING_SportClass_NAME);
		}
		catch(Exception e){
			assertEquals("No such instructor exists" ,e.getMessage());
			assertNull(registration);
		}

		try{
			//testing if it the registration finds when we use correct Session start time
			registration = registrationService.getRegistration(NONEXISTING_Customer_USERNAME, Session_START, NONEXISTING_Instructor_USERNAME, NONEXISTING_SportClass_NAME);
		}
		catch(Exception e){
			assertEquals("No such user exists" ,e.getMessage());
			assertNull(registration);
		}

		try{
			//testing if it finds the registration when we use correct instructor username (everything else is wrong)
			registration = registrationService.getRegistration(NONEXISTING_Customer_USERNAME, NONEXISTING_Session_START, Instructor_USERNAME, NONEXISTING_SportClass_NAME);

		}
		catch(Exception e){
			assertEquals("No such user exists" ,e.getMessage());
			assertNull(registration);
		}

		try{
			//testing if it finds the registration with the correct sport class name
			registration = registrationService.getRegistration(NONEXISTING_Customer_USERNAME, NONEXISTING_Session_START, NONEXISTING_Instructor_USERNAME, SportClass_NAME);
		}
		catch(Exception e){
			assertEquals("No such user exists" ,e.getMessage());
			assertNull(registration);
		}

		try{
			//testing if it finds the registration with the correct sport class name, instructor name, and session start time
			registration = registrationService.getRegistration(NONEXISTING_Customer_USERNAME, Session_START, Instructor_USERNAME, SportClass_NAME);
		}
		catch(Exception e){
			assertEquals("No such user exists" ,e.getMessage());
			assertNull(registration);
		}
	
	}

	@Test
	public void testCreateRegistration(){
		// assertEquals(0, registrationService.getAllRegistrations().size());
		String customerUsername = "david";
		Customer customer = new Customer(customerUsername, "david@gmail.com", "example");
		accountRepository.save(customer);
		customerRepository.save(customer);
		String instructorUsername = "exampleInstructorUsername";
		Instructor instructor = new Instructor(instructorUsername, "example@gmail.com", "exampleInstructorPassword");
		accountRepository.save(instructor);
		instructorRepository.save(instructor);
		SportClass sportclass = new SportClass("football");
		sportClassRepository.save(sportclass);
		Date sessionDate = new Date(2230760856200L);
		Session session = new Session(Time.valueOf("13:00:00"), Time.valueOf("15:00:00"), "gym", sessionDate, instructor, sportclass);
		sessionRepository.save(session);

		Registration registration = null;
		try{
			registration = registrationService.createRegistration(sessionDate, customerUsername, instructorUsername, sportclass.getName(), session.getStartTime());
		}
		catch(Exception e){
			fail(e.getMessage());
		}

		assertNotNull(registration);
		assertEquals(registration.getAccount(), customer);
		assertEquals(registration.getSession(), session);
		assertEquals(registration.getDate(), sessionDate);
	}

	@Test
	public void testCreateRegistrationNullDate(){
		String customerUsername = "david";
		Customer customer = new Customer(customerUsername, "david@gmail.com", "example");
		accountRepository.save(customer);
		customerRepository.save(customer);
		String instructorUsername = "exampleInstructorUsername";
		Instructor instructor = new Instructor(instructorUsername, "example@gmail.com", "exampleInstructorPassword");
		accountRepository.save(instructor);
		instructorRepository.save(instructor);
		SportClass sportclass = new SportClass("football");
		sportClassRepository.save(sportclass);
		//this time sessionDate is null
		Date sessionDate = null;
		Session session = new Session(Time.valueOf("13:00:00"), Time.valueOf("15:00:00"), "gym", sessionDate, instructor, sportclass);
		sessionRepository.save(session);

		Registration registration = null;
		String error = null;
		try{
			registration = registrationService.createRegistration(sessionDate, customerUsername, instructorUsername, sportclass.getName(), session.getStartTime());
		}
		catch(Exception e){
			error = e.getMessage();
		}

		assertNull(registration);
		assertEquals("Date can not be null",error);
	}

	@Test
	public void testCreateRegistrationNullAccountUsername(){
		//this time customerUsername = null
		String customerUsername = null;
		Customer customer = new Customer(customerUsername, "david@gmail.com", "example");
		accountRepository.save(customer);
		customerRepository.save(customer);
		String instructorUsername = "exampleInstructorUsername";
		Instructor instructor = new Instructor(instructorUsername, "example@gmail.com", "exampleInstructorPassword");
		accountRepository.save(instructor);
		instructorRepository.save(instructor);
		SportClass sportclass = new SportClass("football");
		sportClassRepository.save(sportclass);
		Date sessionDate = new Date(2230760856200L);
		Session session = new Session(Time.valueOf("13:00:00"), Time.valueOf("15:00:00"), "gym", sessionDate, instructor, sportclass);
		sessionRepository.save(session);

		Registration registration = null;
		String error = null;
		try{
			registration = registrationService.createRegistration(sessionDate, customerUsername, instructorUsername, sportclass.getName(), session.getStartTime());
		}
		catch(Exception e){
			error = e.getMessage();
		}

		assertNull(registration);
		assertEquals("Customer username cannot be empty!", error);
		assertTrue(true);
	}

	@Test
	public void testCreateRegistrationNullInstructorUsername(){
		String customerUsername = "david";
		Customer customer = new Customer(customerUsername, "david@gmail.com", "example");
		accountRepository.save(customer);
		customerRepository.save(customer);
		//this time the instructorUsername is null
		String instructorUsername = null;
		Instructor instructor = new Instructor(instructorUsername, "example@gmail.com", "exampleInstructorPassword");
		accountRepository.save(instructor);
		instructorRepository.save(instructor);
		SportClass sportclass = new SportClass("football");
		sportClassRepository.save(sportclass);
		Date sessionDate = new Date(2230760856200L);
		Session session = new Session(Time.valueOf("13:00:00"), Time.valueOf("15:00:00"), "gym", sessionDate, instructor, sportclass);
		sessionRepository.save(session);

		Registration registration = null;
		String error = null;
		try{
			registration = registrationService.createRegistration(sessionDate, customerUsername, instructorUsername, sportclass.getName(), session.getStartTime());
		}
		catch(Exception e){
			error = e.getMessage();
		}

		assertNull(registration);
		assertEquals("Instructor username cannot be empty!", error);
		assertTrue(true);
	}

	@Test
	public void testCreateRegistrationNullSportClassName(){
		String customerUsername = "david";
		Customer customer = new Customer(customerUsername, "david@gmail.com", "example");
		accountRepository.save(customer);
		customerRepository.save(customer);
		String instructorUsername = "ExampleInstructorName";
		Instructor instructor = new Instructor(instructorUsername, "example@gmail.com", "exampleInstructorPassword");
		accountRepository.save(instructor);
		instructorRepository.save(instructor);
		//this time sportclass name is null
		SportClass sportclass = new SportClass(null);
		sportClassRepository.save(sportclass);
		Date sessionDate = new Date(2230760856200L);
		Session session = new Session(Time.valueOf("13:00:00"), Time.valueOf("15:00:00"), "gym", sessionDate, instructor, sportclass);
		sessionRepository.save(session);

		Registration registration = null;
		String error = null;
		try{
			registration = registrationService.createRegistration(sessionDate, customerUsername, instructorUsername, sportclass.getName(), session.getStartTime());
		}
		catch(Exception e){
			error = e.getMessage();
		}

		assertNull(registration);
		assertEquals("Sport class name cannot be empty!", error);
		assertTrue(true);
	}

	@Test
	public void testCreateRegistrationNullSessionStartTime(){
		String customerUsername = "david";
		Customer customer = new Customer(customerUsername, "david@gmail.com", "example");
		accountRepository.save(customer);
		customerRepository.save(customer);
		String instructorUsername = "ExampleInstructorName";
		Instructor instructor = new Instructor(instructorUsername, "example@gmail.com", "exampleInstructorPassword");
		accountRepository.save(instructor);
		instructorRepository.save(instructor);
		SportClass sportclass = new SportClass("football");
		sportClassRepository.save(sportclass);
		Date sessionDate = new Date(2230760856200L);
		//this time session starttime is null
		Session session = new Session(null, Time.valueOf("15:00:00"), "gym", sessionDate, instructor, sportclass);
		sessionRepository.save(session);

		Registration registration = null;
		String error = null;
		try{
			registration = registrationService.createRegistration(sessionDate, customerUsername, instructorUsername, sportclass.getName(), session.getStartTime());
		}
		catch(Exception e){
			error = e.getMessage();
		}

		assertNull(registration);
		assertEquals("Session start time cannot be null!", error);
		assertTrue(true);
	}

	@Test
	public void testCreateRegistrationWhenAccountDoesNotExist(){
		String customerUsername = "david";
		//this time we don't make a customer object and we don't save it to the repository

		String instructorUsername = "ExampleInstructorName";
		Instructor instructor = new Instructor(instructorUsername, "example@gmail.com", "exampleInstructorPassword");
		accountRepository.save(instructor);
		instructorRepository.save(instructor);
		SportClass sportclass = new SportClass("football");
		sportClassRepository.save(sportclass);
		Date sessionDate = new Date(2230760856200L);
		Session session = new Session(Time.valueOf("14:00:00"), Time.valueOf("15:00:00"), "gym", sessionDate, instructor, sportclass);
		sessionRepository.save(session);

		Registration registration = null;
		String error = null;
		try{
			registration = registrationService.createRegistration(sessionDate, customerUsername, instructorUsername, sportclass.getName(), session.getStartTime());
		}
		catch(Exception e){
			error = e.getMessage();
		}

		assertNull(registration);
		assertEquals("An account with the given username doesn't exist", error);
		assertTrue(true);
	}

	@Test
	public void testCreateRegistrationWhenInstructorDoesNotExist(){
		String customerUsername = "david";
		Customer customer = new Customer(customerUsername, "david@gmail.com", "example");
		accountRepository.save(customer);
		customerRepository.save(customer);
		String instructorUsername = "ExampleInstructorName";
		Instructor instructor = new Instructor(instructorUsername, "instructor@gmail.com", "example");
		SportClass sportclass = new SportClass("football");
		sportClassRepository.save(sportclass);
		Date sessionDate = new Date(2230760856200L);
		Session session = new Session(Time.valueOf("14:00:00"), Time.valueOf("15:00:00"), "gym", sessionDate, instructor, sportclass);
		sessionRepository.save(session);

		Registration registration = null;
		String error = null;
		try{
			//put the name of an instructor that doesn't exist (the session exists)
			registration = registrationService.createRegistration(sessionDate, customerUsername, "nonExistingInstructor", sportclass.getName(), session.getStartTime());
		}
		catch(Exception e){
			error = e.getMessage();
		}

		assertNull(registration);
		assertEquals("Instructor with the given username doesn't exist", error);
		assertTrue(true);
	}

	@Test
	public void testCreateRegistrationWhenSessionDoesNotExist(){
		String customerUsername = "david";
		Customer customer = new Customer(customerUsername, "david@gmail.com", "example");
		accountRepository.save(customer);
		String instructorUsername = "ExampleInstructorName";
		Instructor instructor = new Instructor(instructorUsername, "instructor@gmail.com", "example");
		SportClass sportclass = new SportClass("football");
		sportClassRepository.save(sportclass);
		Date sessionDate = new Date(2230760856200L);
		//no session created this time

		Registration registration = null;
		String error = null;
		try{
			
			registration = registrationService.createRegistration(sessionDate, customerUsername, instructorUsername, sportclass.getName(), Time.valueOf("14:00:00"));
		}
		catch(Exception e){
			error = e.getMessage();
		}

		assertNull(registration);
		assertEquals("Session with the given start time, instructor, and sportclass does not exist.", error);
		assertTrue(true);
	}

	@Test
	public void testCreateRegistrationWhenSportClassDoesNotExist(){
		String customerUsername = "david";
		Customer customer = new Customer(customerUsername, "david@gmail.com", "example");
		accountRepository.save(customer);
		customerRepository.save(customer);
		//this time the instructorUsername is null
		String instructorUsername = null;
		Instructor instructor = new Instructor(instructorUsername, "example@gmail.com", "exampleInstructorPassword");
		accountRepository.save(instructor);
		instructorRepository.save(instructor);
		SportClass sportclass = new SportClass("football");
		sportClassRepository.save(sportclass);
		Date sessionDate = new Date(2230760856200L);
		Session session = new Session(Time.valueOf("13:00:00"), Time.valueOf("15:00:00"), "gym", sessionDate, instructor, sportclass);
		sessionRepository.save(session);

		Registration registration = null;
		String error = null;
		try{
			//sportclass does not exist
			registration = registrationService.createRegistration(sessionDate, customerUsername, instructorUsername,"stretching", session.getStartTime());
		}
		catch(Exception e){
			error = e.getMessage();
		}

		assertNull(registration);
		assertEquals("Instructor username cannot be empty!", error);
		assertTrue(true);
	}

	@Test
	public void testCreateRegistrationWhenRegistrationAlreadyExists(){
		String customerUsername = "david";
		Customer customer = new Customer(customerUsername, "david@gmail.com", "example");
		accountRepository.save(customer);
		customerRepository.save(customer);
		String instructorUsername = "ExampleInstructorName";
		Instructor instructor = new Instructor(instructorUsername, "example@gmail.com", "exampleInstructorPassword");
		accountRepository.save(instructor);
		instructorRepository.save(instructor);
		SportClass sportclass = new SportClass("football");
		sportClassRepository.save(sportclass);
		Date sessionDate = new Date(2230760856200L);
		Session session = new Session(Time.valueOf("14:00:00"), Time.valueOf("15:00:00"), "gym", sessionDate, instructor, sportclass);
		sessionRepository.save(session);
		Registration registrationAlreadyExists = new Registration(sessionDate, customer, session);
		registrationRepository.save(registrationAlreadyExists);
		// assertEquals(1, registrationService.getAllRegistrations().size());

		Registration registration = null;
		String error = null;
		try{
			registration = registrationService.createRegistration(sessionDate, customerUsername, instructorUsername, sportclass.getName(), session.getStartTime());
		}
		catch(Exception e){
			error = e.getMessage();
		}

		assertNull(registration);
		assertEquals("Registration already exists", error);
		assertTrue(true);
	}


	@Test
	public void testDeleteRegistration(){
		try{
			registrationService.deleteRegistration(Customer_USERNAME, Session_START, Instructor_USERNAME, SportClass_NAME);
		}
		catch(IllegalArgumentException e){
			fail(e.getMessage());
		}
		assertTrue(true);
	}
	
}