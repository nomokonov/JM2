package com.example.test.model;

import javax.persistence.*;

@Entity
public class UserRole {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    String role;

    public UserRole() {
    }

    public UserRole(User user, String role){
        this.user = user;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
