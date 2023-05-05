package com.alexproject.loginandregistration.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexproject.loginandregistration.models.Book;
import com.alexproject.loginandregistration.models.User;
import com.alexproject.loginandregistration.repositories.BookRepository;
import com.alexproject.loginandregistration.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BookRepository bookRepo;
	
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
	
	
	public void borrowBook(User user, Book bookBorrowed)
	{
//		System.out.println(user + " " + bookBorrowed);
		
		
//		List<Book> booksUnborrowed = bookRepo.findAll();
		List<Book> booksBorrowed = user.getBooksBorrowed();
		
		booksBorrowed.add(bookBorrowed);
//		booksUnborrowed.remove(bookBorrowed);
		
		this.userRepo.save(user);
	}
	
	public void returnBook(User user, Book bookReturned)
	{
		List<Book> booksUnborrowed = bookRepo.findAll();
		List<Book> booksBorrowed = user.getBooksBorrowed();
		
		booksUnborrowed.add(bookReturned);
		booksBorrowed.remove(bookReturned);
		
		this.userRepo.save(user);
	}
	
	
	
	
	
}
