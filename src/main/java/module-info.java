module com.example.ce216librarymanagementproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;
    requires javafx.swing;

    opens com.example.ce216librarymanagementproject to com.google.gson, javafx.fxml;
    exports com.example.ce216librarymanagementproject;
}