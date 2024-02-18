package ca.mcgill.ecse321.sportCenterRegistration.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;

@SpringBootTest
public class InstructorRepositoryTest{
    @Autowired
    private InstructorRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateAndReadInstructor(){
        // Create Instructor
        String username = "instructor1";
        String email = "instructor1@gmail.com";
        String password = "instructor1";
        Instructor instructor = new Instructor(username, email, password);

        // Save in the database
        instructor = repo.save(instructor);
        int instructorId = instructor.getId();

        // Read from database
//        Instructor result = repo.findInstructorById(instructorId);
        Instructor result = repo.findInstructorByUsername(username);

        // check objects
        assertNotNull(result);

        // check attributes
        assertEquals(instructorId, result.getId());
        assertEquals(username, result.getUsername());
        assertEquals(email, result.getEmail());
        assertEquals(password, result.getPassword());

    }
}