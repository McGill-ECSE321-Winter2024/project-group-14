package ca.mcgill.ecse321.sportCenterRegistration.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportCenterRegistration.model.SportClass;

@SpringBootTest
public class SportClassRespositoryTest{
    @Autowired
    private SportClassRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateAndReadSportClass(){
        // Create Customer
        String name = "cardio";
        SportClass sportClass = new SportClass(name);

        // Save in the database
        sportClass = repo.save(sportClass);
        int sportClassId = sportClass.getId();

        // Read from database
        SportClass result = repo.findSportClassByName(name);

        assertNotNull(result);
        assertEquals(sportClassId, result.getId());
        assertEquals(name, result.getName());

    }
}