// package ca.mcgill.ecse321.sportCenterRegistration.integration;

// import static org.junit.jupiter.api.Assertions.*;

// import java.sql.Date;
// import java.sql.Time;

// import org.junit.jupiter.api.MethodOrderer;
// import org.junit.jupiter.api.Order;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.TestInstance;
// import org.junit.jupiter.api.TestInstance.Lifecycle;
// import org.junit.jupiter.api.TestMethodOrder;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
// import org.springframework.boot.test.web.client.TestRestTemplate;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;

// import ca.mcgill.ecse321.sportCenterRegistration.dto.SessionDTO;
// import ca.mcgill.ecse321.sportCenterRegistration.model.Account;
// import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;
// import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
// import ca.mcgill.ecse321.sportCenterRegistration.model.SportClass;

// @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// @TestInstance(Lifecycle.PER_CLASS)

// public class SessionIntegrationTests {
// @Autowired
// private TestRestTemplate client;

// private static final String VALID_CUSTOMER_NAME = "ValidAccount";
// private static final String VALID_CUSTOMER_EMAIL = "valid@mail.mcgill.ca";
// private static final String VALID_CUSTOMER_PASSWORD = "password123";
// private static final String INVALID_CUSTOMER_USERNAME = "";

// private static final Account VALID_CUSTOMER = new
// Customer(VALID_CUSTOMER_NAME, VALID_CUSTOMER_EMAIL,
// VALID_CUSTOMER_PASSWORD);

// private static final String VALID_INSTRUCTOR_NAME = "validInstructor";
// private static final String VALID_INSTRUCTOR_EMAIL =
// "validInstructor@mail.mcgill.ca";
// private static final String VALID_INSTRUCTOR_PASSWORD = "password123";

// private static final Account VALID_INSTRUCTOR = new
// Instructor(VALID_INSTRUCTOR_NAME, VALID_INSTRUCTOR_EMAIL,
// VALID_INSTRUCTOR_PASSWORD);

// private static final String VALID_SPORT_CLASS_NAME = "football";
// private static final SportClass VALID_SPORT_CLASS = new
// SportClass(VALID_SPORT_CLASS_NAME);

// private static final Date VALID_REGISTRATION_DATE = new Date(0L);
// private static final Date VALID_SESSION_DATE = new Date(1L);
// private static final Time VALID_SESSION_START_TIME =
// Time.valueOf("13:00:00");
// private static final Time VALID_SESSION_END_TIME = Time.valueOf("15:00:00");
// private static final String VALID_SESSION_LOCATION = "GYM";
// private static int VALID_ID = 1;

// @Test
// @Order(1)
// public void testCreateValidSession() {
// // Set up
// SessionDTO request = new SessionDTO(VALID_SESSION_DATE,
// VALID_SESSION_START_TIME, VALID_SESSION_END_TIME,
// VALID_ID, VALID_SESSION_LOCATION, (Instructor) VALID_INSTRUCTOR,
// VALID_SPORT_CLASS);
// VALID_ID++;

// // Act
// ResponseEntity<SessionDTO> response = client.postForEntity("/create_session",
// request, SessionDTO.class);

// // Assert
// assertNotNull(response);
// assertEquals(HttpStatus.CREATED, response.getStatusCode());
// SessionDTO createdSession = response.getBody();
// assertNotNull(createdSession);
// assertEquals(VALID_INSTRUCTOR_NAME,
// createdSession.getInstructor().getUsername());
// assertEquals(VALID_INSTRUCTOR_EMAIL,
// createdSession.getInstructor().getEmail());
// assertNotNull(createdSession.getId());
// assertTrue(createdSession.getId() > 0, "Response should have a positive
// ID.");
// }

// @Test
// @Order(2)
// public void testReadSessionByValidId() {
// // Set up
// String url = "/view_session/{id}" + VALID_ID;

// // Act
// ResponseEntity<SessionDTO> response = client.getForEntity(url,
// SessionDTO.class);

// // Assert
// assertNotNull(response);
// assertEquals(HttpStatus.OK, response.getStatusCode());
// SessionDTO session = response.getBody();
// assertNotNull(session);
// assertEquals(VALID_INSTRUCTOR_NAME, session.getInstructor().getUsername());
// assertEquals(VALID_ID--, session.getId());
// }

// @Test
// @Order(3)
// public void testCreateInvalidSession() {
// // Set up
// SessionDTO request = new SessionDTO(VALID_SESSION_DATE,
// VALID_SESSION_START_TIME, VALID_SESSION_END_TIME,
// VALID_ID, null,
// (Instructor) VALID_INSTRUCTOR, VALID_SPORT_CLASS);
// VALID_ID++;

// // Act
// ResponseEntity<SessionDTO> response = client.postForEntity("/create_session",
// request, SessionDTO.class);

// // Assert
// assertNotNull(response);
// assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
// SessionDTO body = response.getBody();
// assertNotNull(body);
// assertEquals(1, body.getErrors().size());
// assertEquals("Session location cannot be empty!", body.getErrors().get(0));
// }

// @Test
// @Order(4)
// public void testReadSessionByInvalidId() {
// // Set up
// String url = "/view_session/{id}" + (this.VALID_ID + 1);

// // Act
// ResponseEntity<SessionDTO> response = client.getForEntity(url,
// SessionDTO.class);

// // Assert
// assertNotNull(response);
// assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
// SessionDTO body = response.getBody();
// assertNotNull(body);
// assertEquals(1, body.getErrors().size());
// // stephen didnt make it give errors
// assertEquals("", body.getErrors().get(0));
// }
// }
