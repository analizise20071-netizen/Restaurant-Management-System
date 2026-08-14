package com.restaurant.model;

import com.restaurant.enums.Role;
import java.time.LocalDate;

public abstract class User {
    protected String id;
    protected String username;
    protected String password;
    protected LocalDate dateOfBirth;
    protected Role role;

    public User(String id, String username, String password, LocalDate dateOfBirth, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
    }

    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public Role getRole() { return role; }
}