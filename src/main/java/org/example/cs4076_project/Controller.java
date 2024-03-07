package org.example.cs4076_project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    public Button add;
    public Button rem;
    public Button view;
    public Label title;
    private final Stage stage = new Stage();
    private final App app = new App();

    @FXML
    protected void onAdd() throws IOException {
        stage.setScene(app.addClass());
    }
    @FXML
    protected void onRem() throws IOException {
        stage.setScene(app.removeClass());
    }
    @FXML
    protected void onView() throws IOException {
        stage.setScene(app.viewSchedule());
    }
}