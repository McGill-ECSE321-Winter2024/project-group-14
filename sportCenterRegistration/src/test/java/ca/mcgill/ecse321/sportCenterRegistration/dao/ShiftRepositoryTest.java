package ca.mcgill.ecse321.sportCenterRegistration.dao;


import java.sql.Time;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportCenterRegistration.model.Shift;
import ca.mcgill.ecse321.sportCenterRegistration.model.Staff;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
@SpringBootTest
public class ShiftRepositoryTest{
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
    // Test create and read shifts for the same day
    public void testCreateAndReadShift1(){
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
        for (Shift shift: shifts){
            shift = repoShift.save(shift);
        }
        List<Shift> result = repoShift.findByStaffAndDate(instructor, date1);

        assertNotNull(result);
        assertEquals(2, result.size()); // test size day 1
    }

    @Test
    // test create and read shifts from different days
    public void testCreateAndReadShift2(){
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
        for (Shift shift: shifts){
            shift = repoShift.save(shift);
        }
        Date d1 = Date.valueOf("2024-02-16");
        Date d2 = Date.valueOf("2024-02-17");
        List<Shift> result1 = repoShift.findByStaffAndDate(instructor, d1);
        List<Shift> result2 = repoShift.findByStaffAndDate(instructor, d2);

        assertNotNull(result1);
        assertNotNull(result2);
        assertEquals(2, result1.size()); // test size day 1
        assertEquals(1, result2.size()); // test size day 2

    }

    @Test
    // test create and read shifts for different staffs
    public void testCreateAndReadShift3() {
        // Create Instructor 1
        String username1 = "instructor1";
        String email1 = "instructor1@gmail.com";
        String password1 = "instructor1";
        Instructor instructor1 = new Instructor(username1, email1, password1);
        instructor1 = repoStaff.save(instructor1);

        // Create Instructor 2
        String username2 = "instructor2";
        String email2 = "instructor2@gmail.com";
        String password2 = "instructor2";
        Instructor instructor2 = new Instructor(username2, email2, password2);
        instructor2 = repoStaff.save(instructor2);

        // Create shifts
        List<Shift> shifts = new ArrayList<>();

        // Shifts for Instructor 1
        Time startTime1 = Time.valueOf("09:00:00");
        Time endTime1 = Time.valueOf("10:00:00");
        Date date1 = Date.valueOf("2024-02-16");
        shifts.add(new Shift(startTime1, endTime1, date1, instructor1));

        Time startTime2 = Time.valueOf("09:00:00");
        Time endTime2 = Time.valueOf("10:00:00");
        Date date2 = Date.valueOf("2024-02-16");
        shifts.add(new Shift(startTime2, endTime2, date2, instructor2));


        // Save shifts in the database
        for (Shift shift : shifts) {
            repoShift.save(shift);
        }

        // Retrieve shifts from the database for Instructor 1 on different days
        Date d1 = Date.valueOf("2024-02-16");
        List<Shift> result1 = repoShift.findByStaffAndDate(instructor1, d1);

        // Retrieve shifts from the database for Instructor 2 on different days
        Date d2 = Date.valueOf("2024-02-16");
        List<Shift> result2 = repoShift.findByStaffAndDate(instructor2, d2);

        // Assertions
        assertNotNull(result1);
        assertNotNull(result2);
        assertEquals(1, result1.size());
        assertEquals(1, result2.size());
    }


}