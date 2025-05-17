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
import org.example.javafxsistemadentista.util.Alerts;

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
    @FXML private TableColumn<Patient, String> nameColumn;
    @FXML private TableColumn<Patient, String> cpfColumn;
    @FXML private TableColumn<Patient, String> phoneColumn;
    @FXML private TableColumn<Patient, String> emailColumn;
    @FXML private TableColumn<Patient, String> addressColumn;
    @FXML private TableColumn<Patient, String> birthDateColumn;


    // Personal Data Tab
    @FXML private Text idLabel;
    @FXML private Text nameLabel;
    @FXML private Text cpfLabel;
    @FXML private Text phoneLabel;
    @FXML private Text emailLabel;
    @FXML private Text addressLabel;
    @FXML private Text birthDateLabel;

    // Profile Tab
    @FXML private TextArea observationsTextArea;
    @FXML private TableView<Patient> examsTableView;

    // Appointments Tab
    @FXML private TableView<?> appointmentsTableView;

    private final PatientService patientService = new PatientService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeColumns();
        patientsTableView.getSelectionModel().selectedItemProperty().addListener((
                obs, oldSelection, newSelection)->{
            if(newSelection != null){
                loadPatientData(newSelection);
            }
        });
    }

    private void loadPatientData(Patient patient){
        idLabel.setText(String.valueOf(patient.getId()));
        nameLabel.setText(patient.getName());
        cpfLabel.setText(patient.getCpf());
        phoneLabel.setText(patient.getPhone());
        emailLabel.setText(patient.getEmail());
        addressLabel.setText(patient.getAddress());
        birthDateLabel.setText(patient.getBirthDate().toString());
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
                Alerts.showErrorAlert("Nenhum paciente encontrado.");
            }
        } catch (Exception e) {
            Alerts.showErrorAlert("Erro ao buscar paciente.");
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

    private void initializeColumns(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
    }

}