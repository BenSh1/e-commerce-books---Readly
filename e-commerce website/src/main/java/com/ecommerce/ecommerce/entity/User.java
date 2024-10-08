package com.ecommerce.ecommerce.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "first_name")
    private String firstName;


    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "streetAddress")
    private String streetAddress;

    @Column(name = "apartmentNumber")
    private String apartmentNumber;

    @Column(name = "zipCode")
    private String zipCode;

    @Column(name = "creditCardNumber")
    private String creditCardNumber;

    @Column(name = "creditCardCompany")
    private String creditCardCompany;

    @Column(name = "cardExpiryMonth")
    private Integer  cardExpiryMonth;

    @Column(name = "cardExpiryYear")
    private Integer cardExpiryYear;



    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    //a user can have multiple cart items
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<CartItems> cartItems;

    //A user can have multiple orders on their behalf
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<Order> orders;

    /**
     * Default constructor for the User class.
     * This constructor initializes a new User object without setting any attributes.
     */
    public User() {
    }

    /**
     * Parameterized constructor for the User class.
     * This constructor initializes a new User object with the specified attributes.
     *
     * @param userName the username of the user
     * @param password the password of the user
     * @param enabled  the enabled status of the user (true if the user is enabled, false otherwise)
     */
    public User(String userName, String password, boolean enabled) {
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
    }


    /**
     * Parameterized constructor for the User class.
     * This constructor initializes a new User object with the specified attributes.
     *
     * @param userName the username of the user
     * @param password the password of the user
     * @param enabled  the enabled status of the user (true if the user is enabled, false otherwise)
     * @param roles    the roles assigned to the user
     */
    public User(String userName, String password, boolean enabled,
                Collection<Role> roles) {
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }

    /**
     * Parameterized constructor for the User class.
     * This constructor initializes a new User object with the specified attributes.
     *
     * @param userName           the username of the user
     * @param password           the password of the user
     * @param firstName          the first name of the user
     * @param enabled            the enabled status of the user (true if the user is enabled, false otherwise)
     * @param lastName           the last name of the user
     * @param phone              the phone number of the user
     * @param email              the email address of the user
     * @param country            the country of the user
     * @param city               the city of the user
     * @param streetAddress      the street address of the user
     * @param apartmentNumber    the apartment number of the user
     * @param zipCode            the zip code of the user
     * @param creditCardNumber   the credit card number of the user
     * @param creditCardCompany  the credit card company of the user
     * @param cardExpiryMonth    the expiry month of the credit card
     * @param cardExpiryYear     the expiry year of the credit card
     */
    public User(String userName, String password, String firstName, boolean enabled,
                String lastName, String phone, String email, String country, String city,
                String streetAddress, String apartmentNumber, String zipCode,
                String creditCardNumber, String creditCardCompany, int cardExpiryMonth,
                int cardExpiryYear) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.enabled = enabled;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.country = country;
        this.city = city;
        this.streetAddress = streetAddress;
        this.apartmentNumber = apartmentNumber;
        this.zipCode = zipCode;
        this.creditCardNumber = creditCardNumber;
        this.creditCardCompany = creditCardCompany;
        this.cardExpiryMonth = cardExpiryMonth;
        this.cardExpiryYear = cardExpiryYear;

    }


    /**
     * Getters + Setters
     *
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public Integer getCardExpiryMonth() {
        return cardExpiryMonth;
    }

    public void setCardExpiryMonth(Integer cardExpiryMonth) {
        this.cardExpiryMonth = cardExpiryMonth;
    }

    public Integer getCardExpiryYear() {
        return cardExpiryYear;
    }

    public void setCardExpiryYear(Integer cardExpiryYear) {
        this.cardExpiryYear = cardExpiryYear;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    /**
     * Returns a string representation of the User object.
     *
     * @return a string representation of the User object.
     */

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", apartmentNumber='" + apartmentNumber + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", creditCardNumber='" + creditCardNumber + '\'' +
                ", creditCardCompany='" + creditCardCompany + '\'' +
                ", cardExpiryMonth=" + cardExpiryMonth +
                ", cardExpiryYear=" + cardExpiryYear +
                '}';
    }


}

