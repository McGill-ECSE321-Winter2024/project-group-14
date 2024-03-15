package ca.mcgill.ecse321.sportCenterRegistration.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

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
public class SportCenterRegistrationService {

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


	@Transactional
	public Instructor getInstructor(String username) {
		if (username == null || username.trim().length() == 0) {
			throw new IllegalArgumentException("Instructor name cannot be empty!");
		}

		Instructor instructor = InstructorRepository.findInstructorByUsername(username);
		return instructor;
	}

	@Transactional
	public Customer getCustomer(String username) {
		if (username == null || username.trim().length() == 0) {
			throw new IllegalArgumentException("Customer name cannot be empty!");
		}
		Customer customer = CustomerRepository.findCustomerByUsername(username);
		return customer;
	}

	
	@Transactional
	public Boolean deleteInstructor(String username) {
		Instructor instructorToDelete = getInstructor(username);
		InstructorRepository.delete(instructorToDelete);
		return true;
	}
	
	@Transactional
	public Boolean deleteCustomer(String username) {
		Customer customerToDelete = getCustomer(username);
		CustomerRepository.delete(customerToDelete);
		return true;
	}




    /*note: all the below are going to be our wrapper classes annotated with transactional. If you use 
    something that should be wrapped just add it in here */
    

    /*
	@Transactional
	public Person createPerson(String name) {
		Person person = new Person();
		person.setName(name);
		personRepository.save(person);
		return person;
	}

	@Transactional
	public Person getPerson(String name) {
		Person person = personRepository.findPersonByName(name);
		return person;
	}

	@Transactional
	public List<Person> getAllPersons() {
		return toList(personRepository.findAll());
	}

	@Transactional
	public Event createEvent(String name, Date date, Time startTime, Time endTime) {
		Event event = new Event();
		event.setName(name);
		event.setDate(date);
		event.setStartTime(startTime);
		event.setEndTime(endTime);
		eventRepository.save(event);
		return event;
	}

	@Transactional
	public Event getEvent(String name) {
		Event event = eventRepository.findEventByName(name);
		return event;
	}

	@Transactional
	public List<Event> getAllEvents() {
		return toList(eventRepository.findAll());
	}

	@Transactional
	public Registration register(Person person, Event event) {
		Registration registration = new Registration();
		registration.setId(person.getName().hashCode() * event.getName().hashCode());
		registration.setPerson(person);
		registration.setEvent(event);

		registrationRepository.save(registration);

		return registration;
	}

	@Transactional
	public List<Registration> getAllRegistrations(){
		return toList(registrationRepository.findAll());
	}

	@Transactional
	public List<Event> getEventsAttendedByPerson(Person person) {
		List<Event> eventsAttendedByPerson = new ArrayList<>();
		for (Registration r : registrationRepository.findByPerson(person)) {
			eventsAttendedByPerson.add(r.getEvent());
		}
		return eventsAttendedByPerson;
	}

*/

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}