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
import ca.mcgill.ecse321.sportCenterRegistration.model.Shift;
import ca.mcgill.ecse321.sportCenterRegistration.model.Staff;

@SpringBootTest
public class SessionRepositoryTest{
    @Autowired
    private SessionRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
}
    @Test
    public void testCreateAndReadSession(){
    
        //create Session
        List<Session> sessions = new ArrayList<>();

        int id = 442233;
        Time startTime = Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("11:00:00");
        String location = "trottbuilding";
        Date date = Date.valueOf("2024-02-13");
       
        Session trottSession = new Session(id,startTime,endTime,location,date); //error here

        // Save Session.
		trottSession = repo.save(trottSession);

        // Read Session from database.
		Session = trottSession.findSessionByin(id); //error here 

        // Assert event is not null and has correct attributes.
		assertNotNull(sessions);
		
}


        


       





}
}
