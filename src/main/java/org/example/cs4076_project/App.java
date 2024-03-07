package org.example.cs4076_project;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private final int minWidth = 720;
    private final int minHeight = 480;
    public Stage stage;

    // setting primary stage
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        primaryStage.setTitle("Class Scheduler");
        primaryStage.setResizable(false);
        primaryStage.setScene(startup());
        primaryStage.show();
    }

    //stage.setScene(facultyLogin())

    // Add class
    // Remove class
    // View schedule
    public Scene startup() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("StartupView.fxml"));

        return new Scene(fxmlLoader.load(), minWidth, minHeight);
    }

    public Scene addClass() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("addScene.fxml"));

        return new Scene(fxmlLoader.load(), minWidth, minHeight);
    }

    public Scene removeClass() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("addScene.fxml"));

        return new Scene(fxmlLoader.load(), minWidth, minHeight);
    }

    public Scene viewSchedule() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("viewScheduleView.fxml"));

        return new Scene(fxmlLoader.load(), minWidth, minHeight);
    }

    public static void main(String[] args) {
        launch();
    }

}