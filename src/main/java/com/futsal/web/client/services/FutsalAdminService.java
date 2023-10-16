package com.futsal.web.client.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.futsal.web.client.models.AdminDetails;

@Service
public class FutsalAdminService {

	private static JdbcTemplate jdbcTemplate;
	
	@Autowired
	public FutsalAdminService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate=jdbcTemplate;
	}
	
	
	public static List<Map<String, Object>> getAdminDetails(AdminDetails admin){
		System.out.println();
		return null;
	}
}
