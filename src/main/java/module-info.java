module org.example.javafxsistemadentista {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.javafxsistemadentista to javafx.fxml;
    exports org.example.javafxsistemadentista;
}