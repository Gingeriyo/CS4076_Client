module org.example.cs4076_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.cs4076_project to javafx.fxml;
    exports org.example.cs4076_project;
}