package ca.mcgill.ecse321.sportCenterRegistration.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;

@SpringBootTest
public class CustomerRepositoryTest{
    @Autowired
    private CustomerRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateAndReadCustomer(){
        // Create Customer
        String username = "customer1";
        String email = "customer1@gmail.com";
        String password = "customer1";
        Customer customer = new Customer(username, email, password);

        // Save in the database
        customer = repo.save(customer);
        int customerId = customer.getId();

        // Read from database
//find the customer by their username 
        Customer result = repo.findCustomerByUsername(username);

        // check objects
        assertNotNull(result);

        // check attributes
        assertEquals(customerId, result.getId());
        assertEquals(username, result.getUsername());
        assertEquals(email, result.getEmail());
        assertEquals(password, result.getPassword());

    }
}