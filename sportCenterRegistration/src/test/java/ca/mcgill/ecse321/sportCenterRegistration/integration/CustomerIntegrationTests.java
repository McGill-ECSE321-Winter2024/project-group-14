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

import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;
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
public class CustomerIntegrationTests {
    @Autowired
    private TestRestTemplate client;


	@Autowired
	private CustomerRepository CustomerRepository;
    @BeforeEach
	@AfterEach
	public void clearDatabase() {
        CustomerRepository.deleteAll();
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
    public void testCreateCustomer() {
        // Set up
       
        String url = "/Customer/" + this.VALID_USERNAME + "/" + this.VALID_EMAIL + "/" + this.VALID_PASSWORD;

        // Act
        ResponseEntity<CustomerDTO> response = client.postForEntity(url, null , CustomerDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        CustomerDTO createdCustomer = response.getBody();
        assertNotNull(createdCustomer);
        assertEquals(VALID_USERNAME, createdCustomer.getCustomerUsername());
        assertEquals(VALID_EMAIL, createdCustomer.getCustomerEmail());


    }

    @Test
    @Order(2)
    public void testReadCustomerByValidUsername() {
        // Set up
        String url = "/Customer/" + this.VALID_USERNAME;

        // Act
        ResponseEntity<CustomerDTO> response = client.getForEntity(url, CustomerDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        CustomerDTO Customer = response.getBody();
        assertNotNull(Customer);
        assertEquals(VALID_USERNAME, Customer.getCustomerUsername());
        assertEquals(VALID_EMAIL, Customer.getCustomerEmail());
        

    }
/* 
    @Test
    @Order(3)
    public void testCreateInvalidCustomer() {
        // Set up
        String url = "/Customer/" + this.VALID_USERNAME + "/" + this.VALID_EMAIL + "/" + this.VALID_PASSWORD;

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
    public void testReadCustomerByInvalidUsername() {
        // Set up
        String url = "/Customer/" + this.INVALID_USERNAME;

        // Act
        ResponseEntity<Error> response = client.getForEntity(url, Error.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Error body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getErrors().size());
        assertEquals("Customer name is invalid", body.getErrors().get(0));
    }*/

    @Test
    @Order(3)
    public void testReadAllCustomer(){
        // Set up
        String url = "/Customer/all";

        // Actd
        ResponseEntity<CustomerDTO> response = client.getForEntity(url, CustomerDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<CustomerDTO> Customers = (List<CustomerDTO>) response.getBody();
        assertNotNull(Customers);
        assertEquals(1, Customers.size());
    }

    @Test
    @Order(4)
    public void testDeleteCustomerValidUsername() {
        // Set up
        String url = "/Customer/delete" + this.VALID_USERNAME;

        // Act
        ResponseEntity<CustomerDTO> response = client.getForEntity(url, CustomerDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        CustomerDTO Customer = response.getBody();
        assertNotNull(Customer);
        assertEquals(VALID_USERNAME, Customer.getCustomerUsername());
        assertEquals(VALID_EMAIL, Customer.getCustomerEmail());
        assertEquals(VALID_PASSWORD, Customer.getCustomerPassword());
    }

    /* 
    @Test
    @Order(7)
    public void testDeleteCustomerByInvalidUsername() {
        // Set up
        String url = "/Customer/delete" + this.INVALID_USERNAME;

        // Act
        ResponseEntity<CustomerDTO> response = client.deleteForEntity(url, Error.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Error body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getErrors().size());
        assertEquals("Customer name cannot be empty!", body.getErrors().get(0));
    }
    
   
*/
    @Test
    @Order(5)
    public void testUpdateCustomerUsername() {
        // Set up
        String url = "/Customer/update/" + this.VALID_USERNAME + "/" + this.VALID_USERNAME2 + "/" + this.VALID_EMAIL + "/" + this.VALID_PASSWORD;

        // Act
        ResponseEntity<CustomerDTO> response = client.getForEntity(url, CustomerDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        CustomerDTO Customer = response.getBody();
        assertNotNull(Customer);
        assertEquals(VALID_USERNAME2, Customer.getCustomerUsername());
        assertEquals(VALID_EMAIL, Customer.getCustomerEmail());
        assertEquals(VALID_PASSWORD, Customer.getCustomerPassword());
    }

    @Test
    @Order(6)
    public void testUpdateCustomerEmail() {
        // Set up
        String url = "/Customer/update/" + this.VALID_USERNAME + "/" + this.VALID_USERNAME + "/" + this.VALID_EMAIL2 + "/" + this.VALID_PASSWORD;

        // Act
        ResponseEntity<CustomerDTO> response = client.getForEntity(url, CustomerDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        CustomerDTO Customer = response.getBody();
        assertNotNull(Customer);
        assertEquals(VALID_USERNAME, Customer.getCustomerUsername());
        assertEquals(VALID_EMAIL2, Customer.getCustomerEmail());
        assertEquals(VALID_PASSWORD, Customer.getCustomerPassword());
    }

    @Test
    @Order(7)
    public void testUpdateCustomerPassword() {
        // Set up
        String url = "/Customer/update/" + this.VALID_USERNAME + "/" + this.VALID_USERNAME + "/" + this.VALID_EMAIL + "/" + this.VALID_PASSWORD2;

        // Act
        ResponseEntity<CustomerDTO> response = client.getForEntity(url, CustomerDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        CustomerDTO Customer = response.getBody();
        assertNotNull(Customer);
        assertEquals(VALID_USERNAME, Customer.getCustomerUsername());
        assertEquals(VALID_EMAIL, Customer.getCustomerEmail());
        assertEquals(VALID_PASSWORD2, Customer.getCustomerPassword());
    }
/* 
    @Test
    @Order(12)
    public void testUpdateCustomerByInvalidUsername() {
        // Set up
        String url = "/Customer/update/" + this.INVALID_USERNAME + "/" + this.INVALID_USERNAME + "/" + this.VALID_EMAIL + "/" + THIS.VALID_PASSWORD2;

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
    public void testUpdateCustomerByInvalidEmail() {
        // Set up
        String url = "/Customer/update/" + this.VALID_USERNAME + "/" + this.VALID_USERNAME2 + "/" + this.INVALID_EMAIL + "/" + THIS.VALID_PASSWORD2;

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
    public void testUpdateCustomerByInvalidPassword() {
        // Set up
        String url = "/Customer/update/" + this.VALID_USERNAME + "/" + this.VALID_USERNAME2 + "/" + this.VALID_EMAIL + "/" + THIS.INVALID_PASSWORD2;

        // Act
        ResponseEntity<CustomerDTO> response = client.getForEntity(url, Error.class);

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
    public void testUpdateCustomerByNonUniqueUsername() {
        // Set up
        String url = "/Customer/update/" + this.VALID_USERNAME + "/" + this.VALID_USERNAME + "/" + this.VALID_EMAIL + "/" + THIS.INVALID_PASSWORD2;

        // Act
        ResponseEntity<CustomerDTO> response = client.getForEntity(url, Error.class);

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