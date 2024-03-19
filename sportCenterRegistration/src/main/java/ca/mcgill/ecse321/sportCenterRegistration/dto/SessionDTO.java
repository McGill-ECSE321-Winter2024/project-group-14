package ca.mcgill.ecse321.sportCenterRegistration.dto;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.SportClass;

public class SessionDTO {
    private SportClassDto sportclass;
    private Time startTime;
    private Time endTime;
    private String location;
    private Date date;
    private InstructorDTO instructor;
    
    public SessionDTO(){

    }

    public SessionDTO(Time startTime, Time endTime, String location, Date date, Instructor instructor, SportClass sportclassInput){
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.date = date;
        this.instructor = new InstructorDTO(instructor.getId(), instructor.getUsername(), instructor.getEmail(), instructor.getPassword());
        this.sportclass = new SportClassDto(sportclassInput.getName(), sportclassInput.getApproved());
    }
}
