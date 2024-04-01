package ca.mcgill.ecse321.sportCenterRegistration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportCenterRegistration.dto.CustomerDTO;
import ca.mcgill.ecse321.sportCenterRegistration.dto.InstructorDTO;
import ca.mcgill.ecse321.sportCenterRegistration.dto.OwnerDTO;
import ca.mcgill.ecse321.sportCenterRegistration.model.Account;
import ca.mcgill.ecse321.sportCenterRegistration.model.Customer;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.Owner;
import ca.mcgill.ecse321.sportCenterRegistration.service.LoginService;

@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;

	@GetMapping(value = { "/login/email", "/login/email/" })
	public ResponseEntity<?> loginByEmail(@RequestParam String email, @RequestParam String password) {
		Account user = null;
		try {
			user = loginService.loginByEmail(email, password);
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

	@GetMapping(value = { "/login/username", "/login/username/" })
	public ResponseEntity<?> loginByUsername(@RequestParam String username, @RequestParam String password) {
		Account user = null;
		try {
			user = loginService.loginByUsername(username, password);
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


	public static CustomerDTO convertToCustomerDto(Customer customer) {
		if (customer == null) {
			throw new IllegalArgumentException("There is no such Customer!");
		}
		CustomerDTO customerDto = new CustomerDTO(customer.getId(), customer.getUsername(), customer.getEmail(),
				customer.getPassword());
		return customerDto;
	}

	private InstructorDTO convertToInstructorDto(Instructor instrutor) {
		if (instrutor == null) {
			throw new IllegalArgumentException("There is no such Instrutor!");
		}
		InstructorDTO InstructorDTO = new InstructorDTO(instrutor.getId(), instrutor.getUsername(),
				instrutor.getEmail(), instrutor.getPassword());
		return InstructorDTO;
	}

	public static OwnerDTO convertToOwnerDTO(Owner owner) {
		if (owner == null)
			return null;
		return new OwnerDTO(owner.getId(), owner.getUsername(), owner.getEmail(), owner.getPassword());
	}

}
