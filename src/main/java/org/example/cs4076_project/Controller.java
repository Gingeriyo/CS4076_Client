    package org.example.cs4076_project;

    import javafx.beans.property.SimpleStringProperty;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Node;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.control.cell.PropertyValueFactory;
    import javafx.scene.layout.AnchorPane;
    import javafx.scene.layout.GridPane;
    import javafx.stage.Stage;

    import java.io.IOException;
    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.Objects;

    public class Controller {

        @FXML
        public Button add;
        public Label title;
        public Label result;
        public TextField module;
        public DatePicker date;
        public TextField room;
        public ComboBox<String> time;
        public DatePicker date2;
        public TextArea scheduleField;

        private javafx.collections.FXCollections FXCollections;


        public Controller() throws IOException {}

        public void switchScene(ActionEvent event, String name) throws IOException {
            Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource(name + ".fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            int minWidth = 720;
            stage.setMinWidth(minWidth);
            int minHeight = 480;
            stage.setMinHeight(minHeight);
            stage.setScene(scene);
            stage.show();
        }

        @FXML
        protected void onManage(ActionEvent event) throws IOException {
            switchScene(event, "manageClassesView");
        }

        @FXML
        protected void onView(ActionEvent event) throws IOException {
            switchScene(event, "viewScheduleView");
        }

        public void onHome(ActionEvent event) throws IOException {
            switchScene(event, "StartupView");
        }

        public void onAddClass(ActionEvent event) {
            TCP tcp = new TCP();
            String m = tcp.init();
            String input = module.getText() + "_" + room.getText() + "_" + date.getValue().toString() + "_" + time.getValue();
            if (!Objects.equals(m, "OK")) {
                result.setText(m);
            } else {
                result.setText(tcp.send("ADD_" + input));
            }
        }

        public void onRemClass(ActionEvent event) {
            TCP tcp = new TCP();
            String m = tcp.init();
            String input = module.getText() + "_" + room.getText() + "_" + date.getValue().toString() + "_" + time.getValue();
            if (!Objects.equals(m, "OK")) {
                result.setText(m);
            } else {
                result.setText(tcp.send("REMOVE_" + input));
            }
        }


        public void genSchedule(ActionEvent event) throws IOException {
            TCP tcp = new TCP();
            String m = tcp.init();
            if (!Objects.equals(m, "OK")) {
                result.setText(m);
            } else {
                String input = tcp.send("VIEW_" + date2.getValue().toString());
                try {
                    HashMap<Integer, ArrayList<String[]>> h = ViewCalc.in(input);
                }
                catch (Exception e) {
                    result.setText(input);
                }
            }

            scheduleField.setText("");
        }

        public void onClassSched(ActionEvent event) {
            //todo
        }

        public void onStop(ActionEvent event) {
            TCP tcp = new TCP();
            String m = tcp.init();
            if (!Objects.equals(m, "OK")) {
                System.out.println("Could not connect to server! Exiting...");
                System.exit(1);
            } else {
                if (Objects.equals(tcp.send("STOP"), "TERMINATE")) {
                    System.out.println("Server has been closed, exiting...");
                    System.exit(1);
                }
                else {
                    System.out.println("Incorrect response from server! Exiting...");
                    System.exit(1);
                }
            }
        }

    }