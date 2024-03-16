package ca.mcgill.ecse321.sportCenterRegistration.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportCenterRegistration.dao.AccountRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.InstructorRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.OwnerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.SessionRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.ShiftRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.SportClassRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.StaffRepository;


import ca.mcgill.ecse321.sportCenterRegistration.model.Account;
import ca.mcgill.ecse321.sportCenterRegistration.model.instructor;
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
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	StaffRepository staffRepository;

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}


    @Transactional
	public Instructor getInstructor(String username) {
		Instructor instructor = InstructorRepository.findInstructorByUsername(username);
        if (instructor == null) {
            throw new IllegalArgumentException("instructor name is invalid");
        }
		return instructor;
	}

    @Transactional
	public Boolean deleteInstructor(String username) {
        if (username == null || username.trim().length() == 0) {
			throw new IllegalArgumentException("instructor name cannot be empty!");
		}
		Instructor instructorToDelete = getInstructor(username);
		InstructorRepository.delete(instructorToDelete);
		return true;
	}

    @Transactional
    public Instructor createInstructor(String username, String email, String password ) {
		

        if (username == null || username.strip() == "") {
            throw new IllegalArgumentException("Username cannot be empty!");
        }
        if (email == null || email.strip() == "") {
            throw new IllegalArgumentException("Email cannot be empty!");
        }
        if (password == null || password.strip() == "") {
            throw new IllegalArgumentException("Password cannot be empty!");
        }

		Instructor instructor = new Instructor(username, email, password);
		InstructorRepository.save(instructor);
	        accountRepository.save(instructor);
		staffRepository.save(instructor);
		return instructor;
	}

	@Transactional
	public List<Instructor> getAllInstructors() {
		return toList(InstructorRepository.findAll());
	}



 
 


}
