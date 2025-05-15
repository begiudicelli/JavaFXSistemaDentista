package org.example.javafxsistemadentista.daos;

import org.example.javafxsistemadentista.connection.DatabaseManager;
import org.example.javafxsistemadentista.daos.interfaces.IPatientDAO;
import org.example.javafxsistemadentista.entities.Patient;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO implements IPatientDAO {

    private Connection connection;

    public PatientDAO() {
    }

    public PatientDAO(Connection conn) {
        this.connection = conn;
    }

    @Override
    public void save(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (name, cpf, phone, email, birth_date, address) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, patient.getName());
            stmt.setString(2, patient.getCpf());
            stmt.setString(3, patient.getPhone());
            stmt.setString(4, patient.getEmail());
            stmt.setString(5, patient.getBirthDate().toString());
            stmt.setString(6, patient.getAddress());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    patient.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public Patient findByCpf(String cpf) {
        String sql = "SELECT * FROM patients WHERE cpf = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Patient patient = new Patient();
                    patient.setId(rs.getInt("id"));
                    patient.setName(rs.getString("name"));
                    patient.setCpf(rs.getString("cpf"));
                    patient.setPhone(rs.getString("phone"));
                    patient.setEmail(rs.getString("email"));
                    String birthDateStr = rs.getString("birth_date");
                    if (birthDateStr != null && !birthDateStr.isEmpty()) {
                        patient.setBirthDate(LocalDate.parse(birthDateStr));
                    }

                    patient.setAddress(rs.getString("address"));
                    return patient;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Patient patient) {
        String sql = "UPDATE patients SET name = ?, phone = ?, email = ?, birth_date = ?, address = ? WHERE cpf = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, patient.getName());
            stmt.setString(2, patient.getPhone());
            stmt.setString(3, patient.getEmail());
            stmt.setString(4, patient.getBirthDate().toString());
            stmt.setString(5, patient.getAddress());
            stmt.setString(6, patient.getCpf());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar paciente: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Patient> findAll() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Patient patient = new Patient();
                patient.setId(rs.getInt("id"));
                patient.setName(rs.getString("name"));
                patient.setCpf(rs.getString("cpf"));
                patient.setPhone(rs.getString("phone"));
                patient.setEmail(rs.getString("email"));
                String birthDateStr = rs.getString("birth_date");
                if (birthDateStr != null && !birthDateStr.isEmpty()) {
                    patient.setBirthDate(LocalDate.parse(birthDateStr));
                }

                patient.setAddress(rs.getString("address"));
                patients.add(patient);
            }
        }
        return patients;
    }
}