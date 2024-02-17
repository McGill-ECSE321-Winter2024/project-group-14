package ca.mcgill.ecse321.sportCenterRegistration.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportCenterRegistration.model.Staff;

public interface StaffRepository extends CrudRepository<Staff, Integer>{
//    public Staff findStaffById(int id);

}