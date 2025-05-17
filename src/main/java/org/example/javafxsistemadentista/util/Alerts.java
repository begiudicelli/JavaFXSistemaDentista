package org.example.javafxsistemadentista.util;

import javafx.scene.control.Alert;

public class Alerts {
    public static void showErrorAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro ao salvar paciente");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
