package com.ecommerce.ecommerce.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

public class WebUser {

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String userName;

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String password;

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String firstName;

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String lastName;

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	@Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
	private String email;

	@NotNull(message = "is required")
	@Size(min = 10, message = "Please enter a valid phone number. Valid formats of phone numbers include at least 10 digits ")
	@Pattern(regexp="^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$" , message = "Please enter a valid phone number. Valid formats include (123) 456-7890, 123.456.7890, or 123 456 7890.")
	private String phone;

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String country;


	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String city;

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String streetAddress;

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String apartmentNumber;

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String zipCode;


    @NotEmpty(message = "Credit card number is required")
    @Pattern(regexp = "\\d{16}", message = "Credit card number must be 16 digits")
    private String creditCardNumber;

	@NotEmpty(message = "Credit card company is required")
	private String creditCardCompany;


	@Min(value = 1, message = "Month must be between 1 and 12")
	@Max(value = 12, message = "Month must be between 1 and 12")
	private int cardExpiryMonth;

	@Min(value = 2023, message = "Year must be greater than or equal to the current year")
	private int cardExpiryYear;




	public WebUser() {

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getApartmentNumber() {
		return apartmentNumber;
	}

	public void setApartmentNumber(String apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public String getCreditCardCompany() {
		return creditCardCompany;
	}

	public void setCreditCardCompany(String creditCardCompany) {
		this.creditCardCompany = creditCardCompany;
	}

	public int getCardExpiryMonth() {
		return cardExpiryMonth;
	}

	public void setCardExpiryMonth(int cardExpiryMonth) {
		this.cardExpiryMonth = cardExpiryMonth;
	}

	public int getCardExpiryYear() {
		return cardExpiryYear;
	}

	public void setCardExpiryYear(int cardExpiryYear) {
		this.cardExpiryYear = cardExpiryYear;
	}
}


