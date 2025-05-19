package org.example.javafxsistemadentista.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.javafxsistemadentista.util.Alerts;

import java.io.IOException;

public class AppointmentController {

    private void saveAppointmentData() {

    }

    public void handleSave(ActionEvent actionEvent) {
    }

    public void handleCancel(ActionEvent actionEvent) {
    }

    public void handleAddTreatment(ActionEvent actionEvent) {
    }

    public void handlePatientSearch(ActionEvent actionEvent) {
    }

    public void handlePatientSelection(MouseEvent mouseEvent) {
    }

    @FXML
    public void handleNextPage(ActionEvent actionEvent) {
        try {
            saveAppointmentData();
            Node source = (Node) actionEvent.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafxsistemadentista/fxml/ScheduleView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Agenda de Consultas");
        } catch (IOException e) {
            Alerts.showErrorAlert(e.getMessage());
            e.printStackTrace();
        }
    }

}