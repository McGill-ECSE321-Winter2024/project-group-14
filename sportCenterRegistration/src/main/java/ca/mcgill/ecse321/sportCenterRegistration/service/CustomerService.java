package ca.mcgill.ecse321.sportCenterRegistration.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportCenterRegistration.dao.*;
import ca.mcgill.ecse321.sportCenterRegistration.model.*;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository CustomerRepository;
	@Autowired
	AccountRepository accountRepository;

	/**
	 * Section: Customer servive
	 * Author:
	 * This method creates a customer
	 */
	@Transactional
	public Customer createCustomer(String username, String email, String password) {

		if (username == null || username.strip() == "") {
			throw new IllegalArgumentException("Username cannot be empty!");
		}
		if (email == null || email.strip() == "") {
			throw new IllegalArgumentException("Email cannot be empty!");
		}
		if (password == null || password.strip() == "") {
			throw new IllegalArgumentException("Password cannot be empty!");
		}

		Customer customer = new Customer(username, email, password);
		CustomerRepository.save(customer);
		accountRepository.save(customer);
		return customer;
	}

	/**
	 * Section: Customer servive
	 * Author:
	 * This method finds a customer by username
	 */
	@Transactional
	public Customer getCustomerByUsername(String username) {
		Customer customer = CustomerRepository.findCustomerByUsername(username);
		if (customer == null) {
			throw new IllegalArgumentException("Customer not found.");
		}
		return customer;
	}

	/**
	 * Section: Customer servive
	 * Author: Stephen Huang
	 * This method finds a customer by email
	 */
	@Transactional
	public Customer getCustomerByEmail(String email) {
		// Checking the customer exists in the system
		Customer customer = CustomerRepository.findCustomerByEmail(email);
		if (customer == null) {
			throw new IllegalArgumentException("Customer not found.");
		}

		return customer;
	}

	/**
	 * Section: Customer servive
	 * Author: Stephen Huang and
	 * This method deletes a customer by email
	 */
	@Transactional
	public Boolean deleteCustomer(String email) {
		// check the input is valid
		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("Email cannot be empty!");
		}

		// Checking the customer exists in the system
		Customer customer = CustomerRepository.findCustomerByEmail(email);
		if (customer == null) {
			throw new IllegalArgumentException("Customer not found.");
		}

		// Delete the customer
		CustomerRepository.deleteCustomerByEmail(email);
		return true;
	}

	/**
	 * Section: Customer servive
	 * Author: Stephen Huang
	 * This method updates customer's info and retures if no error
	 */
	@Transactional
	public Customer updateCustomer(String email, String password, String username) {

		// Checking all the inputs are valid
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Customer password must not be empty!");
		} else if (username == null || username.isEmpty()) {
			throw new IllegalArgumentException("Customer name must not be empty!");
		} else if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("Customer address must not be empty!");
		}

		// Checking the customer exists in the system
		Customer customer = CustomerRepository.findCustomerByEmail(email);
		if (customer == null) {
			throw new IllegalArgumentException("Customer not found.");
		}

		// Update the customer
		customer.setPassword(password);
		customer.setUsername(username);
		customer.setEmail(email);
		CustomerRepository.save(customer);
		return customer;
	}

	/**
	 * Section: Customer servive
	 * Author:
	 * This method returns a list of all customers in the system
	 */
	@Transactional
	public List<Customer> getAllCustomers() {
		return toList(CustomerRepository.findAll());
	}

	// convert iterable into a list
	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}

// still need custom