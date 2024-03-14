package ca.mcgill.ecse321.sportCenterRegistration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportCenterRegistration.service.SportCenterRegistrationService;

@CrossOrigin(origins = "*")
@RestController
public class SportCenterRegistrationRestController {

	@Autowired
	private SportCenterRegistrationService service;


	@DeleteMapping(value= {"/customer/{username}", "/customer/{username}/"})
	public void deleteCustomer(@PathVariable("username") String username) throws 
	IllegalArgumentException {
		service.deleteCustomer(username);
	}


	@DeleteMapping(value= {"/instructor/{username}", "/instructor/{username}/"})
	public void deleteInstructor(@PathVariable("username") String username) throws 
	IllegalArgumentException {
		service.deleteInstructor(username);
	}



	
	
	

	

	

}