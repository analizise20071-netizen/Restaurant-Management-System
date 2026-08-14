package com.restaurant.controller;
import com.restaurant.db.RestaurantDatabase;
import com.restaurant.model.*;
import com.restaurant.model.MenuItem; // استيراد MenuItem الخاص بمشروعك صراحة

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;

// استيراد كافة عناصر التحكم المطلوبة بدون MenuItem الخاص بـ JavaFX
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;
import java.util.stream.Collectors;
public class MenuController {

    // GUI Controls
    @FXML private ComboBox<MenuCategory> categoryComboBox;
    @FXML private ListView<MenuItem> menuListView;
    @FXML private Spinner<Integer> quantitySpinner;
    @FXML private TextField notesField;

    @FXML private TableView<OrderItem> orderTableView;
    @FXML private TableColumn<OrderItem, String> colItemName;
    @FXML private TableColumn<OrderItem, Integer> colQty;
    @FXML private TableColumn<OrderItem, Double> colUnitPrice;
    @FXML private TableColumn<OrderItem, Double> colTotal;
    @FXML private TableColumn<OrderItem, String> colNotes;

    @FXML private Label runningTotalLabel;

    // Admin Inputs
    @FXML private TextField newItemName;
    @FXML private TextField newItemPrice;
    @FXML private TextField newItemDesc;
    @FXML private CheckBox newItemAvailable;

    private ObservableList<OrderItem> currentOrderItems = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize Quantity Spinner (1 to 20)
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1));

        // Configure Order Cart Table
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        colNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));

        orderTableView.setItems(currentOrderItems);

        // Load Categories and Menu Items asynchronously (Bonus)
        loadMenuAsync();
    }

    // BONUS: Asynchronous Loading of Menu Data
    @FXML
    public void handleAsyncRefresh() {
        loadMenuAsync();
    }

    private void loadMenuAsync() {
        Task<Void> loadTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                // المحاكاة للتحميل من الـ DB/Network
                Thread.sleep(400);

                Platform.runLater(() -> {
                    categoryComboBox.getItems().setAll(RestaurantDatabase.categories);
                    menuListView.getItems().setAll(RestaurantDatabase.menuItems);
                });
                return null;
            }
        };
        new Thread(loadTask).start();
    }

    // Category Filter
    @FXML
    public void handleCategoryChange() {
        MenuCategory selectedCategory = categoryComboBox.getValue();
        if (selectedCategory == null) {
            menuListView.getItems().setAll(RestaurantDatabase.menuItems);
        } else {
            List<MenuItem> filtered = RestaurantDatabase.menuItems.stream()
                    .filter(item -> item.getCategory() != null && item.getCategory().getCategoryId().equals(selectedCategory.getCategoryId()))
                    .collect(Collectors.toList());
            menuListView.getItems().setAll(filtered);
        }
    }

    // Add selected item to current order
    @FXML
    public void handleAddToOrder() {
        MenuItem selectedItem = menuListView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert("Selection Error", "Please select an item from the menu!");
            return;
        }

        // Validate availability
        if (!selectedItem.isAvailable()) {
            showAlert("Item Unavailable", "Sorry, " + selectedItem.getName() + " is currently out of stock!");
            return;
        }

        int qty = quantitySpinner.getValue();
        String notes = notesField.getText();

        OrderItem orderItem = new OrderItem(selectedItem, qty, notes);
        currentOrderItems.add(orderItem);

        updateRunningTotal();

        // Reset inputs
        notesField.clear();
        quantitySpinner.getValueFactory().setValue(1);
    }

    private void updateRunningTotal() {
        double total = 0;
        for (OrderItem item : currentOrderItems) {
            total += item.getTotalPrice();
        }
        runningTotalLabel.setText(String.format("%.2f EGP", total));
    }

    @FXML
    public void handleClearOrder() {
        currentOrderItems.clear();
        updateRunningTotal();
    }

    @FXML
    public void handleSubmitOrder() {
        if (currentOrderItems.isEmpty()) {
            showAlert("Order Empty", "Please add items to your order before submitting!");
            return;
        }

        // Create Order object using first dummy Customer and Table for demo
        Customer customer = RestaurantDatabase.customers.isEmpty() ? null : RestaurantDatabase.customers.get(0);
        Table table = RestaurantDatabase.tables.isEmpty() ? null : RestaurantDatabase.tables.get(0);

        Order newOrder = new Order("ORD-" + (RestaurantDatabase.orders.size() + 1), customer, table);
        for (OrderItem item : currentOrderItems) {
            newOrder.addItem(item);
        }

        RestaurantDatabase.orders.add(newOrder);

        showAlert("Order Success", "Order placed successfully! Order ID: " + newOrder.getOrderId());
        handleClearOrder();
    }

    // Admin Feature: Add Item to Menu (CRUD)
    @FXML
    public void handleAdminAddItem() {
        String name = newItemName.getText();
        String priceText = newItemPrice.getText();
        String desc = newItemDesc.getText();
        boolean available = newItemAvailable.isSelected();
        MenuCategory category = categoryComboBox.getValue();

        if (name.isEmpty() || priceText.isEmpty() || category == null) {
            showAlert("Validation Error", "Please fill in Name, Price, and select a Category!");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            MenuItem newItem = new MenuItem("M" + (RestaurantDatabase.menuItems.size() + 1), name, price, desc, category, available);

            RestaurantDatabase.menuItems.add(newItem);
            handleCategoryChange(); // Refresh list view

            newItemName.clear();
            newItemPrice.clear();
            newItemDesc.clear();
            showAlert("Admin Success", "New item added to menu!");
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Price must be a valid number!");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}