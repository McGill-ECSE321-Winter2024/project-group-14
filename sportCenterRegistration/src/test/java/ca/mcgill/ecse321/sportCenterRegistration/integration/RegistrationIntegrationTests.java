package ca.mcgill.ecse321.sportCenterRegistration.integration;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import ca.mcgill.ecse321.sportCenterRegistration.model.Account;
import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class RegistrationIntegrationTests {
    @Autowired
    private TestRestTemplate client;

    private static final String VALID_CUSTOMER_NAME = "ValidAccount";
    private static final String VALID_CUSTOMER_EMAIL = "valid@mail.mcgill.ca";
    private static final String VALID_CUSTOMER_PASSWORD = "password123";
    private static final String INVALID_CUSTOMER_PASSWORD = "123";

    private static final Account VALID_CUSTOMER = new Customer(VALID_CUSTOMER_NAME, VALID_CUSTOMER_EMAIL,
            VALID_CUSTOMER_PASSWORD);

    private static final String VALID_INSTRUCTOR_NAME = "";
}