package com.futsal.web.client.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.module.kotlin.ReflectionCache.BooleanTriState.False;
import com.futsal.web.client.models.UserDetails;
import com.futsal.web.client.services.FutsalServices;

@Controller
@RequestMapping("futsal_home")
public class futsalController {

	@GetMapping("/")
	@ResponseBody
	String home() {
		return "Hello World Futsal Home shareeq";
	}
	
	@GetMapping("/home")
	public ModelAndView home(ModelMap model) {
		ModelAndView home=new ModelAndView("futsal/index.html");
		return home;
	}
	
	@GetMapping("/bookings")
	public ModelAndView bookings(ModelMap model) {
		ModelAndView home=new ModelAndView("futsal/bookings.html");
		return home;
	}
	
	@GetMapping("/about_us")
	public ModelAndView about_us(ModelMap model) {
		ModelAndView home=new ModelAndView("futsal/about_us.html");
		return home;
	}
	
	@GetMapping("/just")
	public ModelAndView just(ModelMap model) {
		ModelAndView home=new ModelAndView("futsal/just.html");
		return home;
	}
	
	@GetMapping("/login")
	public ModelAndView login(@RequestParam(name = "UserExist", required = false, defaultValue = "false") String userExist, ModelMap model) {
		ModelAndView login = new ModelAndView("futsal/login2.html");
	    
	    // Use the "userExist" parameter as needed in your login page logic
	    model.addAttribute("UserExist", userExist);
		return login;
	}
	
	
	@GetMapping("/signup")
	public ModelAndView signup(ModelMap model, @RequestAttribute(name = "passwordNotMatching", required = false) String passwordNotMatching) {
	    ModelAndView signup = new ModelAndView("futsal/signup.html");
	    
//	    if (passwordNotMatching != null) {
//	        // Add the attribute to the model if it exists
//	    	model.addAttribute("passwordMatching", Boolean.TRUE);
//	       
//	    }else {
//	    	 model.addAttribute("passwordNotMatching", Boolean.TRUE);
//	    }
//
	    return signup;
	}
		
		
	
	@GetMapping("/myprofile")
	public ModelAndView myprofile(ModelMap model) {
		ModelAndView myprofile=new ModelAndView("futsal/myprofile.html");
		return myprofile;
	}
	
	@PostMapping("/registration")
	public String signup(@RequestParam("username") String username,
	                     @RequestParam("fName") String firstName,
	                     @RequestParam("lName") String lastName,
	                     @RequestParam("contactNo") String contactNo,
	                     @RequestParam("email") String email,
	                     @RequestParam("pass") String password,
	                     @RequestParam("repass") String rePassword,
	                     ModelMap model, HttpSession session) {

	    // Process the form data here

	    // Example: Print the form data
	    System.out.println("Username: " + username);
	    System.out.println("First Name: " + firstName);
	    System.out.println("Last Name: " + lastName);
	    System.out.println("Contact No: " + contactNo);
	    System.out.println("Email: " + email);
	    System.out.println("Password: " + password);
	    System.out.println("Re-entered Password: " + rePassword);

	    // Example test 1
	    UserDetails u_details = new UserDetails();
	    u_details.setUserName(username);
	    u_details.setAddress(email);
	    u_details.setContactNo(contactNo);
	    u_details.setFirstName(firstName);
	    u_details.setLastName(lastName);
	    u_details.setPassword(password);
	    u_details.setRePassword(rePassword);

	    if (!password.equals(rePassword)) {
	        model.addAttribute("passwordNotMatching", Boolean.TRUE);
	        return "redirect:/futsal_home/signup";
	    }

	    System.out.println(u_details.toString());

	    List<Map<String, Object>> checkUser = FutsalServices.validateUser(u_details);

	    if (checkUser != null) {
	        model.addAttribute("UserExist", Boolean.TRUE);
	        return "redirect:/futsal_home/login?UserExist=true";
	    }

	    List<Map<String, Object>> addUsers = FutsalServices.getUserDetails(u_details);

	    if (addUsers != null) {
	        boolean isUserFound = addUsers.stream()
	                .anyMatch(userCheck -> userCheck.get("email").toString().equals(u_details.getAddress()));
	        System.out.println(isUserFound);
	    }

	    // You can add your logic here, such as user registration
	    return "redirect:/futsal_home/login"; // Redirect to a success page
	}

	
	@GetMapping("/process_booking")
	public ModelAndView process_booking(ModelMap model) {
		ModelAndView home=new ModelAndView("futsal/checkout.html");
		return home;
	}
	@PostMapping("/LoginValidate")
	public String LoginValidate(ModelMap model,@RequestParam("email") String email,@RequestParam("pass") String password ,RedirectAttributes redirectAttributes) {
//		ModelAndView home=new ModelAndView("futsal/checkout.html");
		
		UserDetails user=new UserDetails();
		user.setAddress(email);
		user.setPassword(password);
		
		System.out.println("Login Details: "+user.getAddress()+" "+user.getPassword());
		
		List<Map<String, Object>> validateUser = FutsalServices.sendUserDetails(user);
		
		boolean isUserFound = validateUser.stream()
		    .anyMatch(validateUserDetail -> 
		        validateUserDetail.get("Email").toString().equalsIgnoreCase(user.getAddress())
		    );

		if (isUserFound) {
		    System.out.println("User found with email: " + user.getAddress());
		    model.addAttribute("UserFound",Boolean.TRUE);
		    model.addAttribute("loggedIn",Boolean.TRUE);
		    return "redirect:/futsal_home/home";
		} else {
		    System.out.println("User not found with email: " + user.getAddress());
		    redirectAttributes.addFlashAttribute("UserNotFound", "UserNotFound");
		    return "redirect:/futsal_home/login";
		}
		
	}
}
