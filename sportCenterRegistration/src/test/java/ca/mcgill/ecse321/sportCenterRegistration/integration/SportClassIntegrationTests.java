package ca.mcgill.ecse321.sportCenterRegistration.integration;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.sportCenterRegistration.dto.SportClassDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SportClassIntegrationTests{
    // 1. Test create new sport class by Instructor
    // 2. Test get sport class by name
    // 3. Test approve new sport class by Owner (Update)
    // 4. Test get all sport classes
    // 5. Test delete sport class given name by Owner
    // 6. Test get all sport classes (new sport class should be deleted)


    @Autowired
    private TestRestTemplate client;

    private final String SPORT_CLASS_NAME = "cardio";



    @Test
    @Order(1)
    public void testCreateSportClass(){
        String url = "/sport-class/" + SPORT_CLASS_NAME;

        ResponseEntity<SportClassDTO> response = client.postForEntity(url, null, SportClassDTO.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        SportClassDTO sportClass = response.getBody();
        assertNotNull(sportClass);
        assertEquals("cardio", sportClass.getName());
    }

    @Test
    @Order(2)
    public void testGetSportClassByName(){
        String url = "/sport-class/" + SPORT_CLASS_NAME;
        ResponseEntity<SportClassDTO> response = client.getForEntity(url, SportClassDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        SportClassDTO sportClass = response.getBody();
        assertNotNull(sportClass);
        assertEquals("cardio", sportClass.getName());
    }

    @Test
    @Order(3)
    public void testApproveSportClass(){
        String url = "/sport-class/approve/" + SPORT_CLASS_NAME;


        client.put(url, null); // Approve the sport class (update)

        String url2 = "/sport-class/" + SPORT_CLASS_NAME;
        ResponseEntity<SportClassDTO> response = client.getForEntity(url2, SportClassDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        SportClassDTO sportClass = response.getBody();
        assertNotNull(sportClass);
        assertEquals("cardio", sportClass.getName());
        assertTrue(sportClass.getApproved());
    }

    @Test
    @Order(4)
    public void testGetAllSportClasses(){
        String url = "/sport-class";
        ResponseEntity<SportClassDTO[]> response = client.getForEntity(url, SportClassDTO[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        SportClassDTO[] sportClasses = response.getBody();
        assertNotNull(sportClasses);
        assertEquals(1, sportClasses.length);
        assertEquals("cardio", sportClasses[0].getName());
    }

    @Test
    @Order(5)
    public void testDeleteSportClass(){
        String url = "/sport-class/" + SPORT_CLASS_NAME;

        client.delete(url); // Delete the sport class

        String url2 = "/sport-class";
        ResponseEntity<SportClassDTO[]> response = client.getForEntity(url2, SportClassDTO[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        SportClassDTO[] sportClasses = response.getBody();
        assertNotNull(sportClasses);
        assertEquals(0, sportClasses.length);
    }

    @Test
    @Order(6)
    public void testGetAllSportClassesAfterDelete(){
        String url = "/sport-class";
        ResponseEntity<SportClassDTO[]> response = client.getForEntity(url, SportClassDTO[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        SportClassDTO[] sportClasses = response.getBody();
        assertNotNull(sportClasses);
        assertEquals(0, sportClasses.length);
    }
}