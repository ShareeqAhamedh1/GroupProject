package com.futsal.web.client.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.futsal.web.client.models.BookingDetails;
import com.futsal.web.client.models.CheckoutDetails;
import com.futsal.web.client.models.SportDetails;
import com.futsal.web.client.models.UserDetails;

@Service
public class FutsalServices {

	 private static JdbcTemplate jdbcTemplate;
	 
	 @Autowired
	    private PasswordEncoder passwordEncoder;
	 
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

		    String sql = "SELECT * FROM futsaldb.userdetails WHERE Email LIKE ?";
		    List<Map<String, Object>> getDetailsFromDb = jdbcTemplate.queryForList(sql, "%" + user.getAddress() + "%");

		    return getDetailsFromDb;
		}
	 
	 public static  List<Map<String,Object>> getBookingDetails(BookingDetails bookingDetails){
		 System.out.println("Im in Service class :"+bookingDetails.toString());
		 String sql = "INSERT INTO futsaldb.bookingDetails (b_name, b_sport, b_place, b_date, b_time,futsal_id) VALUES (?, ?, ?, ?, ?,?)";
		 int insertedDataRows = jdbcTemplate.update(sql, bookingDetails.getName(), bookingDetails.getSport(), bookingDetails.getPlace(), bookingDetails.getDate(), bookingDetails.getTime(),bookingDetails.getFutsal_id());
		 System.out.println("inserted "+insertedDataRows+" Rows to the table");
		 return null;
	 }
	 
	 public static List<Map<String,Object>> getBooking(BookingDetails b_details){
		    String sql = "SELECT * FROM futsaldb.bookingDetails WHERE b_place = ? AND b_date = ? AND b_time = ? AND futsal_id = ?";
		    
		    List<Map<String,Object>> getDetails = jdbcTemplate.queryForList(sql, b_details.getPlace(), b_details.getDate(), b_details.getTime(), b_details.getFutsal_id());

		    return getDetails;
		}
	 
	 public static int addCheckoutDetails(CheckoutDetails checkoutDetails) {
		 String sql = "INSERT INTO checkoutDetails "
                 + "(full_name, email, address, city, state, zip, name_on_card, credit_card, expMonthYear, cvv,b_id) "
                 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

      int getDetailsFromDb = jdbcTemplate.update(sql, checkoutDetails.getFirstname(), checkoutDetails.getEmail(),
                          checkoutDetails.getAddress(), checkoutDetails.getCity(),
                          checkoutDetails.getState(), checkoutDetails.getZip(), checkoutDetails.getCardname(),
                          checkoutDetails.getCardnumber(), checkoutDetails.getExpmonthYear(), checkoutDetails.getCvv(),checkoutDetails.getBooking_id());


		 
		 return (Integer) getDetailsFromDb;
	 }
	 
	 public static List<Map<String,Object>> getSports(){
		 
		 
		 String sql ="SELECT t.typeofsport, t.frequency,(SELECT image FROM sportsdetails WHERE typeofsport = t.typeofsport LIMIT 1) AS first_image FROM (SELECT typeofsport, COUNT(typeofsport) AS frequency FROM sportsdetails GROUP BY typeofsport ORDER BY frequency DESC LIMIT 4 ) AS t";
		 List<Map<String,Object>> getSportDetails=jdbcTemplate.queryForList(sql);
		 return getSportDetails;
	 }
	 
	 

	 

}
