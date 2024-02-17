package ca.mcgill.ecse321.sportCenterRegistration.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;

public interface InstructorRepository extends CrudRepository<Instructor, Integer>{
    public Instructor findInstructorById(int id);

}