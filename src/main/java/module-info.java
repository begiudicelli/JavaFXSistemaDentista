module org.example.javafxsistemadentista {
    requires javafx.controls;
    requires javafx.fxml;
<<<<<<< HEAD


    opens org.example.javafxsistemadentista to javafx.fxml;
    exports org.example.javafxsistemadentista;
}
=======
    requires java.sql;

    opens org.example.javafxsistemadentista to javafx.fxml;
    opens org.example.javafxsistemadentista.controllers to javafx.fxml;

    exports org.example.javafxsistemadentista;
    exports org.example.javafxsistemadentista.controllers;
    exports org.example.javafxsistemadentista.entities;
}
>>>>>>> a844a0c394e549f40503c18884635b6ab932091f
