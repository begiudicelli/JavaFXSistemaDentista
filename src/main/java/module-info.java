module org.example.javafxsistemadentista {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example.javafxsistemadentista to javafx.fxml;
    opens org.example.javafxsistemadentista.controllers to javafx.fxml;
    exports org.example.javafxsistemadentista;
}
