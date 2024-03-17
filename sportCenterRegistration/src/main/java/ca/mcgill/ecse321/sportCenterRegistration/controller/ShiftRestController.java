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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportCenterRegistration.service.ShiftService;
import ca.mcgill.ecse321.sportCenterRegistration.dto.ShiftDto;
import ca.mcgill.ecse321.sportCenterRegistration.model.Shift;

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
     * @param staffName
     * @return ShiftDto
     */

    @PostMapping(value={"/shift/{staffName}", "/shift/{staffName}/"})
    public ShiftDto createShift(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime startTime,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime endTime,
    @RequestParam Date date,
    @PathVariable("staffName") String staffName) 
    throws IllegalArgumentException{
        Shift shift = shiftService.createShift(Time.valueOf(startTime), Time.valueOf(endTime), date, staffName);
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
     * @param staffName
     * @return List<ShiftDto>
     */

    @GetMapping(value={"/shift/{staffName}", "/shift/{staffName}/"})
    public List<ShiftDto> getShiftByDay(@PathVariable("staffName") String staffName) throws IllegalArgumentException{
        ArrayList <ShiftDto> shiftDtos = new ArrayList<>();
        for (var shift: shiftService.getShiftByStaff(staffName)){
            shiftDtos.add(convertToDto(shift));
        }
        return shiftDtos;
    }

    /**
     * This method gets the list of shifts given a name of the staff and date
     * @author Ming Xuan Yue
     * 
     * @param staffName
     * @param date
     * @return List<ShiftDto>
     */

    @GetMapping(value={"/shift/{staffName}/{date}", "/shift/{staffName}/{date}/"})
    public List<ShiftDto> getShiftByStaffAndDate(@PathVariable("staffName") String staffName, 
    @PathVariable("date") Date date) throws IllegalArgumentException{
        ArrayList <ShiftDto> shiftDtos = new ArrayList<>();
        for (var shift: shiftService.getShiftByStaffandDate(staffName, date)){
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
     * This method updates a shift 
     * @author Ming Xuan Yue
     * 
     * @param date
     * @param startTime
     * @param endTime
     * @param staffName
     * @param id
     * @return ShiftDto
     */

    @PutMapping(value={"/shift", "/shift/"})
    public ShiftDto updateShiftByDay(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime startTime,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime endTime,
    @RequestParam Date date,
    @RequestParam String staffName, @RequestParam Integer id) throws IllegalArgumentException{
        return convertToDto(shiftService.updateShift(date, Time.valueOf(startTime), Time.valueOf(endTime), staffName, id));
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
