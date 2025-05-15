
package org.example.javafxsistemadentista.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.javafxsistemadentista.daos.PatientDAO;
import org.example.javafxsistemadentista.entities.Patient;
import org.example.javafxsistemadentista.services.PatientService;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddPatientController implements Initializable {

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
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button searchButton;

    private final PatientService patientService = new PatientService();
    private Patient patient;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        birthDatePicker.setValue(LocalDate.now());
    }

    @FXML
    void handleSave(ActionEvent event) {
        patient = new Patient();
        try {
            patient.setName(nameField.getText());
            patient.setCpf(cpfField.getText());
            patient.setPhone(phoneField.getText());
            patient.setEmail(emailField.getText());
            patient.setBirthDate(birthDatePicker.getValue());
            patient.setAddress(addressField.getText());

            patientService.savePatient(patient);

            showInfoAlert("Paciente cadastrado com sucesso.");
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    @FXML
    void handleUpdate(){
        String cpf = cpfField.getText();
        Patient existingPatient = patientService.searchPatientByCpf(cpf);
        try{
            existingPatient.setName(nameField.getText());
            existingPatient.setPhone(phoneField.getText());
            existingPatient.setEmail(emailField.getText());
            existingPatient.setBirthDate(birthDatePicker.getValue());
            existingPatient.setAddress(addressField.getText());
            patientService.updatePatient(existingPatient);
            showErrorAlert("Paciente editado com sucesso.");
        }catch(Exception e){
            showErrorAlert(e.getMessage());
        }

    }

    @FXML
    void handleSearch(){
        String cpf = cpfField.getText();
        Patient foundPatient = patientService.searchPatientByCpf(cpf);
        if (foundPatient != null) {
            nameField.setText(foundPatient.getName());
            phoneField.setText(foundPatient.getPhone());
            emailField.setText(foundPatient.getEmail());
            birthDatePicker.setValue(foundPatient.getBirthDate());
            addressField.setText(foundPatient.getAddress());
        } else {
            showErrorAlert("Paciente não encontrado.");
        }
    }

    @FXML
    void handleCancel(ActionEvent event) {
        closeForm();
    }

    private void closeForm() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void showInfoAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showErrorAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro ao salvar paciente");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}