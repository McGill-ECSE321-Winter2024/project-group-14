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
public class InstructorService {
    
    @Autowired
	InstructorRepository InstructorRepository;

	@Transactional
	public Instructor getInstructor(String username) {
		if (username == null || username.trim().length() == 0) {
			throw new IllegalArgumentException("Instructor name cannot be empty!");
		}

		Instructor instructor = InstructorRepository.findInstructorByUsername(username);
		return instructor;
	}


    @Transactional
	public Boolean deleteInstructor(String username) {
		Instructor instructorToDelete = getInstructor(username);
		InstructorRepository.delete(instructorToDelete);
		return true;
	}



}

