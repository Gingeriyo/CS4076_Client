package org.example.cs4076_project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    public Label lbl;

    @FXML
    protected void onBtnClick() {
        lbl.setText("Welcome!");
    }
}