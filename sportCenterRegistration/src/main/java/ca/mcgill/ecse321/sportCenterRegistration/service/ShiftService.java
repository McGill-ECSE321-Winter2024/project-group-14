package ca.mcgill.ecse321.sportCenterRegistration.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.sql.Time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportCenterRegistration.dao.ShiftRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.StaffRepository;
import ca.mcgill.ecse321.sportCenterRegistration.model.Shift;

@Service
public class ShiftService {
    @Autowired 
    ShiftRepository shiftRepository;

    @Autowired 
    StaffRepository staffRepository;


    /**
	 * Service method for the creation of a shift. Returns the created instance if the inputs are correct.
	 * 
	 * @author Ming Xuan Yue
	 * @param date
	 * @param startTime
	 * @param endTime
	 * @param staffUsername
	 * @return shift
	 */
    @Transactional
    public Shift createShift(Time startTime, Time endTime, Date date, String staffUsername) {

	// check if all the inputs are valid
    validateInputs(date, startTime, endTime, staffUsername);
        
    Shift shift = new Shift(startTime, endTime, date, staffRepository.findStaffByUsername(staffUsername));
    shiftRepository.save(shift);
    return shift;
    }
        
    /**
    * Service method for the update of a shift. Returns the updated instance if the inputs are correct.
    * 
    * @author Ming Xuan Yue
    * @param date
    * @param startTime
    * @param endTime
    * @param staffUsername
    * @param id
    * @return shift
    */         

    @Transactional
	public Shift updateShift(Date date, Time startTime, Time endTime, String staffUsername, Integer id) {

    if(id == null){
        throw new IllegalArgumentException("Input a correct iD");
    }
	// search for the shift with a specific id and check if the shift exists
    Shift shift = shiftRepository.findShiftById(id);

	if (shift == null) {
		throw new IllegalArgumentException("No shift with the given id has been found");
	}

	// check if all the inputs are vaild
    validateInputs(date, startTime, endTime, staffUsername);

	// update shift
	shift.setDate(date);
	shift.setStartTime(startTime);
	shift.setEndTime(endTime);
	shiftRepository.save(shift);
	return shift;
	}

    /**
    * Service method to get an instance of shift by id. 
    * 
    * @author Ming Xuan Yue
    * @param id
    * @return shift
    */        

    @Transactional
    public Shift getShiftById(Integer id){
        if(id == null){
            throw new IllegalArgumentException("Input a correct iD");
        }
        return shiftRepository.findShiftById(id);
    }

    /**
    * Service method to get a list of shifts by staff and date. 
    * 
    * @author Ming Xuan Yue
    * @param staffUsername
    * @param date
    * @return List<shift>
    */        

    @Transactional
    public List<Shift> getShiftByStaffandDate(String staffUsername, Date date){
        if (date == null) {
            throw new IllegalArgumentException("Please enter a valid date");
        }

        if(staffUsername == null || staffUsername.trim().length() == 0){
            throw new IllegalArgumentException("Please enter a valid staff");
        }

        if (staffRepository.findStaffByUsername(staffUsername) == null){
            throw new IllegalArgumentException("The staff doesn't exist");
        }

        return shiftRepository.findShiftByStaffAndDate(staffRepository.findStaffByUsername(staffUsername), date);
    }
    
    /**
    * Service method to get an instance of shift by date. 
    * 
    * @author Ming Xuan Yue
    * @param date
    * @return List<shift>
    */        

    @Transactional
    public List<Shift> getShiftByDate(Date date){
        if (date == null) {
            throw new IllegalArgumentException("Please enter a valid date");
        }

        return shiftRepository.findShiftByDate(date);
    }    

    /**
    * Service method to get an instance of shift by staff. 
    * 
    * @author Ming Xuan Yue
    * @param staffUsername
    * @return List<shift>
    */        

    @Transactional
    public List<Shift> getShiftByStaff(String staffUsername){
        if(staffUsername == null || staffUsername.trim().length() == 0){
            throw new IllegalArgumentException("Please enter a valid staff");
        }

        if (staffRepository.findStaffByUsername(staffUsername) == null){
            throw new IllegalArgumentException("The staff doesn't exist");
        }

        return shiftRepository.findShiftByStaff(staffRepository.findStaffByUsername(staffUsername));
    }
    
    /**
    * Service method to get all instances of shift. 
    * 
    * @author Ming Xuan Yue
    * @return List<shift>
    */     
    
    @Transactional
    public List<Shift> getAllShift(){
        return toList(shiftRepository.findAll());
    }

    /**
    * Service method to delete an instance of shift by id.
    * 
    * @author Ming Xuan Yue
    * @param id
    * @return boolean
    */         

	public boolean deleteShiftById(Integer id) {

		if (id == null) {
			throw new IllegalArgumentException("Input a correct iD");
		}

		// check if the shift exists
		Shift shift = shiftRepository.findShiftById(id);

		if (shift == null)
			throw new IllegalArgumentException("No shift with the given id has been found");

		// delete the shift
        shiftRepository.delete(shift);
        return true;
	}    


    /**
    * Validate parameter inputs and throw exception if not correct.
    * Helper method
    * 
    * @author Ming Xuan Yue
    * @param date
    * @param startTime
    * @param endTime
    * @param staffUsername
    * @return void
    */

    public void validateInputs(Date date, Time startTime, Time endTime, String staffUsername){
            
        if (date == null) {
            throw new IllegalArgumentException("Please enter a valid date");
        }

        if (startTime == null) {
            throw new IllegalArgumentException("Please enter a valid start time");
        }

        if (endTime == null) {
            throw new IllegalArgumentException("Please enter a valid end time");
        }

        if (endTime != null && startTime != null && endTime.before(startTime)) {
            throw new IllegalArgumentException("Shift end time cannot be before start time");
        }

        if(staffUsername == null || staffUsername.trim().length() == 0){
            throw new IllegalArgumentException("Please enter a valid staff");
        }

        if (staffRepository.findStaffByUsername(staffUsername) == null){
            throw new IllegalArgumentException("The staff doesn't exist");
        }
    }



    private <T> List<T> toList(Iterable<T> iterable) {
	    List<T> resultList = new ArrayList<T>();
	    for (T t : iterable) {
		resultList.add(t);
	    }
	    return resultList;
    }
}
