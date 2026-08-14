package com.restaurant.db;

import com.restaurant.enums.TableStatus;
import com.restaurant.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDatabase {
    // static ArrayLists لكافة الكيانات المطلوبة في المشروع
    public static List<Customer> customers = new ArrayList<>();
    public static List<User> staffMembers = new ArrayList<>();
    public static List<Table> tables = new ArrayList<>();
    public static List<MenuCategory> categories = new ArrayList<>();
    public static List<MenuItem> menuItems = new ArrayList<>();
    public static List<Order> orders = new ArrayList<>();

    static {
        seedDummyData();
    }

    public static void seedDummyData() {
        // 1. عميل تجريبي (Customer)
        Customer c1 = new Customer("C101", "customer1", "Pass@1234",
                LocalDate.of(2000, 1, 1), "01012345678", 500.0, 50, "None");
        customers.add(c1);

        // 2. طاولات تجريبية (Tables)
        Table t1 = new Table("T1", 2, "Indoor", TableStatus.AVAILABLE);
        Table t2 = new Table("T2", 4, "Outdoor", TableStatus.AVAILABLE);
        Table t3 = new Table("T3", 6, "VIP", TableStatus.RESERVED);
        tables.add(t1);
        tables.add(t2);
        tables.add(t3);

        // 3. أقسام الطعام (Menu Categories) - خاصة بـ NAME 3
        MenuCategory appetizers = new MenuCategory("CAT1", "Appetizers", "Starters & Snacks");
        MenuCategory mainCourse = new MenuCategory("CAT2", "Main Course", "Hearty Meals");
        MenuCategory beverages = new MenuCategory("CAT3", "Beverages", "Cold & Hot Drinks");
        categories.add(appetizers);
        categories.add(mainCourse);
        categories.add(beverages);

        // 4. عناصر قائمة الطعام (Menu Items) - خاصة بـ NAME 3
        MenuItem item1 = new MenuItem("M1", "Garlic Bread", 25.0, "Freshly baked with garlic butter", appetizers, true);
        MenuItem item2 = new MenuItem("M2", "Grilled Chicken Pasta", 120.0, "Creamy Alfredo sauce with grilled chicken", mainCourse, true);
        MenuItem item3 = new MenuItem("M3", "Fresh Mango Juice", 35.0, "100% fresh natural mango", beverages, true);
        MenuItem item4 = new MenuItem("M4", "Steak Medium Rare", 220.0, "Prime beef steak", mainCourse, false); // Out of stock example

        menuItems.add(item1);
        menuItems.add(item2);
        menuItems.add(item3);
        menuItems.add(item4);

        // 5. طلب تجريبي أول (Initial Order) - خاصة بـ NAME 3
        Order initialOrder = new Order("ORD101", c1, t1);
        initialOrder.addItem(new OrderItem(item1, 2, "Extra Crispy"));
        initialOrder.addItem(new OrderItem(item2, 1, "Extra Cheese"));
        orders.add(initialOrder);
    }
}