package com.futsal.web.client.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.futsal.web.client.models.AdminDetails;
import com.futsal.web.client.models.BookingDetails;
import com.futsal.web.client.models.FutsalDetails;

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
	
	public static List<Map<String,Object>> addFutsal(FutsalDetails f_details){
		System.out.println("In adding Futsal");
		
		String sql="INSERT INTO futsaldb.futsaldetails (fname,email,contactno,address,state,password,image) VALUES (?,?,?,?,?,?,?)";
		int insertFutsal=jdbcTemplate.update(sql,f_details.getFutsalName(),f_details.getFutsalEmail(),f_details.getContactNo(),f_details.getAddress(),f_details.getState(),f_details.getPassword(),f_details.getImage() );
		System.out.println("Inserted "+insertFutsal +" rows into DB");
		return null;
	}
	
	public static List<Map<String,Object>> futsalDetails(){
//		System.out.println("Checking Email of "+f_details.getFutsalEmail());
		System.out.println("Getting Futsal details");
		String sql = "SELECT * FROM futsaldb.futsaldetails LIMIT 0,4";
		
		 List<Map<String, Object>> getDetailsFromDb = jdbcTemplate.queryForList(sql);
//		System.out.println("Checked and got "+checkedDataRows+" Rows from the admin DB");
		return getDetailsFromDb;
	}
	
	public static List<Map<String,Object>> futsalDetails2(){
//		System.out.println("Checking Email of "+f_details.getFutsalEmail());
		System.out.println("Getting Futsal details");
		String sql = "SELECT * FROM futsaldb.futsaldetails";
		
		 List<Map<String, Object>> getDetailsFromDb = jdbcTemplate.queryForList(sql);
//		System.out.println("Checked and got "+checkedDataRows+" Rows from the admin DB");
		return getDetailsFromDb;
	}
	
	public static List<Map<String,Object>> deleteFutsal(FutsalDetails f_details){
//		System.out.println("Checking Email of "+f_details.getFutsalEmail());
		System.out.println("Deleting Futsal details");
		String sql = "DELETE FROM futsaldb.futsaldetails WHERE futsalid LIKE ?";
		String sql2="DELETE FROM futsaldb.sportsdetails WHERE f_id LIKE ?";
				
		int deleteFutsal=jdbcTemplate.update(sql,f_details.getFutsal_id());
		int deleteSport=jdbcTemplate.update(sql2,f_details.getFutsal_id());
		System.out.println("Deleted "+deleteFutsal+" Rows from the futsaldetails DB");
		System.out.println("Deleted "+deleteSport+" Rows from the sportsdetails DB");
		return null;
	}
	
	public static List<Map<String,Object>> futsalDetails3(FutsalDetails f_details){
//		System.out.println("Checking Email of "+f_details.getFutsalEmail());
		
		String sql = "SELECT * FROM futsaldb.futsaldetails WHERE futsalid LIKE ?";
		
		 List<Map<String, Object>> getDetailsFromDb = jdbcTemplate.queryForList(sql,f_details.getFutsal_id());
//		System.out.println("Checked and got "+checkedDataRows+" Rows from the admin DB");
		return getDetailsFromDb;
	}
	
	public static List<Map<String, Object>> updateFutsal(FutsalDetails f_details) {
	    System.out.println("In updating Futsal");

	    // Corrected SQL query for UPDATE
	    String sql = "UPDATE futsaldb.futsaldetails SET fname=?, email=?, contactno=?, password=?, image=? WHERE futsalid=?";
	    
	    // Assuming futsalid is the primary key in your table
	    int updateFutsal = jdbcTemplate.update(sql, 
	            f_details.getFutsalName(),
	            f_details.getFutsalEmail(),
	            f_details.getContactNo(),
	            f_details.getPassword(),
	            f_details.getImage(),
	            f_details.getFutsal_id()); // Assuming you have a method getFutsalId in your FutsalDetails class

	    System.out.println("Updated " + updateFutsal + " rows in DB");
	    return null;
	}
	
	
	public static List<Map<String,Object>> getAllBookings(){
		String sql="SELECT * FROM futsaldb.bookingdetails ORDER BY bookingdetails.b_id DESC";
		
		List<Map<String,Object>> getBookings=jdbcTemplate.queryForList(sql);
		
		return getBookings;
	}
	
	public static List<Map<String,Object>> getBookingsForFutsal(FutsalDetails f_details){
		String sql="SELECT * FROM futsaldb.bookingdetails WHERE futsal_id LIKE ? ORDER BY bookingdetails.b_id";
		
		List<Map<String,Object>> getBookingDetails=jdbcTemplate.queryForList(sql,f_details.getFutsal_id());
		
		return getBookingDetails;
	}
	public static List<Map<String,Object>> updateBookingStatus(BookingDetails b_details){
		
		
		String sql="UPDATE futsaldb.bookingdetails SET status=? WHERE b_id=?";
		int updateBooking=jdbcTemplate.update(sql,b_details.getPayment(),b_details.getB_id());
		System.out.println("Updated " + updateBooking + " rows in DB");
		return null;
	}
	
	public static List<Map<String,Object>> cancelBooking(BookingDetails b_details){
		
		
		String sql="DELETE FROM futsaldb.bookingdetails WHERE b_id=?";
		int deleteBooking=jdbcTemplate.update(sql,b_details.getB_id());
		System.out.println("Deleted " + deleteBooking + " rows in DB");
		return null;
	}
	
	
	
	
	
}
