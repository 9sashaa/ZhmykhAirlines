module org.example.project_aleksandrkisliak {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens il.ac.sce.project_aleksandrkisliak to javafx.fxml;
    exports il.ac.sce.project_aleksandrkisliak;
    exports il.ac.sce.project_aleksandrkisliak.controller;

    opens il.ac.sce.project_aleksandrkisliak.controller to javafx.fxml;
    opens il.ac.sce.project_aleksandrkisliak.model to javafx.fxml;
    exports il.ac.sce.project_aleksandrkisliak.model;
}