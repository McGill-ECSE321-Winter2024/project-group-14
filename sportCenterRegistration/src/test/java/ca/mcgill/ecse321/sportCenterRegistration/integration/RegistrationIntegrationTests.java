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

import ca.mcgill.ecse321.sportCenterRegistration.dto.CustomerDTO;
import ca.mcgill.ecse321.sportCenterRegistration.dto.RegistrationDTO;
import ca.mcgill.ecse321.sportCenterRegistration.model.Account;
import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.Session;
import ca.mcgill.ecse321.sportCenterRegistration.model.SportClass;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class RegistrationIntegrationTests {
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

        private static final Account VALID_INSTRUCTOR = new Instructor(VALID_INSTRUCTOR_NAME, VALID_INSTRUCTOR_EMAIL,
                        VALID_INSTRUCTOR_PASSWORD);

        private static final String VALID_SPORT_CLASS_NAME = "football";
        private static final SportClass VALID_SPORT_CLASS = new SportClass(VALID_SPORT_CLASS_NAME);

        private static final Date VALID_REGISTRATION_DATE = new Date(0L);
        private static final Date VALID_SESSION_DATE = new Date(1L);
        private static final Time VALID_SESSION_START_TIME = Time.valueOf("13:00:00");
        private static final Time VALID_SESSION_END_TIME = Time.valueOf("15:00:00");
        private static final String VALID_SESSION_LOCATION = "GYM";

        private static Session VALID_SESSION = new Session(VALID_SESSION_START_TIME, VALID_SESSION_END_TIME,
                        VALID_SESSION_LOCATION, VALID_SESSION_DATE, (Instructor) VALID_INSTRUCTOR, VALID_SPORT_CLASS);

        // public static Registration VALID_REGISTRATION = null;

        @Test
        @Order(1)
        public void testCreateValidRegistration() {
                RegistrationDTO registrationDTO = new RegistrationDTO(VALID_REGISTRATION_DATE, VALID_CUSTOMER,
                                VALID_SESSION);

                ResponseEntity<RegistrationDTO> response = client.postForEntity(
                                "/Registration/{account}/{session}/{date}",
                                registrationDTO, RegistrationDTO.class);

                assertNotNull(response);
                assertEquals(HttpStatus.CREATED, response.getStatusCode());
                RegistrationDTO createdRegistrationDTO = response.getBody();
                assertNotNull(createdRegistrationDTO);
                assertEquals(((CustomerDTO) registrationDTO.getAccount()).getCustomerUsername(),
                                ((CustomerDTO) createdRegistrationDTO.getAccount()).getCustomerUsername());
        }

        @Test
        @Order(2)
        public void testReadRegistrationByValidAccountAndSession() {
                String url = "/registration/" + this.VALID_CUSTOMER + "/" + this.VALID_SESSION + "/";

                ResponseEntity<RegistrationDTO> response = client.getForEntity(url, RegistrationDTO.class);

                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                RegistrationDTO registrationDTO = response.getBody();
                assertNotNull(registrationDTO);
                assertEquals(VALID_CUSTOMER_NAME, ((CustomerDTO) registrationDTO.getAccount()).getCustomerUsername());
                assertEquals(VALID_SPORT_CLASS_NAME, (registrationDTO.getSession()).getSportClass().getName());
                assertEquals(VALID_INSTRUCTOR_NAME, (registrationDTO.getSession()).getInstructor().getUsername());
        }

        @Test
        @Order(3)
        public void testCreateInvalidRegistration() {
                String url = "/registration/" + this.VALID_CUSTOMER + "/" + this.VALID_SESSION + "/"
                                + this.VALID_REGISTRATION_DATE + "/";

                RegistrationDTO request = new RegistrationDTO(VALID_REGISTRATION_DATE,
                                new Customer(INVALID_CUSTOMER_USERNAME, VALID_CUSTOMER_EMAIL, VALID_CUSTOMER_PASSWORD),
                                VALID_SESSION);

                ResponseEntity<RegistrationDTO> response = client.postForEntity(
                                "/Registration/{account}/{session}/{date}", request, RegistrationDTO.class);

                assertNotNull(response);
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                RegistrationDTO error = response.getBody();
                assertNotNull(error);
                assertEquals(1, error.getErrors().size());
                assertEquals("Account username can not be null or empty", error.getErrors().get(0));
        }

        @Test
        @Order(4)
        public void testReadPersonByInvalidAccount() {
                // Set up
                String url = "/people/" + this.INVALID_CUSTOMER_USERNAME;

                // Act
                ResponseEntity<RegistrationDTO> response = client.getForEntity(url, RegistrationDTO.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
                RegistrationDTO body = response.getBody();
                assertNotNull(body);
                assertEquals(1, body.getErrors().size());
                assertEquals("No account with the given username exists.", body.getErrors().get(0));
        }
}
