package ca.mcgill.ecse321.sportCenterRegistration.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportCenterRegistration.model.SportClass;

public interface SportClassRepository extends CrudRepository<SportClass, Integer> {
    // public SportClass findSportClassById(int id);
    public List<SportClass> findAll();

    public SportClass findSportClassByName(String name);
}