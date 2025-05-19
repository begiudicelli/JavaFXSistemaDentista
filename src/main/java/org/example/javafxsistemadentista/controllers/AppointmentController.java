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

import java.io.IOException;

public class AppointmentController {

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
            showError("Erro de Navegação", "Não foi possível carregar a tela de agenda: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveAppointmentData() {

    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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

}