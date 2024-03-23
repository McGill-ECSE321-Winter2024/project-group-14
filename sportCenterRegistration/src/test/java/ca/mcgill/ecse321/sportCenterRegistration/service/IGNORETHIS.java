package ca.mcgill.ecse321.sportCenterRegistration.service; 


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.sportCenterRegistration.dao.CustomerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;

import ca.mcgill.ecse321.sportCenterRegistration.service.CustomerService;





import ca.mcgill.ecse321.sportCenterRegistration.dao.CustomerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;








@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

@Mock
private CustomerRepository CustomerDao;

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
    lenient().when(CustomerDao.findCustomerByUsername(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
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
		assertNull(service.getCustomer(NONEXISTING_Customer_USERNAME));
	}



}
