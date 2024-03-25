package ca.mcgill.ecse321.sportCenterRegistration.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.Shift;
import ca.mcgill.ecse321.sportCenterRegistration.model.Staff;

@SpringBootTest
public class ShiftRepositoryTest {
    @Autowired
    private ShiftRepository repoShift;
    @Autowired
    private StaffRepository repoStaff;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repoShift.deleteAll();
        repoStaff.deleteAll();
    }

    @Test
    // test create and read shift
    public void testCreateAndReadShift1() {
        // Create Instructor
        String username = "instructor";
        String email = "instructor@gmail.com";
        String password = "instructor";
        Instructor instructor = new Instructor(username, email, password);

        // Create Shift
        Time startTime = Time.valueOf("09:00:00");
        Time endTime = Time.valueOf("10:00:00");
        Date date = Date.valueOf("2024-02-16");
        Shift shift = new Shift(startTime, endTime, date, instructor);

        // Save shift in the database
        instructor = repoStaff.save(instructor);
        shift = repoShift.save(shift);

        // Retrieve shift from database
        Shift result = repoShift.findShiftById(shift.getId());

        // check objects not null
        assertNotNull(result);

        // check attributes
        assertEquals(startTime, result.getStartTime());
        assertEquals(endTime, result.getEndTime());
        assertEquals(date, result.getDate());
        assertEquals(shift.getStaff().getId(), result.getStaff().getId());

        // check references
        Staff resultStaff = repoStaff.findStaffByUsername(username);
        assertNotNull(resultStaff);
        assertEquals(resultStaff.getId(), result.getStaff().getId());
    }

    // Extra tests
    @Test
    // Test create and read shift for the same day
    public void testCreateAndReadShift2() {
        // Create Instructor
        String username = "instructor1";
        String email = "instructor1@gmail.com";
        String password = "instructor1";
        Instructor instructor = new Instructor(username, email, password);

        // Save in the database
        instructor = repoStaff.save(instructor);
        int instructorId = instructor.getId();

        // Create shifts
        List<Shift> shifts = new ArrayList<>();

        Time startTime1 = Time.valueOf("09:00:00");
        Time endTime1 = Time.valueOf("10:00:00");
        Date date1 = Date.valueOf("2024-02-16");
        shifts.add(new Shift(startTime1, endTime1, date1, instructor));

        Time startTime2 = Time.valueOf("10:00:00");
        Time endTime2 = Time.valueOf("11:00:00");
        Date date2 = Date.valueOf("2024-02-16");
        shifts.add(new Shift(startTime2, endTime2, date2, instructor));

        // Save in the database
        for (Shift shift : shifts) {
            shift = repoShift.save(shift);
        }
        List<Shift> result = repoShift.findShiftByStaffAndDate(instructor, date1);

        assertNotNull(result);
        assertEquals(2, result.size()); // test size day 1
    }

    @Test
    // test create and read shifts from different days
    public void testCreateAndReadShift3() {
        // Create Instructor
        String username = "instructor1";
        String email = "instructor1@gmail.com";
        String password = "instructor1";
        Instructor instructor = new Instructor(username, email, password);

        // Save in the database
        instructor = repoStaff.save(instructor);
        int instructorId = instructor.getId();

        // Create shifts
        List<Shift> shifts = new ArrayList<>();

        Time startTime1 = Time.valueOf("09:00:00");
        Time endTime1 = Time.valueOf("10:00:00");
        Date date1 = Date.valueOf("2024-02-16");
        shifts.add(new Shift(startTime1, endTime1, date1, instructor));

        Time startTime2 = Time.valueOf("10:00:00");
        Time endTime2 = Time.valueOf("11:00:00");
        Date date2 = Date.valueOf("2024-02-16");
        shifts.add(new Shift(startTime2, endTime2, date2, instructor));

        Time startTime3 = Time.valueOf("10:00:00");
        Time endTime3 = Time.valueOf("11:00:00");
        Date date3 = Date.valueOf("2024-02-17"); // Different date
        shifts.add(new Shift(startTime3, endTime3, date3, instructor));

        // Save in the database
        for (Shift shift : shifts) {
            shift = repoShift.save(shift);
        }
        Date d1 = Date.valueOf("2024-02-16");
        Date d2 = Date.valueOf("2024-02-17");
        List<Shift> result1 = repoShift.findShiftByStaffAndDate(instructor, d1);
        List<Shift> result2 = repoShift.findShiftByStaffAndDate(instructor, d2);

        assertNotNull(result1);
        assertNotNull(result2);
        assertEquals(2, result1.size()); // test size day 1
        assertEquals(1, result2.size()); // test size day 2

    }
}