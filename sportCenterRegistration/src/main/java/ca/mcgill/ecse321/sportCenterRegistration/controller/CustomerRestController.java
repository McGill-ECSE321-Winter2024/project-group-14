package ca.mcgill.ecse321.sportCenterRegistration.controller;

import java.util.ArrayList;
import java.util.List;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportCenterRegistration.dto.CustomerDTO;
import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;
import ca.mcgill.ecse321.sportCenterRegistration.service.CustomerService;


@CrossOrigin(origins = "*")
@RestController
public class CustomerRestController {
    @Autowired
    public CustomerService customerService;


    /*
     * 
     * @author Muhammad Hammad
     * 
     * method that converts a customer object to a customer dto
     * 
     */
    private CustomerDTO convertToDTO(Customer customer){
        if (customer == null) {
            throw new IllegalArgumentException("There is no such customer!");
        }
        CustomerDTO customerDTO = new CustomerDTO(customer.getId(), customer.getUsername(), customer.getEmail(), customer.getPassword());
        return customerDTO;
    }

    /*
     * 
     * @author Muhammad Hammad
     * 
     * method that converts a list of customers into a list of customer dtos
     * 
     */
    private List<CustomerDTO> convertListToDto(List<Customer> listCustomer){
        List<CustomerDTO> listCustomerDTO = new ArrayList<CustomerDTO>(listCustomer.size());
        for (Customer customer: listCustomer) {
            listCustomerDTO.add(convertToDTO(customer));
        }
        return listCustomerDTO;
    }




    /*
     * 
     * @author Muhammad Hammad
     * 
     * Controller method that creates a new customer object
     * 
     * 
     */

    @PostMapping(value= {"/customer/{username}/{email}/{password}", "/customer/{username}/{email}/{password}/"})
    public ResponseEntity<?> createCustomer(@PathVariable("username") String username, @PathVariable("email") String email, @PathVariable("password") String password) throws IllegalArgumentException {
        try {
            Customer customer = customerService.createCustomer(username, email, password);
            return ResponseEntity.ok(convertToDTO(customer));
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }
    /*
     * 
     * @author Muhammad Hammad
     * 
     * Controller method that returns a customer object by taking its username
     * 
     * 
     */

    @GetMapping(value= {"/customer/{username}", "/customer/{username}/"})
    public ResponseEntity<?> getCustomer(@PathVariable("username") String username) throws IllegalArgumentException {
        try {
            Customer customer = customerService.getCustomer(username);
            return ResponseEntity.ok(convertToDTO(customer));
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }

    @GetMapping(value= {"/customer/all", "/customer/all/"})
    public ResponseEntity<?> getAllCustomers() throws IllegalArgumentException {
        try {
            List<Customer> customers = customerService.getAllCustomers());
            
            return ResponseEntity.ok(convertListToDto(customers));
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }


    /*
     * 
     * @author Muhammad Hammad
     * 
     * Controller method that deletes a customer with a given username
     * 
     * 
     * 
     */



    @DeleteMapping(value= {"/customer/{username}", "/customer/{username}/"})
    public ResponseEntity<?> deleteCustomer(@PathVariable("username") String username) throws IllegalArgumentException {
        try {
            Boolean deleteSucess = customerService.deleteCustomer(username);
            return ResponseEntity.ok(deleteSucess);
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }



    



}
