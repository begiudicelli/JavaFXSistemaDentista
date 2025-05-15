package org.example.javafxsistemadentista.daos;

import org.example.javafxsistemadentista.daos.interfaces.IPatientDAO;
import org.example.javafxsistemadentista.entities.Patient;
import org.example.javafxsistemadentista.entities.PatientProfile;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PatientProfileDAO implements IPatientDAO {
    private Connection connection;

    public PatientProfileDAO() {
    }

    public PatientProfileDAO(Connection conn) {
        this.connection = conn;
    }

    @Override
    public void save(Patient patient) throws SQLException {

    }

    @Override
    public Patient findByCpf(String cpf) throws SQLException {
        return null;
    }

    @Override
    public void update(Patient patient) throws SQLException {

    }

    @Override
    public List<Patient> findAll() throws SQLException {
        return List.of();
    }
}
