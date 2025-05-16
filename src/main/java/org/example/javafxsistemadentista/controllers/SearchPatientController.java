package org.example.javafxsistemadentista.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.example.javafxsistemadentista.entities.Patient;
import org.example.javafxsistemadentista.entities.PatientProfile;
import org.example.javafxsistemadentista.services.PatientService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SearchPatientController implements Initializable {
    // Search Fields
    @FXML private TextField searchNameField;
    @FXML private TextField searchCpfField;

    //Table view
    @FXML private TableView<Patient> patientsTableView;
    @FXML private TableColumn<Patient, Integer> idColumn;


    // Personal Data Tab
    @FXML private Text idLabel;
    @FXML private Text nameLabel;
    @FXML private Text cpfLabel;
    @FXML private Text birthDateLabel;

    // Profile Tab
    @FXML private TextArea observationsTextArea;
    @FXML private TableView<Patient> examsTableView;

    // Appointments Tab
    @FXML private TableView<?> appointmentsTableView;

    private final PatientService patientService = new PatientService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        patientsTableView.getSelectionModel().selectedItemProperty().addListener((
                obs, oldSelection, newSelection)->{
            if(newSelection != null){
                loadPatientData(newSelection);
            }
        });
    }


    private void loadPatientData(Patient patient){
        idLabel.setText(String.valueOf(patient.getId()));
        // other data load, patient profile etc
    }

    @FXML
    private void handlePatientSelection(MouseEvent event){
        if(event.getClickCount() == 1){
            Patient selected = patientsTableView.getSelectionModel().getSelectedItem();
            if(selected != null){
                loadPatientData(selected);
            }
        }
    }

    @FXML
    private void handleSearch() {
        try{
            String name = searchNameField.getText();
            List<Patient> patientsFound = patientService.searchPatientsByName(name);
            if(!patientsFound.isEmpty()){
                patientsTableView.getItems().setAll(patientsFound);
            }else{
                showErrorAlert("Nenhum paciente encontrado.");
            }
        } catch (Exception e) {
            showErrorAlert("Erro ao buscar paciente.");
            e.printStackTrace();
        }
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

    private void showErrorAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro ao salvar paciente");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public void showPatient(PatientProfile profile) {
        // Update UI with patient data
        nameLabel.setText(profile.getPatient().getName());
        cpfLabel.setText(profile.getPatient().getCpf());
        // Update other fields as needed
    }

}