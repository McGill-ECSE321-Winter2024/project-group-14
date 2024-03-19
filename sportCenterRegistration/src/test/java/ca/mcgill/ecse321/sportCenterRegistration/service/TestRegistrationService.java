package ca.mcgill.ecse321.sportCenterRegistration.service;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

import ca.mcgill.ecse321.sportCenterRegistration.dao.AccountRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.CustomerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.InstructorRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.OwnerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.SessionRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.SportClassRepository;
import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.Registration;
import ca.mcgill.ecse321.sportCenterRegistration.model.Registration;
import ca.mcgill.ecse321.sportCenterRegistration.model.Session;
import ca.mcgill.ecse321.sportCenterRegistration.model.SportClass;

import ca.mcgill.ecse321.sportCenterRegistration.service.CustomerService;
import ca.mcgill.ecse321.sportCenterRegistration.service.SportClassService;








import ca.mcgill.ecse321.sportCenterRegistration.dao.CustomerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;








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
	private static final String NONEXISTING_Customer_EMAIL = "NotACustomerEmail";
	private static final String NONEXISTING_Customer_PASSWORD = "NotACustomerPassword";

	private static final String NONEXISTING_Instructor_USERNAME = "NotAnInstructorUsername";
	private static final String NONEXISTING_Instructor_EMAIL = "NotAnInstructorEmail";
	private static final String NONEXISTING_Instructor_PASSWORD = "NotAnInstructorPassword";

	private static final Date NONEXISTING_Registration_DATE = new Date(3210760800000L);

	private static final Time NONEXISTING_Session_START = Time.valueOf("15:30:00");
	private static final Time NONEXISTING_Session_END = Time.valueOf("16:30:00");
	private static final String NONEXISTING_Session_LOCATION = "TestSessionLocation";
	private static final Date NONEXISTING_Session_DATE = new Date(3210490867000L);
	private static final String NONEXISTING_SportClass_NAME = "NotAnSportClassName";

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

	@Test
	public void testGetExistingRegistration() {
		assertEquals(Registration_DATE, registrationService.getRegistration(Customer_USERNAME, Session_START, Instructor_USERNAME, SportClass_NAME).getDate());
		assertEquals(sessionRepository.findSessionByStartTimeAndInstructorAndSportClass(Session_START, instructorRepository.findInstructorByUsername(Instructor_USERNAME), sportClassRepository.findSportClassByName(SportClass_NAME)), registrationService.getRegistration(Customer_USERNAME, Session_START, Instructor_USERNAME, SportClass_NAME).getSession());
	}


	@Test
	public void testGetNonExistingRegistration() {
		assertNull(registrationService.getRegistration(NONEXISTING_Customer_USERNAME, NONEXISTING_Session_START, NONEXISTING_Instructor_USERNAME, NONEXISTING_SportClass_NAME));
	}



}