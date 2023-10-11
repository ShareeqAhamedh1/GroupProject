package com.futsal.web.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("SuperAdminDashboard")
public class futsalSuperAdmin {

	


		@GetMapping("/")
		@ResponseBody
		String home() {
			return "This is SuperAdmin";
		}
		
		@GetMapping("/home")
		public ModelAndView home(ModelMap model) {
			ModelAndView home=new ModelAndView("futsal/SuperAdmin/index.html");
			return home;
		}
		@GetMapping("/widget")
		public ModelAndView widget(ModelMap model) {
			ModelAndView widget=new ModelAndView("futsal/SuperAdmin/widget.html");
			return widget;
		}
		@GetMapping("/form")
		public ModelAndView form(ModelMap model) {
			ModelAndView form=new ModelAndView("futsal/SuperAdmin/form.html");
			return form;
		}
		@GetMapping("/table")
		public ModelAndView table(ModelMap model) {
			ModelAndView table=new ModelAndView("futsal/SuperAdmin/table.html");
			return table;
		}
		@GetMapping("/chart")
		public ModelAndView chart(ModelMap model) {
			ModelAndView chart=new ModelAndView("futsal/SuperAdmin/chart.html");
			return chart;
		}
		@GetMapping("/signin")
		public ModelAndView signin(ModelMap model) {
			ModelAndView signin=new ModelAndView("futsal/SuperAdmin/signin.html");
			return signin;
		}
		@GetMapping("/signup")
		public ModelAndView signup(ModelMap model) {
			ModelAndView signup=new ModelAndView("futsal/SuperAdmin/signup.html");
			return signup;
		}
		
		@GetMapping("/404")
		public ModelAndView error(ModelMap model) {
			ModelAndView error=new ModelAndView("futsal/SuperAdmin/404.html");
			return error;
		}
		
		@GetMapping("/blank")
		public ModelAndView blank(ModelMap model) {
			ModelAndView blank=new ModelAndView("futsal/SuperAdmin/blank.html");
			return blank;
		}
		@GetMapping("/button")
		public ModelAndView button(ModelMap model) {
			ModelAndView button=new ModelAndView("futsal/SuperAdmin/button.html");
			return button;
		}
		@GetMapping("/typography")
		public ModelAndView typography(ModelMap model) {
			ModelAndView typography=new ModelAndView("futsal/SuperAdmin/typography.html");
			return typography;
		}
		@GetMapping("/element")
		public ModelAndView element(ModelMap model) {
			ModelAndView element=new ModelAndView("futsal/SuperAdmin/element.html");
			return element;
	
		}
	}


