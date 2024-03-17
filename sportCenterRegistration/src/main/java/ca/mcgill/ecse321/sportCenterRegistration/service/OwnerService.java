package ca.mcgill.ecse321.sportCenterRegistration.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportCenterRegistration.dao.*;
import ca.mcgill.ecse321.sportCenterRegistration.model.*;

@Service
public class OwnerService {

	@Autowired
	OwnerRepository OwnerRepository;

	/**
	 * Section: Owner servive
	 * Author: Stephen Huang
	 * This method creates the owner.
	 */
	@Transactional
	public Owner createOwner(String username, String email, String password) {

		if (OwnerRepository.findOwnerByEmail("admin@sportcenter.com") != null) {
			throw new IllegalArgumentException("Owner already exists");
		}
		if (username == null)
			throw new IllegalArgumentException("Name cannot be null");
		if (username.isBlank())
			throw new IllegalArgumentException("Name cannot be blank");
		if (!email.equals("admin@sportcenter.com"))
			throw new IllegalArgumentException("Invalid email, use system email \"admin@sportcenter.com\"");
		passwordIsValid(password);

		Owner owner = new Owner(username, email, password);
		return OwnerRepository.save(owner);
	}

	/**
	 * Section: Owner servive
	 * Author: Stephen Huang
	 * This method updates the password of the owner.
	 */
	@Transactional
	public Owner updateOwnerPassword(String newPassword) {
		Owner oldOwner = OwnerRepository.findOwnerByEmail("admin@sportcenter.com");

		if (passwordIsValid(newPassword)) {
			oldOwner.setPassword(newPassword);
		}

		oldOwner = OwnerRepository.save(oldOwner);

		return oldOwner;
	}

	/**
	 * Section: Owner servive
	 * Author: Stephen Huang
	 * This method updates the name of the owner.
	 */
	@Transactional
	public Owner updateOwnerName(String newName) {
		Owner oldOwner = OwnerRepository.findOwnerByEmail("admin@sportcenter.com");

		if (newName == null)
			throw new IllegalArgumentException("Name cannot be null");
		if (newName.isBlank())
			throw new IllegalArgumentException("Name cannot be blank");

		oldOwner.setUsername(newName);

		oldOwner = OwnerRepository.save(oldOwner);

		return oldOwner;
	}

	/**
	 * Section: Owner servive
	 * Author: Stephen Huang
	 * This method gets the owner
	 */
	@Transactional
	public Owner getOwner() {
		Owner owner = OwnerRepository.findOwnerByEmail("admin@sportcenter.com");
		return owner;
	}

	/**
	 * Section: Owner servive
	 * Author: Stephen Huang
	 * This method deletes the owner
	 */
	@Transactional
	public boolean deleteOwner() throws IllegalArgumentException {

		Owner owner = OwnerRepository.findOwnerByEmail("admin@sportcenter.com");

		if (owner == null) {
			throw new IllegalArgumentException("Owner not found.");
		}

		OwnerRepository.delete(owner);
		return true;
	}

	private boolean passwordIsValid(String password) {

		if (password == null)
			throw new IllegalArgumentException("Password cannot be null");
		if (password.isBlank())
			throw new IllegalArgumentException("Password cannot be blank");

		return true;
	}

}