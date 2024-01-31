package com.futsal.web.client.models;

public class SportDetails {

	private String typeOfSports;
	private String image;
	private int s_id;
	private int f_id;
	public String getTypeOfSports() {
		return typeOfSports;
	}
	public void setTypeOfSports(String typeOfSports) {
		this.typeOfSports = typeOfSports;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getS_id() {
		return s_id;
	}
	public void setS_id(int s_id) {
		this.s_id = s_id;
	}
	public int getF_id() {
		return f_id;
	}
	public void setF_id(int f_id) {
		this.f_id = f_id;
	}
	@Override
	public String toString() {
		return "SportDetails [typeOfSports=" + typeOfSports + ", image=" + image + ", s_id=" + s_id + ", f_id=" + f_id
				+ "]";
	}
	
	
	
}
