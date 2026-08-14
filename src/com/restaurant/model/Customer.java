package com.restaurant.model;

import com.restaurant.enums.Role;
import java.time.LocalDate;

public class Customer extends User {
    private String phoneNumber;
    private double balance;
    private int loyaltyPoints;
    private String dietaryPreferences;

    public Customer(String id, String username, String password, LocalDate dateOfBirth,
                    String phoneNumber, double balance, int loyaltyPoints, String dietaryPreferences) {
        super(id, username, password, dateOfBirth, Role.CUSTOMER);
        this.phoneNumber = phoneNumber;
        this.balance = balance;
        this.loyaltyPoints = loyaltyPoints;
        this.dietaryPreferences = dietaryPreferences;
    }

    public String getPhoneNumber() { return phoneNumber; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public int getLoyaltyPoints() { return loyaltyPoints; }
    public void addLoyaltyPoints(int points) { this.loyaltyPoints += points; }
    public String getDietaryPreferences() { return dietaryPreferences; }
}