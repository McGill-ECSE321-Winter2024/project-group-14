package ca.mcgill.ecse321.sportCenterRegistration.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportCenterRegistration.dao.AccountRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.CustomerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.InstructorRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.CustomerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.SessionRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.ShiftRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.SportClassRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.StaffRepository;


import ca.mcgill.ecse321.sportCenterRegistration.model.Account;
import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;
import ca.mcgill.ecse321.sportCenterRegistration.model.Registration;
import ca.mcgill.ecse321.sportCenterRegistration.model.Session;
import ca.mcgill.ecse321.sportCenterRegistration.model.Shift;
import ca.mcgill.ecse321.sportCenterRegistration.model.SportClass;
import ca.mcgill.ecse321.sportCenterRegistration.model.Staff;





@Service
public class CustomerService {
    
    @Autowired
	CustomerRepository CustomerRepository;
	@Autowired
	AccountRepository accountRepository;

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

	/*
	 * 
	 * @author Muhammad Hammad
	 * 
	 * Method returns the Customer object with the corresponding username
	 * @param String username
	 * @return Customer
	 * 
	 * 
	 * 
	 * 
	 */
    @Transactional
	public Customer getCustomer(String username) {
		Customer Customer = CustomerRepository.findCustomerByUsername(username);
        // if the customer doesn't exist then the customer object will be equal to null
		if (Customer == null) {
            throw new IllegalArgumentException("Customer name is invalid");
        }
		return Customer;
	}

	/*
	 * 
	 * @author Muhammad Hammad
	 * @param String email
	 * @return boolean 
	 * 
	 * Returns a boolean indiciating whether or not the email is formatted correctly
	 * 
	 * 
	 */
	private Boolean emailIsValid(String email){
		int i = email.indexOf("@");
		//confirms that there is an @ sign in the string and that the @sign is not at the beginning or end of the string
		if (i == -1 || i == 0 || i == email.length() - 1){
			return false;
		}
		//confirms that there is onyl one @ sign
		if (!(email.chars().filter(ch -> ch == '@').count() == 1)){
			return false;
		}
		return true;
	}

/*
 * @author Muhammad Hammad
 * 
 * Helper method that helps determine if a username is unique
 * 
 * 
 */

	private boolean usernameIsUnique(String username){
		// if there already exists a username then null would not be returned
		if (CustomerRepository.findCustomerByUsername(username) == null) {
			return true;
		}
		return false;
	}


 	/*
	@author Muhammad Hammad

	Method deletes Customer with a given username adn returns a boolean indicating whether the deletion is sucessful 
	@param String username
	@return Boolean

	
	*/

    @Transactional
	public Customer deleteCustomer(String username) {
        //confirms that the username inputted is valid
		if (username == null || username.trim().length() == 0) {
			throw new IllegalArgumentException("Customer name cannot be empty!");
		}
		
		Customer CustomerToDelete = getCustomer(username);
		CustomerRepository.delete(CustomerToDelete);
		return CustomerToDelete;
	}

	/*
	 * 
	 * @author Muhammad Hammad
	 * 
	 * Method creates a Customer with a given username, email, and password
	 * @param String username
	 * @param String email
	 * @param String password
	 * @return Customer
	 * 
	 * 
	 * 
	 * 
	 */

    @Transactional
    public Customer createCustomer(String username, String email, String password ) {
		
		// pefroms various input checks on the username, email, and password
        if (username == null || username.strip() == "") {
            throw new IllegalArgumentException("Username cannot be empty!");
        }
        if (email == null || email.strip() == "") {
            throw new IllegalArgumentException("Email cannot be empty!");
        }
        if (password == null || password.strip() == "") {
            throw new IllegalArgumentException("Password cannot be empty!");
        }
		if (!(emailIsValid(email))){
			throw new IllegalArgumentException("Email is invalid!");
		}
		if(!(usernameIsUnique(username))) {
			throw new IllegalArgumentException("Username is not unique!");
		}
		
		//creates and saves to the repository
		Customer Customer = new Customer(username, email, password);
		CustomerRepository.save(Customer);
		
		return Customer;
	}

	/*
	 * 
	 * @author Muhammad Hammad
	 * Method returns a list of all the Customer in the repository
	 * 
	 * @return List<Customer>
	 * 
	 * 
	 */
	@Transactional
	public List<Customer> getAllCustomers() {
		return toList(CustomerRepository.findAll());
	}

    /*
     * 
     * @author Muhammad Hammad
     * @param username
     * @param password
     * @return Customer object
     * 
     * method that checks to see if the username and password correspond to an Customer object at which point it returns the Customer object
     * 
     * 
     * 
     * 
     */

	@Transactional
	public Customer customerLogin(String username, String password){
		//chose to only return one type of error message for invalid username and password to maintain security for the application
		if (CustomerRepository.findCustomerByUsername(username) == null){
			throw new IllegalArgumentException("Either the username or password is invalid!");
		}
		else if (CustomerRepository.findCustomerByUsername(username).getPassword() != password) {
			throw new IllegalArgumentException("Either the username or password is invalid!");
		}
		return CustomerRepository.findCustomerByUsername(username);
	}
    /*
     * @author Muhammad Hammad
     * 
     * @param String oldUsername
     * @param String username
     * @param String email
     * @param String password
     * @return Customer object
     * 
     * Method that updates an Customer object corresponding to the old username with the new information
     * 
     * 
     */

	@Transactional
	public Customer updateCustomer(String oldUsername, String username, String email, String password) {
		//validates the information and then accordingly updates the customer information
		if (username == null || username.strip() == "") {
            throw new IllegalArgumentException("Username cannot be empty!");
        }
        if (email == null || email.strip() == "") {
            throw new IllegalArgumentException("Email cannot be empty!");
        }
        if (password == null || password.strip() == "") {
            throw new IllegalArgumentException("Password cannot be empty!");
        }
		if (!(emailIsValid(email))){
			throw new IllegalArgumentException("Email is invalid!");
		}
		if(!(usernameIsUnique(username))) {
			throw new IllegalArgumentException("Username is not unique!");
		}
		
		Customer CustomerUpdated = CustomerRepository.findCustomerByUsername(oldUsername);
		CustomerUpdated.setUsername(username);
		CustomerUpdated.setEmail(email);
		CustomerUpdated.setPassword(password);
		return CustomerUpdated;

	}
	
	

}
