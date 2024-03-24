package ca.mcgill.ecse321.sportCenterRegistration.dao;

import java.util.List;
import java.sql.Date;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportCenterRegistration.model.Shift;
import ca.mcgill.ecse321.sportCenterRegistration.model.Staff;


public interface ShiftRepository extends CrudRepository<Shift, Integer>{
    public Shift findShiftById(int id);
    public List<Shift> findShiftByStaffAndDate(Staff staff, Date date);
    public List<Shift> findShiftByDate(Date date);
    public List<Shift> findShiftByStaff(Staff staff);
}
