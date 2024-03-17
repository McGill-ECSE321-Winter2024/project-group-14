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
    SessionRepository SessionRepository;

    /**
     * Section: Session servive
     * Author: Stephen Huang
     * This method creates a session schedual
     */

    @Transactional
    public Session createSession(Time startTime, Time endTime, String location, Date date, Instructor instructor, SportClass sportClass) {

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

        // create a session
        Session session = new Session(startTime, endTime, location, date, instructor, sportClass);
        Session newSession = SessionRepository.save(session);
        return newSession;
    }

    /**
     * Section: Session servive
     * Author: Stephen Huang
     * This method updates a session schedual
     */

    public Session updateSession(int id, Time startTime, Time endTime, String location, Date date, Instructor instructor, SportClass sportClass) {

        // check if the session exists
        Session session = SessionRepository.findSessionById(id);
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

        // update session
        session.setDate(date);
        session.setStartTime(startTime);
        session.setEndTime(endTime);
        session.setLocation(location);
        return SessionRepository.save(session);
    }

    /**
     * Section: Session servive
     * Author: Stephen Huang
     * This method gets a specific session schedule given the corresponding id
     */
    @Transactional
    public Session getSession(int id) {

        // return dailyschedule with the inputed id
        Session session = SessionRepository.findSessionById(id);
        return session;
    }

    /**
     * Section: Session servive
     * Author: Stephen Huang
     * This method gets a specific session schedule given the corresponding
     * sportclasstype
     */
    @Transactional
    public List<Session> getSession(SportClass sportClass) {

        // return dailyschedule with the inputed id
        List<Session> sessions = SessionRepository.findSessionBySportClass(sportClass);
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
        Session session = SessionRepository.findSessionById(id);

        // check null
        if (session == null)
            throw new IllegalArgumentException("Session not found");

        // check Id for an invalid "id" value
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be positive and non-zero");
        }

        // delete dailyschedule
        SessionRepository.delete(session);
//        session.delete();
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
        return toList(SessionRepository.findAll());
    }

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;

    }
}