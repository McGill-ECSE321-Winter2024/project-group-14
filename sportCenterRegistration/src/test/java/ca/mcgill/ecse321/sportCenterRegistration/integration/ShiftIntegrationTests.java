package ca.mcgill.ecse321.sportCenterRegistration.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.sportCenterRegistration.dto.ShiftDto;
import ca.mcgill.ecse321.sportCenterRegistration.model.Account;
import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.SportClass;
import ca.mcgill.ecse321.sportCenterRegistration.model.Staff;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)

public class ShiftIntegrationTests {
    @Autowired
    private TestRestTemplate client;

    private static final String VALID_CUSTOMER_NAME = "ValidAccount";
    private static final String VALID_CUSTOMER_EMAIL = "valid@mail.mcgill.ca";
    private static final String VALID_CUSTOMER_PASSWORD = "password123";
    private static final String INVALID_CUSTOMER_USERNAME = "";

    private static final Account VALID_CUSTOMER = new Customer(VALID_CUSTOMER_NAME, VALID_CUSTOMER_EMAIL,
            VALID_CUSTOMER_PASSWORD);

    private static final String VALID_INSTRUCTOR_NAME = "validInstructor";
    private static final String VALID_INSTRUCTOR_EMAIL = "validInstructor@mail.mcgill.ca";
    private static final String VALID_INSTRUCTOR_PASSWORD = "password123";

    private static final Staff VALID_INSTRUCTOR = new Instructor(VALID_INSTRUCTOR_NAME, VALID_INSTRUCTOR_EMAIL,
            VALID_INSTRUCTOR_PASSWORD);

    private static final String VALID_SPORT_CLASS_NAME = "football";
    private static final SportClass VALID_SPORT_CLASS = new SportClass(VALID_SPORT_CLASS_NAME);

    private static final Date VALID_REGISTRATION_DATE = new Date(0L);
    private static final Date VALID_SESSION_DATE = new Date(1L);
    private static final Time VALID_SESSION_START_TIME = Time.valueOf("13:00:00");
    private static final Time VALID_SESSION_END_TIME = Time.valueOf("15:00:00");
    private static final String VALID_SESSION_LOCATION = "GYM";
    private static int VALID_ID = 1;

    @Test
    @Order(1)
    public void testCreateValidShift() {
        // Set up
        ShiftDto request = new ShiftDto(VALID_SESSION_DATE, VALID_SESSION_START_TIME, VALID_SESSION_END_TIME, VALID_ID,
                (Staff) VALID_INSTRUCTOR);
        VALID_ID++;
        // Act
        ResponseEntity<ShiftDto> response = client.postForEntity("/shift/{staff}", request, ShiftDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        ShiftDto createdShift = response.getBody();
        assertNotNull(createdShift);
        assertEquals(VALID_INSTRUCTOR_NAME, createdShift.geStaff().getUsername());
        assertEquals(VALID_INSTRUCTOR_EMAIL, createdShift.geStaff().getEmail());
        assertNotNull(createdShift.getId());
        assertTrue(createdShift.getId() > 0, "Response should have a positive ID.");
    }

    @Test
    @Order(2)
    public void testReadShiftByValidId() {
        // Set up
        String url = "/shift/" + VALID_REGISTRATION_DATE;

        // Act
        ResponseEntity<ShiftDto> response = client.getForEntity(url, ShiftDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ShiftDto shift = response.getBody();
        assertNotNull(shift);
        assertEquals(VALID_INSTRUCTOR_NAME, shift.geStaff().getUsername());
        assertEquals(this.VALID_ID - 1, shift.getId());
    }

    @Test
    @Order(3)
    public void testCreateInvalidShift() {
        // Set up
        ShiftDto request = new ShiftDto(VALID_SESSION_DATE, VALID_SESSION_START_TIME, VALID_SESSION_END_TIME, VALID_ID,
                VALID_INSTRUCTOR);

        // Act
        ResponseEntity<ShiftDto> response = client.postForEntity("/people", request, ShiftDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ShiftDto body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getErrors().size());
    }

    
}
