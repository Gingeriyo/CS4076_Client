package org.example.cs4076_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    // setting primary stage
    @Override
    public void start(Stage stage) {
        try{
            final int minWidth = 720;
            final int minHeight = 480;
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StartupView.fxml")));
            Scene scene = new Scene(root);
            stage.setTitle("Class Scheduler");
            // replace icon
            Image icon = new Image(Objects.requireNonNull(Controller.class.getResource("/kit.png")).toExternalForm(), false);
            stage.getIcons().add(icon);
            stage.setResizable(false);
            stage.setMinWidth(minWidth);
            stage.setMinHeight(minHeight);
            stage.setScene(scene);
            stage.show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}