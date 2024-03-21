module com.example.ce216librarymanagementproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ce216librarymanagementproject to javafx.fxml;
    exports com.example.ce216librarymanagementproject;
}