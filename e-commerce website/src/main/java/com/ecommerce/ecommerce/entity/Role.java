package com.ecommerce.ecommerce.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();


    /**
     * Default constructor for the Role class.
     * This constructor initializes a new Role object without setting any attributes.
     */
    public Role() {
    }

    /**
     * Parameterized constructor for the Role class.
     * This constructor initializes a new Role object with the specified attribute.
     *
     * @param name       the name of the role
     */
    public Role(String name) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a string representation of the Role object.
     *
     * @return a string representation of the Role object.
     */
    @Override
    public String toString() {
        return "Role{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}



