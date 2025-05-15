package org.example.javafxsistemadentista.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.example.javafxsistemadentista.entities.PatientProfile;

public class SearchPatientController {
    // Search Fields
    @FXML private TextField searchNameField;
    @FXML private TextField searchCpfField;
    @FXML private TableView<?> patientsTableView;

    // Personal Data Tab
    @FXML private Text idLabel;
    @FXML private Text nameLabel;
    @FXML private Text cpfLabel;
    @FXML private Text birthDateLabel;

    // Profile Tab
    @FXML private TextArea observationsTextArea;
    @FXML private TableView<?> examsTableView;

    // Appointments Tab
    @FXML private TableView<?> appointmentsTableView;

    @FXML
    private void handleSearch() {
        // Handle search logic here
    }

    @FXML
    private void onEditProfile() {
        // Handle profile edit
    }

    @FXML
    private void onSaveProfile() {
        // Handle profile save
    }

    @FXML
    private void onEditAppointment() {
        // Handle appointment edit
    }

    @FXML
    private void onSaveAppointment() {
        // Handle appointment save
    }

    public void showPatient(PatientProfile profile) {
        // Update UI with patient data
        nameLabel.setText(profile.getPatient().getName());
        cpfLabel.setText(profile.getPatient().getCpf());
        // Update other fields as needed
    }
}