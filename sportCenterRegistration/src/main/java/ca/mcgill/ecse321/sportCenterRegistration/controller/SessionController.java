package main.java.ca.mcgill.ecse321.sportCenterRegistration.controller;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportCenterRegistration.dto.*;
import ca.mcgill.ecse321.sportCenterRegistration.model.*;
import ca.mcgill.ecse321.sportCenterRegistration.service.*;

@RestController
public class SessionController {
    
    @Autowired
    private SportClassService SessionService;
    @Autowired
    private InstructorService instructorService;
    @Autowired
    private OwnerService ownerService;


	/**
     * author: Stephen
     * This method gets all session
     */
    @GetMapping(value = { "/view_session" })
	public List<SessionDto> getAllStores() {
		return SessionService.getAllSession().stream().map(session -> convertToDTO(session))
				.collect(Collectors.toList());
	}

    /**
     * author: Stephen
     * This method  gets a specific session schedule given the corresponding id
     */
    @GetMapping(value = "/view_session/{id}")
    public SessionDto viewStore(@PathVariable("id") String id) {
    // Correctly parsing the id from String to int
    int sessionId = Integer.parseInt(id);

    // Assuming SessionService.getSession expects an int and returns a Session object
    // that needs to be converted to SessionDto
    return convertToDTO(SessionService.getSession(sessionId));
}

     /**
     * author: Stephen
     * This method creates a daily schedule
     */
    @PostMapping(value = { "/create_session" })
	public ResponseEntity<?> createSession(@RequestParam("date") Date date,
                                           @RequestParam("location") String location,
                                           @RequestParam("instructor") Instructor instructor,
                                           @RequestParam("sportclass") SportClass sportclass,
	                                       @RequestParam("startTime") String startTime,
			                               @RequestParam("endTime") String endTime) {

		Session session = null;

		startTime = startTime+":00";
		endTime = endTime+":00";

    try {
			session = sessionService.createSession(date, location, instructor,
					Time.valueOf(startTime), Time.valueOf(endTime));
		} catch (IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertToDTO(session), HttpStatus.CREATED);
		

}

@PutMapping(value = { "/update_session" })
	public ResponseEntity<?> updateSession(@RequestParam("SessionId") Int Id,
			@RequestParam("date") Date date, @RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime) {

		Session session = null;

		try {
			session = sessionService.updateSession(Integer.parseInt(Id), date,
					Time.valueOf(startTime), Time.valueOf(endTime));

		} catch (IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertToDTO(session), HttpStatus.CREATED);
	}

    @DeleteMapping(value = { "/delete_session" })
    public boolean deleteSession(@RequestParam("Id") String Id) {

		return instructorService.deleteSession(Integer.parseInt(Id));

	}

    public static SessionDto convertToDTO(Session session) {
		if (session == null)
			throw new IllegalArgumentException("Session not found.");

		SessionDto sessionDto = new SessionDto(session.getDate(),
        session.getStartTime(), session.getEndTime(), session.getId(), session.getInstructor(), session.getSportClass());
		return sessionDto;

}
}