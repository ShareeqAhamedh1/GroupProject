package com.futsal.web.client.controller;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.futsal.web.client.models.BookingDetails;
import com.futsal.web.client.models.CheckoutDetails;
import com.futsal.web.client.models.FutsalDetails;
import com.futsal.web.client.models.SportDetails;
import com.futsal.web.client.models.UserDetails;
import com.futsal.web.client.services.FutsalAdminDashboardService;
import com.futsal.web.client.services.FutsalAdminService;
import com.futsal.web.client.services.FutsalServices;

@Controller
@RequestMapping("futsal_home")
public class futsalController {
	
	  @Autowired
	    private PasswordEncoder passwordEncoder;

	@GetMapping("/")
	@ResponseBody
	String home() {
		return "Hello World Futsal Home shareeq";
	}
	
	@GetMapping("/home")
	public ModelAndView home(ModelMap model) {
		ModelAndView home=new ModelAndView("futsal/index.html");
		List<Map<String,Object>> futsalDetails=FutsalAdminService.futsalDetails();
		
		model.addAttribute("futsalDetails", futsalDetails);
		
		
		for (Map<String, Object> futsal : futsalDetails) {
		    String futsalName = (String) futsal.get("fname");
		    String email = (String) futsal.get("email");
		    String contactNo = (String) futsal.get("contactno");
		    String password = (String) futsal.get("password");
		    String imagePath = (String) futsal.get("image");
		    

		    if (imagePath != null) {
		        // Convert image data to Base64 String
//		        String base64Image = Base64.getEncoder().encodeToString(imageData);

		        // Do something with the retrieved values
		        System.out.println("Futsal Name: " + futsalName);
		        System.out.println("Email: " + email);
		        System.out.println("Contact No: " + contactNo);
		        System.out.println("Password: " + password);
		        System.out.println("Image path: " + imagePath);
		        
		        model.addAttribute("imagePath", imagePath);
		        return home;
		        
		    } else {
		        System.out.println("Image data is null");
		        return home;
		    }
		}
		
		return home;
	}
	
	@GetMapping("/booking")
	public ModelAndView booking(ModelMap model) {
		ModelAndView booking=new ModelAndView("futsal/bookings2.html");
		return booking;
	}
	
	@GetMapping("/bookings")
	public ModelAndView bookings(ModelMap model,@RequestParam(value = "futsal_id", required = false) Integer futsal_id) {
		ModelAndView booking=new ModelAndView("futsal/bookings.html");
		
		 if (futsal_id == null) {
		       
		        return booking; 
		    }
		
		model.addAttribute("futsal_id",futsal_id);
		
		FutsalDetails f_detail=new FutsalDetails();
		
		f_detail.setFutsal_id(futsal_id);
		List<Map<String,Object>> viewFutsaldetails=FutsalAdminService.futsalDetails3(f_detail);
		
		model.addAttribute("futsalDetails",viewFutsaldetails);
		
		boolean isFutsalAvailable=viewFutsaldetails.stream()
				.anyMatch(checkFutsal-> checkFutsal.get("futsalid").equals(f_detail.getFutsal_id()));

		if (isFutsalAvailable) {
			Map<String,Object> futsalData= viewFutsaldetails.get(0);
			
			SportDetails sport=new SportDetails();
			int futsalId =  (int) futsalData.get("futsalid");
			sport.setF_id(futsalId);
			List<Map<String,Object>> viewSportDetails=FutsalAdminDashboardService.sportsDetails2(sport);
			
			model.addAttribute("sportDetails", viewSportDetails);
			
			return booking;
			
		}
		
		
		return booking;
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
	public ModelAndView login(@RequestParam(name = "UserExist", required = false, defaultValue = "false") String userExist,@RequestParam(name = "UserCreated", required = false, defaultValue = "false") String UserCreated, ModelMap model) {
		ModelAndView login = new ModelAndView("futsal/login2.html");
	    
	    // Use the "userExist" parameter as needed in your login page logic
	    model.addAttribute("UserExist", userExist);
	    model.addAttribute("UserCreated", UserCreated);
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

	    
	    System.out.println("Username: " + username);
	    System.out.println("First Name: " + firstName);
	    System.out.println("Last Name: " + lastName);
	    System.out.println("Contact No: " + contactNo);
	    System.out.println("Email: " + email);
	    System.out.println("Password: " + password);
	    System.out.println("Re-entered Password: " + rePassword);

	 
	    UserDetails u_details = new UserDetails();
	    u_details.setUserName(username);
	    u_details.setAddress(email);
	    u_details.setContactNo(contactNo);
	    u_details.setFirstName(firstName);
	    u_details.setLastName(lastName);
	    u_details.setPassword(password);
	    u_details.setRePassword(rePassword);

	    
	    String hashedPassword = passwordEncoder.encode(password);
	    String hashedRePassword = passwordEncoder.encode(rePassword);
	    
	    if (!password.equals(rePassword)) {
	        model.addAttribute("passwordNotMatching", Boolean.TRUE);
	        return "redirect:/futsal_home/signup";
	    }

	    System.out.println(u_details.toString());
	    
	    u_details.setPassword(hashedPassword);
	    u_details.setRePassword(hashedRePassword);

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
	    return "redirect:/futsal_home/login?UserCreated=true"; // Redirect to a success page
	}

	
	@GetMapping("/process_booking")
	public ModelAndView process_booking(ModelMap model) {
		ModelAndView home=new ModelAndView("futsal/checkout.html");
		return home;
	}
	
	
	  public boolean isPasswordValid(String enteredPassword, String storedHashedPassword) {
	        return passwordEncoder.matches(enteredPassword, storedHashedPassword);
	    }
	  
	  
	@PostMapping("/LoginValidate")
	public String LoginValidate(ModelMap model,@RequestParam("email") String email,@RequestParam("pass") String password ,RedirectAttributes redirectAttributes,HttpSession session) {
//		ModelAndView home=new ModelAndView("futsal/checkout.html");
		
		UserDetails user=new UserDetails();
		user.setAddress(email);
		user.setPassword(password);
		
		System.out.println("Login Details: "+user.getAddress()+" "+user.getPassword());
		
		String hashedPassword = passwordEncoder.encode(password);
		user.setPassword(hashedPassword);
		System.out.println("loged password "+hashedPassword);
		
		if (user.getAddress().equals("admin") && user.getAddress().equals("admin")) {
			session.setAttribute("userName",user.getAddress());
			return "redirect:/adminDashboard/home";
		}else {
		List<Map<String, Object>> validateUser = FutsalServices.sendUserDetails(user);
		
		boolean isUserFound = validateUser.stream()
		    .anyMatch(validateUserDetail -> 
		        validateUserDetail.get("Email").toString().equalsIgnoreCase(user.getAddress())
		    );

		if (isUserFound) {
			
			for (Map<String,Object> userDetails:validateUser) {
				System.out.println("User Found "+userDetails.get("Email"));
				
				String passwordFromDb=(String) userDetails.get("Password");
				System.out.println("Password from db "+passwordFromDb);
		
				if (isPasswordValid(password, passwordFromDb)) {
					System.out.println("User found with email: " + user.getAddress());
				    model.addAttribute("UserFound",Boolean.TRUE);
				    model.addAttribute("loggedIn",Boolean.TRUE);
				    return "redirect:/futsal_home/home";
				}else {
					System.out.println(user.getPassword()+" "+passwordFromDb);
					redirectAttributes.addFlashAttribute("UserNotFound", "UserNotFound");
				    return "redirect:/futsal_home/login";
				}
		
		    
			}
		    return "redirect:/futsal_home/home";
			
		} else {
		    System.out.println("User not found with email: " + user.getAddress());
		    redirectAttributes.addFlashAttribute("UserNotFound", "UserNotFound");
		    return "redirect:/futsal_home/login";
		}
		
		}
	}
	
	//add booking
		@PostMapping("/addbooking")
		public String addBooking(ModelMap model,
									RedirectAttributes redirectAttributes,
									@RequestParam("name") String name,
								    @RequestParam("sport") String sport,
								    @RequestParam("place") String place,
								    @RequestParam("date") String date,
								    @RequestParam("time") String time,
								    @RequestParam("futsal_id") int futsal_id,RedirectAttributes redAt) {
			
//			if(futsal_id==0) {
//				
//			}

			BookingDetails bookingDetails = new BookingDetails(name, sport, place, date, time,futsal_id);
			System.out.println(bookingDetails.toString());
			
			List<Map<String, Object>> addBooking = FutsalServices.getBookingDetails(bookingDetails);
//			bookingDetails.toString();
			
			List<Map<String,Object>> getBookingId=FutsalServices.getBooking(bookingDetails);
			
			boolean isBookingIdAvailable = getBookingId.stream()
				    .anyMatch(booking ->
				        booking.get("b_place").toString().equals(bookingDetails.getPlace()) &&
				        booking.get("b_date").toString().equals(bookingDetails.getDate()) &&
				        booking.get("futsal_id").equals(bookingDetails.getFutsal_id()) &&
				        booking.get("b_sport").equals(bookingDetails.getSport())
				    );

			
			if (isBookingIdAvailable) {
				Map<String, Object> bookingID = getBookingId.get(0);
				int booking_id =  (int) bookingID.get("b_id");
				System.out.println(booking_id);
				
				redAt.addFlashAttribute("booking_id", booking_id);
				return "redirect:/futsal_home/process_booking"; 
			}else {
				redAt.addAttribute("futsal_id",bookingDetails.getFutsal_id());
		    return "redirect:/futsal_home/bookings"; 
			}
		}
		
		// add checkout
		
		@GetMapping("/checkout")
		public ModelAndView checkout(ModelMap model) {
			ModelAndView checkout=new ModelAndView("futsal/checkout.html");
			return checkout;
		}
		
		
			@PostMapping("/addcheckout")
			public String addcheckout(ModelMap model,
										RedirectAttributes redirectAttributes,
										@RequestParam("firstname") String firstname,
									    @RequestParam("email") String email,
									    @RequestParam("address") String address,
									    @RequestParam("city") String city,
									    @RequestParam("state") String state,
									    @RequestParam("zip") String zip,
									    @RequestParam("cardname") String cardname,
									    @RequestParam("cardnumber") String cardnumber,
									    @RequestParam("expMonthYear") String expMonthYear,
									    @RequestParam("booking_id") int booking_id,
									    @RequestParam("cvv") String cvv
									    
						) {

				CheckoutDetails CheckoutDetails = new CheckoutDetails(firstname, email, address, city, state, Integer.parseInt(zip), "", cardname, cardnumber, expMonthYear, Integer.parseInt(cvv),booking_id);
				System.out.println(CheckoutDetails.toString());
				
				int addCheckout = FutsalServices.addCheckoutDetails(CheckoutDetails);
				System.out.println(CheckoutDetails.toString());
				
			    return "redirect:/futsal_home/home";
			}
		
	


}
