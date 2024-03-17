package ca.mcgill.ecse321.sportCenterRegistration.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;

public interface InstructorRepository extends CrudRepository<Instructor, Integer> {
    // public Instructor findInstructorById(int id);
    public Instructor findInstructorByUsername(String name);

    public Instructor findInstructorByEmail(String email);

    void deleteInstructorByEmail(String email);

    boolean existsByEmail(String email);
}