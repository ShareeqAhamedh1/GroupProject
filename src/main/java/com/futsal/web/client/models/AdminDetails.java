package com.futsal.web.client.models;

public class AdminDetails {
	private String adminName;
	private String email;
	private String contactNo;
	private String password;
	private String rePassword;
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRePassword() {
		return rePassword;
	}
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
	@Override
	public String toString() {
		return "AdminDetails [adminName=" + adminName + ", email=" + email + ", contactNo=" + contactNo + ", password="
				+ password + ", rePassword=" + rePassword + "]";
	}
	
}
