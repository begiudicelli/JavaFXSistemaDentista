package org.example.javafxsistemadentista;

import org.example.javafxsistemadentista.connection.DatabaseManager;
import org.example.javafxsistemadentista.daos.PatientDAO;
import org.example.javafxsistemadentista.entities.Patient;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseManager.getConnection();
            System.out.println("Database connection established successfully");

            PatientDAO patientDAO = new PatientDAO(conn);
            List<Patient> patientList = patientDAO.findAll();

            for (Patient patient : patientList) {
                System.out.println(patient);
            }

        } catch (SQLException e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
