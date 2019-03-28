package com.example.test.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;
    @Column(name = "username", nullable = false, unique = true)
    private String name;
    private String password;
    private String description;
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<UserRole> roles = new ArrayList<UserRole>();

    public User() {
    }

    public User(long id, String name, String password, String description) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.description = description;
    }

    public User(String name, String password, String description) {
        this.name = name;
        this.password = password;
        this.description = description;
    }

    public User(String name, String password, String description, List<UserRole> roles) {
        this.name = name;
        this.password = password;
        this.description = description;
        this.roles = roles;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public void addRole(UserRole userRole){
        this.roles.add(userRole);
    }

    public void addRole(String role){
        this.addRole(new UserRole(this,role));
    }

}
