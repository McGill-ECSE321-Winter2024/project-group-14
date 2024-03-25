package ca.mcgill.ecse321.sportCenterRegistration.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import static org.mockito.Mockito.verify;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.ArrayList;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;


import ca.mcgill.ecse321.sportCenterRegistration.dao.ShiftRepository;
import ca.mcgill.ecse321.sportCenterRegistration.dao.StaffRepository;
import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.Owner;
import ca.mcgill.ecse321.sportCenterRegistration.model.Shift;
import ca.mcgill.ecse321.sportCenterRegistration.model.Staff;
import ca.mcgill.ecse321.sportCenterRegistration.service.ShiftService;


@ExtendWith(MockitoExtension.class)
public class TestShiftService {
    @Mock
    private ShiftRepository shiftDao;

    @Mock
    private StaffRepository staffDao;

    @InjectMocks
    private ShiftService shiftService;

    private static final Time START_TIME_0 = Time.valueOf("07:00:00");
    private static final Time END_TIME_0 = Time.valueOf("20:00:00");
    
    private static final Staff STAFF_0 = new Instructor("Ming", "ming@gmail.com", "Ming123");
    private static final Staff STAFF_1 = new Owner("David", "david@gmail.com", "David123");
    

    private static final Date DATE_0 = Date.valueOf("2024-03-20");
    private static final Date DATE_1 = Date.valueOf("2024-03-05");
    private static final Date DATE_2 = Date.valueOf("2024-03-01");
    
    @BeforeEach
    public void setMockOutput() {
        Shift shift1 = new Shift(START_TIME_0, END_TIME_0, DATE_0, STAFF_0);
        shift1.setId(1);
        Shift shift2 = new Shift(START_TIME_0, END_TIME_0, DATE_1, STAFF_0);
        shift2.setId(2);
        Shift shift3 = new Shift(START_TIME_0, END_TIME_0, DATE_2, STAFF_1);
        shift3.setId(3);

        lenient().when(shiftDao.findShiftByStaffAndDate(any(Staff.class), any(Date.class))).thenAnswer((InvocationOnMock invocation) -> {
            
            if (invocation.getArgument(0).equals(STAFF_0) && invocation.getArgument(1).equals(DATE_0)) {
                List<Shift> shifts = new ArrayList<>();
                shifts.add(shift1);
                return shifts;
            } else if (invocation.getArgument(0).equals(STAFF_0) && invocation.getArgument(1).equals(DATE_1)) {
                List<Shift> shifts = new ArrayList<>();
                shifts.add(shift2);
                return shifts;
            } else if (invocation.getArgument(0).equals(STAFF_1) && invocation.getArgument(1).equals(DATE_2)) {
                List<Shift> shifts = new ArrayList<>();
                shifts.add(shift3);
                return shifts;
            } else {
                return null;
            }
        }
      );
        
        lenient().when(shiftDao.findShiftByDate(any(Date.class))).thenAnswer(
                (InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(DATE_0)) {
                        List<Shift> shifts = new ArrayList<>();
                        shifts.add(shift1);
                        return shifts;
                    } else if (invocation.getArgument(0).equals(DATE_1)) {
                        List<Shift> shifts = new ArrayList<>();
                        shifts.add(shift2);
                        return shifts;
                    } else if (invocation.getArgument(0).equals(DATE_2)) {
                        List<Shift> shifts = new ArrayList<>();
                        shifts.add(shift3);
                        return shifts;
                    } else {
                        return null;
                    }
                }
        );

        lenient().when(shiftDao.findShiftById(anyInt())).thenAnswer(
            (InvocationOnMock invocation) -> {
                if (invocation.getArgument(0).equals(1)) {
                    return shift1;
                } else if (invocation.getArgument(0).equals(2)) {
                    return shift2;
                } else if (invocation.getArgument(0).equals(3)) {
                    return shift3;
                } else {
                    return null;
                }
            }
        );

        lenient().when(shiftDao.findShiftByStaff(any(Staff.class))).thenAnswer(
            (InvocationOnMock invocation) -> {
                if (invocation.getArgument(0).equals(STAFF_0)) {
                    List<Shift> shifts = new ArrayList<>();
                    shifts.add(shift1);
                    shifts.add(shift2);
                    return shifts;
                } else if (invocation.getArgument(0).equals(STAFF_1)) {
                    List<Shift> shifts = new ArrayList<>();
                    shifts.add(shift3);
                    return shifts;
                } else {
                    return null;
                }
            }
        );


        lenient().when(shiftDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            List<Shift> shifts= new ArrayList<>();
            shifts.add(shift1);
            shifts.add(shift2);
            shifts.add(shift3);
            return shifts;
        });
        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(shiftDao.save(any(Shift.class))).thenAnswer(returnParameterAsAnswer);
    }
    
    @Test
    public void testCreateShift(){
        Time start = Time.valueOf("07:00:00");
        Time end = Time.valueOf("20:00:00");
        Date date = Date.valueOf("2024-03-20");
        Staff staff = new Instructor("Loridy", "loridy@gmail.com", "Loridy123");
        
        Shift shift = null;
        String error = null;

        try{
            shift = shiftService.createShift(start, end, date, staff);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNotNull(shift);
        assertNull(error);
        assertEquals(start, shift.getStartTime());
        assertEquals(end, shift.getEndTime());
        assertEquals(date, shift.getDate());
        assertEquals(staff, shift.getStaff());
    }

    @Test
    public void testCreateShiftWithNullStartTime() {
        String error = null;
        Shift shift = null;
        Staff staff = new Instructor("Ming", "ming@gmail.com", "Ming123");
        try {
            shift = shiftService.createShift(null, Time.valueOf("11:00:00"), Date.valueOf("2024-03-20"), staff);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(shift);
        assertEquals("Please enter a valid start time", error);
    }
    
    @Test
    public void testCreateShiftWithNullEndTime() {
        String error = null;
        Shift shift = null;
        Staff staff = new Instructor("Ming", "ming@gmail.com", "Ming123");
        try {
            shift = shiftService.createShift(Time.valueOf("07:00:00"), null, Date.valueOf("2024-03-20"), staff);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(shift);
        assertEquals("Please enter a valid end time", error);
    }

    @Test
    public void testCreateShiftWithNullDate() {
        String error = null;
        Shift shift = null;
        Staff staff = new Instructor("Ming", "ming@gmail.com", "Ming123");
        try {
            shift = shiftService.createShift(Time.valueOf("07:00:00"), Time.valueOf("20:00:00"), null, staff);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(shift);
        assertEquals("Please enter a valid date", error);
    }

    @Test
    public void testCreateShiftWithNullStaff() {
        String error = null;
        Shift shift = null;
        try {
            shift = shiftService.createShift(Time.valueOf("07:00:00"), Time.valueOf("20:00:00"), Date.valueOf("2024-03-20"), null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(shift);
        assertEquals("Please enter a valid staff", error);
    }

	@Test
	public void testCreateShiftInvalidTimePeriod() {
		String error = null;
		Staff staff = new Instructor("Ming", "ming@gmail.com", "Ming123");
		Shift shift = null;

		try {
			shift = shiftService.createShift(Time.valueOf("20:00:00"), Time.valueOf("8:00:00"), Date.valueOf("2024-03-20"), staff);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

        assertNull(shift);
        assertEquals("Shift end time cannot be before start time", error);
	}

    @Test
	public void testUpdateShift() { // Normal use case
		String error = null;
        Shift shift = null;

        Time start = Time.valueOf("09:00:00");
        Time end = Time.valueOf("12:00:00");
        Date date = Date.valueOf("2024-03-04");
        Staff staff = new Instructor("Test", "Test@email", "Test");
		try {
			shift = shiftService.updateShift(date, start, end, staff, 1);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check the inputs are correctly passed
		assertNotNull(shift);
		assertNull(error);
		assertEquals(start, shift.getStartTime());
		assertEquals(end, shift.getEndTime());
		assertEquals(date, shift.getDate());
        assertEquals(staff, shift.getStaff());
        assertEquals(1, shift.getId());
	}
    
    @Test
	public void testUpdateShiftNullId() { // Normal use case
		String error = null;
        Shift shift = null;

        Time start = Time.valueOf("09:00:00");
        Time end = Time.valueOf("12:00:00");
        Date date = Date.valueOf("2024-03-04");
        Staff staff = new Instructor("Test", "Test@email", "Test");
		try {
			shift = shiftService.updateShift(date, start, end, staff, null);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

        assertNull(shift);
		assertNotNull(error);
		assertEquals("Input a correct iD", error);
	}

    @Test
    public void testGetShiftById(){
        Time start = Time.valueOf("07:00:00");
        Time end = Time.valueOf("20:00:00");
        Date date = Date.valueOf("2024-03-20");
        Staff staff = new Instructor("Ming", "ming@gmail.com", "Ming123");

        Shift shift = null;
        try{
            shift = shiftService.getShiftById(1);
        } catch (IllegalArgumentException e){
            fail();
        }

        assertNotNull(shift);
        assertEquals(1, shift.getId());
        assertEquals(start, shift.getStartTime());
        assertEquals(end, shift.getEndTime());
        assertEquals(date, shift.getDate());
        assertEquals(STAFF_0, shift.getStaff());
    }

    @Test
    public void testGetShiftNullId(){

        Shift shift = null;
        String error = null;
        try{
            shift = shiftService.getShiftById(null);
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(shift);
        assertNotNull(error);
        assertEquals("Input a correct Id", error);
    }

    @Test
    public void testGetShiftByStaff(){

        List <Shift> shifts = null;
        try{
            shifts = shiftService.getShiftByStaff(STAFF_0);
        } catch (IllegalArgumentException e){
            fail();
        }

        assertNotNull(shifts);
        assertEquals(1, shifts.get(0).getId());
        assertEquals(2, shifts.get(1).getId());
        assertEquals(2, shifts.size());
        assertEquals(STAFF_0, shifts.get(0).getStaff());

    }

    @Test
    public void testGetShiftByDate(){

        List <Shift> shifts = null;
        try{
            shifts = shiftService.getShiftByDate(Date.valueOf("2024-03-01"));
        } catch (IllegalArgumentException e){
            fail();
        }

        assertNotNull(shifts);
        assertEquals(1, shifts.size());
        assertEquals(STAFF_1.getUsername(), shifts.get(0).getStaff().getUsername());
        assertEquals(3, shifts.get(0).getId());
        assertEquals(STAFF_1, shifts.get(0).getStaff());
    }

    @Test
    public void testGetShiftByStaffAndDate(){
        Time start = Time.valueOf("07:00:00");
        Time end = Time.valueOf("20:00:00");
        Date date = Date.valueOf("2024-03-01");


        List <Shift> shifts = null;
        try{
            shifts = shiftService.getShiftByStaffandDate(STAFF_1, Date.valueOf("2024-03-01"));
        } catch (IllegalArgumentException e){
            fail();
        }

        assertNotNull(shifts);
        assertEquals(1, shifts.size());
        assertEquals(start, shifts.get(0).getStartTime());
        assertEquals(end, shifts.get(0).getEndTime());
        assertEquals(date, shifts.get(0).getDate());
        assertEquals(STAFF_1.getUsername(), shifts.get(0).getStaff().getUsername());
    }


    @Test
    public void testGetAllShifts(){
        List<Shift> shifts = null;
        try {
            shifts = shiftService.getAllShift();
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertEquals(1, shifts.get(0).getId());
        assertEquals(2, shifts.get(1).getId());
        assertEquals(3, shifts.get(2).getId());
        assertEquals(3, shifts.size());
    }

    @Test
    public void testDeleteShift(){
        try{
            shiftService.deleteShiftById(1);
        } catch (IllegalArgumentException e){
            fail();
        }
        verify(shiftDao).delete(any());
    }

    @Test
    public void testDeleteNonExistShift(){
        String error = null;
        try{
            shiftService.deleteShiftById(5); // id 4 is not exist
        } catch (IllegalArgumentException e){
            error = e.getMessage();
        }
        assertEquals("No shift with the given id has been found", error);

    }
}
