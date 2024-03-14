    package org.example.cs4076_project;

    import javafx.collections.ObservableList;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Node;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.layout.HBox;
    import javafx.scene.layout.VBox;
    import javafx.scene.shape.Rectangle;
    import javafx.stage.Popup;
    import javafx.stage.PopupWindow;
    import javafx.stage.Stage;

    import java.io.IOException;
    import java.util.Objects;

    public class Controller {

        @FXML
        public Button add;
        public Button rem;
        public Button view;
        public Label title;
        public Label result;
        public TextField module;
        public DatePicker date;
        public TextField room;
        public ComboBox time;

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
        protected void onAdd(ActionEvent event) throws IOException {
            switchScene(event,"addSceneView");
        }
        @FXML
        protected void onRem(ActionEvent event) throws IOException {
            switchScene(event, "removeClassView");
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
                String tmp = tcp.send("ADD_" + input);
                switch(tmp) {
                    case "ADD_CLASS_SUCCESS":
                        result.setText("Successfully Added Class!");
                        break;
                    case "ADD_CLASS_FAIL":
                        result.setText("There is already a class with that date, room and time!");
                        break;
                    default:
                        result.setText(tmp);
                }
            }
        }

        public void onRemClass(ActionEvent event) {
            TCP tcp = new TCP();
            String m = tcp.init();
            String input = module.getText() + "_" + room.getText() + "_" + date.getValue().toString() + "_" + time.getValue();
            if (!Objects.equals(m, "OK")) {
                result.setText(m);
            } else {
                String tmp = tcp.send("REMOVE_" + input);
                switch(tmp) {
                    case "REMOVE_CLASS_SUCCESS":
                        result.setText("Successfully Removed Class!");
                        break;
                    case "REMOVE_CLASS_FAIL":
                        result.setText("That class does not exist!");
                        break;
                    default:
                        result.setText(tmp);
                }
            }
        }

    }