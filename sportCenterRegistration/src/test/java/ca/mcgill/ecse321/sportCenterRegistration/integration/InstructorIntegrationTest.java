import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.sportCenterRegistration.dto.Error;
import ca.mcgill.ecse321.sportCenterRegistration.dto.InstructorRequestDto;
import ca.mcgill.ecse321.sportCenterRegistration.dto.InstructorDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class InstructorIntegrationTests {
    @Autowired
    private TestRestTemplate client;

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
        InstructorDTO InstructorDTO = new InstructorDTO(VALID_USERNAME, VALID_EMAIL, VALID_PASSWORD);

        // Act
        ResponseEntity<InstructorDTO> response = client.postForEntity("/Instructor", request, InstructorDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        InstructorDTO createdInstructor = response.getBody();
        assertNotNull(createdInstructor);
        assertEquals(VALID_USERNAME, createdInstructor.getName());
        assertEquals(VALID_EMAIL, createdInstructor.getEmail());

        assertTrue(createdInstructor.getId() > 0, "Response should have a positive ID.");
        assertEquals(LocalDate.now(), createdInstructor.getCreationDate());

        this.validId = createdInstructor.getId();
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
        assertEquals(VALID_USERNAME, Instructor.getName());
        assertEquals(VALID_EMAIL, Instructor.getEmail());
        

    }

    @Test
    @Order(3)
    public void testCreateInvalidInstructor() {
        // Set up
        InstructorRequestDto request = new InstructorRequestDto(VALID_USERNAME, VALID_EMAIL, INVALID_PASSWORD);

        // Act
        ResponseEntity<Error> response = client.postForEntity("/Instructor/create", request, Error.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Error body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getErrors().size());
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
    }

    @Test
    @Order(5)
    public void testReadAllInstructor(){
        // Set up
        String url = "/Instructor/all";

        // Actd
        ResponseEntity<Error> response = client.getForEntity(url, Error.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<InstructorDTO> Instructors = response.getBody();
        assertNotNull(Instructors);
        assertEquals(1, Instructors.size());
    }

    @Test
    @Order(6)
    public void testDeleteInstructorValidUsername() {
        // Set up
        String url = "/Instructor/delete" + this.VALID_USERNAME;

        // Act
        ResponseEntity<InstructorDTO> response = client.deleteForEntity(url, InstructorDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        InstructorDTO Instructor = response.getBody();
        assertNotNull(Instructor);
        assertEquals(VALID_USERNAME, Instructor.getName());
        assertEquals(VALID_EMAIL, Instructor.getEmail());
        assertEquals(VALID_PASSWORD, Instructor.getPassword());
    }

    @Test
    @Order(7)
    public void testDeleteInstructorByInvalidUsername() {
        // Set up
        String url = "/Instructor/delete" + this.INVALID_USERNAME;

        // Act
        ResponseEntity<Error> response = client.deleteForEntity(url, Error.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Error body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getErrors().size());
        assertEquals("Instructor name cannot be empty!", body.getErrors().get(0));
    }
    
    @Test
    @Order(8)
    public void testDeleteInstructorValidUsername() {
        // Set up
        String url = "/Instructor/delete/" + this.VALID_USERNAME;

        // Act
        ResponseEntity<InstructorDTO> response = client.deleteForEntity(url, InstructorDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        InstructorDTO Instructor = response.getBody();
        assertNotNull(Instructor);
        assertEquals(VALID_USERNAME, Instructor.getName());
        assertEquals(VALID_EMAIL, Instructor.getEmail());
        assertEquals(VALID_PASSWORD, Instructor.getPassword());
    }

    @Test
    @Order(9)
    public void testUpdateInstructorUsername() {
        // Set up
        String url = "/Instructor/update/" + this.VALID_USERNAME + "/" + this.VALID_USERNAME2 + "/" + this.VALID_EMAIL + "/" + THIS.VALID_PASSWORD;

        // Act
        ResponseEntity<InstructorDTO> response = client.putForEntity(url, InstructorDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        InstructorDTO Instructor = response.getBody();
        assertNotNull(Instructor);
        assertEquals(VALID_USERNAME2, Instructor.getName());
        assertEquals(VALID_EMAIL, Instructor.getEmail());
        assertEquals(VALID_PASSWORD, Instructor.getPassword());
    }

    @Test
    @Order(10)
    public void testUpdateInstructorEmail() {
        // Set up
        String url = "/Instructor/update/" + this.VALID_USERNAME + "/" + this.VALID_USERNAME + "/" + this.VALID_EMAIL2 + "/" + THIS.VALID_PASSWORD;

        // Act
        ResponseEntity<InstructorDTO> response = client.putForEntity(url, InstructorDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        InstructorDTO Instructor = response.getBody();
        assertNotNull(Instructor);
        assertEquals(VALID_USERNAME, Instructor.getName());
        assertEquals(VALID_EMAIL2, Instructor.getEmail());
        assertEquals(VALID_PASSWORD, Instructor.getPassword());
    }

    @Test
    @Order(11)
    public void testUpdateInstructorPassword() {
        // Set up
        String url = "/Instructor/update/" + this.VALID_USERNAME + "/" + this.VALID_USERNAME + "/" + this.VALID_EMAIL + "/" + THIS.VALID_PASSWORD2;

        // Act
        ResponseEntity<InstructorDTO> response = client.putForEntity(url, InstructorDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        InstructorDTO Instructor = response.getBody();
        assertNotNull(Instructor);
        assertEquals(VALID_USERNAME, Instructor.getName());
        assertEquals(VALID_EMAIL, Instructor.getEmail());
        assertEquals(VALID_PASSWORD2, Instructor.getPassword());
    }

    @Test
    @Order(12)
    public void testUpdateInstructorByInvalidUsername() {
        // Set up
        String url = "/Instructor/update/" + this.INVALID_USERNAME + "/" + this.INVALID_USERNAME + "/" + this.VALID_EMAIL + "/" + THIS.VALID_PASSWORD2;

        // Act
        ResponseEntity<Error> response = client.PUTForEntity(url, Error.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Error body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getErrors().size());
        assertEquals("Username cannot be empty!", body.getErrors().get(0));
    }

    @Test
    @Order(12)
    public void testUpdateInstructorByInvalidEmail() {
        // Set up
        String url = "/Instructor/update/" + this.VALID_USERNAME + "/" + this.VALID_USERNAME2 + "/" + this.INVALID_EMAIL + "/" + THIS.VALID_PASSWORD2;

        // Act
        ResponseEntity<Error> response = client.PUTForEntity(url, Error.class);

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
        ResponseEntity<Error> response = client.PUTForEntity(url, Error.class);

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
        ResponseEntity<Error> response = client.PUTForEntity(url, Error.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Error body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getErrors().size());
        assertEquals("Username is not unique!", body.getErrors().get(0));
    }

    





}