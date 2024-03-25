package ca.mcgill.ecse321.sportCenterRegistration.dto;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.SportClass;

public class SessionDTO {

	private int id;
	private Time startTime;
	private Time endTime;
	private String location;
	private Date date;
	private Instructor instructor;
	private SportClass sportClass;

	public SessionDTO() {
	}

	public SessionDTO(Date date, Time startTime, Time endTime, int id, String location, Instructor instructor,
			SportClass sportClass) {
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.location = location;
		this.instructor = instructor;
		this.sportClass = sportClass;
		this.id = id;
	}

	public Time getEndTime() {
		return endTime;
	}

	public Time getStartTime() {
		return startTime;
	}

	public Date getDate() {
		return date;
	}

	public int getId() {
		return id;
	}

	public String getLocation() {
		return location;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public SportClass getSportClass() {
		return sportClass;
	}
}
