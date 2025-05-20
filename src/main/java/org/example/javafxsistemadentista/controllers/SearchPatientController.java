package org.example.javafxsistemadentista.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.javafxsistemadentista.entities.Appointments;
import org.example.javafxsistemadentista.entities.Patient;
import org.example.javafxsistemadentista.services.PatientService;
import org.example.javafxsistemadentista.util.Alerts;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SearchPatientController implements Initializable {

    // Search Fields
    @FXML private TextField searchNameField;

    // Table view
    @FXML private TableView<Patient> patientsTableView;
    @FXML private TableColumn<Patient, Integer> idColumn;
    @FXML private TableColumn<Patient, String> nameColumn;
    @FXML private TableColumn<Patient, String> cpfColumn;
    @FXML private TableColumn<Patient, String> phoneColumn;
    @FXML private TableColumn<Patient, String> emailColumn;
    @FXML private TableColumn<Patient, String> addressColumn;
    @FXML private TableColumn<Patient, String> birthDateColumn;

    // Personal Data Tab
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField cpfField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private TextField addressField;
    @FXML private DatePicker birthDatePicker;
    @FXML private TextField notesField;

    // Profile Tab
    @FXML private TextArea observationsTextArea;
    @FXML private TableView<Patient> examsTableView;

    // Appointments Tab
    @FXML
    private TableView<Appointments> appointmentsTableView;

    private final PatientService patientService = new PatientService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeColumns();
        patientsTableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        loadPatientData(newSelection);
                    }
                }
        );

    }

    @FXML
    private void handlePatientSelection(MouseEvent event) {
        if (event.getClickCount() == 1) {
            Patient selected = patientsTableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                loadPatientData(selected);
            }
        }
    }

    @FXML
    private void handleSearch() {
        try {
            String name = searchNameField.getText();
            List<Patient> patientsFound = patientService.searchPatientsByName(name);
            if (!patientsFound.isEmpty()) {
                patientsTableView.getItems().setAll(patientsFound);
            } else {
                Alerts.showErrorAlert("Nenhum paciente encontrado.");
            }
        } catch (Exception e) {
            Alerts.showErrorAlert("Erro ao buscar paciente.");
        }
    }

    @FXML
    private void handleEdit() {
        enableFormFields();
    }

    @FXML
    private void handleSave() {
        Patient selected = patientsTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alerts.showErrorAlert("Nenhum paciente selecionado.");
            return;
        }

        try {
            selected.setName(nameField.getText());
            selected.setCpf(cpfField.getText());
            selected.setPhone(phoneField.getText());
            selected.setEmail(emailField.getText());
            selected.setAddress(addressField.getText());
            selected.setBirthDate(birthDatePicker.getValue());
            selected.setNotes(notesField.getText());

            patientService.updatePatient(selected);
            Alerts.showInfoAlert("Paciente atualizado com sucesso.");
            disableFormFields();
        } catch (Exception e) {
            Alerts.showErrorAlert("Erro ao salvar paciente: " + e.getMessage());
        }
    }

    private void loadPatientData(Patient patient) {
        idField.setText(String.valueOf(patient.getId()));
        nameField.setText(patient.getName());
        cpfField.setText(patient.getCpf());
        phoneField.setText(patient.getPhone());
        emailField.setText(patient.getEmail());
        addressField.setText(patient.getAddress());
        birthDatePicker.setValue(patient.getBirthDate());
        notesField.setText(patient.getNotes());

        disableFormFields();
    }

    private void initializeColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
    }

    private void enableFormFields() {
        nameField.setDisable(false);
        cpfField.setDisable(false);
        phoneField.setDisable(false);
        emailField.setDisable(false);
        addressField.setDisable(false);
        birthDatePicker.setDisable(false);
        notesField.setDisable(false);
    }

    private void disableFormFields() {
        nameField.setDisable(true);
        cpfField.setDisable(true);
        phoneField.setDisable(true);
        emailField.setDisable(true);
        addressField.setDisable(true);
        birthDatePicker.setDisable(true);
        notesField.setDisable(true);
    }
}