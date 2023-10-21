package com.futsal.web;


import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;





@Controller
@SpringBootApplication

public class FutsalWebLinksNewApplication{


	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;


	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World!";
	}

	
	public static void main(String[] args) {
		SpringApplication.run(FutsalWebLinksNewApplication.class, args);
	}

	/*
	 * To create a REST client with Spring, you need to create a RestTemplate
	 * instance. This class allows you to easily communicate with a REST API and
	 * serialize/deserialize the data to and from JSON.
	 */
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {

		return builder.setConnectTimeout(Duration.ofMillis(7000)).setReadTimeout(Duration.ofMillis(7000)).build();
	}


//	@Override
//	public void run(String... args) throws Exception {
//		String sql="SELECT * FROM U001";
//		List<LecoUserDetails> lecoUser= jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(LecoUserDetails.class));
//		
//		lecoUser.forEach(System.out :: println);
//	}

}
