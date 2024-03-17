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


    @Transactional
	public Customer getCustomer(String username) {
		Customer customer = CustomerRepository.findCustomerByUsername(username);
        if (customer == null) {
            throw new IllegalArgumentException("Customer name is invalid");
        }
		return customer;
	}

    @Transactional
	public Boolean deleteCustomer(String username) {
        if (username == null || username.trim().length() == 0) {
			throw new IllegalArgumentException("Customer name cannot be empty!");
		}
		Customer customerToDelete = getCustomer(username);
		CustomerRepository.delete(customerToDelete);
		return true;
	}

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

		Customer customer = new Customer(username, email, password);
		CustomerRepository.save(customer);
	    accountRepository.save(customer);
		return customer;
	}

	@Transactional
	public List<Customer> getAllCustomers() {
		return toList(CustomerRepository.findAll());
	}





 
 


}
