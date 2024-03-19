package ca.mcgill.ecse321.sportCenterRegistration.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.sportCenterRegistration.dao.AccountRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.InstructorRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.SessionRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.SportClassRepository;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.Registration;
import jakarta.transaction.Transactional;

@Service
public class RegistrationService{
    @Autowired
    RegistrationRepository registrationRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    SportClassRepository sportClassRepository;

    //helper
    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

    //idk if we need this
    // @Transactional
    // public Registration getRegistration(Integer id){
    //     if(id == null){
    //         throw new IllegalArgumentException("id can not be null");
    //     }

    //     Registration registration = registrationRepository.findRegistrationById(id);
    //     if(registration == null){
    //         throw new IllegalArgumentException("id is invalid");
    //     }

    //     return registration;
    // }
    
    @Transactional
    public Registration getRegistration(String accountUsername, Time startTime, String instructorUsername, String sportClassName){
        if(accountUsername == null || accountUsername.strip().length() == 0){
            throw new IllegalArgumentException("Account name can not be null or empty");
        }
        
        if(startTime == null){
            throw new IllegalArgumentException("Start time can not be null");
        }

        Registration registration = registrationRepository.findRegistrationByAccountAndSession(accountRepository.findAccountByUsername(accountUsername),
         sessionRepository.findSessionByStartTimeAndInstructorAndSportClass(startTime, instructorRepository.findInstructorByUsername(instructorUsername), sportClassRepository.findSportClassByName(sportClassName)));


        if(registration == null){
            throw new IllegalArgumentException("Failed to create a registration");
        }

        return registration;
    }

    //idk if we need this
    // @Transactional
    // public Boolean deleteRegistration(Integer id){       

    //     Registration toDelete = getRegistration(id);
    //     registrationRepository.delete(toDelete);
	//     toDelete = null;
    //     return true;

    // }

    @Transactional
    public Boolean deleteRegistration(String accountUsername, Time startTime, String instructorUsername, String sportClassName){       

        Registration toDelete = getRegistration(accountUsername, startTime, instructorUsername, sportClassName);
        registrationRepository.delete(toDelete);
	    toDelete = null;
        return true;

    }

    //idk if we need this
    // @Transactional
    // public Registration createRegistration(Date aDate, Integer accountId, Integer sessionId){
    //     if(aDate == null){
    //         throw new IllegalArgumentException("Date can not be null");
    //     }
    //     if(accountId == null){
    //         throw new IllegalArgumentException("accountId can not be null");
    //     }
    //     if(sessionId == null){
    //         throw new IllegalArgumentException("sessionId can not be null");
    //     }
        
    //     Registration registration = new Registration(aDate, accountRepository.findAccountById(accountId), sessionRepository.findSessionById(sessionId));
        
    //     if(registration.getAccount() == null){
    //         throw new IllegalArgumentException("No account with id " + accountId + " found");
    //     }
    //     if(registration.getSession() == null){
    //         throw new IllegalArgumentException("No Session with id " + sessionId + " found");
    //     }

    //     registrationRepository.save(registration);
    //     return registration;
    // }

    @Transactional
    public Registration createRegistration(Date aDate, String username, String instructorUsername, String sportClassName, Time sessionStarTime){
        //fcheck if inputs are null and if strings are empty
        if(aDate == null){
            throw new IllegalArgumentException("Date can not be null");
        }
        if (username == null || username.strip().length() == 0) {
            throw new IllegalArgumentException("Customer username cannot be empty!");
        }
        if (instructorUsername == null || instructorUsername.strip().length() == 0) {
            throw new IllegalArgumentException("Instructor username cannot be empty!");
        }
        if (sportClassName == null || sportClassName.strip().length() == 0) {
            throw new IllegalArgumentException("Sport class name cannot be empty!");
        }
        if(sessionStarTime == null){
            throw new IllegalArgumentException("Session start time cannot be null!");
        }
        
        //check if user doesn't exist
        if(accountRepository.findAccountByUsername(username) == null){
            throw new IllegalArgumentException("Austomer with the given username doesn't exist");
        }
        //check if instructor doesn't exist
        if(instructorRepository.findInstructorByUsername(instructorUsername) == null){
            throw new IllegalArgumentException("Instructor with the given username doesn't exist");
        }
        //check if person registering for a session is also the instructor that is in charge of it
        if(accountRepository.findAccountByUsername(username).equals(instructorRepository.findInstructorByUsername(instructorUsername))){
            throw new IllegalArgumentException("Instructor can not register for the session they are supervising.");
        }
        //check if sportclass exists
        if(sportClassRepository.findSportClassByName(sportClassName) == null){
            throw new IllegalArgumentException("Sport class with the given name does not exist.");
        }
        //check if session with instructor, starttime, and sportclass exists
        if(sessionRepository.findSessionByStartTimeAndInstructorAndSportClass(sessionStarTime, instructorRepository.findInstructorByUsername(instructorUsername), sportClassRepository.findSportClassByName(sportClassName)) == null){
            throw new IllegalArgumentException("Session with the given start time, instructor, and sportclass does not exist.");
        }
        //check if session date is before registration date
        if(sessionRepository.findSessionByStartTimeAndInstructorAndSportClass(sessionStarTime, instructorRepository.findInstructorByUsername(instructorUsername), sportClassRepository.findSportClassByName(sportClassName)).getDate().before(aDate)){
            throw new IllegalArgumentException("Can not register to a session that already ended.");
        }
        //check if registration already exists
        if(registrationRepository.findRegistrationByAccountAndSession(accountRepository.findAccountByUsername(username), sessionRepository.findSessionByStartTimeAndInstructorAndSportClass(sessionStarTime, instructorRepository.findInstructorByUsername(instructorUsername), sportClassRepository.findSportClassByName(sportClassName))) != null){
            throw new IllegalArgumentException("Registration already exists");
        }

        Registration registration = new Registration(aDate, accountRepository.findAccountByUsername(username), sessionRepository.findSessionByStartTimeAndInstructorAndSportClass(sessionStarTime, (Instructor)accountRepository.findAccountByUsername(instructorUsername), sportClassRepository.findSportClassByName(sportClassName)));

        registrationRepository.save(registration);
        return registration;
    }

    @Transactional
	public List<Registration> getAllRegistrations() {
		return toList(registrationRepository.findAll());
	}
}