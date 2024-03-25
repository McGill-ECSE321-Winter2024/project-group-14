package ca.mcgill.ecse321.sportCenterRegistration.dao;

import java.sql.Time;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportCenterRegistration.model.Instructor;
import ca.mcgill.ecse321.sportCenterRegistration.model.Session;
import ca.mcgill.ecse321.sportCenterRegistration.model.SportClass;

public interface SessionRepository extends CrudRepository<Session, Integer> {
    public Session findSessionById(int id);

    public List<Session> findSessionBySportClass(SportClass sportClass);

    public Session findSessionByStartTimeAndInstructorAndSportClass(Time sessionStartTime,
            Instructor instructor, SportClass sportClass);

    // public List<Session> findAllByDate();
}
