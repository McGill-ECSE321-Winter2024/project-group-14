package ca.mcgill.ecse321.sportCenterRegistration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportCenterRegistration.service.SportCenterRegistrationService;

@CrossOrigin(origins = "*")
@RestController
public class SportCenterRegistrationRestController {

	@Autowired
	private SportCenterRegistrationService service;
}