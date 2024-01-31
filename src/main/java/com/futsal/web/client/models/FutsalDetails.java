package com.futsal.web.client.models;

import org.springframework.web.multipart.MultipartFile;

public class FutsalDetails {
	private int futsal_id;
	private String futsalName;
	private String futsalEmail;
	private String contactNo;
	private String password;
	private String image;
	
	
	public int getFutsal_id() {
		return futsal_id;
	}
	public void setFutsal_id(int futsal_id) {
		this.futsal_id = futsal_id;
	}
	public String getFutsalName() {
		return futsalName;
	}
	public void setFutsalName(String futsalName) {
		this.futsalName = futsalName;
	}
	public String getFutsalEmail() {
		return futsalEmail;
	}
	public void setFutsalEmail(String futsalEmail) {
		this.futsalEmail = futsalEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String string) {
		this.image = string;
	}
	
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	@Override
	public String toString() {
		return "FutsalDetails [futsalName=" + futsalName + ", futsalEmail=" + futsalEmail + ", contactNo=" + contactNo
				+ ", password=" + password + ", image=" + image + "]";
	}
	
	
	
}
