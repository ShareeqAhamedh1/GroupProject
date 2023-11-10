package com.futsal.web.client.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.futsal.web.client.models.UserDetails;

@Service
public class FutsalServices {

	 private static JdbcTemplate jdbcTemplate;
	 
	 @Autowired
	 public FutsalServices(JdbcTemplate jdbcTemplate) {
		 this.jdbcTemplate=jdbcTemplate;
	 }
	 
	 
	 
	 public static  List<Map<String,Object>> getUserDetails(UserDetails user){
		 System.out.println("Im in Service class :"+user.toString());
		 String sql="INSERT INTO futsaldb.userdetails (UserName,FirstName,LastName,Email,ContactNo,Password,RePassword) VALUES (?, ?, ?, ?, ?, ?, ?)";
		 int insertedDataRows=jdbcTemplate.update(sql,user.getUserName(),user.getFirstName(),user.getLastName(),user.getAddress(),user.getContactNo(),user.getPassword(),user.getRePassword());
		 
		 System.out.println("inserted "+insertedDataRows+" Rows to the table");
		 return null;
		 
	 }
	 
	 public static List<Map<String, Object>> validateUser(UserDetails user) {
		    System.out.println("Searching User In ValidateUserService: " + user.getAddress());

		    String sql = "SELECT * FROM futsaldb.userdetails WHERE Email LIKE ?";
		    
		    List<Map<String, Object>> userDetailsFromDb = jdbcTemplate.queryForList(sql, "%" + user.getAddress() + "%");

		    // Check if userDetailsFromDb is empty, return null if true
		    return userDetailsFromDb.isEmpty() ? null : userDetailsFromDb;
		}

	 
	 public static List<Map<String, Object>> sendUserDetails(UserDetails user) {
		    System.out.println("Searching " + user.getAddress() + " in DB");

		    String sql = "SELECT * FROM futsaldb.userdetails WHERE Email LIKE ? AND Password = ?";
		    List<Map<String, Object>> getDetailsFromDb = jdbcTemplate.queryForList(sql, "%" + user.getAddress() + "%", user.getPassword());

		    return getDetailsFromDb;
		}
}
