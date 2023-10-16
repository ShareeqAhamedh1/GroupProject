package com.futsal.web.client.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.futsal.web.client.models.AdminDetails;
import com.futsal.web.client.services.FutsalAdminService;


@Controller
@RequestMapping("adminDashboard")
public class futsalAdmin {

	@GetMapping("/")
	@ResponseBody
	String home() {
		return "This is admin";
	}
	
	@GetMapping("/home")
	public ModelAndView home(ModelMap model) {
		ModelAndView home=new ModelAndView("futsal/admin/index.html");
		return home;
	}
	@GetMapping("/widget")
	public ModelAndView widget(ModelMap model) {
		ModelAndView widget=new ModelAndView("futsal/admin/widget.html");
		return widget;
	}
	@GetMapping("/form")
	public ModelAndView form(ModelMap model) {
		ModelAndView form=new ModelAndView("futsal/admin/form.html");
		return form;
	}
	@GetMapping("/table")
	public ModelAndView table(ModelMap model) {
		ModelAndView table=new ModelAndView("futsal/admin/table.html");
		return table;
	}
	@GetMapping("/chart")
	public ModelAndView chart(ModelMap model) {
		ModelAndView chart=new ModelAndView("futsal/admin/chart.html");
		return chart;
	}
	@GetMapping("/signin")
	public ModelAndView signin(ModelMap model) {
		ModelAndView signin=new ModelAndView("futsal/admin/signin.html");
		return signin;
	}
	@GetMapping("/signup")
	public ModelAndView signup(ModelMap model) {
		ModelAndView signup=new ModelAndView("futsal/admin/signup.html");
		
		System.out.println("In admin dashboard signup.html");
		return signup;
	}
	
	@PostMapping("/signinValidate")
	public String signinValidate() {
		return "redirect:home";
	}
	
	@PostMapping("/registration")
	public String registration(@RequestParam("adminName") String adminName,
            @RequestParam("contactNo") String contactNo,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("rePassword") String rePassword ,ModelMap model) {
		
		if (!password.equals(rePassword)) {
			
			model.addAttribute("passwordNotMatching",Boolean.TRUE);
			return "futsal/admin/signup.html";
		}
		
		AdminDetails a_details=new AdminDetails();
		
		a_details.setAdminName(adminName);
		a_details.setContactNo(contactNo);
		a_details.setEmail(email);
		a_details.setPassword(password);
		a_details.setRePassword(rePassword);
		
		
		System.out.println("Admin details :  "+a_details.toString());
		
		List<Map<String, Object>> RegisterAdmin=FutsalAdminService.getAdminDetails(a_details);
		
		return "redirect:/adminDashboard/signin";
	}
	
	@GetMapping("/404")
	public ModelAndView error(ModelMap model) {
		ModelAndView error=new ModelAndView("futsal/admin/404.html");
		return error;
	}
	
	@GetMapping("/blank")
	public ModelAndView blank(ModelMap model) {
		ModelAndView blank=new ModelAndView("futsal/admin/blank.html");
		return blank;
	}
	@GetMapping("/button")
	public ModelAndView button(ModelMap model) {
		ModelAndView button=new ModelAndView("futsal/admin/button.html");
		return button;
	}
	@GetMapping("/typography")
	public ModelAndView typography(ModelMap model) {
		ModelAndView typography=new ModelAndView("futsal/admin/typography.html");
		return typography;
	}
	@GetMapping("/element")
	public ModelAndView element(ModelMap model) {
		ModelAndView element=new ModelAndView("futsal/admin/element.html");
		return element;
	}
}
