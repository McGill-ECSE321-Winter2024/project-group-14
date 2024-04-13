package ca.mcgill.ecse321.sportCenterRegistration.controller;

import java.sql.Time;
import java.sql.Date;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportCenterRegistration.dto.*;
import ca.mcgill.ecse321.sportCenterRegistration.model.*;
import ca.mcgill.ecse321.sportCenterRegistration.service.*;

@RestController
@CrossOrigin(origins = "*")
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
	@GetMapping(value = { "/view_sessions", "/view_sessions/" })
	public ResponseEntity<?> getSessions(
			@RequestParam(value = "sessionId", required = false) String sessionId,
			@RequestParam(value = "sportclassName", required = false) String sportclassName)
			throws IllegalArgumentException {
		if (sessionId == null && sportclassName == null) {
			return ResponseEntity.ok(SessionService.getAllSession().stream().map(session -> convertToDTO(session))
					.collect(Collectors.toList()));
		}
		if (sportclassName != null) {
			return ResponseEntity.ok(
					SessionService.getSessionBySportClass(sportclassName).stream().map(session -> convertToDTO(session))
							.collect(Collectors.toList()));
		}
		if (sessionId != null) {
			return ResponseEntity.ok(SessionService.getSession(Integer.parseInt(sessionId)).stream()
					.map(session -> convertToDTO(session))
					.collect(Collectors.toList()));
		}
		return ResponseEntity.notFound().build();

	}

	/**
	 * author: Stephen
	 * This method gets a specific session schedule given the corresponding id
	 */
	// @GetMapping(value = { "/view_session/{id}", "/view_session/{id}/" })
	// public SessionDTO viewStore(@PathVariable("id") String id) {
	// // Correctly parsing the id from String to int
	// int sessionId = Integer.parseInt(id);

	// // Assuming SessionService.getSession expects an int and returns a Session
	// // object
	// // that needs to be converted to SessionDTO
	// return convertToDTO(SessionService.getSession(sessionId));
	// }

	// @GetMapping(value = "view_sessios/{sportclassName}")
	// public List<SessionDTO>
	// getSessionBySportClass(@PathVariable("sportclassName") String sportclassName)
	// {
	// try {
	// return
	// SessionService.getSessionBySportClass(sportclassName).stream().map(session ->
	// convertToDTO(session))
	// .collect(Collectors.toList());
	// } catch (IllegalArgumentException e) {
	// return null;
	// }
	// }

	/**
	 * author: Stephen
	 * This method creates a daily schedule
	 */
	@PostMapping(value = { "/create_session", "/create_session/" })
	public ResponseEntity<?> createSession(
			@RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime,
			@RequestParam("location") String location,
			@RequestParam("date") String date,
			@RequestParam("instructorName") String instructorName,
			@RequestParam("sportclassName") String sportclassName) {

		Session session = null;

		startTime = startTime + ":00";
		endTime = endTime + ":00";

		try {
			session = SessionService.createSession(Time.valueOf(startTime), Time.valueOf(endTime), location,
					Date.valueOf(date), instructorName, sportclassName);
			return ResponseEntity.ok(convertToDTO(session));
		} catch (IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = { "/update_session/{sessionId}", "/update_session/{sessionId}/" })
	public ResponseEntity<?> updateSession(@PathVariable("sessionId") String Id,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "location", required = false) String location,
			@RequestParam(value = "date", required = false) String date,
			@RequestParam(value = "instructorName", required = false) String instructorName) {

		Session session = SessionService.getSession(Integer.parseInt(Id)).get(0);

		if (startTime == null) {
			startTime = session.getStartTime().toString();
		}
		if (endTime == null) {
			endTime = session.getEndTime().toString();
		}

		if (location == null) {
			location = session.getLocation();
		}

		if (date == null) {
			date = session.getDate().toString();
		}

		if (instructorName == null) {
			instructorName = session.getInstructor().getUsername();
		}

		try {
			session = SessionService.updateSession(Integer.parseInt(Id), Time.valueOf(startTime), Time.valueOf(endTime),
					location, Date.valueOf(date), instructorName, session.getSportClass());
			return ResponseEntity.ok(convertToDTO(session));
		} catch (IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = { "/delete_session", "/delete_session/" })
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