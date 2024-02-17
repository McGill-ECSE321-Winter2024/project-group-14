package ca.mcgill.ecse321.sportCenterRegistration.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportCenterRegistration.model.Account;

public interface AccountRepository extends CrudRepository<Account, Integer>{
//    public Account findAccountById(int id);

}