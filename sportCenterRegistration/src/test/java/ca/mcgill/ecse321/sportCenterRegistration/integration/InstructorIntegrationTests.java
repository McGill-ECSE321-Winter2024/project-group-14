package ca.mcgill.ecse321.sportCenterRegistration.integration;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.dao.InstructorRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dto.InstructorDTO;
/*
 * @author Muhammad Hammad
 * 
 * Integration test for Instructor
 * 
 * Note: The tests where we determine if the correct error was received could not be done because error.getErrors() was not working
 * I left them in thought because I believe the logic of the code works besides that but I commented them out to prevent compliation
 * errors, but do note that if you uncomment it that the order needs to be adjusted
 * 
 */

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class InstructorIntegrationTests {
    @Autowired
    private TestRestTemplate client;


	@Autowired
	private InstructorRepository InstructorRepository;
    @BeforeEach
	@AfterEach
	public void clearDatabase() {
        InstructorRepository.deleteAll();
	}

    private final String VALID_USERNAME = "Hammad";
    private final String VALID_EMAIL = "Hammad@mail.mcgill.ca";
    private final String VALID_PASSWORD = "hammad123";
    private final String VALID_USERNAME2 = "Hammad2";
    private final String VALID_EMAIL2 = "Hamma2d@mail.mcgill.ca";
    private final String VALID_PASSWORD2 = "hammad1232";


    
    private final String INVALID_USERNAME = "";
    private final String INVALID_EMAIL = "email";
    private final String INVALID_PASSWORD = "";


    private int validId;


    @Test
    @Order(1)
    public void testCreateInstructor() {
        // Set up
       
        String url = "/Instructor/" + this.VALID_USERNAME + "/" + this.VALID_EMAIL + "/" + this.VALID_PASSWORD;

        // Act
        ResponseEntity<InstructorDTO> response = client.postForEntity(url, null , InstructorDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        InstructorDTO createdInstructor = response.getBody();
        assertNotNull(createdInstructor);
        assertEquals(VALID_USERNAME, createdInstructor.getInstructorUsername());
        assertEquals(VALID_EMAIL, createdInstructor.getInstructorEmail());


    }

    @Test
    @Order(2)
    public void testReadInstructorByValidUsername() {
        // Set up
        String url = "/Instructor/" + this.VALID_USERNAME;

        // Act
        ResponseEntity<InstructorDTO> response = client.getForEntity(url, InstructorDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        InstructorDTO Instructor = response.getBody();
        assertNotNull(Instructor);
        assertEquals(VALID_USERNAME, Instructor.getInstructorUsername());
        assertEquals(VALID_EMAIL, Instructor.getInstructorEmail());
        

    }
/* 
    @Test
    @Order(3)
    public void testCreateInvalidInstructor() {
        // Set up
        String url = "/Instructor/" + this.VALID_USERNAME + "/" + this.VALID_EMAIL + "/" + this.VALID_PASSWORD;

        // Act
        ResponseEntity<Error> response = client.postForEntity(url, null, Error.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Error body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getErrors());
        assertEquals("Password cannot be empty", body.getErrors().get(0));

    }

    @Test
    @Order(4)
    public void testReadInstructorByInvalidUsername() {
        // Set up
        String url = "/Instructor/" + this.INVALID_USERNAME;

        // Act
        ResponseEntity<Error> response = client.getForEntity(url, Error.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Error body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getErrors().size());
        assertEquals("Instructor name is invalid", body.getErrors().get(0));
    }*/

    @Test
    @Order(3)
    public void testReadAllInstructor(){
        // Set up
        String url = "/Instructor/all";

        // Actd
        ResponseEntity<InstructorDTO> response = client.getForEntity(url, InstructorDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<InstructorDTO> Instructors = (List<InstructorDTO>) response.getBody();
        assertNotNull(Instructors);
        assertEquals(1, Instructors.size());
    }
    @Test
    @Order(4)
    public void testDeleteInstructorValidUsername() {
        // Set up
        String url = "/Instructor/delete" + this.VALID_USERNAME;

        // Act
        ResponseEntity<InstructorDTO> response = client.getForEntity(url, InstructorDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        InstructorDTO Instructor = response.getBody();
        assertNotNull(Instructor);
        assertEquals(VALID_USERNAME, Instructor.getInstructorUsername());
        assertEquals(VALID_EMAIL, Instructor.getInstructorEmail());
        assertEquals(VALID_PASSWORD, Instructor.getInstructorPassword());
    }

    /* 
    @Test
    @Order(7)
    public void testDeleteInstructorByInvalidUsername() {
        // Set up
        String url = "/Instructor/delete" + this.INVALID_USERNAME;

        // Act
        ResponseEntity<InstructorDTO> response = client.deleteForEntity(url, Error.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Error body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getErrors().size());
        assertEquals("Instructor name cannot be empty!", body.getErrors().get(0));
    }
    
   
*/
    @Test
    @Order(5)
    public void testUpdateInstructorUsername() {
        // Set up
        String url = "/Instructor/update/" + this.VALID_USERNAME + "/" + this.VALID_USERNAME2 + "/" + this.VALID_EMAIL + "/" + this.VALID_PASSWORD;

        // Act
        ResponseEntity<InstructorDTO> response = client.getForEntity(url, InstructorDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        InstructorDTO Instructor = response.getBody();
        assertNotNull(Instructor);
        assertEquals(VALID_USERNAME2, Instructor.getInstructorUsername());
        assertEquals(VALID_EMAIL, Instructor.getInstructorEmail());
        assertEquals(VALID_PASSWORD, Instructor.getInstructorPassword());
    }

    @Test
    @Order(6)
    public void testUpdateInstructorEmail() {
        // Set up
        String url = "/Instructor/update/" + this.VALID_USERNAME + "/" + this.VALID_USERNAME + "/" + this.VALID_EMAIL2 + "/" + this.VALID_PASSWORD;

        // Act
        ResponseEntity<InstructorDTO> response = client.getForEntity(url, InstructorDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        InstructorDTO Instructor = response.getBody();
        assertNotNull(Instructor);
        assertEquals(VALID_USERNAME, Instructor.getInstructorUsername());
        assertEquals(VALID_EMAIL2, Instructor.getInstructorEmail());
        assertEquals(VALID_PASSWORD, Instructor.getInstructorPassword());
    }

    @Test
    @Order(7)
    public void testUpdateInstructorPassword() {
        // Set up
        String url = "/Instructor/update/" + this.VALID_USERNAME + "/" + this.VALID_USERNAME + "/" + this.VALID_EMAIL + "/" + this.VALID_PASSWORD2;

        // Act
        ResponseEntity<InstructorDTO> response = client.getForEntity(url, InstructorDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        InstructorDTO Instructor = response.getBody();
        assertNotNull(Instructor);
        assertEquals(VALID_USERNAME, Instructor.getInstructorUsername());
        assertEquals(VALID_EMAIL, Instructor.getInstructorEmail());
        assertEquals(VALID_PASSWORD2, Instructor.getInstructorPassword());
    }
/* 
    @Test
    @Order(12)
    public void testUpdateInstructorByInvalidUsername() {
        // Set up
        String url = "/Instructor/update/" + this.INVALID_USERNAME + "/" + this.INVALID_USERNAME + "/" + this.VALID_EMAIL + "/" + THIS.VALID_PASSWORD2;

        // Act
        ResponseEntity<Error> response = client.getForEntity(url, Error.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Error body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getErrors().size());
        assertEquals("Username cannot be empty!", body.getErrors().get(0));
    }
*/
    /*@Test
    @Order(12)
    public void testUpdateInstructorByInvalidEmail() {
        // Set up
        String url = "/Instructor/update/" + this.VALID_USERNAME + "/" + this.VALID_USERNAME2 + "/" + this.INVALID_EMAIL + "/" + THIS.VALID_PASSWORD2;

        // Act
        ResponseEntity<Error> response = client.getForEntity(url, Error.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Error body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getErrors().size());
        assertEquals("Email is invalid!", body.getErrors().get(0));
    }

    @Test
    @Order(12)
    public void testUpdateInstructorByInvalidPassword() {
        // Set up
        String url = "/Instructor/update/" + this.VALID_USERNAME + "/" + this.VALID_USERNAME2 + "/" + this.VALID_EMAIL + "/" + THIS.INVALID_PASSWORD2;

        // Act
        ResponseEntity<InstructorDTO> response = client.getForEntity(url, Error.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Error body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getErrors().size());
        assertEquals("Password cannot be empty!", body.getErrors().get(0));
    }

    @Test
    @Order(12)
    public void testUpdateInstructorByNonUniqueUsername() {
        // Set up
        String url = "/Instructor/update/" + this.VALID_USERNAME + "/" + this.VALID_USERNAME + "/" + this.VALID_EMAIL + "/" + THIS.INVALID_PASSWORD2;

        // Act
        ResponseEntity<InstructorDTO> response = client.getForEntity(url, Error.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Error body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getErrors().size());
        assertEquals("Username is not unique!", body.getErrors().get(0));
    }
 */
    





}