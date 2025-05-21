
package org.example.javafxsistemadentista.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.javafxsistemadentista.entities.Patient;
import org.example.javafxsistemadentista.services.PatientService;
import org.example.javafxsistemadentista.util.Alerts;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PatientController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField cpfField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private TextField addressField;
    @FXML
    private TextField notesField;

    private final PatientService patientService = new PatientService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        birthDatePicker.setValue(LocalDate.now());
    }

    @FXML
    private void handleSave() {
        try {
            Patient patient = new Patient();
            patient.setName(nameField.getText());
            patient.setCpf(cpfField.getText());
            patient.setPhone(phoneField.getText());
            patient.setEmail(emailField.getText());
            patient.setBirthDate(birthDatePicker.getValue());
            patient.setAddress(addressField.getText());
            patient.setNotes(notesField.getText());
            patientService.savePatient(patient);
            Alerts.showInfoAlert("Paciente cadastrado com sucesso.");
            clearForm();
        } catch (Exception e) {
            Alerts.showErrorAlert(e.getMessage());
        }
    }

    private void clearForm() {
        nameField.clear();
        cpfField.clear();
        phoneField.clear();
        emailField.clear();
        birthDatePicker.setValue(LocalDate.now());
        addressField.clear();
        notesField.clear();
    }
}