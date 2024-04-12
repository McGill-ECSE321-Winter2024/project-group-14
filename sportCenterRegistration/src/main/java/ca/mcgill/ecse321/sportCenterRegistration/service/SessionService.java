package ca.mcgill.ecse321.sportCenterRegistration.service;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportCenterRegistration.dao.*;
import ca.mcgill.ecse321.sportCenterRegistration.model.*;

@Service
public class SessionService {
    @Autowired
    SessionRepository sessionRepo;
    @Autowired
    SportClassRepository sportClassRepo;
    @Autowired
    InstructorRepository instructorRepo;

    /**
     * Section: Session servive
     * Author: Stephen Huang
     * This method creates a session schedual
     */

    @Transactional
    public Session createSession(Time startTime, Time endTime, String location, Date date, String instructorName,
            String sportClassName) {
        Instructor instructor = instructorRepo.findInstructorByUsername(instructorName);
        SportClass sportClass = sportClassRepo.findSportClassByName(sportClassName);
        // checking all inputs are valid
        String error = "";
        if (location == null) {
            throw new IllegalArgumentException("Session location cannot be empty!");
        }
        if (date == null) {
            throw new IllegalArgumentException("Session date cannot be empty!");
        }
        if (startTime == null) {
            throw new IllegalArgumentException("Session start time cannot be empty!");
        }
        if (endTime == null) {
            throw new IllegalArgumentException("Session end time cannot be empty!");
        }
        if (endTime != null && startTime != null && endTime.before(startTime)) {
            throw new IllegalArgumentException("Session end time cannot be before Session start time! ");
        }
        if (instructor == null) {
            throw new IllegalArgumentException("Session instructor cannot be empty!");
        }
        if (sportClass == null) {
            throw new IllegalArgumentException("Session sport class cannot be empty!");
        }
        System.out.println("ok");
        // create a session
        Session session = new Session(startTime, endTime, location, date, instructor, sportClass);
        Session newSession = sessionRepo.save(session);
        return newSession;
    }

    /**
     * Section: Session servive
     * Author: Stephen Huang
     * This method updates a session schedual
     */

    public Session updateSession(int id, Time startTime, Time endTime, String location, Date date,
            String instructorName, SportClass sportClass) {
        Instructor instructor = instructorRepo.findInstructorByUsername(instructorName);
        // check if the session exists
        Session session = sessionRepo.findSessionById(id);
        if (session == null) {
            throw new IllegalArgumentException("No Session found");
        }

        // check if all the inputs are vaild
        String error = "";
        if (location == null) {
            throw new IllegalArgumentException("Session location cannot be empty!");
        }
        if (date == null) {
            throw new IllegalArgumentException("Session date cannot be empty!");
        }
        if (startTime == null) {
            throw new IllegalArgumentException("Session start time cannot be empty!");
        }
        if (endTime == null) {
            throw new IllegalArgumentException("Session end time cannot be empty!");
        }
        if (endTime != null && startTime != null && endTime.before(startTime)) {
            throw new IllegalArgumentException("Session end time cannot be before Session start time! ");
        }
        if (instructor == null) {
            throw new IllegalArgumentException("Session instructor cannot be empty!");
        }
        if (sportClass == null) {
            throw new IllegalArgumentException("Session sport class cannot be empty!");
        }
        // update session
        session.setDate(date);
        session.setStartTime(startTime);
        session.setEndTime(endTime);
        session.setLocation(location);
        session.setInstructor(instructor);
        return sessionRepo.save(session);
    }

    /**
     * Section: Session servive
     * Author: Stephen Huang
     * This method gets a specific session schedule given the corresponding id
     */
    @Transactional
    public List<Session> getSession(int sessionId) {

        // return dailyschedule with the inputed id
        List<Session> sessions = new ArrayList<Session>();
        sessions.add(sessionRepo.findSessionById(sessionId));
        return sessions;
    }

    /**
     * Section: Session servive
     * Author: Stephen Huang
     * This method gets a specific session schedule given the corresponding
     * 
     * @param sportClassName is the target sport class used to retrive all Sessions
     *                       from database
     *                       sportclasstype
     */
    @Transactional
    public List<Session> getSessionBySportClass(String sportClassName) {
        System.out.println(sportClassName);
        // return dailyschedule with the inputed id
        SportClass sportClass = sportClassRepo.findSportClassByName(sportClassName);
        System.out.println(sportClassName);
        if (sportClass == null) {
            throw new IllegalArgumentException("Sport Class does not exist!");
        }
        List<Session> sessions = sessionRepo.findSessionBySportClass(sportClass);
        System.out.println("hh");
        return sessions;
    }

    /**
     * Section: Session servive
     * Author: Stephen Huang
     * This method deletes a session schedual
     */
    @Transactional
    public boolean deleteSession(int id) {

        // check if the daily schedule exists
        Session session = sessionRepo.findSessionById(id);

        // check null
        if (session == null)
            throw new IllegalArgumentException("Session not found");

        // check Id for an invalid "id" value
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be positive and non-zero");
        }

        // delete dailyschedule
        sessionRepo.delete(session);
        // session.delete();
        return true;
    }

    /**
     * Section: Session servive
     * Author: Stephen Huang
     * This method gets all session
     */
    @Transactional
    public List<Session> getAllSession() {

        // return all session in the system
        return toList(sessionRepo.findAll());
    }

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;

    }
}