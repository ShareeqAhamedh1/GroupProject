package com.futsal.web.client.models;

public class BookingDetails {

	private String name;
    private String sport;
    private String place;
    private String date;
    private String time;
    private int futsal_id;

    public BookingDetails(String name, String sport, String place, String date, String time,int futsal_id) {
        this.name = name;
        this.sport = sport;
        this.place = place;
        this.date = date;
        this.time = time;
        this.futsal_id=futsal_id;
    }
    
    
	public int getFutsal_id() {
		return futsal_id;
	}


	public void setFutsal_id(int futsal_id) {
		this.futsal_id = futsal_id;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSport() {
		return sport;
	}
	public void setSport(String sport) {
		this.sport = sport;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "BookingDetails [name=" + name + ", sport=" + sport + ", place=" + place + ", date=" + date + ", time="
				+ time + "]";
	}
	
	
	

}