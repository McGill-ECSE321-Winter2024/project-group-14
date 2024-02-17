package ca.mcgill.ecse321.sportCenterRegistration.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportCenterRegistration.model.Session;

public interface SessionRepository extends CrudRepository<Session, Integer>{
//    public Session findSessionById(int id);

}