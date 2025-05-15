package org.example.javafxsistemadentista.daos.interfaces;

import org.example.javafxsistemadentista.entities.Patient;

import java.sql.SQLException;
import java.util.List;

public interface IPatientDAO {
    void save(Patient patient) throws SQLException;
    Patient findByCpf(String cpf)throws SQLException;
    void update(Patient patient)throws SQLException;
    List<Patient> findAll()throws SQLException;
}