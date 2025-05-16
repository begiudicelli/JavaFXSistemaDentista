package org.example.javafxsistemadentista.services;

import org.example.javafxsistemadentista.daos.PatientDAO;
import org.example.javafxsistemadentista.entities.Patient;
import org.example.javafxsistemadentista.validators.PatientValidators;

import java.sql.SQLException;
import java.util.List;

public class PatientService {
    private final PatientDAO patientDAO = new PatientDAO();
    private final PatientValidators validators = new PatientValidators();

    public void savePatient(Patient patient) throws SQLException {
        validators.validatePatient(patient);
        patientDAO.save(patient);
    }

    public Patient searchPatientByCpf(String cpf){
        return patientDAO.findByCpf(cpf);
    }

    public void updatePatient(Patient patient) {
        patientDAO.update(patient);
    }

    public List<Patient> searchPatientsByName(String name) throws SQLException {
        return patientDAO.findByName(name);
    }

}
