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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

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

import ca.mcgill.ecse321.sportCenterRegistration.dao.SessionRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.InstructorRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.SportClassRepository;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.SportClass;
import ca.mcgill.ecse321.sportCenterRegistration.model.Session;


@SpringBootTest
public class SessionTests{
    @Mock
    private SessionRepository sessionRepo;
    @Mock
    private InstructorRepository instructorRepo;
    @Mock
    private SportClassRepository sportClassRepo;

    @InjectMocks
    private SessionService sessionService;

    private static final Time START_TIME_0 = Time.valueOf("08:00:00");
    private static final Time END_TIME_0 = Time.valueOf("11:00:00");
    private static final String LOCATION_0 = "trottbuilding";
    private static final Date DATE_0 = Date.valueOf("2024-03-03");
    private static final Instructor INSTRUCTOR_0 = new Instructor("Loridy", "loridy@gmail.com", "loridy123");
    private static final SportClass SPORT_CLASS_0 = new SportClass("cardio");

    private static final Date DATE_1 = Date.valueOf("2024-03-02");
    private static final SportClass SPORT_CLASS_1 = new SportClass("cardio");

    private static final Date DATE_2 = Date.valueOf("2024-03-01");
    private static final SportClass SPORT_CLASS_2 = new SportClass("stretching");

    @BeforeEach
    public void setMockOutput() {
        Session session1 = new Session(START_TIME_0, END_TIME_0, LOCATION_0, DATE_0, INSTRUCTOR_0, SPORT_CLASS_0);
        session1.setId(1);
        Session session2 = new Session(START_TIME_0, END_TIME_0, LOCATION_0, DATE_1, INSTRUCTOR_0, SPORT_CLASS_1);
        session2.setId(2);
        Session session3 = new Session(START_TIME_0, END_TIME_0, LOCATION_0, DATE_2, INSTRUCTOR_0, SPORT_CLASS_2);
        session3.setId(3);

        lenient().when(sessionRepo.findSessionBySportClass(any(SportClass.class))).thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals("cardio")){
                        List<Session> sessions = new ArrayList<>();
                        sessions.add(session1);
                        sessions.add(session2);
                        return sessions;
                    } else {
                        return null;
                    }
                }
        );

        lenient().when(sessionRepo.findSessionById(anyInt())).thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(1)) {
                        return session1;
                    } else if (invocation.getArgument(0).equals(2)) {
                        return session2;
                    } else if (invocation.getArgument(0).equals(3)) {
                        return session3;
                    } else {
                        return null;
                    }
                }
        );

        lenient().when(sessionRepo.findAll()).thenAnswer(
                (InvocationOnMock invocation) -> {
                    List<Session> sessions = new ArrayList<>();
                    sessions.add(session1);
                    sessions.add(session2);
                    sessions.add(session3);
                    return sessions;
                }
        );

        
        lenient().when(sessionRepo.save(any(Session.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
    }

    @Test
    public void TestCreateSession(){
        Time start = Time.valueOf("08:00:00");
        Time end = Time.valueOf("11:00:00");
        String location = "trottbuilding";
        Date date = Date.valueOf("2024-03-03");
        Instructor instructor = new Instructor("Loridy", "loridy@gmail.com", "loridy123");
        SportClass sportClass = new SportClass("cardio");
        Session session = null;
        String error = null;
        try{
            session = sessionService.createSession(start, end, location, date, instructor, sportClass);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNotNull(session);
        assertEquals(start, session.getStartTime());
        assertEquals(end, session.getEndTime());
        assertEquals(location, session.getLocation());
        assertEquals(date, session.getDate());
        assertEquals(instructor, session.getInstructor());
    }

    @Test
    public void testCreateSessionWithNullStartTime() {
        String error = null;
        try {
            sessionService.createSession(null, Time.valueOf("11:00:00"), "trottbuilding", Date.valueOf("2024-03-03"), new Instructor("Loridy", "loridy@gmail.com", "loridy123"), new SportClass("cardio"));
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Session start time cannot be empty!",error);
    }

    @Test
    public void testCreateSessionWithNullEndTime() {
        String error = null;
        try {
            sessionService.createSession(Time.valueOf("08:00:00"), null, "trottbuilding", Date.valueOf("2024-03-03"), new Instructor("Loridy", "loridy@gmail.com", "loridy123"), new SportClass("cardio"));
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Session end time cannot be empty!",error);
    }
    @Test
    public void testCreateSessionWithNullLocation() {
        String error = null;
        try {
            sessionService.createSession(Time.valueOf("08:00:00"), Time.valueOf("11:00:00"), null, Date.valueOf("2024-03-03"), new Instructor("Loridy", "loridy@gmail.com", "loridy123"), new SportClass("cardio"));
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Session location cannot be empty!",error);
    }

    @Test
    public void testCreateSessionWithNullDate() {
        String error = null;
        try {
            sessionService.createSession(Time.valueOf("08:00:00"), Time.valueOf("11:00:00"), "trottbuilding", null, new Instructor("Loridy", "loridy@gmail.com", "loridy123"), new SportClass("cardio"));
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Session date cannot be empty!",error);
    }

    @Test
    public void testCreateSessionWithNullInstructor() {
        String error = null;
        try {
            sessionService.createSession(Time.valueOf("08:00:00"), Time.valueOf("11:00:00"), "trottbuilding", Date.valueOf("2024-03-03"), null, new SportClass("cardio"));
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Session instructor cannot be empty!",error);
    }

    @Test
    public void testCreateSessionWithNullSportClass() {
        String error = null;
        try {
            sessionService.createSession(Time.valueOf("08:00:00"), Time.valueOf("11:00:00"), "trottbuilding", Date.valueOf("2024-03-03"), new Instructor("Loridy", "loridy@gmail.com", "loridy123"), null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Session sport class cannot be empty!",error);
    }

    @Test
    public void testUpdateSession(){
        Time start = Time.valueOf("09:00:00");
        Time end = Time.valueOf("12:00:00");
        String location = "testing";
        Date date = Date.valueOf("2024-03-04");
        Instructor instructor = new Instructor("Test", "Test@email", "Test");
        SportClass sportClass = new SportClass("stretching");

        Session session = null;
        String error = null;

        try{
            session = sessionService.updateSession(1, start, end, location, date, instructor, sportClass);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNotNull(session);
    }

    @Test
    public void testGetSessionById(){
        Time start = Time.valueOf("08:00:00");
        Time end = Time.valueOf("11:00:00");
        String location = "trottbuilding";
        Date date = Date.valueOf("2024-03-03");
        Instructor instructor = new Instructor("Loridy", "loridy@gmail.com", "loridy123");
        SportClass sportClass = new SportClass("cardio");

        Session session = null;
        try{
            session = sessionService.getSession(1);
        } catch (IllegalArgumentException e){
            fail();
        }

        assertNotNull(session);
        assertEquals(1, session.getId());
        assertEquals(start, session.getStartTime());
        assertEquals(end, session.getEndTime());
        assertEquals(location, session.getLocation());
        assertEquals(date, session.getDate());
    }


    @Test
    public void testGetSessionBySportClass(){
        List<Session> sessions = null;
        try {
            sessions = sessionService.getSessionBySportClass("cardio");
        } catch (IllegalArgumentException e) { 
            fail();
        }

        assertEquals(2, sessions.size());
    }

    @Test
    public void testGetSessionBySportClassFail(){
        List<Session> sessions = null;
        String error = null;
        try {
            sessions = sessionService.getSessionBySportClass("stretching");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(sessions);
        assertEquals(error, "Sport Class does not exist!");
    }

    @Test
    public void testDeleteSession(){
        try{
            sessionService.deleteSession(1);
        } catch (IllegalArgumentException e){
            fail();
        }
        verify(sessionRepo).delete(any());
    }

    @Test
    public void testDeleteNonExistSession(){
        String error = null;
        try{
            sessionService.deleteSession(4); // id 4 is not exist
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertEquals("Session not found", error);

    }

    @Test
    public void testGetAllSession(){
        List<Session> sessions = null;
        try {
            sessions = sessionService.getAllSession();
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertEquals(3, sessions.size());
    }
}
