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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.futsal.web.client.models.AdminDetails;
import com.futsal.web.client.models.FutsalDetails;
import com.futsal.web.client.models.SportDetails;
import com.futsal.web.client.services.FutsalAdminDashboardService;
import com.futsal.web.client.services.FutsalAdminService;
import com.futsal.web.client.util.SessionUtil;


@Controller
@RequestMapping("SuperAdmin")
public class futsalSuperAdmin {
	
	@Autowired
    private HttpServletRequest requestAdmin;
	
	@Autowired
    private SessionUtil sessionUtil;
	
	@Autowired
	private HttpSession httpSessionAdmin;
	
	
	 @Autowired
	    private PasswordEncoder passwordEncoder;

	 private static final String uploadFolder="src/main/resources/static/futsal/images/img/";
	 
	@GetMapping("/")
	@ResponseBody
	String home() {
		return "This is admin";
	}
	
	@GetMapping("/home")
	public ModelAndView home(ModelMap model,String user) {
		
		if(sessionUtil.getUserNameFromSession()!=null) {
		ModelAndView home=new ModelAndView("/futsal/super_admin/index.html");
		
		String userId = sessionUtil.getUserNameFromSession();
		System.out.println(userId);
		
		List<Map<String,Object>> getAllBookingDetails=FutsalAdminService.getAllBookings();
		
		System.out.println(getAllBookingDetails);
		model.addAttribute("bookingDetails",getAllBookingDetails);
		
		return home;
		}else {
			ModelAndView signout=new ModelAndView("/futsal/super_admin/signin.html");
			return signout;
		}
	}
	
	@GetMapping("/form")
	public ModelAndView form(ModelMap model,RedirectAttributes redirectAttributes) {
		if(sessionUtil.getUserNameFromSession()!=null) {
			
//			 redirectAttributes.addFlashAttribute("editDetails", true);
			 
//			 model.addAttribute("editDetails",redirectAttributes.addFlashAttribute("editDetails", true));
		ModelAndView form=new ModelAndView("/futsal/super_admin/form.html");
		return form;
		}else {
			ModelAndView signout=new ModelAndView("/futsal/super_admin/signin.html");
			return signout;
		}
	}
	@GetMapping("/table")
	public ModelAndView table(ModelMap model) {
		if(sessionUtil.getUserNameFromSession()!=null) {
			
			List<Map<String,Object>> viewFutsalDetails=FutsalAdminService.futsalDetails2();
			
			model.addAttribute("futsalDetails", viewFutsalDetails);
			
		ModelAndView table=new ModelAndView("/futsal/super_admin/table.html");
		return table;
		}else {
			ModelAndView signout=new ModelAndView("/futsal/super_admin/signin.html");
			return signout;
		}
	}
	@GetMapping("/chart")
	public ModelAndView chart(ModelMap model) {
		if(sessionUtil.getUserNameFromSession()!=null) {
		ModelAndView chart=new ModelAndView("/futsal/super_admin/chart.html");
		return chart;
		}else {
			ModelAndView signout=new ModelAndView("/futsal/super_admin/signin.html");
			return signout;
		}
	}
	@GetMapping("/signin")
	public ModelAndView signin(ModelMap model) {
		ModelAndView signin=new ModelAndView("/futsal/super_admin/signin.html");
		return signin;
	}
	@GetMapping("/signup")
	public ModelAndView signup(ModelMap model) {
		ModelAndView signup=new ModelAndView("futsal/super_admin/signup.html");
		
		System.out.println("In super_admin dashboard signup.html");
		return signup;
	}
	
	@PostMapping("/signinValidate")
	public String signinValidate(@RequestParam("email") String email,@RequestParam("password") String password, ModelMap model
			,HttpSession session,RedirectAttributes redirectAttributes) {
			
		AdminDetails admin=new AdminDetails();
		admin.setEmail(email);
		admin.setPassword(password);
		
		List<Map<String,Object>> CheckAdmin=FutsalAdminService.adminDetails(admin);
		
		String hashedPassword=passwordEncoder.encode(password);
		
		admin.setPassword(hashedPassword);
		
		boolean isAdminFound=CheckAdmin.stream()
				.anyMatch(CheckAdminDetails->
				CheckAdminDetails.get("Email").toString().equalsIgnoreCase(admin.getEmail())
						);
		
		if(isAdminFound) {
			for(Map<String,Object> adminDetails:CheckAdmin) {
				System.out.println("Admin Found "+admin.getAdminName());
				String passwordFromDB=(String)adminDetails.get("password");
				
				if(isPasswordValid(password,passwordFromDB)) {
					int adId=(int) adminDetails.get("adminid");
					admin.setAdminId(adId);
					System.out.println("User found with email: " + admin.getEmail()+" AdminId: "+adId);
				    model.addAttribute("UserFound",Boolean.TRUE);
				    model.addAttribute("loggedIn",Boolean.TRUE);
				    session.setAttribute("userId",String.valueOf(admin.getAdminId()));
					return "redirect:/SuperAdmin/home";
				    
				}else {
					redirectAttributes.addFlashAttribute("UserNotFound", "UserNotFound");
					return "redirect:/SuperAdmin/signin";
				}
			}
		}else {
			redirectAttributes.addFlashAttribute("UserNotExisted", "UserNotExisted");
			return "redirect:/SuperAdmin/signin";
		}
		System.out.println(isAdminFound);
		
		return "redirect:home";
	}
	
	
	
	@PostMapping("/registration")
	public String registration(@RequestParam("adminName") String adminName,
            @RequestParam("contactNo") String contactNo,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("rePassword") String rePassword ,ModelMap model,RedirectAttributes redAt) {
		
		if (!password.equals(rePassword)) {
			
			model.addAttribute("passwordNotMatching",Boolean.TRUE);
			return "/futsal/super_admin/signup.html";
		}
		
		AdminDetails a_details=new AdminDetails();
		
		a_details.setAdminName(adminName);
		a_details.setContactNo(contactNo);
		a_details.setEmail(email);
		a_details.setPassword(password);
		a_details.setRePassword(rePassword);
		
		String hashedPassword =passwordEncoder.encode(password);
		String hashedRePassword=passwordEncoder.encode(rePassword);
		
		
		
		System.out.println("Admin details :  "+a_details.toString());
		
		List<Map<String,Object>> CheckAdmins=FutsalAdminService.adminDetails(a_details);
	
		a_details.setPassword(hashedPassword);
		a_details.setRePassword(hashedRePassword);
		
		if (CheckAdmins != null) {
		    boolean isAdminFound = CheckAdmins.stream()
		            .anyMatch(adminCheck ->
		                adminCheck.get("email").toString().equals(a_details.getEmail())
		            );

		    	System.out.println(isAdminFound);
		    if (isAdminFound) {
		        System.out.println("Admin already registered");
		        model.addAttribute("adminExisted", Boolean.TRUE);
		        return "redirect:/SuperAdmin/signin";
		    } 
		    else {
		    	System.out.println("Admin Registered");
		        List<Map<String, Object>> RegisterAdmin = FutsalAdminService.getAdminDetails(a_details);
		        model.addAttribute("adminCreated", Boolean.TRUE);
		        return "redirect:/SuperAdmin/signin";
		    }
		} else {
			System.out.println("Admin Registered");
	        List<Map<String, Object>> RegisterAdmin = FutsalAdminService.getAdminDetails(a_details);
	        model.addAttribute("adminCreated", Boolean.TRUE);
	        return "redirect:/SuperAdmin/signin";
		}

		
		
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
	
	@PostMapping("/addFutsal")
	public String addFutsal(ModelMap model,@RequestParam("futsalName") String futsalName,@RequestParam("email") String email,
			@RequestParam("contactNo") String contactNo,
			@RequestParam("password") String password, @RequestParam("image") MultipartFile  imageFile) {
		
		FutsalDetails f_details=new FutsalDetails();
		f_details.setContactNo(contactNo);
		f_details.setFutsalEmail(email);
		f_details.setFutsalName(futsalName);
//		f_details.setImage(imageFile);
		
		String hashedPassword=passwordEncoder.encode(password);
		
		f_details.setPassword(hashedPassword);
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
	                

	                f_details.setImage(imagePathFinal);
	                // Print the path for testing
	                System.out.println("Image saved at: " + f_details.getImage());
	              
	                
	                
	                List<Map<String,Object>> addFutsal=FutsalAdminService.addFutsal(f_details);

	                // Now, you can store the filename or path in your database or use it as needed
	            } catch (IOException e) {
	                e.printStackTrace();
	                // Handle the exception (e.g., show an error message)
	            }
	        }

		 return "redirect:/SuperAdmin/form";
	}
	
	
	public boolean isPasswordValid(String enteredPassword, String hashedPassword) {
		return passwordEncoder.matches(enteredPassword, hashedPassword);
	}
	
	@GetMapping("/404")
	public ModelAndView error(ModelMap model) {
		ModelAndView error=new ModelAndView("/futsal/super_admin/404.html");
		return error;
	}
	
	public void invalidateSessionAdmin() {
	    // Invalidate the session
		httpSessionAdmin.invalidate();
	}
	
	
	@GetMapping("/signout")
	public ModelAndView signout(ModelMap model) {
	    // Retrieve the attribute from the session
	    futsalSuperAdmin adminSession = (futsalSuperAdmin) httpSessionAdmin.getAttribute(sessionUtil.getUserNameFromSession());

	    // Check if the attribute is not null before invalidating
	    if (adminSession != null) {
	        adminSession.invalidateSessionAdmin();
	    }

	    // Invalidate the entire session
	    httpSessionAdmin.invalidate();

	    ModelAndView signout = new ModelAndView("/futsal/super_admin/signin.html");
	    return signout;
	}
	
	@GetMapping("/deleteFutsal")
	public String deleteFutsal(ModelMap model,@RequestParam("futsal_id") int futsal_id) {
		
		FutsalDetails f_details=new FutsalDetails();
		f_details.setFutsal_id(futsal_id);
		
		List<Map<String,Object>> deleteFutsal=FutsalAdminService.deleteFutsal(f_details);
		
		model.addAttribute("deletedSuccessfully",Boolean.TRUE);
		
		
		System.out.println(futsal_id);
		
		
		return "redirect:/SuperAdmin/table";
	}
	
	
	@GetMapping("/editFutsal")
	public String editFutsal(ModelMap model, @RequestParam("futsal_id") int futsal_id, RedirectAttributes redirectAttributes) {
	    FutsalDetails f_details = new FutsalDetails();
	    f_details.setFutsal_id(futsal_id);

	    System.out.println("Futsal Id " + f_details.getFutsal_id());
	    List<Map<String, Object>> editFutsal = FutsalAdminService.futsalDetails3(f_details);

	    boolean isFutsalFound = editFutsal.stream()
	            .anyMatch(futsalCheck ->
	                    futsalCheck.get("futsalid").equals(f_details.getFutsal_id()));

	    if (isFutsalFound) {
	        Map<String, Object> futsalDetails = editFutsal.get(0);

	        String futsalName = (String) futsalDetails.get("fname");
	        String contactNo = (String) futsalDetails.get("contactno");
	        String email = (String) futsalDetails.get("email");
	        String password = (String) futsalDetails.get("password");
	        String image = (String) futsalDetails.get("image");
	        int futsalId =  (int) futsalDetails.get("futsalid");
	        
	        redirectAttributes.addFlashAttribute("futsalName", futsalName);
	        redirectAttributes.addFlashAttribute("contactNo", contactNo);
	        redirectAttributes.addFlashAttribute("email", email);
	        redirectAttributes.addFlashAttribute("password", password);
	        redirectAttributes.addFlashAttribute("image", image);
	        redirectAttributes.addFlashAttribute("futsalId", futsalId);
	        
	        return "redirect:/SuperAdmin/formEdit";
	    }

	    System.out.println(isFutsalFound);
	    return "redirect:/SuperAdmin/table";
	}

	
	@GetMapping("/formEdit")
	public ModelAndView formEdit(ModelMap model,RedirectAttributes redirectAttributes) {
		if(sessionUtil.getUserNameFromSession()!=null) {
			
//			 redirectAttributes.addFlashAttribute("editDetails", true);
			 
//			 model.addAttribute("editDetails",redirectAttributes.addFlashAttribute("editDetails", true));
		ModelAndView form=new ModelAndView("/futsal/super_admin/form2.html");
		return form;
		}else {
			ModelAndView signout=new ModelAndView("/futsal/super_admin/signin.html");
			return signout;
		}
	}
	@PostMapping("/updateFutsal")
	public String updateFutsal(ModelMap model,@RequestParam("futsalName") String futsalName,@RequestParam("email") String email,
			@RequestParam("contactNo") String contactNo,
			@RequestParam("password") String password, @RequestParam("image") MultipartFile  imageFile,@RequestParam("futsalId") int futsalId) {
		
		
		FutsalDetails f_details=new FutsalDetails();
		f_details.setContactNo(contactNo);
		f_details.setFutsalEmail(email);
		f_details.setFutsalName(futsalName);
		f_details.setFutsal_id(futsalId);
//		f_details.setImage(imageFile);
		
		String hashedPassword=passwordEncoder.encode(password);
		
		f_details.setPassword(hashedPassword);
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
	                

	                f_details.setImage(imagePathFinal);
	                // Print the path for testing
	                System.out.println("Image saved at: " + f_details.getImage());
	              
	                
	                
	                List<Map<String,Object>> updateFutsal=FutsalAdminService.updateFutsal(f_details);

	                // Now, you can store the filename or path in your database or use it as needed
	            } catch (IOException e) {
	                e.printStackTrace();
	                // Handle the exception (e.g., show an error message)
	            }
	        }
		return "redirect:/SuperAdmin/table";
	}
	
	
	
	
}
