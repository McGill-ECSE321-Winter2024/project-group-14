package main.java.ca.mcgill.ecse321.sportCenterRegistration.controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.mcgill.ecse321.sportCenterRegistration.dto.*;
import ca.mcgill.ecse321.sportCenterRegistration.model.*;
import ca.mcgill.ecse321.sportCenterRegistration.service.*;


@RestController
public class LoginController {

    @Autowired
	private LoginService loginService;

    @PostMapping(value = { "/login", "/login/" })
	public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
		Account user = null;
		try {
			user = loginService.login(email, password);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// Determine what type of user is login in in the system.
		if (user instanceof Customer) {

			return new ResponseEntity<>(convertToCustomerDto((Customer) user), HttpStatus.OK);
		}

		if (user instanceof Instructor) {
			return new ResponseEntity<>(convertToInstructorDto((Instructor) user), HttpStatus.OK);
		}

		if (user instanceof Owner) {
			return new ResponseEntity<>(convertToOwnerDTO((Owner) user), HttpStatus.OK);
		}
		return null;
	}

    public static CustomerDto convertToCustomerDto(Customer customer) {
		if (customer == null) {
			throw new IllegalArgumentException("There is no such Customer!");
		}
		CustomerDto customerDto = new CustomerDto(customer.getemail(), customer.getpassword(), customer.getusername());
		return customerDto;
	}

    private InstrutorDto convertToInstrutorDto(Instrutor instrutor) {
		if (instrutor == null) {
			throw new IllegalArgumentException("There is no such Instrutor!");
		}
		InstrutorDto instrutorDto = new InstrutorDto(instrutor.getemail(), instrutor.getpassword(), instrutor.getusername(),
        instrutor.getSession());
		return instrutorDto;
	}

	public static OwnerDto convertToOwnerDTO(Owner owner) {
		if (owner == null)
			return null;
		return new OwnerDto(owner.getemail(), owner.getpassword(), owner.getusername());
	}
    
}
