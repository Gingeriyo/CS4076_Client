package org.example.cs4076_project;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class App extends Application {

    private Stage stage;
    final int minWidth = 720;
    final int minHeight = 480;


    // setting primary stage
    @Override
    public void start(Stage primaryStage) throws Exception{
            // Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StartupView.fxml")));
            // Scene scene = new Scene(root);
        stage = primaryStage;
        primaryStage.setTitle("UL Portal");
        primaryStage.setResizable(false);

        stage.setTitle("Class Scheduler");
        // replace icon
        Image icon = new Image(Objects.requireNonNull(Controller.class.getResource("/kit.png")).toExternalForm(), false);
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setMinWidth(minWidth);
        stage.setMinHeight(minHeight);
        stage.setScene(homeMenu());
        stage.show();
    }
    private Scene homeMenu() {
        Label header = new Label("Home");
        Label subtitle = new Label("What would you like to do?");
        Button classDir = new Button("Manage Classes");
        Button scheduleDir = new Button("View Schedule");
        Button exitBtn = new Button("Exit");

        header.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 30));
        subtitle.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        subtitle.setTextFill(Color.web("#FF0000"));

        classDir.setMinWidth(200);
        scheduleDir.setMinWidth(200);
        exitBtn.setMinWidth(200);

        classDir.setOnAction(e -> stage.setScene(manageClasses()));
        scheduleDir.setOnAction(e -> stage.setScene(viewSchedule()));
        exitBtn.setOnAction(e -> {
            Platform.exit();
                });

        HBox layout = new HBox(
                new VBox(
                header,
                new Label(""), // this is here to act as a blank space
                subtitle,
                classDir,
                scheduleDir,
                exitBtn));

        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setSpacing(6);
        layout.setMinSize(500, 500);
        layout.setStyle("-fx-padding: 180;");

        return new Scene(layout, minWidth, minHeight);
    }

    private Scene manageClasses(){

        // ----------- POPUP -----------------

        Popup popup = new Popup();

        Label popText = new Label("");
        popText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox popBox = new VBox(
                popText
        );
        popBox.setStyle("-fx-background-color: LIGHTBLUE; -fx-padding: 10px; -fx-border-color: black; -fx-border-width: 2px;");

        popup.getContent().add(popBox);

        // when user clicks outside popup, popup hides
        popup.setAutoHide(true);


        // ----------- SCENE -----------------

        Label header = new Label("Manage Classes");
        header.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 20));

        TextField module = new TextField();
        TextField room = new TextField();
        DatePicker date = new DatePicker();
        ComboBox time = new ComboBox();

        module.setPromptText("e.g., CS4076");
        room.setPromptText("e.g., EGO10");
        date.setPromptText("Select Date");
        time.setPromptText("Select time");

        date.setMinWidth(200);

        ArrayList<String> list = new ArrayList<String>(Arrays.asList("9", "10", "11", "12", "13", "14", "15", "16", "17"));
        ObservableList<String> times = FXCollections.observableList(list);
        time.setItems(times);

        Button addClass = new Button("Add Class");
        addClass.setMinWidth(100);
        addClass.setOnAction(e -> {
            TCP tcp = new TCP();
            String m = tcp.init();
            String input = module.getText() + "_" + room.getText() + "_" + date.getValue().toString() + "_" + time.getValue();
            if (!Objects.equals(m, "OK")) {
                popText.setText(m);
                popup.show(stage);
            } else {
                popText.setText(tcp.send("ADD_" + input));
                popup.show(stage);
            }
        });

        Button remClass = new Button("Remove Class");
        remClass.setMinWidth(100);
        remClass.setOnAction(e -> {
            TCP tcp = new TCP();
            String m = tcp.init();
            String input = module.getText() + "_" + room.getText() + "_" + date.getValue().toString() + "_" + time.getValue();
            if (!Objects.equals(m, "OK")) {
                popText.setText(m);
                popup.show(stage);
            } else {
                popText.setText(tcp.send("REMOVE_" + input));
                popup.show(stage);
            }
        });

        Button homeBtn = new Button("Home");
        homeBtn.setOnAction(e -> stage.setScene(homeMenu()));

        VBox col1 = new VBox(
                new Label("Module Code:"),
                new Label("Room:"),
                new Label("Date:"),
                new Label("Time:"),
                new Label("")
        );
        col1.setSpacing(10);

        VBox col2 = new VBox(
                module,
                room,
                date,
                time
        );
        col2.setSpacing(2);

        HBox columns = new HBox(
                col1,
                col2
        );
        columns.setSpacing(5);

        HBox buttons = new HBox(
             addClass,
             remClass
        );
        buttons.setSpacing(10);

        VBox layout = new VBox(
                header,
                new Label(""),
                columns,
                buttons,
                new Label(""),
                homeBtn
        );
        layout.setAlignment(Pos.CENTER_LEFT);

        layout.setAlignment(Pos.CENTER_LEFT);

        return new Scene(layout, minWidth, minHeight);
    }

    private Scene viewSchedule(){

        Label header = new Label("View Schedule");
        header.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 20));


        TextField module = new TextField();
        DatePicker date = new DatePicker();

        module.setMinWidth(200);
        module.setPromptText("e.g., CS4076");
        date.setMinWidth(200);
        date.setPromptText("Select Date");

        TextArea scheduleField = new TextArea("");
        scheduleField.setEditable(false);
        scheduleField.setMaxWidth(400);
        scheduleField.setMinWidth(400);
        scheduleField.setMaxHeight(400);
        scheduleField.setMinHeight(400);

        Label result = new Label("");

        Button homeBtn = new Button("Home");
        homeBtn.setOnAction(e -> stage.setScene(homeMenu()));

        Button daySched = new Button("Day Schedule");
        daySched.setOnAction(e -> {
            TCP tcp = new TCP();
            String m = tcp.init();
            if (!Objects.equals(m, "OK")) {
                result.setText(m);
            } else {
                String input = tcp.send("VIEW_" + date.getValue().toString());
                try {
                    scheduleField.setText(ViewCalc.in(input));
                }
                catch (Exception ex) {
                    result.setText(input);
                }
            }
        });

        Button modSched = new Button("Module Schedule");
        modSched.setOnAction(e -> {
            TCP tcp = new TCP();
            String m = tcp.init();
            if (!Objects.equals(m, "OK")) {
                result.setText(m);
            } else {
                String input = tcp.send("VIEWCLASS_" + date.getValue().toString() + "_" + module.getText());
                try {
                    scheduleField.setText(ViewCalc.in(input));
                }
                catch (Exception ex) {
                    // when inputting module and date,
                    // if a module is input but no date
                    // throws exception
                    // but 'result' doesn't change?
                    result.setText(input);
                }
            }
        });

        VBox col1 = new VBox(
                new Label("Module Code:"),
                new Label("Date:"),
                new Label("")
        );
        col1.setSpacing(10);

        VBox col2 = new VBox(
                module,
                date
        );
        col2.setSpacing(2);

        HBox columns = new HBox(
                col1,
                col2
        );
        columns.setSpacing(5);

        HBox buttons = new HBox(
                daySched,
                modSched
        );
        buttons.setSpacing(10);

        VBox schedDisplay = new VBox(
                scheduleField
        );
        schedDisplay.setAlignment(Pos.CENTER_RIGHT);

        VBox layout = new VBox(
                header,
                new Label(""),
                columns,
                buttons,
                result,
                new Label(""),
                homeBtn
        );
        layout.setAlignment(Pos.CENTER_LEFT);

        HBox sides = new HBox(
                layout,
                schedDisplay
        );
        sides.setSpacing(10);

        return new Scene(sides, minWidth, minHeight);
    }

    public static void main(String[] args) {
        launch();
    }
}