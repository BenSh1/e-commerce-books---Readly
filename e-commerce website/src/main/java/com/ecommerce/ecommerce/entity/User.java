package com.ecommerce.ecommerce.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /*
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")

     */
    @Column(name = "username")
    private String userName;

    /*
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")

     */
    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;


/*
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")

 */
    @Column(name = "first_name")
    private String firstName;

    /*
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")

     */
    @Column(name = "last_name")
    private String lastName;

    /*
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    @Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")

     */
    @Column(name = "email")
    private String email;

    /*
    @NotNull(message = "is required")
    @Size(min = 10, message = "Please enter a valid phone number. Valid formats of phone numbers include at least 10 digits ")
    @Pattern(regexp="^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$" , message = "Please enter a valid phone number. Valid formats include (123) 456-7890, 123.456.7890, or 123 456 7890.")
    */
    @Column(name = "phone")
    private String phone;

    /*
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")

     */
    @Column(name = "country")
    private String country;

    /*
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")

     */
    @Column(name = "city")
    private String city;

    /*
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")

     */
    @Column(name = "streetAddress")
    private String streetAddress;

    /*
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")

     */
    @Column(name = "apartmentNumber")
    private String apartmentNumber;


    /*
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")

     */
    @Column(name = "zipCode")
    private String zipCode;

    /*
    @NotEmpty(message = "Credit card number is required")
    @Pattern(regexp = "\\d{16}", message = "Credit card number must be 16 digits")

     */
    @Column(name = "creditCardNumber")
    private String creditCardNumber;

    /*
    @NotEmpty(message = "Credit card company is required")

     */
    @Column(name = "creditCardCompany")
    private String creditCardCompany;

    /*
    @Min(value = 1, message = "Month must be between 1 and 12")
    @Max(value = 12, message = "Month must be between 1 and 12")

     */
    @Column(name = "cardExpiryMonth")
    private Integer  cardExpiryMonth;

    /*
    @Min(value = 2023, message = "Year must be greater than or equal to the current year")

     */
    @Column(name = "cardExpiryYear")
    private Integer cardExpiryYear;



    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    //private Set<Role> roles = new HashSet<>();
    private Collection<Role> roles;

    //a user can have multiple cart items
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<CartItems> cartItems;


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<Order> orders;









    /*
    //a user can have multiple orders
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<Order> orders;

     */

/*
    @OneToOne(mappedBy = "")
    @JoinColumn(name = "user_id")
    private Collection<Book> booksInCart;

 */


    public User() {
    }

    public User(String userName, String password, boolean enabled) {
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
    }

    public User(String userName, String password, boolean enabled,
                Collection<Role> roles) {
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }


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

    }

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




    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", apartmentNumber='" + apartmentNumber + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", roles='" + roles + '\'' +
                '}';
                /*
                ", creditCardNumber='" + creditCardNumber + '\'' +
                ", creditCardCompany='" + creditCardCompany + '\'' +
                ", cardExpiryMonth=" + cardExpiryMonth +
                ", cardExpiryYear=" + cardExpiryYear +

                 */

    }
}

