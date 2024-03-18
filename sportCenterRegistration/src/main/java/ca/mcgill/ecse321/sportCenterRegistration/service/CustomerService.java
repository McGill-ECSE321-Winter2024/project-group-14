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
import ca.mcgill.ecse321.sportCenterRegistration.dao.OwnerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.SessionRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.ShiftRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.SportClassRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.StaffRepository;


import ca.mcgill.ecse321.sportCenterRegistration.model.Account;
import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.Owner;
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
	 * Method returns the customer object with the corresponding username
	 * @param String username
	 * @return Customer
	 * 
	 * 
	 * 
	 * 
	 */
    @Transactional
	public Customer getCustomer(String username) {
		Customer customer = CustomerRepository.findCustomerByUsername(username);
        if (customer == null) {
            throw new IllegalArgumentException("Customer name is invalid");
        }
		return customer;
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

		if (i == -1 || i == 0 || i == email.length() - 1){
			return false;
		}
		if (!(email.chars().filter(ch -> ch == '@').count() == 1)){
			return false;
		}
		return true;
	}



	priavte boolean usernameIsUnique(String username){
		if (CustomerRepository.findCustomerByUsername(username) == null) {
			return true;
		}
		return false;
	}


 	/*
	@author Muhammad Hammad

	Method deletes customer with a given username adn returns a boolean indicating whether the deletion is sucessful 
	@param String username
	@return Boolean

	
	*/

    @Transactional
	public Boolean deleteCustomer(String username) {
        if (username == null || username.trim().length() == 0) {
			throw new IllegalArgumentException("Customer name cannot be empty!");
		}
		Customer customerToDelete = getCustomer(username);
		CustomerRepository.delete(customerToDelete);
		return true;
	}

	/*
	 * 
	 * @author Muhammad Hammad
	 * 
	 * Method creates a customer with a given username, email, and password
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
			throw new IllegalArgumentException("Username is not unique!")
		}
		

		Customer customer = new Customer(username, email, password);
		CustomerRepository.save(customer);
	    accountRepository.save(customer);
		return customer;
	}

	/*
	 * 
	 * @author Muhammad Hammad
	 * Method returns a list of all the customer in the repository
	 * 
	 * @return List<Customer>
	 * 
	 * 
	 */
	@Transactional
	public List<Customer> getAllCustomers() {
		return toList(CustomerRepository.findAll());
	}

	@Transactional
	public Customer customerLogin(String username, String password){
		
	}

}
