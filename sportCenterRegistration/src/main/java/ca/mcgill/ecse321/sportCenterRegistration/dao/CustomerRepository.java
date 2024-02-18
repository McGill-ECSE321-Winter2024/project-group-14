package ca.mcgill.ecse321.sportCenterRegistration.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{
//    public Customer findCustomerById(int id);
    public Customer findCustomerByUsername(String name);
}