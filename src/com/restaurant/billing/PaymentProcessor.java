package com.restaurant.billing;

import com.restaurant.enums.PaymentMethod;

public class PaymentProcessor {

    private static final int POINTS_PER_EGP = 1;

    public boolean processPayment(
            Invoice invoice,
            CustomerAccount account,
            PaymentMethod method) {

        boolean successful = false;

        if (method == PaymentMethod.CASH) {
            successful = true;
        }

        else if (method == PaymentMethod.CREDIT_CARD) {
            successful = true;
        }

        else if (method == PaymentMethod.BALANCE) {
            successful = account.deductBalance(invoice.calculateTotal());
        }

        else if (method == PaymentMethod.LOYALTY_POINTS) {

            int requiredPoints =
                    (int) Math.ceil(
                            invoice.calculateTotal() * POINTS_PER_EGP
                    );

            successful = account.deductLoyaltyPoints(requiredPoints);
        }

        if (successful) {
            invoice.markAsPaid(method);
        }

        return successful;
    }
}