package ca.mcgill.ecse321.sportCenterRegistration.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.sportCenterRegistration.dao.CustomerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;



@ExtendWith(MockitoExtension.class)
public class TestCustomerService {

@Mock
private CustomerRepository customerDao;

@InjectMocks
private CustomerService service;

private static final String Customer_USERNAME = "TestCustomerUsername";
private static final String Customer_EMAIL = "TestCustomerEmail";
private static final String Customer_PASSWORD = "TestCustomerPassword";

private static final String NONEXISTING_Customer_USERNAME = "NotAnCustomerUsername";
private static final String NONEXISTING_Customer_EMAIL = "NotAnCustomerEmail";
private static final String NONEXISTING_Customer_PASSWORD = "NotAnCustomerPassword";

@BeforeEach
public void setMockOutput() {
    lenient().when(customerDao.findCustomerByUsername(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
        if(invocation.getArgument(0).equals(Customer_USERNAME)) {
            Customer Customer = new Customer(Customer_USERNAME, Customer_EMAIL, Customer_PASSWORD);
            return Customer;
        } else {
            return null;
        }
    });
}


@Test
public void testGetExistingCustomer() {
    assertEquals(Customer_USERNAME, service.getCustomer(Customer_USERNAME).getUsername());
    assertEquals(Customer_EMAIL, service.getCustomer(Customer_USERNAME).getEmail());
    assertEquals(Customer_PASSWORD, service.getCustomer(Customer_USERNAME).getPassword());
}


@Test
public void testGetNonExistingCustomer() {

    assertThrows(IllegalArgumentException.class, () -> {
        service.getCustomer(NONEXISTING_Customer_USERNAME);
    });

}

@Test
public void testCreateCustomer() {
		assertEquals(0, service.getAllCustomers().size());

		String username = "Muhammad";
        String email = "Memail@gmail.com";
        String password = "Mpass";
		Customer customer = null;
		try {
			customer = service.createCustomer(username, email, password);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(customer);
		assertEquals(username, customer.getUsername());
        assertEquals(email, customer.getEmail());
        assertEquals(password, customer.getPassword());
    }

    @Test
	public void testCreateCustomerNullUsername() {
		String username = null;
		String email = "email@gmail.com";
		String password = "password";
        Customer customer = null;
        String error = null;
		try {
            customer = service.createCustomer(username, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		
		assertEquals("Username cannot be empty!", error);
	}

    @Test
	public void testCreateCustomerNullEmail() {
		String username = "username";
		String email = null;
		String password = "password";
        Customer customer = null;
        String error = null;
		try {
            customer = service.createCustomer(username, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		
		assertEquals("Email cannot be empty!", error);
	}

    @Test
	public void testCreateCustomerNullPassword() {
		String username = "username";
		String email = "email@gmail.com";
        String password = null;
        Customer customer = null;
        String error = null;
		try {
            customer = service.createCustomer(username, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		
		assertEquals("Password cannot be empty!", error);
	}

	@Test
	public void testCreateCustomerInvalidEmail() {
		String username = "username";
		String email = "email";
        String password = "password";
        Customer customer = null;
        String error = null;
		try {
            customer = service.createCustomer(username, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		
		assertEquals("Email is invalid!", error);
	}

//doing this rn
	@Test
	public void testCreateCustomerInvalidUsername() {
		String email = "email@gmail.com";
        String password = "password";
        String error = null;

		try {
            service.createCustomer(Customer_USERNAME, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Username is not unique!", error);
	}



    @Test
	public void testCreateCustomerEmptyUsername() {
		String username = "";
		String email = "email@gmail.com";
		String password = "password";
        Customer customer = null;
        String error = null;
		try {
            customer = service.createCustomer(username, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		
		assertEquals("Username cannot be empty!", error);
	}

    @Test
	public void testCreateCustomerEmptyEmail() {
		String username = "username";
		String email = "";
		String password = "password";
        Customer customer = null;
        String error = null;
		try {
            customer = service.createCustomer(username, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		
		assertEquals("Email cannot be empty!", error);
	}


    @Test
	public void testCreateCustomerEmptyPassword() {
		String username = "username";
		String email = "email@gmail.com";
		String password = "";
        Customer customer = null;
        String error = null;
		try {
            customer = service.createCustomer(username, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		
		assertEquals("Password cannot be empty!", error);
	}
    

    //the below test probably isnt working because the projcet isnt compiling properly
    @Test
	public void testDeleteCustomer() {
		
		Customer customerDelete = null;

		String error = null;
		try {
        	customerDelete = service.deleteCustomer(Customer_USERNAME);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(Customer_USERNAME, customerDelete.getUsername());
		assertEquals(Customer_EMAIL, customerDelete.getEmail());
		assertEquals(Customer_PASSWORD, customerDelete.getPassword());

	}


	@Test
	public void testDeleteCustomerNullUsername() {
		
		Customer customerDelete = null;

		String error = null;
		try {
        	customerDelete = service.deleteCustomer(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Username cannot be empty!", error);

	}

	@Test
	public void testDeleteCustomerInvalidUsername() {
		
		Customer customerDelete = null;

		String error = null;
		try {
        	customerDelete = service.deleteCustomer("");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Username cannot be empty!", error);

	}

	@Test
	public void testDeleteCustomerNonExistingdUsername() {
		
		Customer customerDelete = null;

		String error = null;
		try {
        	customerDelete = service.deleteCustomer("nonexistingusername");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Customer name is invalid", error);

	}
	@Test
	public void toList() {
		String myString = "iterable";
		List<Character> charList = new ArrayList<Character>();
		String error = null;


		for (char c: myString.toCharArray()){
			charList.add(c);
		}
		
		try {
        	List<Character> customerList = service.toList(charList);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

	}

	@Test
	public void testGetAllCustomers() {
		List<Customer> customerList = null;
		String error = null;
		String username = "username";
		String email = "email@gmail.com";
		String password = "password";
		
		try {
			service.createCustomer(username, email, password);
        	customerList = service.getAllCustomers();
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(Customer_USERNAME, customerList.get(0).getUsername());
		assertEquals(Customer_EMAIL, customerList.get(0).getEmail());
		assertEquals(Customer_PASSWORD, customerList.get(0).getPassword());

	}




    


    




}