package org.example.javafxsistemadentista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
<<<<<<< HEAD
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
=======
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.javafxsistemadentista.connection.DatabaseManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        //DatabaseManager.resetDatabase();
        try {
            Connection conn = DatabaseManager.getConnection();
            System.out.println("Database connection established successfully");
        } catch (SQLException e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
            e.printStackTrace();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/MainView.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("Sistema Odontológico");
>>>>>>> a844a0c394e549f40503c18884635b6ab932091f
        stage.setScene(scene);
        stage.show();
    }

<<<<<<< HEAD
    public static void main(String[] args) {
        launch();
    }
}
=======
    @Override
    public void stop() {
        DatabaseManager.closeConnection();
        System.out.println("Database connection closed");
    }

    public static void main(String[] args) {
        launch();
    }
}
>>>>>>> a844a0c394e549f40503c18884635b6ab932091f
