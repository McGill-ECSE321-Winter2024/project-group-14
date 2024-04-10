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
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.sportCenterRegistration.dao.CustomerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dto.CustomerDTO;


/*
* @author Muhammad Hammad
*
* Integration test for Customer
*
* Note: The tests where we determine if the correct error was received could not be done because error.getErrors() was not working
* I left them in thought because I believe the logic of the code works besides that but I commented them out to prevent compliation
* errors, but do note that if you uncomment it that the order needs to be adjusted
*
*/


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerIntegrationTests {
   @Autowired
   private TestRestTemplate client;


	// @Autowired
	// private CustomerRepository CustomerRepository;

   // @BeforeEach
	// @AfterEach
	// public void clearDatabase() {
   //     CustomerRepository.deleteAll();
	// }

   private final String VALID_USERNAME = "Hammad";
   private final String VALID_EMAIL = "Hammad@mail.mcgill.ca";
   private final String VALID_PASSWORD = "hammad123";
   private final String VALID_USERNAME2 = "Hammad2";
   private final String VALID_EMAIL2 = "Hammad2@mail.mcgill.ca";
   private final String VALID_PASSWORD2 = "hammad1232";



   private final String INVALID_USERNAME = " ";
   private final String INVALID_EMAIL = "email";
   private final String INVALID_PASSWORD = " ";




   @Test
   @Order(1)
   public void testCreateCustomer() {
       // Set up

       String url = "/customer/" + this.VALID_USERNAME + "/" + this.VALID_EMAIL + "/" + this.VALID_PASSWORD;
       // Act
       ResponseEntity<CustomerDTO> response = client.postForEntity(url, null , CustomerDTO.class);
       // Assert

       assertEquals(HttpStatus.OK, response.getStatusCode());

       CustomerDTO createdCustomer = response.getBody();
       assertNotNull(createdCustomer);
       assertEquals(VALID_USERNAME, createdCustomer.getUsername());
       assertEquals(VALID_EMAIL, createdCustomer.getEmail());
       assertEquals(VALID_PASSWORD, createdCustomer.getPassword());

   }

   @Test
   @Order(2)
   public void testReadCustomerByValidUsername() {
       // Set up
       String url = "/customer/" + this.VALID_USERNAME;

       // Act
       ResponseEntity<CustomerDTO> response = client.getForEntity(url, CustomerDTO.class);

       // Assert
       assertNotNull(response);
       assertEquals(HttpStatus.OK, response.getStatusCode());
       CustomerDTO Customer = response.getBody();
       assertNotNull(Customer);
       assertEquals(VALID_USERNAME, Customer.getUsername());
       assertEquals(VALID_EMAIL, Customer.getEmail());

   }



   @Test
   @Order(3)
   public void testCreateInvalidCustomer() {
       // Set up
       String url = "/customer/" + this.VALID_USERNAME + "/" + this.VALID_EMAIL + "/" + this.VALID_PASSWORD;

       // Act
       ResponseEntity<String> response = client.postForEntity(url, null, String.class);

       // Assert
       assertNotNull(response);
       assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

   }

   @Test
   @Order(4)
   public void testReadCustomerByInvalidUsername() {
       // Set up
       String url = "/customer/" + this.INVALID_USERNAME;

       // Act
       ResponseEntity<String> response = client.getForEntity(url, String.class);

       // Assert
       assertNotNull(response);
       assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
   }

   @Test
   @Order(5)
   public void testReadAllCustomer(){
       // Set up
       String url = "/customer/all";

       // Actd
       ResponseEntity<?> response = client.exchange(url, HttpMethod.GET, null, List.class);

       // Assert
       assertNotNull(response);
       assertEquals(HttpStatus.OK, response.getStatusCode());
       List<CustomerDTO> customers = (List<CustomerDTO>) response.getBody();
       System.out.println(customers.size());
       assertNotNull(customers);
       assertEquals(1, customers.size());
   }

   @Test
   @Order(6)
   public void testDeleteCustomer() {
       // Set up
       String url = "/customer/" + this.VALID_USERNAME;

      
       // Act
       ResponseEntity<CustomerDTO> response = client.exchange(url, HttpMethod.DELETE, null, CustomerDTO.class);

       // Assert
       assertNotNull(response);
       assertEquals(HttpStatus.OK, response.getStatusCode());
       CustomerDTO Customer = response.getBody();
       assertNotNull(Customer);
       assertEquals(VALID_USERNAME, Customer.getUsername());
       assertEquals(VALID_EMAIL, Customer.getEmail());
       assertEquals(VALID_PASSWORD, Customer.getPassword());
   }

   @Test
   @Order(7)
   public void createCustomer() {
       // Set up
       String url = "/customer/" + this.VALID_USERNAME + "/" + this.VALID_EMAIL + "/" + this.VALID_PASSWORD;

       // Act
       ResponseEntity<CustomerDTO> response = client.postForEntity(url, null, CustomerDTO.class);
   }


   @Test
   @Order(8)
   public void testDeleteCustomerByInvalidUsername() {
       // Set up
       String url = "/customer/" + this.INVALID_USERNAME;

       // Act
       ResponseEntity<String> response = client.exchange(url, HttpMethod.DELETE, null, String.class);

       // Assert
       assertNotNull(response);
       assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

   }



   @Test
   @Order(9)
   public void testUpdateCustomerUsername() {
       // Set up
       String url = "/customer/update/" + this.VALID_USERNAME + "/" + this.VALID_USERNAME2 + "/" + this.VALID_EMAIL + "/" + this.VALID_PASSWORD;

       // Act
       ResponseEntity<CustomerDTO> response = client.exchange(url, HttpMethod.PUT, null, CustomerDTO.class);

       // Assert
       assertNotNull(response);
       assertEquals(HttpStatus.OK, response.getStatusCode());
       CustomerDTO Customer = response.getBody();
       assertNotNull(Customer);
       assertEquals(VALID_USERNAME2, Customer.getUsername());
       assertEquals(VALID_EMAIL, Customer.getEmail());
       assertEquals(VALID_PASSWORD, Customer.getPassword());
   }

   @Test
   @Order(10)
   public void testUpdateCustomerEmail() {
       // Set up
       String url = "/customer/update/" + this.VALID_USERNAME2 + "/" + this.VALID_USERNAME2 + "/" + this.VALID_EMAIL2 + "/" + this.VALID_PASSWORD;

       // Act
       ResponseEntity<CustomerDTO> response = client.exchange(url, HttpMethod.PUT, null, CustomerDTO.class);
       // Assert
       assertNotNull(response);
       assertEquals(HttpStatus.OK, response.getStatusCode());
       CustomerDTO Customer = response.getBody();
       assertNotNull(Customer);
       assertEquals(VALID_USERNAME2, Customer.getUsername());
       assertEquals(VALID_EMAIL2, Customer.getEmail());
       assertEquals(VALID_PASSWORD, Customer.getPassword());
   }

   @Test
   @Order(11)
   public void testUpdateCustomerPassword() {
       // Set up
       String url = "/customer/update/" + this.VALID_USERNAME2 + "/" + this.VALID_USERNAME2 + "/" + this.VALID_EMAIL + "/" + this.VALID_PASSWORD2;

       // Act
       ResponseEntity<CustomerDTO> response = client.exchange(url, HttpMethod.PUT, null, CustomerDTO.class);

       // Assert
       assertNotNull(response);
       assertEquals(HttpStatus.OK, response.getStatusCode());
       CustomerDTO Customer = response.getBody();
       assertNotNull(Customer);
       assertEquals(VALID_USERNAME2, Customer.getUsername());
       assertEquals(VALID_EMAIL, Customer.getEmail());
       assertEquals(VALID_PASSWORD2, Customer.getPassword());
   }

   @Test
   @Order(12)
   public void testUpdateCustomerByInvalidUsername() {
       // Set up
       String url = "/customer/update/" + this.INVALID_USERNAME + "/" + this.INVALID_USERNAME + "/" + this.VALID_EMAIL + "/" + this.VALID_PASSWORD2;

       // Act
       ResponseEntity<String> response = client.exchange(url, HttpMethod.PUT, null, String.class);

       // Assert
       assertNotNull(response);
       assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
       String body = response.getBody();
       assertNotNull(body);
       assertEquals("Customer name is invalid", body);
   }

   @Test
   @Order(13)
   public void testUpdateCustomerByInvalidEmail() {
       // Set up
       String url = "/customer/update/" + this.VALID_USERNAME2 + "/" + this.VALID_USERNAME + "/" + this.INVALID_EMAIL + "/" + this.VALID_PASSWORD2;

       // Act
       ResponseEntity<String> response = client.exchange(url, HttpMethod.PUT, null, String.class);

       // Assert
       assertNotNull(response);
       assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
       String body = response.getBody();
       assertNotNull(body);
       assertEquals("Email is invalid!", body);
   }

   @Test
   @Order(14)
   public void testUpdateCustomerByInvalidPassword() {
       // Set up
       String url = "/customer/update/" + this.VALID_USERNAME2 + "/" + this.VALID_USERNAME + "/" + this.VALID_EMAIL + "/" + this.INVALID_PASSWORD;

       // Act
       ResponseEntity<String> response = client.exchange(url, HttpMethod.PUT, null, String.class);

       // Assert
       assertNotNull(response);
       assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
       String body = response.getBody();
       assertNotNull(body);
       assertEquals("Password cannot be empty!", body);
   }


}