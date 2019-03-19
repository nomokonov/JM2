package model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "username", unique = true)
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "description")
    private String description;
    private String role;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
