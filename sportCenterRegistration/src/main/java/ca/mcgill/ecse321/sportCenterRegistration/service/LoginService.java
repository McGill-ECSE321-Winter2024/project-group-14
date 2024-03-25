package ca.mcgill.ecse321.sportCenterRegistration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportCenterRegistration.dao.CustomerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.InstructorRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.OwnerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.model.Account;
import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.Owner;

@Service
public class LoginService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    OwnerRepository ownerRepository;

    /**
     * Section: Login service
     * Author: Stephen Huang
     * login a user in system
     */
    @Transactional
    public Account login(String email, String password) {

        // Check if the user exists in the system, and throw an error if it does not.
        if (!customerRepository.existsByEmail(email) &&
                !ownerRepository.existsByEmail(email) &&
                !instructorRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Invalid email");
        }

        // Determine what type of user is logging in.
        Customer customer = customerRepository.findCustomerByEmail(email);
        if (customer != null && customer.getPassword().equals(password))
            return customer;

        Owner owner = ownerRepository.findOwnerByEmail(email);
        if (owner != null && owner.getPassword().equals(password))
            return owner;

        Instructor instructor = instructorRepository.findInstructorByEmail(email);
        if (instructor != null && instructor.getPassword().equals(password))
            return instructor;

        // Throw an exception if the password is incorrect.
        throw new IllegalArgumentException("Incorrect password");
    }

}
