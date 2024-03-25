package ca.mcgill.ecse321.sportCenterRegistration.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportCenterRegistration.dto.SessionDTO;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.Session;
import ca.mcgill.ecse321.sportCenterRegistration.model.SportClass;
import ca.mcgill.ecse321.sportCenterRegistration.service.InstructorService;
import ca.mcgill.ecse321.sportCenterRegistration.service.OwnerService;
import ca.mcgill.ecse321.sportCenterRegistration.service.SessionService;

@RestController
public class SessionController {

	@Autowired
	private SessionService SessionService;
	@Autowired
	private InstructorService instructorService;
	@Autowired
	private OwnerService ownerService;

	/**
	 * author: Stephen
	 * This method gets all session
	 */
	@GetMapping(value = { "/view_session" })
	public List<SessionDTO> getAllStores() {
		return SessionService.getAllSession().stream().map(session -> convertToDTO(session))
				.collect(Collectors.toList());
	}

	/**
	 * author: Stephen
	 * This method gets a specific session schedule given the corresponding id
	 */
	@GetMapping(value = "/view_session/{id}")
	public SessionDTO viewStore(@PathVariable("id") String id) {
		// Correctly parsing the id from String to int
		int sessionId = Integer.parseInt(id);

		// Assuming SessionService.getSession expects an int and returns a Session
		// object
		// that needs to be converted to SessionDTO
		return convertToDTO(SessionService.getSession(sessionId));
	}

	/**
	 * author: Stephen
	 * This method creates a daily schedule
	 */
	@PostMapping(value = { "/create_session" })
	public ResponseEntity<?> createSession(
			@RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime,
			@RequestParam("location") String location,
			@RequestParam("date") Date date,
			@RequestParam("instructor") Instructor instructor,
			@RequestParam("sportclass") SportClass sportclass) {

		Session session = null;

		startTime = startTime + ":00";
		endTime = endTime + ":00";

		try {
			session = SessionService.createSession(Time.valueOf(startTime), Time.valueOf(endTime), location, date,
					instructor, sportclass);
		} catch (IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertToDTO(session), HttpStatus.CREATED);

	}

	@PutMapping(value = { "/update_session" })
	public ResponseEntity<?> updateSession(@RequestParam("SessionId") int Id,
			@RequestParam("date") Date date, @RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime) {

		Session session = null;

		try {
			session = SessionService.updateSession(Id, Time.valueOf(startTime), Time.valueOf(endTime),
					SessionService.getSession(Id).getLocation(), date, SessionService.getSession(Id).getInstructor(),
					SessionService.getSession(Id).getSportClass());

		} catch (IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertToDTO(session), HttpStatus.CREATED);
	}

	@DeleteMapping(value = { "/delete_session" })
	public boolean deleteSession(@RequestParam("Id") String Id) {

		return SessionService.deleteSession(Integer.parseInt(Id));

	}

	public static SessionDTO convertToDTO(Session session) {
		if (session == null)
			throw new IllegalArgumentException("Session not found.");

		SessionDTO sessionDto = new SessionDTO(session.getDate(),
				session.getStartTime(), session.getEndTime(), session.getId(), session.getLocation(),
				session.getInstructor(), session.getSportClass());
		return sessionDto;

	}
}