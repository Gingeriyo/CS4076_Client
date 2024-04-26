package org.example.cs4076_project;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App extends Application {

    private Stage stage;
    final int minWidth = 720;
    final int minHeight = 480;
    public String dateString;
    public String moduleString;

    // setting primary stage
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        primaryStage.setTitle("UL Portal");
        primaryStage.setResizable(false);

        stage.setTitle("Class Scheduler");

        stage.setResizable(false);
        stage.setMinWidth(minWidth);
        stage.setMinHeight(minHeight);
        stage.setScene(homeMenu());
        stage.show();
    }

    // first stage (home)
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
        exitBtn.setOnAction(e -> Platform.exit());

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

    // to add and remove classes/lectures
    private Scene manageClasses(){

// ----------------------- POPUP -----------------------

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


// ----------------------- SCENE -----------------------

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

        ArrayList<String> list = new ArrayList<>(Arrays.asList("9", "10", "11", "12", "13", "14", "15", "16", "17"));
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

    // to view day and module schedule, along with 'early lectures'
    private Scene viewSchedule(){

        Label header = new Label("View Schedule");
        header.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 20));

        DatePicker date = new DatePicker();
        TextField module = new TextField();

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

//----------------------- Implementation of javafx.concurrent -----------------------

        Button daySched = new Button("Day Schedule");
        daySched.setOnAction(e -> {

            dateString = date.getValue().toString();
            moduleString = module.getText();

            ConcurrentCalc mySchedule = new ConcurrentCalc(dateString, "VIEW_", moduleString);
            result.textProperty().bind(mySchedule.messageProperty());

            mySchedule.setOnSucceeded((succeededEvent) -> scheduleField.setText(mySchedule.getValue()));

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(mySchedule);

            executorService.shutdown();
        });

        Button modSched = new Button("Module Schedule");
        modSched.setOnAction(e -> {

            dateString = date.getValue().toString();
            moduleString = module.getText();

            ConcurrentCalc mySchedule = new ConcurrentCalc(dateString, "VIEWCLASS_", moduleString);
            result.textProperty().bind(mySchedule.messageProperty());

            mySchedule.setOnSucceeded((succeededEvent) -> scheduleField.setText(mySchedule.getValue()));

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(mySchedule);

            executorService.shutdown();
        });


// ----------------------- EARLY LECTURES -----------------------
        // also implements javafx.concurrent

        Button earlyLectures = new Button("Early Lectures");
        earlyLectures.setOnAction(e -> {

            dateString = date.getValue().toString();
            moduleString = module.getText();

            ConcurrentCalc mySchedule = new ConcurrentCalc(dateString, "EARLYLECTURES", moduleString);
            result.textProperty().bind(mySchedule.messageProperty());

            mySchedule.setOnSucceeded((succeededEvent) -> scheduleField.setText(mySchedule.getValue()));

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(mySchedule);

            executorService.shutdown();
        });


        // ----------------------- LAYOUT -----------------------

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
                modSched,
                earlyLectures
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

    // launches application
    public static void main(String[] args) {
        launch();
    }
}