package com.alexproject.loginandregistration.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexproject.loginandregistration.models.Book;
import com.alexproject.loginandregistration.repositories.BookRepository;



@Service
public class BookService {

	@Autowired
	BookRepository bookRepo;
	
	
	// Create
	public Book createNewBook(Book newBook)
	{
		return bookRepo.save(newBook);
	}
	
	
	// Read
	public List<Book> findAllBooks()
	{
		return bookRepo.findAll();
	}
	
	public Book findOneBook(Long id)
	{
		return bookRepo.findById(id).orElse(null);
	}
	
	
	// Update
	public Book updateBook(Book updatedBook)
	{
		return this.bookRepo.save(updatedBook);
	}
	
	// Delete
	public void deleteBook(Long id)
	{
		bookRepo.deleteById(id);
	}
}
