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
import ca.mcgill.ecse321.sportCenterRegistration.dao.InstructorRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.InstructorRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.SessionRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.ShiftRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.SportClassRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.StaffRepository;


import ca.mcgill.ecse321.sportCenterRegistration.model.Account;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
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
	SportClassRepository sportClassRepo;

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

	/*
	 * 
	 * @author Muhammad Hammad
	 * 
	 * Method returns the Instructor object with the corresponding username
	 * @param String username
	 * @return Instructor
	 * 
	 * 
	 * 
	 * 
	 */
    @Transactional
	public Instructor getInstructor(String username) {
		Instructor Instructor = InstructorRepository.findInstructorByUsername(username);
        if (Instructor == null) {
            throw new IllegalArgumentException("Instructor name is invalid");
        }
		return Instructor;
	}

	/*
	 * 
	 * @author Muhammad Hammad
	 * @param String email
	 * @return boolean 
	 * 
	 * Returns a boolean indiciating whether or not the email is formatted correctly
	 * 
	 * 
	 */
	private Boolean emailIsValid(String email){
		int i = email.indexOf("@");

		if (i == -1 || i == 0 || i == email.length() - 1){
			return false;
		}
		if (!(email.chars().filter(ch -> ch == '@').count() == 1)){
			return false;
		}
		return true;
	}



	private boolean usernameIsUnique(String username){
		if (InstructorRepository.findInstructorByUsername(username) == null) {
			return true;
		}
		return false;
	}


 	/*
	@author Muhammad Hammad

	Method deletes Instructor with a given username adn returns a boolean indicating whether the deletion is sucessful 
	@param String username
	@return Boolean

	
	*/

    @Transactional
	public Instructor deleteInstructor(String username) {
        if (username == null || username.trim().length() == 0) {
			throw new IllegalArgumentException("Instructor name cannot be empty!");
		}
		Instructor InstructorToDelete = getInstructor(username);
		InstructorRepository.delete(InstructorToDelete);
		return InstructorToDelete;
	}

	/*
	 * 
	 * @author Muhammad Hammad
	 * 
	 * Method creates a Instructor with a given username, email, and password
	 * @param String username
	 * @param String email
	 * @param String password
	 * @return Instructor
	 * 
	 * 
	 * 
	 * 
	 */

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
		if (!(emailIsValid(email))){
			throw new IllegalArgumentException("Email is invalid!");
		}
		if(!(usernameIsUnique(username))) {
			throw new IllegalArgumentException("Username is not unique!");
		}
		

		Instructor Instructor = new Instructor(username, email, password);
		InstructorRepository.save(Instructor);
	    accountRepository.save(Instructor);
		return Instructor;
	}

	/*
	 * 
	 * @author Muhammad Hammad
	 * Method returns a list of all the Instructor in the repository
	 * 
	 * @return List<Instructor>
	 * 
	 * 
	 */
	@Transactional
	public List<Instructor> getAllInstructors() {
		return toList(InstructorRepository.findAll());
	}

    /*
     * 
     * @author Muhammad Hammad
     * @param username
     * @param password
     * @return Instructor object
     * 
     * method that checks to see if the username and password correspond to an Instructor object at which point it returns the Instructor object
     * 
     * 
     * 
     * 
     */

	@Transactional
	public Instructor InstructorLogin(String username, String password){
		//chose to only return one type of error message for invalid username and password to maintain security for the application
		if (InstructorRepository.findInstructorByUsername(username) == null){
			throw new IllegalArgumentException("Either the username or password is invalid!");
		}
		else if (InstructorRepository.findInstructorByUsername(username).getPassword() != password) {
			throw new IllegalArgumentException("Either the username or password is invalid!");
		}
		return InstructorRepository.findInstructorByUsername(username);
	}
    /*
     * @author Muhammad Hammad
     * 
     * @param String oldUsername
     * @param String username
     * @param String email
     * @param String password
     * @return Instructor object
     * 
     * Method that updates an Instructor object corresponding to the old username with the new information
     * 
     * 
     */

	@Transactional
	public Instructor updateInstructor(String oldUsername, String username, String email, String password) {
		if (username == null || username.strip() == "") {
            throw new IllegalArgumentException("Username cannot be empty!");
        }
        if (email == null || email.strip() == "") {
            throw new IllegalArgumentException("Email cannot be empty!");
        }
        if (password == null || password.strip() == "") {
            throw new IllegalArgumentException("Password cannot be empty!");
        }
		if (!(emailIsValid(email))){
			throw new IllegalArgumentException("Email is invalid!");
		}
		if(!(usernameIsUnique(username))) {
			throw new IllegalArgumentException("Username is not unique!");
		}
		
		Instructor InstructorUpdated = InstructorRepository.findInstructorByUsername(oldUsername);
		InstructorUpdated.setUsername(username);
		InstructorUpdated.setEmail(email);
		InstructorUpdated.setPassword(password);
		return InstructorUpdated;

	}




	@Transactional
	public SportClass createSportClass(String name){
		if (name==null || name.length()<=0){
			throw new IllegalArgumentException("Sport Class name should not be empty!");
		}
		if (sportClassRepo.findSportClassByName(name)!=null){
			throw new IllegalArgumentException("Sport Class already exists!");
		}
		SportClass sportClass = new SportClass(name);
		return sportClassRepo.save(sportClass);
	}

}