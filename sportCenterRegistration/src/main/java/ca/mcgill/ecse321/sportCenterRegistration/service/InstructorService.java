package ca.mcgill.ecse321.sportCenterRegistration.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportCenterRegistration.dao.*;
import ca.mcgill.ecse321.sportCenterRegistration.model.*;

@Service
public class InstructorService {

	@Autowired
	InstructorRepository InstructorRepository;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	StaffRepository staffRepository;

	/**
	 * Section: Instructor service
	 * Author:
	 * This method creates a Instructor
	 */
	@Transactional
	public Instructor createInstructor(String username, String email, String password) {

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

	/**
	 * Section: Instructor service
	 * Author:
	 * This method finds an instructor by Username
	 */
	@Transactional
	public Instructor getInstructorByUsername(String username) {
		Instructor instructor = InstructorRepository.findInstructorByUsername(username);
		if (instructor == null) {
			throw new IllegalArgumentException("instructor not found.");
		}
		return instructor;
	}

	/**
	 * Section: Customer servive
	 * Author: Stephen Huang
	 * This method finds a customer by email
	 */
	@Transactional
	public Instructor getInstructorByEmail(String email) {
		// Checking the Instructor exists in the system
		Instructor instructor = InstructorRepository.findInstructorByEmail(email);
		if (instructor == null) {
			throw new IllegalArgumentException("Instructor not found.");
		}
		return instructor;
	}

	/**
	 * Section: Instructor servive
	 * Author: Stephen Huang
	 * This method deletes an instructor in the system by email
	 */
	@Transactional
	public Boolean deleteInstructor(String email) {
		// check the input is valid
		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("email cannot be empty!");
		}

		// Checking the customer exists in the system
		Instructor instructor = InstructorRepository.findInstructorByEmail(email);
		if (instructor == null) {
			throw new IllegalArgumentException("Instructor not found.");
		}

		// Delete the instructor
		InstructorRepository.deleteInstructorByEmail(email);
		return true;
	}

	/**
	 * Section: Instructor servive
	 * Author: Stephen Huang
	 * This method updates instructor's info and retures if no error
	 */
	@Transactional
	public Instructor updateInstructor(String email, String password, String username) {

		// Checking all the inputs are valid
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Customer password must not be empty!");
		} else if (username == null || username.isEmpty()) {
			throw new IllegalArgumentException("Customer name must not be empty!");
		} else if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("Customer address must not be empty!");
		}

		// Checking the instructor exists in the system
		Instructor intructor = InstructorRepository.findInstructorByEmail(email);
		if (intructor == null) {
			throw new IllegalArgumentException("Instructor not found.");
		}

		// Update the instructor
		intructor.setPassword(password);
		intructor.setUsername(username);
		intructor.setEmail(email);
		InstructorRepository.save(intructor);
		return intructor;
	}

	/**
	 * Section: Instructor servive
	 * Author:
	 * This method returns a list of all instructor in the system
	 */
	@Transactional
	public List<Instructor> getAllInstructors() {
		return toList(InstructorRepository.findAll());
	}

	// convert iterable into a list
	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
