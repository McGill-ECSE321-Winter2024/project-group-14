package ca.mcgill.ecse321.sportCenterRegistration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportCenterRegistration.dao.*;
import ca.mcgill.ecse321.sportCenterRegistration.model.*;

@Service
public class LoginService {
    @Autowired
    CustomerRepository customerRepo;
    @Autowired
    InstructorRepository instructorRepo;
    @Autowired
    OwnerRepository ownerRepo;
    @Autowired
    AccountRepository accountRepo;

    /**
     * Section: Login service
     * Author: Stephen Huang
     * login a user in system
     */
    @Transactional
    public Account loginByEmail(String email, String password) {
        System.out.println(customerRepo.existsByEmail(email));
        // Check if the user exists in the system, and throw an error if it does not.
        if (!customerRepo.existsByEmail(email) &&
                !ownerRepo.existsByEmail(email) &&
                !instructorRepo.existsByEmail(email)) {
            throw new IllegalArgumentException("Invalid email");
        }

        System.out.println(email);
        System.out.println(password);

        // Determine what type of user is logging in.
        Customer customer = customerRepo.findCustomerByEmail(email);
        if (customer != null && customer.getPassword().equals(password))
            return customer;

        Owner owner = ownerRepo.findOwnerByEmail(email);
        if (owner != null && owner.getPassword().equals(password))
            return owner;

        Instructor instructor = instructorRepo.findInstructorByEmail(email);
        if (instructor != null && instructor.getPassword().equals(password))
            return instructor;

        // Throw an exception if the password is incorrect.
        throw new IllegalArgumentException("Incorrect password");
    }


    
    @Transactional
    public Account loginByUsername(String username, String password) {
        System.out.println(customerRepo.existsByEmail(username));
        // Check if the user exists in the system, and throw an error if it does not.
        if (!customerRepo.existsByUsername(username) &&
                !ownerRepo.existsByUsername(username) &&
                !instructorRepo.existsByUsername(username)) {
            throw new IllegalArgumentException("Invalid username");
        }

        System.out.println(username);
        System.out.println(password);

        // Determine what type of user is logging in.
        Customer customer = customerRepo.findCustomerByUsername(username);
        if (customer != null && customer.getPassword().equals(password))
            return customer;

        Owner owner = ownerRepo.findOwnerByUsername(username);
        if (owner != null && owner.getPassword().equals(password))
            return owner;

        Instructor instructor = instructorRepo.findInstructorByUsername(username);
        if (instructor != null && instructor.getPassword().equals(password))
            return instructor;

        // Throw an exception if the password is incorrect.
        throw new IllegalArgumentException("Incorrect password");
    }

}
