package org.example.javafxsistemadentista;

import org.example.javafxsistemadentista.connection.DatabaseManager;
import org.example.javafxsistemadentista.daos.PatientDAO;

import org.example.javafxsistemadentista.entities.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;


public class Test {

    public static void main(String[] args) {
        //resetDatabase();
        try {
            Patient patient = new Patient();
            patient.setName("Carlos Silva");
            patient.setCpf("98765432100");
            patient.setPhone("11988887777");
            patient.setEmail("carlos@email.com");
            patient.setBirthDate(LocalDate.parse("1985-05-15"));
            patient.setAddress("Avenida Principal, 456");
            patient.setNotes("Primeiro cadastro.");

            PatientDAO patientDAO = new PatientDAO();
            patientDAO.save(patient);

            System.out.println("Paciente salvo com ID: " + patient.getId());
            System.out.println("Notes do paciente: '" + patient.getNotes() + "'");


        } catch (SQLException e) {
            System.err.println("Erro ao salvar paciente ou consulta:");
            e.printStackTrace();
        }
    }
}
