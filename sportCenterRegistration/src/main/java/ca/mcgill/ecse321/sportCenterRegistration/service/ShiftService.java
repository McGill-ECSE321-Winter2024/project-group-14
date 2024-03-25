package ca.mcgill.ecse321.sportCenterRegistration.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportCenterRegistration.dao.ShiftRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.StaffRepository;
import ca.mcgill.ecse321.sportCenterRegistration.model.Shift;
import ca.mcgill.ecse321.sportCenterRegistration.model.Staff;

@Service
public class ShiftService {
    @Autowired
    ShiftRepository shiftRepository;

    @Autowired
    StaffRepository staffRepository;

    /**
     * Service method for the creation of a shift. Returns the created instance if
     * the inputs are correct.
     * 
     * @author Ming Xuan Yue
     * @param date
     * @param startTime
     * @param endTime
     * @param staffUsername
     * @return shift
     */
    @Transactional
    public Shift createShift(Time startTime, Time endTime, Date date, Staff staff) {

        // check if all the inputs are valid
        validateInputs(date, startTime, endTime, staff);

        Shift shift = new Shift(startTime, endTime, date, staff);
        shiftRepository.save(shift);
        return shift;
    }

    /**
     * Service method for the update of a shift. Returns the updated instance if the
     * inputs are correct.
     * 
     * @author Ming Xuan Yue
     * @param date
     * @param startTime
     * @param endTime
     * @param staff
     * @param id
     * @return shift
     */

    @Transactional
    public Shift updateShift(Date date, Time startTime, Time endTime, Staff staff, Integer id) {

        if (id == null) {
            throw new IllegalArgumentException("Input a correct iD");
        }
        // search for the shift with a specific id and check if the shift exists
        Shift shift = shiftRepository.findShiftById(id);

        if (shift == null) {
            throw new IllegalArgumentException("No shift with the given id has been found");
        }

        // check if all the inputs are vaild
        validateInputs(date, startTime, endTime, staff);

        // update shift
        shift.setDate(date);
        shift.setStartTime(startTime);
        shift.setEndTime(endTime);
        shift.setStaff(staff);
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
    public Shift getShiftById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Input a correct Id");
        }
        return shiftRepository.findShiftById(id);
    }

    /**
     * Service method to get a list of shifts by staff and date.
     * 
     * @author Ming Xuan Yue
     * @param staff
     * @param date
     * @return List<shift>
     */

    @Transactional
    public List<Shift> getShiftByStaffandDate(Staff staff, Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Please enter a valid date");
        }

        if (staff == null) {
            throw new IllegalArgumentException("Please enter a valid staff");
        }
        return shiftRepository.findShiftByStaffAndDate(staff, date);
    }

    /**
     * Service method to get an instance of shift by date.
     * 
     * @author Ming Xuan Yue
     * @param date
     * @return List<shift>
     */

    @Transactional
    public List<Shift> getShiftByDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Please enter a valid date");
        }

        return shiftRepository.findShiftByDate(date);
    }

    /**
     * Service method to get an instance of shift by staff.
     * 
     * @author Ming Xuan Yue
     * @param staff
     * @return List<shift>
     */

    @Transactional
    public List<Shift> getShiftByStaff(Staff staff) {
        if (staff == null) {
            throw new IllegalArgumentException("Please enter a valid staff");
        }

        return shiftRepository.findShiftByStaff(staff);
    }

    /**
     * Service method to get all instances of shift.
     * 
     * @author Ming Xuan Yue
     * @return List<shift>
     */

    @Transactional
    public List<Shift> getAllShift() {
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
     * @param staff
     * @return void
     */

    public void validateInputs(Date date, Time startTime, Time endTime, Staff staff) {

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

        if (staff == null) {
            throw new IllegalArgumentException("Please enter a valid staff");
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
