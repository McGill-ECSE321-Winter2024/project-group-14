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

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;

import ca.mcgill.ecse321.sportCenterRegistration.dao.CustomerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;



@ExtendWith(MockitoExtension.class)
public class CustomerServiceTests {
	@Mock
	private CustomerRepository customerDao;

	@InjectMocks
	private CustomerService service;

	private static final String Customer_USERNAME = "TestCustomerUsername";
	private static final String Customer_EMAIL = "TestCustomerEmail@gmail.com";
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

		lenient().when(customerDao.save(any(Customer.class))).thenAnswer(
				(InvocationOnMock invocation) -> invocation.getArgument(0)
		);
	}

	/*
	 * @author Muhammad Hammad
	 *  Method that tests getting an existing customer
	 *
	 */
	@Test
	public void testGetExistingCustomer() {
		assertEquals(Customer_USERNAME, service.getCustomer(Customer_USERNAME).getUsername());
		assertEquals(Customer_EMAIL, service.getCustomer(Customer_USERNAME).getEmail());
		assertEquals(Customer_PASSWORD, service.getCustomer(Customer_USERNAME).getPassword());
	}

	/*
	 * @author Muhammad Hammad
	 *  Method that tests if there is an error frmo getting a non existing customer
	 *
	 */
	@Test
	public void testGetNonExistingCustomer() {

		assertThrows(IllegalArgumentException.class, () -> {
			service.getCustomer(NONEXISTING_Customer_USERNAME);
		});

	}
	/*
	 * @author Muhammad Hammad
	 *  Method that tests of creating customer
	 *
	 */
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
	/*
	 * @author Muhammad Hammad
	 *  Method that tests if there is an error in creating a null username customer
	 *
	 */
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
	/*
	 * @author Muhammad Hammad
	 *  Method that tests if there is an error in creating a null email customer
	 *
	 */
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
	/*
	 * @author Muhammad Hammad
	 *  Method that tests if there is an error in creating a null password customer
	 *
	 */
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
	/*
	 * @author Muhammad Hammad
	 *  Method that tests if there is an error in creating an invalid email customer
	 *
	 */
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

	/*
	 * @author Muhammad Hammad
	 *  Method that tests if there is an error in creating an invalid  username customer
	 *
	 */	@Test
		public void testCreateCustomerInvalidUsername() {
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

	/*
	 * @author Muhammad Hammad
	 *  Method that tests if there is an error in creating an empty username customer
	 *
	 */
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
	/*
	 * @author Muhammad Hammad
	 *  Method that tests if there is an error in creating an empty email customer
	 *
	 */
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

	/*
	 * @author Muhammad Hammad
	 *  Method that tests if there is an error in creating an empty password customer
	 *
	 */
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


	/*
	 * @author Muhammad Hammad
	 *  Method that tests deleting a customer
	 *
	 */    @Test
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

	/*
	 * @author Muhammad Hammad
	 *  Method that tests if there is an error in deleting a null username customer
	 *
	 */
		@Test
		public void testDeleteCustomerNullUsername() {

			Customer customerDelete = null;

			String error = null;
			try {
				customerDelete = service.deleteCustomer(null);
			} catch (IllegalArgumentException e) {
				error = e.getMessage();
			}

			assertEquals("Customer name cannot be empty!", error);

		}
	/*
	 * @author Muhammad Hammad
	 *  Method that tests if there is an error in deleting an invalid username customer
	 *
	 */
		@Test
		public void testDeleteCustomerInvalidUsername() {

			Customer customerDelete = null;

			String error = null;
			try {
				customerDelete = service.deleteCustomer("");
			} catch (IllegalArgumentException e) {
				error = e.getMessage();
			}

			assertEquals("Customer name cannot be empty!", error);

		}
	/*
	 * @author Muhammad Hammad
	 *  Method that tests if there is an error in deleting a non existing username customer
	 *
	 */
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
	/*
	 * @author Muhammad Hammad
	 *
	 * The below method tests getting all customers
	 */

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



	}
	/*
	 * @author Muhammad Hammad
	 *
	 * checks to see if a customers information can be updated sucessfully
	 */

	@Test
	public void testUpdateCustomer() {
		Customer updatedCustomer = null;
		String error = null;

		String newUsername = "newUsername";
		String newEmail = "newEmail@gmail.com";
		String newPassword = "newPassword";

		try {
        	updatedCustomer = service.updateCustomer(Customer_USERNAME, newUsername, newEmail, newPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(newUsername, updatedCustomer.getUsername());
		assertEquals(newEmail, updatedCustomer.getEmail());
		assertEquals(newPassword, updatedCustomer.getPassword());

	}

	/*
	 * @author Muhammad Hammad
	 *
	 * checks to see if an error is thrown when an invalid suername is provided
	 */
	@Test
	public void testUpdateCustomerInvalidUsername() {
		Customer updatedCustomer = null;
		String error = null;

		String newUsername = "";
		String newEmail = "newEmail@gmail.com";
		String newPassword = "newPassword";

		try {
        	updatedCustomer = service.updateCustomer(Customer_USERNAME, newUsername, newEmail, newPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(error, "Username cannot be empty!");

 	 }


	 	/*
	 * @author Muhammad Hammad
	 *
	 * checks to see if an error is thrown when an invalid suername is provided
	 */
	@Test
	public void testUpdateCustomerInvalidUsernameNull() {
		Customer updatedCustomer = null;
		String error = null;

		String newUsername = null;
		String newEmail = "newEmail@gmail.com";
		String newPassword = "newPassword";

		try {
        	updatedCustomer = service.updateCustomer(Customer_USERNAME, newUsername, newEmail, newPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(error, "Username cannot be empty!");

 	 }



	 	/*
	 * @author Muhammad Hammad
	 *
	 * checks to see if an error is thrown when an invalid email is provided
	 */
	@Test
	public void testUpdateCustomerInvalidEmail() {
		Customer updatedCustomer = null;
		String error = null;

		String newUsername = "username";
		String newEmail = "";
		String newPassword = "newPassword";

		try {
        	updatedCustomer = service.updateCustomer(Customer_USERNAME, newUsername, newEmail, newPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(error, "Email cannot be empty!");

 	 }





	 	/*
	 * @author Muhammad Hammad
	 *
	 * checks to see if an error is thrown when an invalid suername is provided
	 */
	@Test
	public void testUpdateCustomerInvalidEmailNull() {
		Customer updatedCustomer = null;
		String error = null;

		String newUsername = "username";
		String newEmail = null;
		String newPassword = "newPassword";

		try {
        	updatedCustomer = service.updateCustomer(Customer_USERNAME, newUsername, newEmail, newPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(error, "Email cannot be empty!");

 	 }
	 	 	/*
	 * @author Muhammad Hammad
	 *
	 * checks to see if an error is thrown when an invalid suername is provided
	 */
	@Test
	public void testUpdateCustomerInvalidEmailFromat() {
		Customer updatedCustomer = null;
		String error = null;

		String newUsername = "username";
		String newEmail = "notAValidEmail";
		String newPassword = "newPassword";

		try {
        	updatedCustomer = service.updateCustomer(Customer_USERNAME, newUsername, newEmail, newPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(error, "Email is invalid!");

 	 }
	 	/*
	 * @author Muhammad Hammad
	 *
	 * checks to see if an error is thrown when an invalid password is provided
	 */
	@Test
	public void testUpdateCustomerInvalidPassword() {
		Customer updatedCustomer = null;
		String error = null;

		String newUsername = "username";
		String newEmail = "newEmail@gmail.com";
		String newPassword = "";

		try {
        	updatedCustomer = service.updateCustomer(Customer_USERNAME, newUsername, newEmail, newPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(error, "Password cannot be empty!");

 	 }
	 	/*
	 * @author Muhammad Hammad
	 *
	 * checks to see if an error is thrown when an invalid suername is provided
	 */
	@Test
	public void testUpdateCustomerInvalidPasswordNull() {
		Customer updatedCustomer = null;
		String error = null;

		String newUsername = "username";
		String newEmail = "newEmail@gmail.com";
		String newPassword = null;

		try {
        	updatedCustomer = service.updateCustomer(Customer_USERNAME, newUsername, newEmail, newPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(error, "Password cannot be empty!");

 	 }
	 	 	/*
	 * @author Muhammad Hammad
	 *
	 * checks to see if an error is thrown when a non unique username is provided
	 */
	@Test
	public void testUpdateCustomerNonUniqueUsername() {
		Customer updatedCustomer = null;
		String error = null;

		String newEmail = "email@gmail.com";
		String newPassword = "newPassword";

		try {
        	updatedCustomer = service.updateCustomer(Customer_USERNAME, Customer_USERNAME, newEmail, newPassword);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(error, "Username is not unique!");

 	 }

	/*
	 * @author Muhammad Hammad
	 *
	 * checks to see if an error is thrown when an invalid username is provided
	 */

	  @Test
	  public void testCustomerLoginINvalidUsernameOrEmail() {
		  String error = null;
		  Customer customerLogin = null;

		  try {
			  customerLogin = service.customerLogin(NONEXISTING_Customer_USERNAME, Customer_PASSWORD);
		  } catch (IllegalArgumentException e) {
			  error = e.getMessage();
		  }

		  assertEquals(error, "Either the username or password is invalid!");

	  }
/*
	 * @author Muhammad Hammad
	 *
	 * checks to see if an error is thrown when an invalid username or email combination is provided
	 */
	  @Test
	  public void testCustomerLoginInvalidCombination() {
		  String error = null;
		  Customer customerLogin = null;

		  try {
			  customerLogin = service.customerLogin(Customer_USERNAME, NONEXISTING_Customer_PASSWORD);
		  } catch (IllegalArgumentException e) {
			  error = e.getMessage();
		  }

		  assertEquals(error, "Either the username or password is invalid!");

	  }


	  /*
	 * @author Muhammad Hammad
	 *
	 * checks to see if an error is thrown when an invalid username or email is provided
	 */
	@Test
	  public void testCustomerLogin() {
		  String error = null;
		  Customer customerLogin = null;

		  try {
			  customerLogin = service.customerLogin(Customer_USERNAME, Customer_PASSWORD);
		  } catch (IllegalArgumentException e) {
			  error = e.getMessage();
		  }

		  assertEquals(customerLogin.getUsername(), Customer_USERNAME);
		  assertEquals(customerLogin.getEmail(), Customer_EMAIL);
		  assertEquals(customerLogin.getPassword(), Customer_PASSWORD);


	  }




}