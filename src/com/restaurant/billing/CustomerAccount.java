package com.restaurant.billing;

public class CustomerAccount {

    private double balance;
    private int loyaltyPoints;

    public CustomerAccount(double balance, int loyaltyPoints) {
        this.balance = balance;
        this.loyaltyPoints = loyaltyPoints;
    }

    public double getBalance() {
        return balance;
    }

    public boolean deductBalance(double amount) {

        if (amount <= 0) {
            return false;
        }

        if (amount > balance) {
            return false;
        }

        balance -= amount;
        return true;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public boolean deductLoyaltyPoints(int points) {

        if (points <= 0) {
            return false;
        }

        if (points > loyaltyPoints) {
            return false;
        }

        loyaltyPoints -= points;
        return true;
    }
}