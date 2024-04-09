// package ca.mcgill.ecse321.sportCenterRegistration.integration;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.time.LocalDate;
// import java.util.List;

// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.MethodOrderer;
// import org.junit.jupiter.api.Order;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.TestInstance;
// import org.junit.jupiter.api.TestMethodOrder;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
// import org.springframework.boot.test.web.client.TestRestTemplate;
// import org.springframework.data.repository.CrudRepository;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;

// import ca.mcgill.ecse321.sportCenterRegistration.model.Owner;
// import ca.mcgill.ecse321.sportCenterRegistration.dao.OwnerRepository;
// import ca.mcgill.ecse321.sportCenterRegistration.dto.OwnerDTO;

// /*
// * @author Muhammad Hammad
// *
// * Integration test for Owner
// *
// * Note: The tests where we determine if the correct error was received could
// not be done because error.getErrors() was not working
// * I left them in thought because I believe the logic of the code works
// besides that but I commented them out to prevent compliation
// * errors, but do note that if you uncomment it that the order needs to be
// adjusted
// *
// */

// @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// public class OwnerIntegrationTests {
// @Autowired
// private TestRestTemplate client;

// @Autowired
// private OwnerRepository OwnerRepository;
// @BeforeEach
// @AfterEach
// public void clearDatabase() {
// OwnerRepository.deleteAll();
// }

// private final String VALID_USERNAME = "Hammad";
// private final String VALID_EMAIL = "Hammad@mail.mcgill.ca";
// private final String VALID_PASSWORD = "hammad123";
// private final String VALID_USERNAME2 = "Hammad2";
// private final String VALID_EMAIL2 = "Hamma2d@mail.mcgill.ca";
// private final String VALID_PASSWORD2 = "hammad1232";

// private final String INVALID_USERNAME = "";
// private final String INVALID_EMAIL = "email";
// private final String INVALID_PASSWORD = "";

// private int validId;

// @Test
// @Order(1)
// public void testCreateOwner() {
// // Set up

// String url = "/Owner/" + this.VALID_USERNAME + "/" + this.VALID_EMAIL + "/" +
// this.VALID_PASSWORD;

// // Act
// ResponseEntity<OwnerDTO> response = client.postForEntity(url, null ,
// OwnerDTO.class);

// // Assert
// assertNotNull(response);
// assertEquals(HttpStatus.CREATED, response.getStatusCode());
// OwnerDTO createdOwner = response.getBody();
// assertNotNull(createdOwner);
// assertEquals(VALID_USERNAME, createdOwner.getOwnerUsername());
// assertEquals(VALID_EMAIL, createdOwner.getOwnerEmail());

// }

// @Test
// @Order(2)
// public void testReadOwnerByValidUsername() {
// // Set up
// String url = "/Owner/" + this.VALID_USERNAME;

// // Act
// ResponseEntity<OwnerDTO> response = client.getForEntity(url, OwnerDTO.class);

// // Assert
// assertNotNull(response);
// assertEquals(HttpStatus.OK, response.getStatusCode());
// OwnerDTO Owner = response.getBody();
// assertNotNull(Owner);
// assertEquals(VALID_USERNAME, Owner.getOwnerUsername());
// assertEquals(VALID_EMAIL, Owner.getOwnerEmail());

// }
// /*
// @Test
// @Order(3)
// public void testCreateInvalidOwner() {
// // Set up
// String url = "/Owner/" + this.VALID_USERNAME + "/" + this.VALID_EMAIL + "/" +
// this.VALID_PASSWORD;

// // Act
// ResponseEntity<Error> response = client.postForEntity(url, null,
// Error.class);

// // Assert
// assertNotNull(response);
// assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
// Error body = response.getBody();
// assertNotNull(body);
// assertEquals(1, body.getErrors());
// assertEquals("Password cannot be empty", body.getErrors().get(0));

// }

// @Test
// @Order(4)
// public void testReadOwnerByInvalidUsername() {
// // Set up
// String url = "/Owner/" + this.INVALID_USERNAME;

// // Act
// ResponseEntity<Error> response = client.getForEntity(url, Error.class);

// // Assert
// assertNotNull(response);
// assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
// Error body = response.getBody();
// assertNotNull(body);
// assertEquals(1, body.getErrors().size());
// assertEquals("Owner name is invalid", body.getErrors().get(0));
// }*/

// @Test
// @Order(3)
// public void testReadAllOwner(){
// // Set up
// String url = "/Owner/all";

// // Actd
// ResponseEntity<OwnerDTO> response = client.getForEntity(url, OwnerDTO.class);

// // Assert
// assertNotNull(response);
// assertEquals(HttpStatus.OK, response.getStatusCode());
// List<OwnerDTO> Owners = (List<OwnerDTO>) response.getBody();
// assertNotNull(Owners);
// assertEquals(1, Owners.size());
// }

// @Test
// @Order(4)
// public void testDeleteOwnerValidUsername() {
// // Set up
// String url = "/Owner/delete" + this.VALID_USERNAME;

// // Act
// ResponseEntity<OwnerDTO> response = client.getForEntity(url, OwnerDTO.class);

// // Assert
// assertNotNull(response);
// assertEquals(HttpStatus.OK, response.getStatusCode());
// OwnerDTO Owner = response.getBody();
// assertNotNull(Owner);
// assertEquals(VALID_USERNAME, Owner.getOwnerUsername());
// assertEquals(VALID_EMAIL, Owner.getOwnerEmail());
// assertEquals(VALID_PASSWORD, Owner.getOwnerPassword());
// }

// /*
// @Test
// @Order(7)
// public void testDeleteOwnerByInvalidUsername() {
// // Set up
// String url = "/Owner/delete" + this.INVALID_USERNAME;

// // Act
// ResponseEntity<OwnerDTO> response = client.deleteForEntity(url, Error.class);

// // Assert
// assertNotNull(response);
// assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
// Error body = response.getBody();
// assertNotNull(body);
// assertEquals(1, body.getErrors().size());
// assertEquals("Owner name cannot be empty!", body.getErrors().get(0));
// }

// */
// @Test
// @Order(5)
// public void testUpdateOwnerUsername() {
// // Set up
// String url = "/Owner/update/" + this.VALID_USERNAME + "/" +
// this.VALID_USERNAME2 + "/" + this.VALID_EMAIL + "/" + this.VALID_PASSWORD;

// // Act
// ResponseEntity<OwnerDTO> response = client.getForEntity(url, OwnerDTO.class);

// // Assert
// assertNotNull(response);
// assertEquals(HttpStatus.OK, response.getStatusCode());
// OwnerDTO Owner = response.getBody();
// assertNotNull(Owner);
// assertEquals(VALID_USERNAME2, Owner.getOwnerUsername());
// assertEquals(VALID_EMAIL, Owner.getOwnerEmail());
// assertEquals(VALID_PASSWORD, Owner.getOwnerPassword());
// }

// @Test
// @Order(6)
// public void testUpdateOwnerEmail() {
// // Set up
// String url = "/Owner/update/" + this.VALID_USERNAME + "/" +
// this.VALID_USERNAME + "/" + this.VALID_EMAIL2 + "/" + this.VALID_PASSWORD;

// // Act
// ResponseEntity<OwnerDTO> response = client.getForEntity(url, OwnerDTO.class);

// // Assert
// assertNotNull(response);
// assertEquals(HttpStatus.OK, response.getStatusCode());
// OwnerDTO Owner = response.getBody();
// assertNotNull(Owner);
// assertEquals(VALID_USERNAME, Owner.getOwnerUsername());
// assertEquals(VALID_EMAIL2, Owner.getOwnerEmail());
// assertEquals(VALID_PASSWORD, Owner.getOwnerPassword());
// }

// @Test
// @Order(7)
// public void testUpdateOwnerPassword() {
// // Set up
// String url = "/Owner/update/" + this.VALID_USERNAME + "/" +
// this.VALID_USERNAME + "/" + this.VALID_EMAIL + "/" + this.VALID_PASSWORD2;

// // Act
// ResponseEntity<OwnerDTO> response = client.getForEntity(url, OwnerDTO.class);

// // Assert
// assertNotNull(response);
// assertEquals(HttpStatus.OK, response.getStatusCode());
// OwnerDTO Owner = response.getBody();
// assertNotNull(Owner);
// assertEquals(VALID_USERNAME, Owner.getOwnerUsername());
// assertEquals(VALID_EMAIL, Owner.getOwnerEmail());
// assertEquals(VALID_PASSWORD2, Owner.getOwnerPassword());
// }
// /*
// @Test
// @Order(12)
// public void testUpdateOwnerByInvalidUsername() {
// // Set up
// String url = "/Owner/update/" + this.INVALID_USERNAME + "/" +
// this.INVALID_USERNAME + "/" + this.VALID_EMAIL + "/" + THIS.VALID_PASSWORD2;

// // Act
// ResponseEntity<Error> response = client.getForEntity(url, Error.class);

// // Assert
// assertNotNull(response);
// assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
// Error body = response.getBody();
// assertNotNull(body);
// assertEquals(1, body.getErrors().size());
// assertEquals("Username cannot be empty!", body.getErrors().get(0));
// }
// */
// /*@Test
// @Order(12)
// public void testUpdateOwnerByInvalidEmail() {
// // Set up
// String url = "/Owner/update/" + this.VALID_USERNAME + "/" +
// this.VALID_USERNAME2 + "/" + this.INVALID_EMAIL + "/" + THIS.VALID_PASSWORD2;

// // Act
// ResponseEntity<Error> response = client.getForEntity(url, Error.class);

// // Assert
// assertNotNull(response);
// assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
// Error body = response.getBody();
// assertNotNull(body);
// assertEquals(1, body.getErrors().size());
// assertEquals("Email is invalid!", body.getErrors().get(0));
// }

// @Test
// @Order(12)
// public void testUpdateOwnerByInvalidPassword() {
// // Set up
// String url = "/Owner/update/" + this.VALID_USERNAME + "/" +
// this.VALID_USERNAME2 + "/" + this.VALID_EMAIL + "/" + THIS.INVALID_PASSWORD2;

// // Act
// ResponseEntity<OwnerDTO> response = client.getForEntity(url, Error.class);

// // Assert
// assertNotNull(response);
// assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
// Error body = response.getBody();
// assertNotNull(body);
// assertEquals(1, body.getErrors().size());
// assertEquals("Password cannot be empty!", body.getErrors().get(0));
// }

// @Test
// @Order(12)
// public void testUpdateOwnerByNonUniqueUsername() {
// // Set up
// String url = "/Owner/update/" + this.VALID_USERNAME + "/" +
// this.VALID_USERNAME + "/" + this.VALID_EMAIL + "/" + THIS.INVALID_PASSWORD2;

// // Act
// ResponseEntity<OwnerDTO> response = client.getForEntity(url, Error.class);

// // Assert
// assertNotNull(response);
// assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
// Error body = response.getBody();
// assertNotNull(body);
// assertEquals(1, body.getErrors().size());
// assertEquals("Username is not unique!", body.getErrors().get(0));
// }
// */

// }