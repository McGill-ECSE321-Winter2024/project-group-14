package ca.mcgill.ecse321.sportCenterRegistration.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportCenterRegistration.model.Registration;

public interface RegistrationRepository extends CrudRepository<Registration, Integer>{
//    public Registration findRegistrationById(int id);

}