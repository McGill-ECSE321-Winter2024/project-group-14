package ca.mcgill.ecse321.sportCenterRegistration.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportCenterRegistration.model.Session;
import ca.mcgill.ecse321.sportCenterRegistration.model.SportClass;

public interface SessionRepository extends CrudRepository<Session, Integer>{
    public Session findSessionById(int id);

    public List<Session> findSessionBySportClass(SportClass sportClass);


}