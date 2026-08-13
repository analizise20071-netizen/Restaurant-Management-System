package com.restaurant.testing;

import com.restaurant.billing.CustomerAccount;
import com.restaurant.billing.Invoice;
import com.restaurant.billing.PaymentProcessor;
import com.restaurant.enums.PaymentMethod;

public class BillingTest {

    public static void main(String[] args) {

        testCashPayment();
        testCreditCardPayment();
        testBalancePayment();
        testLoyaltyPointsPayment();

        testInsufficientBalance();
        testInsufficientLoyaltyPoints();

        testInvalidInvoice();
        testInvalidCustomerAccount();

        testAlreadyPaidInvoice();
        testInvalidPaymentMethod();
        testNullInvoice();
        testNullCustomerAccount();
    }

    // -------------------------
    // 1. CASH PAYMENT
    // -------------------------
    private static void testCashPayment() {

        PaymentProcessor processor = new PaymentProcessor();

        Invoice invoice =
                new Invoice("INV001", "ORD001", 100.0);

        CustomerAccount account =
                new CustomerAccount(0.0, 0);

        boolean result =
                processor.processPayment(
                        invoice,
                        account,
                        PaymentMethod.CASH
                );

        System.out.println("===== CASH =====");
        System.out.println("Payment successful: " + result);
        System.out.println("Paid: " + invoice.isPaid());
        System.out.println("Method: " + invoice.getPaymentMethod());
    }

    // -------------------------
    // 2. CREDIT CARD PAYMENT
    // -------------------------
    private static void testCreditCardPayment() {

        PaymentProcessor processor = new PaymentProcessor();

        Invoice invoice =
                new Invoice("INV002", "ORD002", 200.0);

        CustomerAccount account =
                new CustomerAccount(0.0, 0);

        boolean result =
                processor.processPayment(
                        invoice,
                        account,
                        PaymentMethod.CREDIT_CARD
                );

        System.out.println("\n===== CREDIT CARD =====");
        System.out.println("Payment successful: " + result);
        System.out.println("Paid: " + invoice.isPaid());
        System.out.println("Method: " + invoice.getPaymentMethod());
    }

    // -------------------------
    // 3. BALANCE PAYMENT
    // -------------------------
    private static void testBalancePayment() {

        PaymentProcessor processor = new PaymentProcessor();

        Invoice invoice =
                new Invoice("INV003", "ORD003", 100.0);

        CustomerAccount account =
                new CustomerAccount(500.0, 0);

        boolean result =
                processor.processPayment(
                        invoice,
                        account,
                        PaymentMethod.BALANCE
                );

        System.out.println("\n===== BALANCE =====");
        System.out.println("Payment successful: " + result);
        System.out.println("Paid: " + invoice.isPaid());
        System.out.println("Method: " + invoice.getPaymentMethod());
        System.out.println("Remaining balance: "
                + account.getBalance());
    }

    // -------------------------
    // 4. LOYALTY POINTS PAYMENT
    // -------------------------
    private static void testLoyaltyPointsPayment() {

        PaymentProcessor processor = new PaymentProcessor();

        Invoice invoice =
                new Invoice("INV004", "ORD004", 100.0);

        CustomerAccount account =
                new CustomerAccount(0.0, 200);

        boolean result =
                processor.processPayment(
                        invoice,
                        account,
                        PaymentMethod.LOYALTY_POINTS
                );

        System.out.println("\n===== LOYALTY POINTS =====");
        System.out.println("Payment successful: " + result);
        System.out.println("Paid: " + invoice.isPaid());
        System.out.println("Method: "
                + invoice.getPaymentMethod());
        System.out.println("Remaining points: "
                + account.getLoyaltyPoints());
    }

    // -------------------------
    // 5. INSUFFICIENT BALANCE
    // -------------------------
    private static void testInsufficientBalance() {

        PaymentProcessor processor = new PaymentProcessor();

        Invoice invoice =
                new Invoice("INV005", "ORD005", 100.0);

        CustomerAccount account =
                new CustomerAccount(50.0, 0);

        boolean result =
                processor.processPayment(
                        invoice,
                        account,
                        PaymentMethod.BALANCE
                );

        System.out.println("\n===== INSUFFICIENT BALANCE =====");
        System.out.println("Payment successful: " + result);
        System.out.println("Paid: " + invoice.isPaid());
        System.out.println("Remaining balance: "
                + account.getBalance());
    }

    // -------------------------
    // 6. INSUFFICIENT LOYALTY POINTS
    // -------------------------
    private static void testInsufficientLoyaltyPoints() {

        PaymentProcessor processor = new PaymentProcessor();

        Invoice invoice =
                new Invoice("INV006", "ORD006", 100.0);

        CustomerAccount account =
                new CustomerAccount(0.0, 50);

        boolean result =
                processor.processPayment(
                        invoice,
                        account,
                        PaymentMethod.LOYALTY_POINTS
                );

        System.out.println("\n===== INSUFFICIENT LOYALTY POINTS =====");
        System.out.println("Payment successful: " + result);
        System.out.println("Paid: " + invoice.isPaid());
        System.out.println("Remaining points: "
                + account.getLoyaltyPoints());
    }

    // -------------------------
    // 7. INVALID INVOICE
    // -------------------------
    private static void testInvalidInvoice() {

        try {

            Invoice invoice =
                    new Invoice("INV007", "ORD007", -100.0);

            System.out.println("Invalid invoice was created.");

        } catch (IllegalArgumentException e) {

            System.out.println("\n===== INVALID INVOICE =====");
            System.out.println("Error: " + e.getMessage());
        }
    }

    // -------------------------
    // 8. INVALID CUSTOMER ACCOUNT
    // -------------------------
    private static void testInvalidCustomerAccount() {

        try {

            CustomerAccount account =
                    new CustomerAccount(-500.0, 100);

            System.out.println("Invalid account was created.");

        } catch (IllegalArgumentException e) {

            System.out.println("\n===== INVALID CUSTOMER ACCOUNT =====");
            System.out.println("Error: " + e.getMessage());
        }
    }

    // -------------------------
    // 9. PAY ALREADY PAID INVOICE
    // -------------------------
    private static void testAlreadyPaidInvoice() {

        PaymentProcessor processor = new PaymentProcessor();

        Invoice invoice =
                new Invoice("INV009", "ORD009", 100.0);

        CustomerAccount account =
                new CustomerAccount(500.0, 0);

        boolean firstPayment =
                processor.processPayment(
                        invoice,
                        account,
                        PaymentMethod.BALANCE
                );

        boolean secondPayment =
                processor.processPayment(
                        invoice,
                        account,
                        PaymentMethod.BALANCE
                );

        boolean thirdPayment =
                processor.processPayment(
                        invoice,
                        account,
                        PaymentMethod.BALANCE
                );

        System.out.println("\n===== PAY ALREADY PAID INVOICE =====");
        System.out.println("First payment: " + firstPayment);
        System.out.println("Second payment: " + secondPayment);
        System.out.println("Third payment: " + thirdPayment);
        System.out.println("Remaining balance: "
                + account.getBalance());
    }

    // -------------------------
    // 10. INVALID PAYMENT METHOD
    // -------------------------
    private static void testInvalidPaymentMethod() {

        PaymentProcessor processor = new PaymentProcessor();

        try {

            Invoice invoice =
                    new Invoice("INV010", "ORD010", 100.0);

            CustomerAccount account =
                    new CustomerAccount(500.0, 0);

            processor.processPayment(
                    invoice,
                    account,
                    null
            );

            System.out.println("Invalid payment method was accepted.");

        } catch (IllegalArgumentException e) {

            System.out.println("\n===== INVALID PAYMENT METHOD =====");
            System.out.println("Error: " + e.getMessage());
        }
    }

    // -------------------------
    // 11. NULL INVOICE
    // -------------------------
    private static void testNullInvoice() {

        PaymentProcessor processor = new PaymentProcessor();

        CustomerAccount account =
                new CustomerAccount(500.0, 0);

        try {

            processor.processPayment(
                    null,
                    account,
                    PaymentMethod.CASH
            );

            System.out.println("Null invoice was accepted.");

        } catch (IllegalArgumentException e) {

            System.out.println("\n===== NULL INVOICE =====");
            System.out.println("Error: " + e.getMessage());
        }
    }

    // -------------------------
    // 12. NULL CUSTOMER ACCOUNT
    // -------------------------
    private static void testNullCustomerAccount() {

        PaymentProcessor processor = new PaymentProcessor();

        Invoice invoice =
                new Invoice("INV012", "ORD012", 100.0);

        try {

            processor.processPayment(
                    invoice,
                    null,
                    PaymentMethod.BALANCE
            );

            System.out.println("Null customer account was accepted.");

        } catch (IllegalArgumentException e) {

            System.out.println("\n===== NULL CUSTOMER ACCOUNT =====");
            System.out.println("Error: " + e.getMessage());
        }
    }
}