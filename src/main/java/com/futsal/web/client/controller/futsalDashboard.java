package com.futsal.web.client.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.futsal.web.client.models.AdminDetails;
import com.futsal.web.client.models.BookingDetails;
import com.futsal.web.client.models.FutsalDetails;
import com.futsal.web.client.models.SportDetails;
import com.futsal.web.client.services.FutsalAdminDashboardService;
import com.futsal.web.client.services.FutsalAdminService;
import com.futsal.web.client.util.SessionUtil;
import com.futsal.web.client.util.SessionUtilFutsal;

@Controller
@RequestMapping("AdminDashboard")
public class futsalDashboard {
	
	@Autowired
	private futsalDashboard futsalDashboard;

	@Autowired
    private HttpServletRequest requestFutsal;
	
	@Autowired
    private SessionUtilFutsal sessionUtilFutsal;
	
	@Autowired
	private HttpSession httpSessionFutsal;
	

	 private static final String uploadFolder="src/main/resources/static/futsal/images/adimg/";
	
	
	 @Autowired
	    private PasswordEncoder passwordEncoder;


		@GetMapping("/")
		@ResponseBody
		String home() {
			return "This is admin";
		}
		
		@GetMapping("/home")
		public ModelAndView home(ModelMap model, HttpServletRequest request) {
		    ModelAndView modelAndView = new ModelAndView();
		    
		    if (sessionUtilFutsal.getIdFromSessionFutsal() != null) {
		        modelAndView.setViewName("/futsal/admin/index.html");
		        
		          
		      
		        
		        String f_id = sessionUtilFutsal.getIdFromSessionFutsal();
		        int futsal_id=Integer.valueOf(f_id);
		        modelAndView.addObject("f_id", f_id);
		        
		        
		        model.addAttribute("futsalLoggedIn", f_id);
		        FutsalDetails futsal=new FutsalDetails();
		        futsal.setFutsal_id(futsal_id);
		        
		        List<Map<String,Object>> futsalDetails=FutsalAdminService.futsalDetails3(futsal);
		        
		        Map<String,Object> futsaldet=futsalDetails.get(0);
		        
		        String futsalName=(String) futsaldet.get("fname");
		        
		        model.addAttribute("fname",futsalName);
		        
//		        System.out.println(futsalDetails);
		        System.out.println(f_id);
		        
		        
		        
//		        Booking details
		        List<Map<String,Object>> bookingDetails=FutsalAdminService.getBookingsForFutsal(futsal);
		        System.out.println(bookingDetails);
		        List<Map<String,Object>> bookings=bookingDetails;
		        model.addAttribute("bookings",bookings);

		        return modelAndView;
		    } else {
		        modelAndView.setViewName("/futsal/admin/signin.html");
		        return modelAndView;
		    }
		}

	
		@GetMapping("/form")
		public ModelAndView form(ModelMap model) {
			
			if(sessionUtilFutsal.getIdFromSessionFutsal()!=null) {
			ModelAndView form=new ModelAndView("/futsal/admin/form.html");
			
			 String f_id = sessionUtilFutsal.getIdFromSessionFutsal();
		        int futsal_id=Integer.valueOf(f_id);
		      
		        
		        
		        model.addAttribute("futsalLoggedIn", f_id);
		        FutsalDetails futsal=new FutsalDetails();
		        futsal.setFutsal_id(futsal_id);
		        
		        List<Map<String,Object>> futsalDetails=FutsalAdminService.futsalDetails3(futsal);
		        
		        Map<String,Object> futsaldet=futsalDetails.get(0);
		        
		        String futsalName=(String) futsaldet.get("fname");
		        
		        model.addAttribute("fname",futsalName);
		        
		        
		        

		        
			return form;
			
			}else {
				ModelAndView signout=new ModelAndView("/futsal/admin/signin.html");
				return signout;
			}
		}
		@GetMapping("/table")
		public ModelAndView table(ModelMap model) {
			if(sessionUtilFutsal.getIdFromSessionFutsal()!=null) {
			
				SportDetails sport =new SportDetails();
				String f_id_s = sessionUtilFutsal.getIdFromSessionFutsal();
				String f_ids = sessionUtilFutsal.getIdFromSessionFutsal();
				
				
		        model.addAttribute("futsalLoggedIn", f_ids);
				int f_id = Integer.parseInt(f_id_s);
				
				 FutsalDetails futsal=new FutsalDetails();
			        futsal.setFutsal_id(f_id);
			        
			        List<Map<String,Object>> futsalDetails=FutsalAdminService.futsalDetails3(futsal);
			        
			        Map<String,Object> futsaldet=futsalDetails.get(0);
			        
			        String futsalName=(String) futsaldet.get("fname");
			        
			        model.addAttribute("fname",futsalName);
				
				sport.setF_id(f_id);
				List<Map<String,Object>> viewSportDetails=FutsalAdminDashboardService.sportsDetails2(sport);
				
				model.addAttribute("sportDetails", viewSportDetails);
				
			ModelAndView table=new ModelAndView("/futsal/admin/table.html");
			return table;
			
			}else {
				ModelAndView signout=new ModelAndView("/futsal/admin/signin.html");
				return signout;
			}
		}
		@GetMapping("/chart")
		public ModelAndView chart(ModelMap model) {
			

			if(sessionUtilFutsal.getIdFromSessionFutsal()!=null) {
			ModelAndView chart=new ModelAndView("/futsal/admin/chart.html");
			String f_id = sessionUtilFutsal.getIdFromSessionFutsal();
	        model.addAttribute("futsalLoggedIn", f_id);
			return chart;
			
			}else {
				ModelAndView signout=new ModelAndView("/futsal/admin/signin.html");
				return signout;
			}
		}
		@GetMapping("/signin")
		public ModelAndView signin(ModelMap model) {
			ModelAndView signin=new ModelAndView("/futsal/admin/signin.html");
			return signin;
		}
		@GetMapping("/signup")
		public ModelAndView signup(ModelMap model) {
			ModelAndView signup=new ModelAndView("/futsal/admin/signup.html");
			return signup;
		}
		
		@GetMapping("/404")
		public ModelAndView error(ModelMap model) {
			ModelAndView error=new ModelAndView("/futsal/admin/404.html");
			return error;
		}
		
		
		public boolean isPasswordValid(String enteredPassword, String hashedPassword) {
			return passwordEncoder.matches(enteredPassword, hashedPassword);
		}
		
		@PostMapping("/signinValidate")
		public String signinValidate(@RequestParam("email") String email,@RequestParam("password") String password, ModelMap model
				,HttpSession session,RedirectAttributes redAt) {
				
			AdminDetails admin=new AdminDetails();
			admin.setEmail(email);
			admin.setPassword(password);
			
			List<Map<String,Object>> CheckAdmin=FutsalAdminDashboardService.adminDetails(admin);
			
			String hashedPassword=passwordEncoder.encode(password);
			
			admin.setPassword(hashedPassword);
			
			boolean isAdminFound=CheckAdmin.stream()
					.anyMatch(CheckAdminDetails->
					CheckAdminDetails.get("Email").toString().equalsIgnoreCase(admin.getEmail())
							);
			
			if (isAdminFound) {
			    for (Map<String, Object> adminDetails : CheckAdmin) {
//			        System.out.println("Admin Found " + admin.getAdminName());
			        String passwordFromDB = String.valueOf(adminDetails.get("password"));

			        if (isPasswordValid(password, passwordFromDB)) {
			            session.setAttribute("futsalId", String.valueOf(adminDetails.get("futsalid")));
			            System.out.println("User found with email: " + admin.getEmail());
			            model.addAttribute("UserFound", Boolean.TRUE);
			            model.addAttribute("loggedIn", Boolean.TRUE);
			            redAt.addFlashAttribute("futsalLoggedIn",true);
//			            session.setAttribute("userName", admin.getEmail());
			            return "redirect:/AdminDashboard/home";
			        } else {
			            redAt.addFlashAttribute("UserNotFound", "UserNotFound");
			            return "redirect:/AdminDashboard/signin";
			        }
			    }
			} else {
			    redAt.addFlashAttribute("UserNotExisted", "UserNotExisted");
			    return "redirect:/AdminDashboard/signin";
			}

			System.out.println(isAdminFound);
			
			return "redirect:home";
		}
		
		public void invalidateSessionFutsal() {
		    // Invalidate the session
			httpSessionFutsal.invalidate();
		}
		

		@GetMapping("/signout")
		public ModelAndView signout(ModelMap model) {
		    // Retrieve the attribute from the session
		    futsalDashboard futsalSession = (futsalDashboard) httpSessionFutsal.getAttribute(sessionUtilFutsal.getIdFromSessionFutsal());

		    // Check if the attribute is not null before invalidating
		    if (futsalSession != null) {
		    	futsalSession.invalidateSessionFutsal();
		    }

		    // Invalidate the entire session
		    httpSessionFutsal.invalidate();

		    ModelAndView signout = new ModelAndView("/futsal/super_admin/signin.html");
		    return signout;
		}
		
		public static String convertMultipartFileToString(MultipartFile file) {
	        try {
	            // Convert the byte array from MultipartFile to a String
	            String fileContent = new String(file.getBytes());
	            return fileContent;
	        } catch (Exception e) {
	            // Handle exceptions appropriately
	            e.printStackTrace();
	            return null;
	        }
	    }
		
		
		private static String removePrefix(String fullPath, String prefixToRemove) {
	        if (fullPath.startsWith(prefixToRemove)) {
	            return fullPath.substring(prefixToRemove.length());
	        }
	        return fullPath;
	    }
		
		@PostMapping("/addSport")
		public String addSport(ModelMap model,@RequestParam("typeOfSport") String typeOfSport,@RequestParam("image") MultipartFile  imageFile) {
			
			SportDetails s_details=new SportDetails();
		
			s_details.setTypeOfSports(typeOfSport);
			String f_id_s = sessionUtilFutsal.getIdFromSessionFutsal();
			int f_id = Integer.parseInt(f_id_s);
			s_details.setF_id(f_id);
//			f_details.setImage(imageFile);
			
			
			 if (!imageFile.isEmpty()) {
		            try {
		                // Get the bytes of the image
		                byte[] bytes = imageFile.getBytes();

		                // Generate a unique filename (you may need to implement a logic to generate a unique name)
		                String filename = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();

		                // Define the path where you want to save the image
		                Path path = Paths.get(uploadFolder + filename);

		                // Save the image to the specified path
		                Files.write(path, bytes);
		                
		                String fullPath=path.toString();
		    		    
		    		    String prefixToRemove = "src\\main\\resources\\static";

		    	        // Remove the prefix
		    	        String imagePathFinal = removePrefix(fullPath, prefixToRemove);
		                

		                s_details.setImage(imagePathFinal);
		                // Print the path for testing
		                System.out.println("Image saved at: " + s_details.getImage());
		              
		                
		                
		                List<Map<String,Object>> addSports=FutsalAdminDashboardService.addSports(s_details);

		                // Now, you can store the filename or path in your database or use it as needed
		            } catch (IOException e) {
		                e.printStackTrace();
		                // Handle the exception (e.g., show an error message)
		            }
		        }

			 return "redirect:/AdminDashboard/form";
		}
		
		
		@GetMapping("/deleteSport")
		public String deleteFutsal(ModelMap model,@RequestParam("sport_id") int sport_id) {
			
			SportDetails s_details=new SportDetails();
			s_details.setS_id(sport_id);
			
			List<Map<String,Object>> deleteFutsal=FutsalAdminDashboardService.deleteSport(s_details);
			
			model.addAttribute("deletedSuccessfully",Boolean.TRUE);
			
			
			System.out.println("deleted "+s_details.getS_id());
			
			
			return "redirect:/AdminDashboard/table";
		}
		
		
		@GetMapping("/editSport")
		public String editSport(ModelMap model, @RequestParam("sport_id") int sport_id) {
		    SportDetails s_details = new SportDetails();
		    s_details.setS_id(sport_id);

		    System.out.println("Sports Id " + s_details.getS_id());
		    List<Map<String, Object>> selectSport = FutsalAdminDashboardService.sportsDetails(s_details);

		    boolean isSportFound = selectSport.stream()
		            .anyMatch(sportCheck ->
		                    sportCheck.get("s_id").equals(s_details.getS_id()));

		    if (isSportFound) {
		      
		        Map<String, Object> sportDetails = selectSport.get(0);
		      
		        String typeOfSport = (String) sportDetails.get("typeofsport");
		       
		        model.addAttribute("typeOfSport", typeOfSport);
		    }

		    System.out.println(isSportFound);
		    return "redirect:/AdminDashboard/table";
		}
		
		
		@GetMapping("/editBooking")
		public String editBooking(ModelMap model,@RequestParam("b_id") int b_id,@RequestParam("b_method") int b_method,RedirectAttributes redAt) {
			
			BookingDetails b_details=new BookingDetails();
			
			b_details.setB_id(b_id);
			b_details.setPayment(b_method);
			
			System.out.println(b_details.getB_id()+" ,"+b_details.getPayment());
			
			List<Map<String,Object>> updateBooking=FutsalAdminService.updateBookingStatus(b_details);
			
			redAt.addAttribute("updated",true);
			return "redirect:/AdminDashboard/home";
		}
		
		@GetMapping("/cancelBooking")
		public String cancelBooking(ModelMap model,@RequestParam("b_id") int b_id,RedirectAttributes redAt) {
			
			BookingDetails b_details=new BookingDetails();
			b_details.setB_id(b_id);
			
			List<Map<String,Object>> cancelBooking=FutsalAdminService.cancelBooking(b_details);
			
			redAt.addAttribute("cancelled",true);
			return "redirect:/AdminDashboard/home";
			
		}
		
		

	}


