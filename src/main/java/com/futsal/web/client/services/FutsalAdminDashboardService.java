package com.futsal.web.client.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.futsal.web.client.models.AdminDetails;
import com.futsal.web.client.models.FutsalDetails;
import com.futsal.web.client.models.SportDetails;


@Service
public class FutsalAdminDashboardService {
	
private static JdbcTemplate jdbcTemplate;
	
	@Autowired
	public FutsalAdminDashboardService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate=jdbcTemplate;
	}
	
	public static List<Map<String,Object>> adminDetails(AdminDetails admin){
		System.out.println("Checking Email of "+admin.getEmail());
		System.out.println("Getting admin details");
		String sql = "SELECT * FROM futsaldb.futsaldetails WHERE email LIKE ?";
		
		 List<Map<String, Object>> getDetailsFromDb = jdbcTemplate.queryForList(sql, "%" + admin.getEmail());
//		System.out.println("Checked and got "+checkedDataRows+" Rows from the admin DB");
		return getDetailsFromDb;
	}
	
	public static List<Map<String,Object>> addSports(SportDetails s_details){
		System.out.println("In adding Sports");
		
		String sql="INSERT INTO futsaldb.sportsdetails (typeofsport,image,f_id) VALUES (?,?,?)";
		int insertFutsal=jdbcTemplate.update(sql,s_details.getTypeOfSports(),s_details.getImage(),s_details.getF_id());
		System.out.println("Inserted "+insertFutsal +" rows into DB");
		return null;
	}
	

	public static List<Map<String,Object>> sportsDetails2(SportDetails sports){
//		System.out.println("Checking Email of "+f_details.getFutsalEmail());
		System.out.println("Getting sports details");
		String sql = "SELECT * FROM futsaldb.sportsdetails WHERE f_id LIKE ?";
		
		 List<Map<String, Object>> getDetailsFromDb = jdbcTemplate.queryForList(sql,sports.getF_id());
//		System.out.println("Checked and got "+checkedDataRows+" Rows from the admin DB");
		return getDetailsFromDb;
	}
	
	public static List<Map<String,Object>> deleteSport(SportDetails s_details){
//		System.out.println("Checking Email of "+f_details.getFutsalEmail());
		System.out.println("Deleting sport details");
		String sql = "DELETE FROM futsaldb.sportsdetails WHERE s_id LIKE ?";
		
		int deleteFutsal=jdbcTemplate.update(sql,s_details.getS_id());
		System.out.println("Deleted "+deleteFutsal+" Rows from the futsaldetails DB");
		return null;
	}
	
	public static List<Map<String,Object>> sportsDetails(SportDetails sports){
//		System.out.println("Checking Email of "+f_details.getFutsalEmail());
		System.out.println("Getting sports details of "+sports.getS_id());
		String sql = "SELECT * FROM futsaldb.sportsdetails WHERE s_id LIKE ?";
		
		 List<Map<String, Object>> getDetailsFromDb = jdbcTemplate.queryForList(sql,sports.getS_id());
//		System.out.println("Checked and got "+checkedDataRows+" Rows from the admin DB");
		return getDetailsFromDb;
	}

}
