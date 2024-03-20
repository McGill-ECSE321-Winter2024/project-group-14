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
public class SportCenterSessionService {

	@Autowired
	AccountRepository AccountRepository;
	@Autowired
	CustomerRepository CustomerRepository;
	@Autowired
	InstructorRepository InstructorRepository;
	@Autowired
	OwnerRepository OwnerRepository;
	@Autowired
	RegistrationRepository RegistrationRepository;
	@Autowired
	SessionRepository SessionRepository;
	@Autowired
	ShiftRepository ShiftRepository;
	@Autowired
	SportClassRepository SportClassRepository;
	@Autowired
	StaffRepository StaffRepository;

	/*
	 * note: all the below are going to be our wrapper classes annotated with
	 * transactional. If you use
	 * something that should be wrapped just add it in here
	 */

	/**
	 * Section: Session servive
	 * Author: Stephen Huang
	 * This method creates a session schedual
	 */

	@Transactional
	public Session createSession(Time startTime, Time endTime, String location, Date date) {

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
		//REMOVE THIS I ONLY PUT NULL TO REMOVE ERROR
		Session session = new Session(null,null,null,null,null,null);
		session.setDate(date);
		session.setStartTime(startTime);
		session.setEndTime(endTime);
		session.setLocation(location);
		Session newSession = SessionRepository.save(session);
		return newSession;
	}

	/**
	 * Section: Session servive
	 * Author: Stephen Huang
	 * This method updates a session schedual
	 */

	public Session updateSession(int id, Time startTime, Time endTime, String location, Date date) {

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
		SessionRepository.save(session);
		return session;
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
	 * This method deletes a session schedual
	 */
	@Transactional
	public boolean deleteSession(int id) {

		// check null id

		if (id < 0) { // help me here !!!!!!!!!! LMAO
			throw new IllegalArgumentException("Id cannot be empty");
		}

		// check if the daily schedule exists
		Session session = SessionRepository.findSessionById(id);

		if (session == null)
			throw new IllegalArgumentException("Session not found");

		// delete dailyschedule
		SessionRepository.delete(session);
		session = null;
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

	/*
	 * @Transactional
	 * public Person createPerson(String name) {
	 * Person person = new Person();
	 * person.setName(name);
	 * personRepository.save(person);
	 * return person;
	 * }
	 * 
	 * @Transactional
	 * public Person getPerson(String name) {
	 * Person person = personRepository.findPersonByName(name);
	 * return person;
	 * }
	 * 
	 * @Transactional
	 * public List<Person> getAllPersons() {
	 * return toList(personRepository.findAll());
	 * }
	 * 
	 * @Transactional
	 * public Event createEvent(String name, Date date, Time startTime, Time
	 * endTime) {
	 * Event event = new Event();
	 * event.setName(name);
	 * event.setDate(date);
	 * event.setStartTime(startTime);
	 * event.setEndTime(endTime);
	 * eventRepository.save(event);
	 * return event;
	 * }
	 * 
	 * @Transactional
	 * public Event getEvent(String name) {
	 * Event event = eventRepository.findEventByName(name);
	 * return event;
	 * }
	 * 
	 * @Transactional
	 * public List<Event> getAllEvents() {
	 * return toList(eventRepository.findAll());
	 * }
	 * 
	 * @Transactional
	 * public Registration register(Person person, Event event) {
	 * Registration registration = new Registration();
	 * registration.setId(person.getName().hashCode() * event.getName().hashCode());
	 * registration.setPerson(person);
	 * registration.setEvent(event);
	 * 
	 * registrationRepository.save(registration);
	 * 
	 * return registration;
	 * }
	 * 
	 * @Transactional
	 * public List<Registration> getAllRegistrations(){
	 * return toList(registrationRepository.findAll());
	 * }
	 * 
	 * @Transactional
	 * public List<Event> getEventsAttendedByPerson(Person person) {
	 * List<Event> eventsAttendedByPerson = new ArrayList<>();
	 * for (Registration r : registrationRepository.findByPerson(person)) {
	 * eventsAttendedByPerson.add(r.getEvent());
	 * }
	 * return eventsAttendedByPerson;
	 * }
	 * 
	 */

	// private <T> List<T> toList(Iterable<T> iterable) {
	// 	List<T> resultList = new ArrayList<T>();
	// 	for (T t : iterable) {
	// 		resultList.add(t);
	// 	}
	// 	return resultList;
	// }

}
