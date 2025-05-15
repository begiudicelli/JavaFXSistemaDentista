package org.example.javafxsistemadentista.services;

import org.example.javafxsistemadentista.daos.PatientDAO;
import org.example.javafxsistemadentista.entities.Patient;

import java.sql.SQLException;

public class PatientService {
    private final PatientDAO patientDAO = new PatientDAO();

    public void savePatient(Patient patient) throws SQLException {
        patientDAO.save(patient);
    }

    public Patient searchPatientByCpf(String cpf){
        return patientDAO.findByCpf(cpf);
    }

    public void updatePatient(Patient patient){
        patientDAO.updatePatient(patient);
    }
}
