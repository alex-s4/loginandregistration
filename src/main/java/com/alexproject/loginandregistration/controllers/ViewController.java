package com.alexproject.loginandregistration.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alexproject.loginandregistration.models.Book;
import com.alexproject.loginandregistration.models.LoginUser;
import com.alexproject.loginandregistration.models.User;
import com.alexproject.loginandregistration.services.BookService;
import com.alexproject.loginandregistration.services.UserService;

@Controller
public class ViewController {
	
	@Autowired
	UserService userServ;
	
	@Autowired
	BookService bookServ;
	
	@GetMapping("/")

	public String index(Model mv)
	{
		mv.addAttribute("newUser", new User());
		mv.addAttribute("newLogin", new LoginUser());
		return "index.jsp";
	}


	@PostMapping("/register")
	public String createNewUser(@Valid @ModelAttribute("newUser") User newUser, 
			BindingResult result, Model mv, HttpSession session, RedirectAttributes redirect)
	{
		
		 
		if(result.hasErrors())
		{
			// Model layer validations
//			mv.addAttribute("newUser", new User());
			mv.addAttribute("newLogin", new LoginUser());
			System.out.println("Register model layer validation error");
			return "index.jsp";
		}
		else
		{
			// Return error if password did not match
			if(!newUser.getPassword().equals(newUser.getConfirm()))
			{
//				mv.addAttribute("newUser", new User());
				mv.addAttribute("newLogin", new LoginUser());
				mv.addAttribute("passwordMatchError", "Password Did Not Match");
	    		return "index.jsp";
			}
			// Reject if email exists (present in database)
			else if(userServ.validateIfExisting(newUser.getEmail())!=null)
			{
//				mv.addAttribute("newUser", new User());
				mv.addAttribute("newLogin", new LoginUser());
				mv.addAttribute("emailExistsError", "Email already exists");
	    		return "index.jsp";
			}
			else
			{
				// Validation
				redirect.addFlashAttribute("successRegistrationMessage", "Account created succesfully!");
				
				// Create a hash of the user's password to store in the database
				String hashedPassword = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
				newUser.setPassword(hashedPassword);
				
				// Save new user in the database
				userServ.createNewUser(newUser);
				return "redirect:/";
			}
			
		}
	}
	
	@PostMapping("/login")
	public String loginUser(@Valid @ModelAttribute("newLogin") LoginUser newLogin, 
			BindingResult result, Model mv, HttpSession session)
	{
		if(result.hasErrors())
		{
			// Model layer validations
			mv.addAttribute("newUser", new User());
//			mv.addAttribute("newLogin", new LoginUser());
			System.out.println("Login model layer validation error");
			return "index.jsp";
		}
		else
		{
			// Find user in the DB by email, reject if NOT present
			if(userServ.validateIfExisting(newLogin.getEmail())==null)
			{
				mv.addAttribute("newUser", new User());
//				mv.addAttribute("newLogin", new LoginUser());
				mv.addAttribute("loginError", "Email or Password is invalid");
				System.out.println("no such email");
				return "index.jsp";
			} 
			// Reject if BCrypt password match fails
			else if (!BCrypt.checkpw(newLogin.getPassword(), userServ.validateIfExisting(newLogin.getEmail()).getPassword()))
			{
				mv.addAttribute("newUser", new User());
//				mv.addAttribute("newLogin", new LoginUser());
				mv.addAttribute("loginError", "Email or Password is invalid");
				System.out.println("email does not match password");
				return "index.jsp";
			}
			else
			{
				System.out.println("succesful login!");
				System.out.println(userServ.validateIfExisting(newLogin.getEmail()).getId());
				session.setAttribute("loggedUser", userServ.validateIfExisting(newLogin.getEmail()).getId());
				return "redirect:/books";
			}
		}
		
	}

	@GetMapping("/books")
	public String homePage(Model mv, HttpSession session)
	{
		User activeUser = userServ.getOneUser((Long) session.getAttribute("loggedUser"));
		List<Book> allBooks = bookServ.findAllBooks();
		List<Book> booksBorrowed = activeUser.getBooksBorrowed();
				
		mv.addAttribute("loggedUser", activeUser);
		mv.addAttribute("booksBorrowed", booksBorrowed);
		mv.addAttribute("books", allBooks);
		
		
		
		return "dashboard.jsp";
	}
	
	@GetMapping("/bookmarket")
	public String lendingPage(Model mv, HttpSession session)
	{
		User activeUser = userServ.getOneUser((Long) session.getAttribute("loggedUser"));
		List<Book> allBooks = bookServ.findAllBooks();
		List<Book> booksBorrowed = activeUser.getBooksBorrowed();
				
		mv.addAttribute("loggedUser", activeUser);
		mv.addAttribute("booksBorrowed", booksBorrowed);
		mv.addAttribute("books", allBooks);
		
		
		return "lendingDashboard.jsp";
	}
	
	@GetMapping("/books/new")
	public String newBookPage(Model mv, HttpSession session)
	{
		mv.addAttribute("loggedUser", userServ.getOneUser((Long) session.getAttribute("loggedUser")));
		mv.addAttribute("book", new Book());
		return "addBook.jsp";
	}
	
	@PostMapping("/createBook")
	public String createBook(@Valid @ModelAttribute("book") Book newBook,
			BindingResult result,
			Model mv,
			HttpSession session) 
	{
		if(result.hasErrors())
		{
			mv.addAttribute("loggedUser", userServ.getOneUser((Long) session.getAttribute("loggedUser")));
			//System.out.println("some error");
			return "addBook.jsp";
		}
		else
		{
			mv.addAttribute("book", new Book());
			bookServ.createNewBook(newBook);
			return "redirect:/books/new";
		}
	}
	
	@GetMapping("/books/{id}")
	public String viewBookDetails(@PathVariable("id") Long bookId, Model mv, HttpSession session)
	{
		Book book = bookServ.findOneBook(bookId);
		
		mv.addAttribute("loggedUser", userServ.getOneUser((Long) session.getAttribute("loggedUser")));
		mv.addAttribute("book", book);
		return "bookDetails.jsp";
	}
	
	@GetMapping("/books/{id}/edit")
	public String editBookDetails(@PathVariable("id") Long bookId, Model mv, HttpSession session)
	{
		Book updatedBook = bookServ.findOneBook(bookId);
		User activeUser = userServ.getOneUser((Long) session.getAttribute("loggedUser"));
		
		mv.addAttribute("loggedUser", activeUser);
		mv.addAttribute("book", updatedBook);
		
		for(User borrower : updatedBook.getBorrowers()) {
			System.out.println(borrower.getUserName());
		}
		
		
		return "editBook.jsp";
	}
	
	@PutMapping("/books/{id}/edit")
	public String editBook(
			@Valid @ModelAttribute("book") Book updatedBook,
			BindingResult result,
			Model mv,
			HttpSession session
			)
	{
		
		if(result.hasErrors())
		{
			mv.addAttribute("loggedUser", userServ.getOneUser((Long) session.getAttribute("loggedUser")));
			System.out.println(result.getAllErrors());
			//System.out.println("Error");
			//return "redirect:/books/"+updatedBook.getId()+"/edit";.
			return "editBook.jsp";
		}
		else
		{			
			bookServ.updateBook(updatedBook);
			System.out.println("Success");
			return "redirect:/books/"+updatedBook.getId();
		}
	}
	
	@GetMapping("/logout")
	public String logout(Model mv, HttpSession session)
	{
		mv.addAttribute("newUser", new User());
		mv.addAttribute("newLogin", new LoginUser());
		session.removeAttribute("loggedUser");
		return "redirect:/";
	}
	
	@GetMapping("/deleteBook/{bookId}")
	public String eraseBook(@PathVariable("bookId") Long id, Model mv, HttpSession session)
	{
		bookServ.deleteBook(id);
		List<Book> allBooks = bookServ.findAllBooks();
		
		mv.addAttribute("loggedUser", userServ.getOneUser((Long) session.getAttribute("loggedUser")));
		mv.addAttribute("books", allBooks);
		
		return "redirect:/books";
	}
	
	
	@GetMapping("/books/borrow/{id}")
	public String borrowedBook(@PathVariable("id") Long bookId, Model mv, HttpSession session)
	{
		User activeUser = userServ.getOneUser((Long) session.getAttribute("loggedUser"));
		mv.addAttribute("loggedUser", activeUser);	
		Book bookToBorrow = bookServ.findOneBook(bookId);
		userServ.borrowBook(activeUser, bookToBorrow);
		
		
		System.out.println("Borrowed by:" + activeUser.getEmail() + " is book titled: " + bookToBorrow.getTitle());
		
		
		return "redirect:/bookmarket";
	}
	
	@GetMapping("/books/return/{id}")
	public String returnedBook(@PathVariable("id") Long bookId, Model mv, HttpSession session)
	{
		User activeUser = userServ.getOneUser((Long) session.getAttribute("loggedUser"));
		mv.addAttribute("loggedUser", activeUser);	
		Book bookToReturn = bookServ.findOneBook(bookId);
		userServ.returnBook(activeUser, bookToReturn);
		
		System.out.println("Returned by:" + activeUser.getEmail() + " is book titled: " + bookToReturn.getTitle());
		
		return "redirect:/bookmarket";
	}
	
	


}
