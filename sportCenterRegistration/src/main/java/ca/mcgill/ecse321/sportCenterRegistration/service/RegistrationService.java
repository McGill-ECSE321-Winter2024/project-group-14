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
import ca.mcgill.ecse321.sportCenterRegistration.model.Account;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.Registration;
import ca.mcgill.ecse321.sportCenterRegistration.model.Session;
import ca.mcgill.ecse321.sportCenterRegistration.model.SportClass;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.transaction.Transactional;

@Service
public class RegistrationService {
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

    // helper
    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

    @Transactional
    public Registration createRegistration(Date aDate, String registeringAccountUsername, int sessionId) {
        // fcheck if inputs are null and if strings are empty
        if (aDate == null) {
            throw new IllegalArgumentException("Date can not be null");
        }
        if (registeringAccountUsername == null || registeringAccountUsername.strip().length() == 0) {
            throw new IllegalArgumentException("The registering account's username cannot be empty!");
        }
        if (sessionId < 0) {
            throw new IllegalArgumentException("Session id can not be negative");
        }

        Account account = accountRepository.findAccountByUsername(registeringAccountUsername);
        Session session = sessionRepository.findSessionById(sessionId);

        if (account == null)
            throw new IllegalArgumentException("No account with the given username exists.");
        if (session == null)
            throw new IllegalArgumentException("No session with the given id exists.");

        if (registeringAccountUsername.equals(session.getInstructor().getUsername()))
            throw new IllegalArgumentException("Instructor can not register to their own session!");

        if (session.getDate().before(aDate))
            throw new IllegalArgumentException("Can not register to a class that has already ended.");

        Registration registration = registrationRepository.findRegistrationByAccountAndSession(account, session);
        if (registration != null) {
            throw new IllegalArgumentException("Registration already exists.");
        }

        registration = new Registration(aDate, account, session);
        return registrationRepository.save(registration);
    }

    @Transactional
    public List<Registration> getRegistrationByAccount(String registeringAccountUsername) {

        if (registeringAccountUsername == null || registeringAccountUsername.strip().length() == 0) {
            throw new IllegalArgumentException("Account username can not be null or empty");
        }

        Account account = accountRepository.findAccountByUsername(registeringAccountUsername);

        if (account == null)
            throw new IllegalArgumentException("No account with the given username exists.");

        List<Registration> registrations = registrationRepository.findRegistrationByAccount(account);

        return registrations;
    }

    @Transactional
    public List<Registration> getRegistrationBySession(int sessionId) {
        if (sessionId < 0) {
            throw new IllegalArgumentException("Session id can not be negative");
        }

        Session session = sessionRepository.findSessionById(sessionId);

        if (session == null)
            throw new IllegalArgumentException("No session with the given id exists.");

        List<Registration> registrations = registrationRepository.findRegistrationBySession(session);

        return registrations;
    }

    @Transactional
    public Registration getRegistrationByAccountAndSession(String registeringAccountUsername, int sessionId) {
        Account account = accountRepository.findAccountByUsername(registeringAccountUsername);
        Session session = sessionRepository.findSessionById(sessionId);

        if (account == null)
            throw new IllegalArgumentException("Account can not be null.");
        if (session == null)
            throw new IllegalArgumentException("Session can not be null.");

        Registration registration = registrationRepository.findRegistrationByAccountAndSession(account, session);
        if (registration == null)
            throw new IllegalArgumentException("No such registration with the given account and session exists.");

        return registration;
    }

    @Transactional
    public Registration deleteById(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Registration id can not be negative");
        }
        Registration registration = registrationRepository.findRegistrationById(id);
        registrationRepository.delete(registration);
        return registration;
    }

    @Transactional
    public Registration deleteRegistrationByAccountAndSessionId(String accountName, int sessionId) {
        if (accountName == null || accountName.strip().length() == 0) {
            throw new IllegalArgumentException("Account name can not be null or empty");
        }
        if (sessionId < 0) {
            throw new IllegalArgumentException("Registration id can not be negative");
        }
        System.out.println(accountName);
        Account account = accountRepository.findAccountByUsername(accountName);
        Session session = sessionRepository.findSessionById(sessionId);
        System.out.println(account.getUsername());
        Registration registration = registrationRepository.findRegistrationByAccountAndSession(account, session);

        registrationRepository.delete(registration);
        return registration;
    }

    // @Transactional
    // public Registration getRegistration(String registeringAccountUsername, Time
    // startTime, String instructorUsername,
    // String sportClassName) {

    // if (registeringAccountUsername == null ||
    // registeringAccountUsername.strip().length() == 0) {
    // throw new IllegalArgumentException("Account username can not be null or
    // empty");
    // }
    // if (instructorUsername == null || instructorUsername.strip().length() == 0) {
    // throw new IllegalArgumentException("Instructor username can not be null or
    // empty");
    // }
    // if (sportClassName == null || sportClassName.strip().length() == 0) {
    // throw new IllegalArgumentException("Sport Class name can not be null or
    // empty");
    // }
    // if (startTime == null) {
    // throw new IllegalArgumentException("Start time can not be null");
    // }

    // Account account =
    // accountRepository.findAccountByUsername(registeringAccountUsername);
    // Instructor instructor =
    // instructorRepository.findInstructorByUsername(instructorUsername);
    // SportClass sportClass =
    // sportClassRepository.findSportClassByName(sportClassName);

    // if (account == null)
    // throw new IllegalArgumentException("No account with the given username
    // exists.");
    // if (sportClass == null)
    // throw new IllegalArgumentException("No sportclass with the given name
    // exists.");
    // if (instructor == null)
    // throw new IllegalArgumentException("No instructor with the given username
    // exists.");

    // Session session =
    // sessionRepository.findSessionByStartTimeAndInstructorAndSportClass(startTime,
    // (Instructor) instructor, sportClass);

    // if (session == null)
    // throw new IllegalArgumentException("No such session exists.");

    // Registration registration =
    // registrationRepository.findRegistrationByAccountAndSession(account, session);
    // if (registration == null) {
    // throw new IllegalArgumentException("No such registration exists.");
    // }

    // return registration;
    // }

    // @Transactional
    // public Registration getRegistrationByAccountAndSession(Account account,
    // Session session) {
    // if (account == null)
    // throw new IllegalArgumentException("Account can not be null.");
    // if (session == null)
    // throw new IllegalArgumentException("Session can not be null.");

    // Registration registration =
    // registrationRepository.findRegistrationByAccountAndSession(account, session);
    // if (registration == null)
    // throw new IllegalArgumentException("No such registration with the given
    // account and session exists.");

    // return registration;
    // }

    // @Transactional
    // public Registration updateRegistration(Account oldAccount, Session
    // oldSession, Account newAccount,
    // Session newSession, Date newDate) {
    // if (newDate == null)
    // throw new IllegalArgumentException("Date can not be null");
    // if (oldAccount == null)
    // throw new IllegalArgumentException("No old account given");
    // if (oldSession == null)
    // throw new IllegalArgumentException("No old session given");
    // if (newAccount == null)
    // throw new IllegalArgumentException("No new account given");
    // if (newSession == null)
    // throw new IllegalArgumentException("No new session given");

    // Account account =
    // accountRepository.findAccountByUsername(oldAccount.getUsername());
    // if (account == null)
    // throw new IllegalArgumentException("oldAccount was not saved in the account
    // repository");

    // Session session =
    // sessionRepository.findSessionByStartTimeAndInstructorAndSportClass(oldSession.getStartTime(),
    // oldSession.getInstructor(), oldSession.getSportClass());
    // if (session == null)
    // throw new IllegalArgumentException("oldSession was not saved in the session
    // repository");

    // Registration toUpdate =
    // registrationRepository.findRegistrationByAccountAndSession(account, session);
    // if (toUpdate == null)
    // throw new IllegalArgumentException("registration with the given account and
    // session was not found");

    // if (accountRepository.findAccountByUsername(newAccount.getUsername()) ==
    // null)
    // throw new IllegalArgumentException("new account does not exist");

    // if
    // (sessionRepository.findSessionByStartTimeAndInstructorAndSportClass(newSession.getStartTime(),
    // newSession.getInstructor(), newSession.getSportClass()) == null)
    // throw new IllegalArgumentException("new session does not exist");

    // if (newAccount.equals(newSession.getInstructor()))
    // throw new IllegalArgumentException("Can not make instructor register to his
    // own class");
    // if (newSession.getDate().before(newDate))
    // throw new IllegalArgumentException("Can not update registration after session
    // has ended");

    // Registration registration =
    // registrationRepository.findRegistrationByAccountAndSession(account, session);
    // registration.setAccount(newAccount);
    // registration.setDate(newDate);
    // registration.setSession(newSession);
    // return null;
    // }

    // @Transactional
    // public Registration createRegistration(Date aDate, String
    // registeringAccountUsername, String instructorUsername,
    // String sportClassName, Time sessionStarTime) {
    // // fcheck if inputs are null and if strings are empty
    // if (aDate == null) {
    // throw new IllegalArgumentException("Date can not be null");
    // }
    // if (registeringAccountUsername == null ||
    // registeringAccountUsername.strip().length() == 0) {
    // throw new IllegalArgumentException("The registering account's username cannot
    // be empty!");
    // }
    // if (instructorUsername == null || instructorUsername.strip().length() == 0) {
    // throw new IllegalArgumentException("Instructor username cannot be empty!");
    // }
    // if (sportClassName == null || sportClassName.strip().length() == 0) {
    // throw new IllegalArgumentException("Sport class name cannot be empty!");
    // }
    // if (sessionStarTime == null) {
    // throw new IllegalArgumentException("Session start time cannot be null!");
    // }

    // Account account =
    // accountRepository.findAccountByUsername(registeringAccountUsername);
    // SportClass sportClass =
    // sportClassRepository.findSportClassByName(sportClassName);
    // Account instructor =
    // instructorRepository.findInstructorByUsername(instructorUsername);

    // if (account == null)
    // throw new IllegalArgumentException("No account with the given username
    // exists.");
    // if (sportClass == null)
    // throw new IllegalArgumentException("No sportclass with the given name
    // exists.");
    // if (instructor == null)
    // throw new IllegalArgumentException("No instructor with the given username
    // exists.");

    // if (registeringAccountUsername.equals(instructorUsername))
    // throw new IllegalArgumentException("Instructor can not register to their own
    // session!");

    // Session session =
    // sessionRepository.findSessionByStartTimeAndInstructorAndSportClass(sessionStarTime,
    // (Instructor) instructor, sportClass);

    // if (session == null)
    // throw new IllegalArgumentException("No such session exists.");

    // if (session.getDate().before(aDate))
    // throw new IllegalArgumentException("Can not register to a class that has
    // already ended.");

    // Registration registration =
    // registrationRepository.findRegistrationByAccountAndSession(account, session);
    // if (registration != null) {
    // throw new IllegalArgumentException("Registration already exists.");
    // }

    // registration = new Registration(aDate, account, session);
    // return registrationRepository.save(registration);
    // }

    // @Transactional
    // public boolean deleteRegistration(String registeringAccountUsername, String
    // instructorUsername,
    // String sportClassName, Time sessionStarTime) {
    // if (registeringAccountUsername == null ||
    // registeringAccountUsername.strip().length() == 0) {
    // throw new IllegalArgumentException("The registering account's username cannot
    // be empty!");
    // }
    // if (instructorUsername == null || instructorUsername.strip().length() == 0) {
    // throw new IllegalArgumentException("Instructor username cannot be empty!");
    // }
    // if (sportClassName == null || sportClassName.strip().length() == 0) {
    // throw new IllegalArgumentException("Sport class name cannot be empty!");
    // }
    // if (sessionStarTime == null) {
    // throw new IllegalArgumentException("Session start time cannot be null!");
    // }

    // Account account =
    // accountRepository.findAccountByUsername(registeringAccountUsername);
    // SportClass sportClass =
    // sportClassRepository.findSportClassByName(sportClassName);
    // Account instructor =
    // instructorRepository.findInstructorByUsername(instructorUsername);

    // if (account == null)
    // throw new IllegalArgumentException("No account with the given username
    // exists.");
    // if (sportClass == null)
    // throw new IllegalArgumentException("No sportclass with the given name
    // exists.");
    // if (instructor == null)
    // throw new IllegalArgumentException("No instructor with the given username
    // exists.");

    // Session session =
    // sessionRepository.findSessionByStartTimeAndInstructorAndSportClass(sessionStarTime,
    // (Instructor) instructor, sportClass);

    // if (session == null)
    // throw new IllegalArgumentException("No such session exists.");
    // Registration registration =
    // registrationRepository.findRegistrationByAccountAndSession(account, session);

    // if (registration == null)
    // return false;

    // registrationRepository.delete(registration);
    // return true;
    // }

    @Transactional
    public List<Registration> getAllRegistrations() {
        return toList(registrationRepository.findAll());
    }
}
