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

        // setting primary stage
        @Override
        public void start(Stage primaryStage) throws Exception {
            primaryStage.setTitle("Client");
            primaryStage.setResizable(false);
            primaryStage.setScene(startup());
            primaryStage.show();
        }

        private Scene startup() throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("StartupView.fxml"));
            Label lbl = new Label();
            Button btn = new Button();
            return new Scene(fxmlLoader.load(), minWidth, minHeight);
        }

        public static void main(String[] args) {
            launch();
        }

    }