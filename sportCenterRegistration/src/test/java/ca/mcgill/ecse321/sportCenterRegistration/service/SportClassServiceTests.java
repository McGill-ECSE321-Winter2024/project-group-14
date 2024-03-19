package ca.mcgill.ecse321.sportCenterRegistration.service;

import org.junit.jupiter.api.extension.ExtendWith;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportCenterRegistration.dao.InstructorRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.OwnerRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.SportClassRepository;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.Owner;
import ca.mcgill.ecse321.sportCenterRegistration.model.SportClass;


@SpringBootTest
public class SportClassServiceTests{
    @Mock
    private InstructorRepository instructorRepo;
    @Mock
    private OwnerRepository ownerRepo;
    @Mock
    private SportClassRepository sportClassRepo;

    @InjectMocks
    private SportClassService sportClassService;
    @InjectMocks
    private InstructorService instructorService;
    @InjectMocks
    private OwnerService ownerService;

    private static final String TEST_NAME = "cardio";
    @BeforeEach
    public void setMockOutput() {
        lenient().when(sportClassRepo.findSportClassByName(anyString())).thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(TEST_NAME)){
                        SportClass sportClass = new SportClass(TEST_NAME);
                        return sportClass;
                    } else{
                        return null;
                    }
                }
        );
        lenient().when(sportClassRepo.save(any(SportClass.class))).thenAnswer(
                (InvocationOnMock invocation) -> invocation.getArgument(0)
        );
    }


    @Test
    public void testGetSportClass(){
        assertEquals(TEST_NAME, sportClassService.getSportClass(TEST_NAME).getName());
    }

    @Test
    public void testGetEmptySportClass(){
        String name = null;

        String error = null;
        SportClass result = null;
        try {
            result = sportClassService.getSportClass(name);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(error);
        assertNull(result);
        assertEquals("Sport Class name should not be empty!", error);
    }

    @Test
    public void testGetNonExistSportClass(){
        String name = "stretching";

        String error = null;
        SportClass result = null;
        try {
            result = sportClassService.getSportClass(name);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNotNull(error);
        assertNull(result);
        assertEquals("Sport Class doesn't exist!", error);
    }

    /*
        test create new sport class
     */
    @SuppressWarnings("null")
    @Test
    public void testCreateSportClass(){
        String name = "test";
        SportClass createdSportClass = null;

        try{
            createdSportClass = instructorService.createSportClass(name);
        } catch(IllegalArgumentException e){
            fail();
        }

        assertNotNull(createdSportClass);
        assertEquals(name, createdSportClass.getName());
        assertEquals(false, createdSportClass.getApproved());
    }

    /*
        test create repeat sport class
     */
    @Test
    public void testCreateSportClassRepeat(){
        String error = null;
        SportClass createdSportClass = null;
        try {
            createdSportClass = instructorService.createSportClass(TEST_NAME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNotNull(error);
        assertNull(createdSportClass);
        assertEquals("Sport Class already exists!", error);
    }


    @Test
    public void testCreateEmptySportClass(){
        String name = null;

        String error = null;
        SportClass createdSportClass = null;
        try {
            createdSportClass = instructorService.createSportClass(name);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNotNull(error);
        assertNull(createdSportClass);
        assertEquals("Sport Class name should not be empty!", error);
    }


    @Test
    public void testApproveSportClass(){
        // owner approves
        SportClass approvedSportClass = null;
        String error = null;
        try {
            approvedSportClass = ownerService.approveSportClass(TEST_NAME);
        } catch(IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNotNull(approvedSportClass);
        assertEquals(TEST_NAME, approvedSportClass.getName());
        assertEquals(true, approvedSportClass.getApproved());
    }

    @Test
    public void testApproveEmptySportClass(){
        // owner approves
        String name = null;
        SportClass approvedSportClass = null;
        String error = null;
        try {
            approvedSportClass = ownerService.approveSportClass(name);
        } catch(IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(approvedSportClass);
        assertNotNull(error);
        assertEquals("Sport Class name should not be empty!", error);
    }

    @Test
    public void testApproveNonExistSportClass(){
        // owner approves
        String name = "stretching";
        SportClass approvedSportClass = null;
        String error = null;
        try {
            approvedSportClass = ownerService.approveSportClass(name);
        } catch(IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(approvedSportClass);
        assertNotNull(error);
        assertEquals("Sport Class doesn't exist!", error);
    }

    @Test
    public void testDeleteSportClass(){
        try{
            ownerService.deleteSportClass(TEST_NAME);
        } catch (IllegalArgumentException e){
            fail(e.getMessage());
        }
    }
}