package com.restaurant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // تحميل واجهة المنيو
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
        Scene scene = new Scene(loader.load());

        primaryStage.setTitle("Restaurant Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}