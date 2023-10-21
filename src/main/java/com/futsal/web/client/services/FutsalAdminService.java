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
		System.out.println("Im in AdminService class with the data: "+admin.toString());
		String sql="INSERT INTO futsaldb.admindetails (adminname,email,contactno,password,repassword) VALUES (?,?,?,?,?)";
		int insertedDataRows=jdbcTemplate.update(sql,admin.getAdminName(),admin.getEmail(),admin.getContactNo(),admin.getPassword(),admin.getRePassword());
		System.out.println("inserted "+insertedDataRows+" admin datas into Admin Details in DB");
		return null;
	}
	
	
	public static List<Map<String,Object>> adminDetails(AdminDetails admin){
		System.out.println("Checking Email of "+admin.getEmail());
		System.out.println("Getting admin details");
		String sql = "SELECT * FROM futsaldb.admindetails WHERE email LIKE ?";
		
		 List<Map<String, Object>> getDetailsFromDb = jdbcTemplate.queryForList(sql, "%" + admin.getEmail());
//		System.out.println("Checked and got "+checkedDataRows+" Rows from the admin DB");
		return getDetailsFromDb;
	}
}
