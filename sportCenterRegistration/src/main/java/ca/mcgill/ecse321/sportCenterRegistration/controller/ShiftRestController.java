package ca.mcgill.ecse321.sportCenterRegistration.controller;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportCenterRegistration.service.ShiftService;
import ca.mcgill.ecse321.sportCenterRegistration.dto.ShiftDto;
import ca.mcgill.ecse321.sportCenterRegistration.model.Shift;
import ca.mcgill.ecse321.sportCenterRegistration.model.Staff;

@CrossOrigin(origins = "*")
@RestController
public class ShiftRestController {
    @Autowired
    private ShiftService shiftService;

	/**
     * This method creates a shift for a staff given its name
     * @author Ming Xuan Yue
     * 
	 * @param startTime
	 * @param endTime
     * @param date
     * @param staff
     * @return ShiftDto
     */

    @PostMapping(value={"/shift/{staff}", "/shift/{staff}/"})
    public ShiftDto createShift(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime startTime,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime endTime,
    @RequestParam Date date,
    @PathVariable("staff") Staff staff) 
    throws IllegalArgumentException{
        Shift shift = shiftService.createShift(Time.valueOf(startTime), Time.valueOf(endTime), date, staff);
        return convertToDto(shift);
    }

    /**
     * This method gets the list of shifts given a date
     * @author Ming Xuan Yue
     * 
     * @param date
     * @return List<ShiftDto>
     */

    @GetMapping(value={"/shift/{date}", "/shift/{date}/"})
    public List<ShiftDto> getShiftByDay(@PathVariable("date") Date date) throws IllegalArgumentException{
        ArrayList <ShiftDto> shiftDtos = new ArrayList<>();
        for (var shift: shiftService.getShiftByDate(date)){
            shiftDtos.add(convertToDto(shift));
        }
        return shiftDtos;
    }

    /**
     * This method gets the list of shifts given a name of the staff
     * @author Ming Xuan Yue
     * 
     * @param staff
     * @return List<ShiftDto>
     */

    @GetMapping(value={"/shift/{staff}", "/shift/{staff}/"})
    public List<ShiftDto> getShiftByStaff(@PathVariable("staff") Staff staff) throws IllegalArgumentException{
        ArrayList <ShiftDto> shiftDtos = new ArrayList<>();
        for (var shift: shiftService.getShiftByStaff(staff)){
            shiftDtos.add(convertToDto(shift));
        }
        return shiftDtos;
    }

    /**
     * This method gets the list of shifts given a name of the staff and date
     * @author Ming Xuan Yue
     * 
     * @param staff
     * @param date
     * @return List<ShiftDto>
     */

    @GetMapping(value={"/shift/{staff}/{date}", "/shift/{staff}/{date}/"})
    public List<ShiftDto> getShiftByStaffAndDate(@PathVariable("staff") Staff staff, 
    @PathVariable("date") Date date) throws IllegalArgumentException{
        ArrayList <ShiftDto> shiftDtos = new ArrayList<>();
        for (var shift: shiftService.getShiftByStaffandDate(staff, date)){
            shiftDtos.add(convertToDto(shift));
        }
        return shiftDtos;
    }

    /**
     * This method gets the list of all shifts
     * @author Ming Xuan Yue
     * 
     * @return List<ShiftDto>
     */

    @GetMapping(value={"/shift", "/shift/"})
    public List<ShiftDto> getAllShifts() throws IllegalArgumentException{
        ArrayList <ShiftDto> shiftDtos = new ArrayList<>();
        for (var shift: shiftService.getAllShift()){
            shiftDtos.add(convertToDto(shift));
        }
        return shiftDtos;
    }

        /**
     * This method gets the shift by id
     * @author Ming Xuan Yue
     * 
     * @param id
     * @return ShiftDto
     */

     @GetMapping(value={"/shift/{id}", "/shift/{id}/"})
     public ShiftDto getShiftById(@PathVariable("id") Integer id) throws IllegalArgumentException{
         Shift shift= shiftService.getShiftById(id);
         return convertToDto(shift);
     }    

    /**
     * This method updates a shift 
     * @author Ming Xuan Yue
     * 
     * @param date
     * @param startTime
     * @param endTime
     * @param staff
     * @param id
     * @return ShiftDto
     */

    @PutMapping(value={"/shift", "/shift/"})
    public ShiftDto updateShiftById(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime startTime,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime endTime,
    @RequestParam Date date,
    @RequestParam Staff staff, @RequestParam Integer id) throws IllegalArgumentException{
        return convertToDto(shiftService.updateShift(date, Time.valueOf(startTime), Time.valueOf(endTime), staff, id));
    }

    /**
     * This method deletes a shift by id
     * @author Ming Xuan Yue
     *
     * @param id
     * @return boolean
     */

    @DeleteMapping(value={"/shift", "/shift/"})
    public boolean deleteShiftById(@RequestParam Integer id){
        return shiftService.deleteShiftById(id);
    }

    
	/**
     * Converts Shift object to ShiftDto
     * @author Ming Xuan Yue
     * @param shift
     * @return ShiftDto
     */

    private ShiftDto convertToDto (Shift s){
        if (s == null){
            throw new IllegalArgumentException("The shift does not exist");
        }
        ShiftDto shiftDto = new ShiftDto(s.getDate(), s.getStartTime(), s.getEndTime(), s.getId(), s.getStaff());
        return shiftDto;
    }
}
