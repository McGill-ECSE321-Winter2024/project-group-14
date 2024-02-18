package ca.mcgill.ecse321.sportCenterRegistration.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportCenterRegistration.model.Session;
import ca.mcgill.ecse321.sportCenterRegistration.model.SportClass;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;

@SpringBootTest
public class SessionRepositoryTest{
    @Autowired
    private SessionRepository repo;
    @Autowired
    private InstructorRepository instructorRepo;
    @Autowired
    private SportClassRepository sportClassRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
        sportClassRepo.deleteAll();
        instructorRepo.deleteAll();
    }
    @Test
    public void testCreateAndReadSession(){
        // before creating session, create a instructor and a sport class
        String name = "cardio";
        SportClass sportClass = new SportClass(name);

        String username = "instructor";
        String email = "instructor@gmail.com";
        String password = "instructor";
        Instructor instructor = new Instructor(username, email, password);

        //create Session
        List<Session> sessions = new ArrayList<>();

        Time startTime = Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("11:00:00");
        String location = "trottbuilding";
        Date date = Date.valueOf("2024-02-13");

        // Session(Time aStartTime, Time aEndTime, String aLocation, Date aDate, Instructor aInstructor, SportClass aSportClass)
        Session trottSession = new Session(startTime,endTime,location,date, instructor, sportClass); //error here

        // Save instructor
        instructor = instructorRepo.save(instructor);

        // Save Sport Class
        sportClass = sportClassRepo.save(sportClass);

        // Save Session.
        trottSession = repo.save(trottSession);

        // Read Session from database.
        Session result = repo.findSessionById(trottSession.getId());

        // check objects
        assertNotNull(result);

        // check attributes
        assertEquals(trottSession.getId(), result.getId());
        assertEquals(trottSession.getStartTime(), result.getStartTime());
        assertEquals(trottSession.getEndTime(), result.getEndTime());
        assertEquals(trottSession.getLocation(), result.getLocation());
        assertEquals(trottSession.getDate(), result.getDate());
        assertEquals(trottSession.getSportClass().getId(), result.getSportClass().getId());
        assertEquals(trottSession.getInstructor().getId(), result.getInstructor().getId());

        // check references
        Instructor resultInstructor = instructorRepo.findInstructorByUsername(username);
        SportClass resultSportClass = sportClassRepo.findSportClassByName(name);
        assertNotNull(resultInstructor);
        assertNotNull(resultSportClass);
        assertEquals(resultSportClass.getId(), result.getSportClass().getId());
        assertEquals(resultInstructor.getId(), result.getInstructor().getId());

    }

}