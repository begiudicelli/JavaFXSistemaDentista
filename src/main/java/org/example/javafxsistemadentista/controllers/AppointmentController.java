package org.example.javafxsistemadentista.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.javafxsistemadentista.entities.Dentist;
import org.example.javafxsistemadentista.entities.Employee;
import org.example.javafxsistemadentista.entities.Patient;
import org.example.javafxsistemadentista.entities.Treatments;
import org.example.javafxsistemadentista.services.AppointmentService;
import org.example.javafxsistemadentista.services.PatientService;
import org.example.javafxsistemadentista.util.Alerts;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {

    //Combo box
    @FXML private ComboBox<String> dentistComboBox;
    @FXML private ComboBox<String> employeeComboBox;
    @FXML private ComboBox<String> treatmentTypeComboBox;


    //Patient table view
    @FXML private TableView<Patient> patientTableView;
    @FXML private TextField searchNameField;
    @FXML private TableColumn<Patient, Integer> idColumn;
    @FXML private TableColumn<Patient, String> nameColumn;
    @FXML private TableColumn<Patient, String> cpfColumn;
    @FXML private TableColumn<Patient, String> phoneColumn;
    @FXML private Text selectedPatientText;


    //Treatments table view
    @FXML private TableView<Treatments> treatmentsTableView;
    @FXML private TableColumn<Patient, Integer> treatmentIdColumn;
    @FXML private TableColumn<Patient, String> treatmentNameColumn;
    @FXML private TableColumn<Patient, String> treatmentPriceColumn;
    @FXML private TableColumn<Patient, String> treatmentActionsColumn;


    //Services
    PatientService patientService = new PatientService();
    AppointmentService appointmentService = new AppointmentService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeColumns();
        loadComboBoxData();
        patientTableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        selectedPatientText.setText(newSelection.getName());
                    }
                }
        );
    }



    public void handleAddTreatment(ActionEvent actionEvent) {
    }

    @FXML
    private void handlePatientSearch() {
        try {
            String name = searchNameField.getText();
            List<Patient> patientsFound = patientService.searchPatientsByName(name);
            if (!patientsFound.isEmpty()) {
                patientTableView.getItems().setAll(patientsFound);
            } else {
                Alerts.showErrorAlert("Nenhum paciente encontrado.");
            }
        } catch (Exception e) {
            Alerts.showErrorAlert("Erro ao buscar paciente.");
            e.printStackTrace();
        }
    }


    @FXML
    private void handlePatientSelection(MouseEvent event) {
        if (event.getClickCount() == 1) {
            Patient selected = patientTableView.getSelectionModel().getSelectedItem();
            if (selected != null) {

            }
        }
    }

    private void loadComboBoxData(){
        try {
            List<Treatments> treatments = appointmentService.getAllTreatments();
            List<Dentist> dentists = appointmentService.getAllDentists();
            List<Employee> employees = appointmentService.getAllEmployees();

            treatments.forEach(treatment -> treatmentTypeComboBox.getItems().add(treatment.getName()));
            dentists.forEach(dentist -> dentistComboBox.getItems().add(dentist.getName()));
            employees.forEach(employee -> employeeComboBox.getItems().add(employee.getName()));

        } catch (Exception e) {
            Alerts.showErrorAlert("Erro ao carregar dados para os campos.");
            e.printStackTrace();
        }
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

    private void saveAppointmentData() {

    }


    private void initializeColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

}