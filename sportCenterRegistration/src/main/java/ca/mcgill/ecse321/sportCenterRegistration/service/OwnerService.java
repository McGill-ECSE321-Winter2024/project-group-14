package ca.mcgill.ecse321.sportCenterRegistration.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportCenterRegistration.dao.AccountRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.OwnerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.InstructorRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.OwnerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.SessionRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.ShiftRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.SportClassRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.StaffRepository;


import ca.mcgill.ecse321.sportCenterRegistration.model.Account;
import ca.mcgill.ecse321.sportCenterRegistration.model.Owner;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.Owner;
import ca.mcgill.ecse321.sportCenterRegistration.model.Registration;
import ca.mcgill.ecse321.sportCenterRegistration.model.Session;
import ca.mcgill.ecse321.sportCenterRegistration.model.Shift;
import ca.mcgill.ecse321.sportCenterRegistration.model.SportClass;
import ca.mcgill.ecse321.sportCenterRegistration.model.Staff;





@Service
public class OwnerService {
    
    @Autowired
	OwnerRepository OwnerRepository;
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
	 * Method returns the Owner object with the corresponding username
	 * @param String username
	 * @return Owner
	 * 
	 * 
	 * 
	 * 
	 */
    @Transactional
	public Owner getOwner(String username) {
		Owner Owner = OwnerRepository.findOwnerByUsername(username);
        if (Owner == null) {
            throw new IllegalArgumentException("Owner name is invalid");
        }
		return Owner;
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
		if (OwnerRepository.findOwnerByUsername(username) == null) {
			return true;
		}
		return false;
	}


 	/*
	@author Muhammad Hammad

	Method deletes Owner with a given username adn returns a boolean indicating whether the deletion is sucessful 
	@param String username
	@return Boolean

	
	*/

    @Transactional
	public Owner deleteOwner(String username) {
        if (username == null || username.trim().length() == 0) {
			throw new IllegalArgumentException("Owner name cannot be empty!");
		}
		Owner OwnerToDelete = getOwner(username);
		OwnerRepository.delete(OwnerToDelete);
		return OwnerToDelete;
	}

	/*
	 * 
	 * @author Muhammad Hammad
	 * 
	 * Method creates a Owner with a given username, email, and password
	 * @param String username
	 * @param String email
	 * @param String password
	 * @return Owner
	 * 
	 * 
	 * 
	 * 
	 */

    @Transactional
    public Owner createOwner(String username, String email, String password ) {
		

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
		

		Owner Owner = new Owner(username, email, password);
//		OwnerRepository.save(Owner);
		return OwnerRepository.save(Owner);
	}

	/*
	 * 
	 * @author Muhammad Hammad
	 * Method returns a list of all the Owner in the repository
	 * 
	 * @return List<Owner>
	 * 
	 * 
	 */
	@Transactional
	public List<Owner> getAllOwners() {
		return toList(OwnerRepository.findAll());
	}

    /*
     * 
     * @author Muhammad Hammad
     * @param username
     * @param password
     * @return Owner object
     * 
     * method that checks to see if the username and password correspond to an owner object at which point it returns the owner object
     * 
     * 
     * 
     * 
     */

	@Transactional
	public Owner OwnerLogin(String username, String password){
		//chose to only return one type of error message for invalid username and password to maintain security for the application
		if (OwnerRepository.findOwnerByUsername(username) == null){
			throw new IllegalArgumentException("Either the username or password is invalid!");
		}
		else if (OwnerRepository.findOwnerByUsername(username).getPassword() != password) {
			throw new IllegalArgumentException("Either the username or password is invalid!");
		}
		return OwnerRepository.findOwnerByUsername(username);
	}
    /*
     * @author Muhammad Hammad
     * 
     * @param String oldUsername
     * @param String username
     * @param String email
     * @param String password
     * @return Owner object
     * 
     * Method that updates an owner object corresponding to the old username with the new information
     * 
     * 
     */

	@Transactional
	public Owner updateOwner(String oldUsername, String username, String email, String password) {
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
		
		Owner OwnerUpdated = OwnerRepository.findOwnerByUsername(oldUsername);
		OwnerUpdated.setUsername(username);
		OwnerUpdated.setEmail(email);
		OwnerUpdated.setPassword(password);
		return OwnerRepository.save(OwnerUpdated);

	}



}