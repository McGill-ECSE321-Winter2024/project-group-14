package ca.mcgill.ecse321.sportCenterRegistration.dto;

import java.sql.Time;
import java.util.List;
import ca.mcgill.ecse321.sportCenterRegistration.model.Staff;

import java.sql.Date;

public class ShiftDto {
    private Date date;
    private Time startTime;
    private Time endTime;
    private Integer id;
    private Staff staff;

    private List<String> errors;

    public ShiftDto(String error) {
        this.errors = List.of(error);
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
    
    public ShiftDto(){
    }

    public ShiftDto(Date date, Time startTime, Time endTime, Integer id, Staff staff){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.id = id;
        this.staff = staff;
    }

    public Date getDate(){
        return date;
    }

    public Time getStartTime(){
        return startTime;
    }

    public Time getEndTime(){
        return endTime;
    }

    public int getId(){
        return id;
    }

    public Staff geStaff(){
        return staff;
    }
}
