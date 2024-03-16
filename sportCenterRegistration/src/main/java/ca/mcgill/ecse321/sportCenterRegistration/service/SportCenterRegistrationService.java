package ca.mcgill.ecse321.sportCenterRegistration.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.sportCenterRegistration.dao.AccountRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.SessionRepository;
import ca.mcgill.ecse321.sportCenterRegistration.model.Registration;
import jakarta.transaction.Transactional;

@Service
public class SportCenterRegistrationService{
    @Autowired
    RegistrationRepository registrationRepository;
    @Autowired
    AccountRepository accounterRepository;
    @Autowired
    SessionRepository sessionRepository;


    //helper
    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

    @Transactional
    public Registration getRegistration(Integer id){
        if(id == null){
            throw new IllegalArgumentException("id can not be null");
        }

        Registration registration = registrationRepository.findRegistrationById(id);
        if(registration == null){
            throw new IllegalArgumentException("id is invalid");
        }

        return registration;
    }
    
    @Transactional
    public Boolean deleteRegistration(Integer id){       

        Registration toDelete = getRegistration(id);
        registrationRepository.delete(toDelete);
	toDelete = null;
        return true;

    }

    @Transactional
    public Registration createRegistration(Date aDate, Integer accountId, Integer sessionId){
        if(aDate == null){
            throw new IllegalArgumentException("Date can not be null");
        }
        if(accountId == null){
            throw new IllegalArgumentException("accountId can not be null");
        }
        if(sessionId == null){
            throw new IllegalArgumentException("sessionId can not be null");
        }
        
        Registration registration = new Registration(aDate, accounterRepository.findAccountById(accountId), sessionRepository.findSessionById(sessionId));
        
        if(registration.getAccount() == null){
            throw new Error("No account with id " + accountId + " found");
        }
        if(registration.getSession() == null){
            throw new Error("No Session with id " + sessionId + " found");
        }

        registrationRepository.save(registration);
        return registration;
    }

    @Transactional
    public Registration createRegistration(Date aDate, String username, Integer sessionId){
        if(aDate == null){
            throw new IllegalArgumentException("Date can not be null");
        }
        if (username == null || username.strip() == "") {
            throw new IllegalArgumentException("Username cannot be empty!");
        }
        if(sessionId == null){
            throw new IllegalArgumentException("sessionId can not be null");
        }
        
        Registration registration = new Registration(aDate, accounterRepository.findAccountByUsername(username), sessionRepository.findSessionById(sessionId));
        
        if(registration.getAccount() == null){
            throw new Error("No account with username " + username + " found");
        }
        if(registration.getSession() == null){
            throw new Error("No Session with id " + sessionId + " found");
        }

        registrationRepository.save(registration);
        return registration;
    }

    @Transactional
	public List<Registration> getAllRegistrations() {
		return toList(registrationRepository.findAll());
	}
}
