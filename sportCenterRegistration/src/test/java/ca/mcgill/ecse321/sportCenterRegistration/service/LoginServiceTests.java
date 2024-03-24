package ca.mcgill.ecse321.sportCenterRegistration.service;

import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportCenterRegistration.dao.CustomerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.InstructorRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.OwnerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.SessionRepository;
import ca.mcgill.ecse321.sportCenterRegistration.model.Account;
import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.Owner;
import ca.mcgill.ecse321.sportCenterRegistration.model.Session;
import ca.mcgill.ecse321.sportCenterRegistration.model.SportClass;
import ca.mcgill.ecse321.sportCenterRegistration.service.LoginService;


@SpringBootTest
public class LoginServiceTests{
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
        lenient().when(accountRepo.findCustomerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TEST_EMAIL)) {
                Customer customer = new Customer(TEST_NAME, TEST_EMAIL, TEST_PASSWORD);
                return customer;
            } else {
                return null;
            }}
        );
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
        String error =null;
        try {
            customer = loginService.login(email,password);
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
        String error =null;
        try {
            customer = loginService.login(email,password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(customer);
        assertEquals("Incorrect password", error);
    }


}