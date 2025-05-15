package org.example.javafxsistemadentista.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Objects;

public class MainController {

    @FXML
    private BorderPane mainPane;

    @FXML
    private void handleAddPatient() {
        loadView("/org/example/javafxsistemadentista/fxml/PatientView.fxml");
    }

    @FXML
    private void handleSearchPatient() {
        loadView("/org/example/javafxsistemadentista/fxml/SearchPatientView.fxml");
    }

    private void loadView(String fxmlPath) {
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            mainPane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
