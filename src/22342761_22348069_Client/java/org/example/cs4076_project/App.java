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

public class App extends Application implements EventHandler<ActionEvent> {

    private final int minWidth = 720;
    private final int minHeight = 480;

    private Button btn;

    // setting primary stage
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("View.fxml"));
        primaryStage.setTitle("Client");
        primaryStage.setResizable(false);
        primaryStage.setScene(startup());
        primaryStage.show();
    }

    private Scene startup(){

        Button btn = new Button("Click");

        VBox layout = new VBox(
                btn
                );

        btn.setOnAction(this);

        return new Scene(layout, minWidth, minHeight);
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource()==btn){
            System.out.println("hi");
        }
    }
}