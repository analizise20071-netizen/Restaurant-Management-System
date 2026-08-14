package com.restaurant.interfaces;

import com.restaurant.enums.PaymentMethod;

public interface Payable {
    double calculateTotal();
    boolean processPayment(PaymentMethod method, double amount);
}