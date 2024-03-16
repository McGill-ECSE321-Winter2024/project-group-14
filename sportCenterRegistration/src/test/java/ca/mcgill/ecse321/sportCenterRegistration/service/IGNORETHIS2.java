package ca.mcgill.ecse321.sportCenterRegistration.service; 

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import ca.mcgill.ecse321.sportCenterRegistration.dao.InstructorRepository;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;

import ca.mcgill.ecse321.sportCenterRegistration.dao.CustomerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;

import ca.mcgill.ecse321.sportCenterRegistration.service.SportCenterRegistrationService;





@ExtendWith(MockitoExtension.class)
class InstructorServiceTest {

@Mock
private InstructorRepository InstructorDao;

@InjectMocks
private SportCenterRegistrationService service;

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
	
	@Test
	public void testGetExistingInstructor() {
		assertEquals(Instructor_USERNAME, service.getInstructor(Instructor_USERNAME).getUsername());
		assertEquals(Instructor_EMAIL, service.getInstructor(Instructor_USERNAME).getEmail());
		assertEquals(Instructor_PASSWORD, service.getInstructor(Instructor_USERNAME).getPassword());
	}

	@Test
	public void testGetNonExistingInstructor() {
		assertNull(service.getInstructor(NONEXISTING_Instructor_USERNAME));
	}



}
