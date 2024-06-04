package com.ecommerce.ecommerce.Data_Transfer_Object;

public class Customer {
    private int customerId;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String phone;
    private String email;

    private String city;
    private String address;
    private String houseNumber;
    private int zip;

    private String numberCreditCard;
    private String creditCompanyName;
    private String validityMonthCreditCard;
    private String validityYearCreditCard;

    public Customer() {

    }

    public int getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public int getZip() {
        return zip;
    }

    public String getNumberCreditCard() {
        return numberCreditCard;
    }

    public String getCreditCompanyName() {
        return creditCompanyName;
    }

    public String getValidityMonthCreditCard() {
        return validityMonthCreditCard;
    }

    public String getValidityYearCreditCard() {
        return validityYearCreditCard;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public void setNumberCreditCard(String numberCreditCard) {
        this.numberCreditCard = numberCreditCard;
    }

    public void setCreditCompanyName(String creditCompanyName) {
        this.creditCompanyName = creditCompanyName;
    }

    public void setValidityMonthCreditCard(String validityMonthCreditCard) {
        this.validityMonthCreditCard = validityMonthCreditCard;
    }

    public void setValidityYearCreditCard(String validityYearCreditCard) {
        this.validityYearCreditCard = validityYearCreditCard;
    }
}
