package com.alexproject.loginandregistration.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alexproject.loginandregistration.models.LoginUser;
import com.alexproject.loginandregistration.models.User;
import com.alexproject.loginandregistration.services.UserService;

@Controller
public class ViewController {
	
	@Autowired
	UserService userServ;
	
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
				return "redirect:/welcome";
			}
		}
		
	}

	@GetMapping("/welcome")
	public String homePage(Model mv, HttpSession session)
	{
		mv.addAttribute("loggedUser", userServ.getOneUser((Long) session.getAttribute("loggedUser")));
		return "dashboard.jsp";
	}
	
	@GetMapping("/logout")
	public String logout(Model mv, HttpSession session)
	{
		mv.addAttribute("newUser", new User());
		mv.addAttribute("newLogin", new LoginUser());
		session.removeAttribute("loggedUser");
		return "redirect:/";
	}


}
