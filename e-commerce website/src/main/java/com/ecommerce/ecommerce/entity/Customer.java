package com.ecommerce.ecommerce.entity;

/*
import jakarta.persistence.*;

@Entity
@Table(name="customers")
public class Customer {

    // define fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;


    @Column(name="customerId")
    private int customerId;

    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Id
    @Column(name="userName")
    private String userName;

    @Column(name="password")
    private String password;
    @Column(name="phone")
    private String phone;

    @Column(name="email")
    private String email;

    @Column(name="city")
    private String city;
    @Column(name="address")
    private String address;

    @Column(name="houseNumber")
    private String houseNumber;

    @Column(name="zip")
    private int zip;

    @Column(name="numberCreditCard")
    private String numberCreditCard;

    @Column(name="creditCompanyName")
    private String creditCompanyName;

    @Column(name="validityMonthCreditCard")
    private String validityMonthCreditCard;

    @Column(name="validityYearCreditCard")
    private String validityYearCreditCard;

    // define constructors
    public Customer() {

    }

    public Customer(int customerId, String firstName, String lastName, String userName, String password, String phone, String email) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }

    // define getters/setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getNumberCreditCard() {
        return numberCreditCard;
    }

    public void setNumberCreditCard(String numberCreditCard) {
        this.numberCreditCard = numberCreditCard;
    }

    public String getCreditCompanyName() {
        return creditCompanyName;
    }

    public void setCreditCompanyName(String creditCompanyName) {
        this.creditCompanyName = creditCompanyName;
    }

    public String getValidityMonthCreditCard() {
        return validityMonthCreditCard;
    }

    public void setValidityMonthCreditCard(String validityMonthCreditCard) {
        this.validityMonthCreditCard = validityMonthCreditCard;
    }

    public String getValidityYearCreditCard() {
        return validityYearCreditCard;
    }

    public void setValidityYearCreditCard(String validityYearCreditCard) {
        this.validityYearCreditCard = validityYearCreditCard;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", zip=" + zip +
                ", numberCreditCard='" + numberCreditCard + '\'' +
                ", creditCompanyName='" + creditCompanyName + '\'' +
                ", validityMonthCreditCard='" + validityMonthCreditCard + '\'' +
                ", validityYearCreditCard='" + validityYearCreditCard + '\'' +
                '}';
    }
}
*/