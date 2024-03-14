    package org.example.cs4076_project;

    import javafx.collections.ObservableList;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Node;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.Button;
    import javafx.scene.control.Label;
    import javafx.scene.control.PopupControl;
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

        TCP tcp = new TCP();

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
            result.setText(tcp.send("ADD_ghhg_S205_2024-04-04_9"));
        }

        public void onRemClass(ActionEvent event) {
            // todo
        }

    }