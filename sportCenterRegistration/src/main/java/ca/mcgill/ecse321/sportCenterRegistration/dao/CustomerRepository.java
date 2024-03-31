package ca.mcgill.ecse321.sportCenterRegistration.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    // Since the username is unique, the customer can be found by their usernames
    public Customer findCustomerByUsername(String name);

    public Customer findCustomerByEmail(String emmail);

    void deleteCustomerByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
