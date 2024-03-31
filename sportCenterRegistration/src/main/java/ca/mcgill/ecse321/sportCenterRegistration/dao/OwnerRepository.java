package ca.mcgill.ecse321.sportCenterRegistration.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportCenterRegistration.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, Integer> {
    // public Owner findOwnerById(int id);
    public Owner findOwnerByUsername(String name);

    public Owner findOwnerByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}