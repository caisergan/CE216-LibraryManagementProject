module com.example.ce216librarymanagementproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;


    opens com.example.ce216librarymanagementproject to javafx.fxml;
    exports com.example.ce216librarymanagementproject;
}