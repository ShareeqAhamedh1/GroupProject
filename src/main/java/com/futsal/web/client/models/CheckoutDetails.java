package com.futsal.web.client.models;


public class CheckoutDetails {
    private String firstname;
    private String email;
    private String address;
    private String city;
    private String state;
    private int zip;
    private String acceptedCards;
    private String cardname;
    private String cardnumber;
    private String expMonthYear;
//    private String expyear;
    private int booking_id;
    private int cvv;

    // Constructors, getters, and setters

    public CheckoutDetails() {
        // Default constructor
    }

    public CheckoutDetails(String firstname, String email, String address, String city, String state,
                           int zip, String acceptedCards, String cardname, String cardnumber,
                           String expMonthYear, int cvv,int booking_id) {
        this.firstname = firstname;
        this.email = email;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.acceptedCards = acceptedCards;
        this.cardname = cardname;
        this.cardnumber = cardnumber;
        this.expMonthYear = expMonthYear;
        this.booking_id=booking_id;
//        this.expyear = expyear;
        this.cvv = cvv;
    }
    // Getters and Setters for each field

    
    
	public String getFirstname() {
		return firstname;
	}

	public int getBooking_id() {
		return booking_id;
	}

	public void setBooking_id(int booking_id) {
		this.booking_id = booking_id;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public String getAcceptedCards() {
		return acceptedCards;
	}

	public void setAcceptedCards(String acceptedCards) {
		this.acceptedCards = acceptedCards;
	}

	public String getCardname() {
		return cardname;
	}

	public void setCardname(String cardname) {
		this.cardname = cardname;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getExpmonthYear() {
		return expMonthYear;
	}

	public void setExpmonthYear(String expMonthYear) {
		this.expMonthYear = expMonthYear;
	}

//	public String getExpyear() {
//		return expyear;
//	}
//
//	public void setExpyear(String expyear) {
//		this.expyear = expyear;
//	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	@Override
	public String toString() {
		return "CheckoutDetails [firstname=" + firstname + ", email=" + email + ", address=" + address + ", city="
				+ city + ", state=" + state + ", zip=" + zip + ", acceptedCards=" + acceptedCards + ", cardname="
				+ cardname + ", cardnumber=" + cardnumber + ", expMonthYear=" + expMonthYear + ", cvv=" + cvv + "]";
	}

	
}