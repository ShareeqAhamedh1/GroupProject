package com.futsal.web.client.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public ModelAndView login(ModelMap model) {
		ModelAndView login=new ModelAndView("futsal/login2.html");
		return login;
	}
	
	@GetMapping("/signup")
	public ModelAndView signup(ModelMap model) {
		ModelAndView signup=new ModelAndView("futsal/signup.html");
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
	                     @RequestParam("repass") String rePassword) {
	    // Process the form data here
	    
	    // Example: Print the form data
		
		UserDetails u_details=new UserDetails();
		u_details.setUserName(username);
		u_details.setAddress(email);
		u_details.setContactNo(contactNo);
		u_details.setFirstName(firstName);
		u_details.setLastName(lastName);
		u_details.setPassword(rePassword);
		u_details.setRePassword(rePassword);
		
		
	    System.out.println(u_details.toString());
	    
	    List<Map<String,Object>> RegisterUser= FutsalServices.getUserDetails(u_details);
	    
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
		    return "redirect:/futsal_home/home";
		} else {
		    System.out.println("User not found with email: " + user.getAddress());
		    redirectAttributes.addFlashAttribute("UserNotFound", "UserNotFound");
		    return "redirect:/futsal_home/login";
		}
		
	}
}
