package org.example.cs4076_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    private final int minWidth = 720;
    private final int minHeight = 480;

    // setting primary stage
    @Override
    public void start(Stage stage) {
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StartupView.fxml")));
            Scene scene = new Scene(root);
            stage.setTitle("Class Scheduler");
            // replace icon
            Image icon = new Image(Objects.requireNonNull(controller.class.getResource("/kit.png")).toExternalForm(), false);
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

    // Add class
    // Remove class
    // View schedule
    public Scene startup() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("StartupView.fxml"));

        return new Scene(fxmlLoader.load(), minWidth, minHeight);
    }

    public Scene addClass() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("addSceneView.fxml"));

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