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

import ca.mcgill.ecse321.sportCenterRegistration.dao.CustomerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.InstructorRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.OwnerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.RegistrationRepository;
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
	private RegistrationRepository registrationRepo;
    @Mock
    private SportClassRepository sportClassRepo;
	@Mock
    private OwnerRepository ownerRepo;
	@Mock
    private InstructorRepository instructorRepo;

	@InjectMocks
	private RegistrationService registrationService;
    @InjectMocks
    private InstructorService instructorService;
    @InjectMocks
    private OwnerService ownerService;




	private static final String CUSTOMER_USERNAME = "TestCustomerUsername";
	private static final String Customer_EMAIL = "TestCustomerEmail";
	private static final String Customer_PASSWORD = "TestCustomerPassword";

	private static final String NONEXISTING_Customer_USERNAME = "NotAnCustomerUsername";
	private static final String NONEXISTING_Customer_EMAIL = "NotAnCustomerEmail";
	private static final String NONEXISTING_Customer_PASSWORD = "NotAnCustomerPassword";


	private static final String INSTRUCTOR_USERNAME = "InstructorUsername";
	private static final String INSTRUCTOR_EMAIL = "InstructorEmail";
	private static final String INSTRUCTOR_PASSWORD = "InstructorPassword";

	Date REGISTRATION_DATE = new Date(1230760800000L);
	
	Time SESSION_START = Time.valueOf("13:00:00");
	Time SESSION_END = Time.valueOf("14:00:00");
	private static final String SESSION_LOCATION = "SessionLocation";
	Date SESSION_DATE = new Date(1230760800000L);


	private static final String SPORTCLASS_NAME = "SportclassName";

	int REGISTRATION_ID;



	



@BeforeEach
public void setMockOutput() {
    lenient().when(registrationRepo.findRegistrationById(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
        if(invocation.getArgument(0).equals(REGISTRATION_ID)) {
            Customer customer = new Customer(CUSTOMER_USERNAME, Customer_EMAIL, Customer_PASSWORD);
			Instructor instructor = new Instructor(INSTRUCTOR_USERNAME, INSTRUCTOR_EMAIL, INSTRUCTOR_PASSWORD);
			SportClass sportclass = new SportClass(SPORTCLASS_NAME);
			Session session = new Session(SESSION_START, SESSION_END, SESSION_LOCATION, SESSION_DATE, instructor,sportclass);
			Registration registration = new Registration(REGISTRATION_DATE, customer, session);
			REGISTRATION_ID = registration.getId();


            return registration;
        } else {
            return null;
        }
    });
	
}
	
	@Test
	public void testGetExistingRegistration() {
		assertEquals(REGISTRATION_DATE, registrationService.getRegistration(REGISTRATION_ID).getDate());

	 



	}


	@Test
	public void testGetNonExistingCustomer() {
		assertNull(service.CustomerService.getCustomer(NONEXISTING_Customer_USERNAME));
	}



}