package com.restaurant.model;

import com.restaurant.enums.TableStatus;

public class Table {
    private String tableNumber;
    private int capacity;
    private String location;
    private TableStatus status;

    public Table(String tableNumber, int capacity, String location, TableStatus status) {
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.location = location;
        this.status = status;
    }

    public String getTableNumber() { return tableNumber; }
    public int getCapacity() { return capacity; }
    public String getLocation() { return location; }
    public TableStatus getStatus() { return status; }
    public void setStatus(TableStatus status) { this.status = status; }
}