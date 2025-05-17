package org.example.javafxsistemadentista;

import org.example.javafxsistemadentista.connection.DatabaseManager;
import org.example.javafxsistemadentista.daos.PatientDAO;
import org.example.javafxsistemadentista.daos.PatientProfileDAO;
import org.example.javafxsistemadentista.entities.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TestInsertPatientAndAppointments {

    public static void main(String[] args) {
        //DatabaseManager.resetDatabase();
        try {
            // 1. Criar paciente
            Patient patient = new Patient();
            patient.setName("João da Silva");
            patient.setCpf("12345678901");
            patient.setPhone("11999999999");
            patient.setEmail("joao@email.com");
            patient.setBirthDate(LocalDate.parse("1990-01-01"));
            patient.setAddress("Rua das Flores, 123");

            // 2. Criar perfil
            PatientProfile profile = new PatientProfile();
            profile.setNotes("Paciente com histórico de cáries.");

            patient.setPatientProfile(profile);

            // 3. Salvar paciente (com perfil)
            PatientDAO patientDAO = new PatientDAO();
            patientDAO.save(patient); // <- insere na tabela `patients` e `patient_profiles`

            System.out.println("Paciente salvo com ID: " + patient.getId());

            // 4. Criar consultas (appointments)
            Dentist dentist = new Dentist();
            dentist.setId(1); // ID existente no banco!

            Employee employee = new Employee();
            employee.setId(1); // ID existente no banco!

            Appointments appointment1 = new Appointments();
            appointment1.setPatient(patient);
            appointment1.setDentist(dentist);
            appointment1.setEmployee(employee);
            appointment1.setDateTime(LocalDate.from(LocalDateTime.now().plusDays(1)));
            appointment1.setDuration(30);
            appointment1.setStatus("Scheduled");
            appointment1.setNotes("Consulta inicial.");
            appointment1.setTotalPrice(150.0);

            Appointments appointment2 = new Appointments();
            appointment2.setPatient(patient);
            appointment2.setDentist(dentist);
            appointment2.setEmployee(employee);
            appointment2.setDateTime(LocalDate.from(LocalDateTime.now().plusDays(7)));
            appointment2.setDuration(45);
            appointment2.setStatus("Scheduled");
            appointment2.setNotes("Limpeza dental.");
            appointment2.setTotalPrice(200.0);

            // 5. Salvar consultas
            PatientProfileDAO profileDAO = new PatientProfileDAO();
            profileDAO.saveAppointments(List.of(appointment1, appointment2));

            System.out.println("Consultas salvas para o paciente.");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar paciente ou consultas:");
            e.printStackTrace();
        }
    }
}
