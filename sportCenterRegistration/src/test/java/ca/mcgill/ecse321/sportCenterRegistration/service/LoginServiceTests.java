package ca.mcgill.ecse321.sportCenterRegistration.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportCenterRegistration.dao.CustomerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.InstructorRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.OwnerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.model.Account;
import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;

@SpringBootTest
public class LoginServiceTests {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private LoginService loginService;

    private static final String TEST_NAME = "TEST_NAME";
    private static final String TEST_EMAIL = "test@gmail.com";
    private static final String TEST_PASSWORD = "password";

    @BeforeEach
    public void setMockOutput() {
        // Mock the customerRepository
        lenient().when(customerRepository.findCustomerByEmail(anyString()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(TEST_EMAIL)) {
                        Customer customer = new Customer(TEST_NAME, TEST_EMAIL, TEST_PASSWORD);
                        return customer;
                    } else {
                        return null;
                    }
                });
    }

    @Test
    public void testCustomerLogin() {
        Account customer = null;
        String email = "test@gmail.com";
        String password = "password";
        try {
            customer = loginService.login(email, password);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(customer);
    }

    @Test
    public void testCustomerLoginInvalidEmail() {
        Account customer = null;
        String email = "wrong@gmail.com";
        String password = "password";
        String error = null;
        try {
            customer = loginService.login(email, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(customer);
        assertEquals("Invalid email", error);
    }

    @Test
    public void testCustomerLoginInvalidPassword() {
        Account customer = null;
        String email = "test@gmail.com";
        String password = "wrong";
        String error = null;
        try {
            customer = loginService.login(email, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(customer);
        assertEquals("Incorrect password", error);
    }

}