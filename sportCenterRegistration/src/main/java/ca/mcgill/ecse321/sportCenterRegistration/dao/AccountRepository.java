package ca.mcgill.ecse321.sportCenterRegistration.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportCenterRegistration.model.Account;

public interface AccountRepository extends CrudRepository<Account, Integer>{
    // Searches for the account with its id
    public Account findAccountById(int id);

    // Searches for the account with the name
    public Account findAccountByUsername(String name);

}
