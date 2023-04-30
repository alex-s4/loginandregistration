package com.alexproject.loginandregistration.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexproject.loginandregistration.models.User;
import com.alexproject.loginandregistration.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	// Create
	public User createNewUser(User newUser)
	{
		return userRepo.save(newUser);
	}
	
	// Read (all)
	public List<User> getAllUser()
	{
		return userRepo.findAll();
	}
	
	// Read (one)
	public User getOneUser(Long id)
	{
		return userRepo.findById(id).orElse(null);
	}
	
	// Update
	public User updateUser(User updatedUser)
	{
		return userRepo.save(updatedUser);
	}
	
	// Delete	
	public void deleteUser(Long id)
	{
		userRepo.deleteById(id);
	}
	
	
	
	// Others
	
	public User validateIfExisting(String email)
	{
		return userRepo.findByEmail(email).orElse(null);
	}
	
	
	
}
