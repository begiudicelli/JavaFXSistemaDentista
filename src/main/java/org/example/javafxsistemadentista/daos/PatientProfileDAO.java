package org.example.javafxsistemadentista.daos;

import org.example.javafxsistemadentista.connection.DatabaseManager;
import org.example.javafxsistemadentista.daos.interfaces.IPatientDAO;
import org.example.javafxsistemadentista.entities.Appointments;
import org.example.javafxsistemadentista.entities.Patient;
import org.example.javafxsistemadentista.entities.PatientProfile;

import java.sql.*;
import java.util.List;

public class PatientProfileDAO {

    public PatientProfileDAO() {

    }
    private void save(Patient patient) throws SQLException {
        String sql = "INSERT INTO patient_profiles(patient_id, notes) " +
                "VALUES (?, ?)";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            stmt.setInt(1, patient.getId());
            stmt.setString(2, patient.getPatientProfile().getNotes());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int profileId = rs.getInt(1);
                    patient.getPatientProfile().setId(profileId);
                }
            }
        }
    }

    public void saveAppointments(List<Appointments> appointments) throws SQLException {
        String sql = "INSERT INTO appointments(patient_id, dentist_id, employee_id, date_time, duration, status, notes, total_price) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (Appointments app : appointments) {
                stmt.setInt(1, app.getPatient().getId());
                stmt.setInt(2, app.getDentist().getId());
                stmt.setInt(3, app.getEmployee().getId());
                stmt.setString(4, app.getDateTime().toString());
                stmt.setInt(5, app.getDuration());
                stmt.setString(6, app.getStatus());
                stmt.setString(7, app.getNotes());
                stmt.setDouble(8, app.getTotalPrice());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }







    public Patient findByCpf(String cpf) throws SQLException {
        return null;
    }

    public void update(Patient patient) throws SQLException {

    }

    public List<Patient> findAll() throws SQLException {
        return List.of();
    }
}
