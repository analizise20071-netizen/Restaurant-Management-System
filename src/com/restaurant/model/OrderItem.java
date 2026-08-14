package com.restaurant.model;

public class OrderItem {
    private MenuItem menuItem;
    private int quantity;
    private String notes;

    public OrderItem(MenuItem menuItem, int quantity, String notes) {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.notes = notes;
    }

    public MenuItem getMenuItem() { return menuItem; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getNotes() { return notes; }
    public double getTotalPrice() { return menuItem.getPrice() * quantity; }

    public String getItemName() { return menuItem.getName(); }
    public double getUnitPrice() { return menuItem.getPrice(); }
}