package org.example.javafxsistemadentista.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:clinic.db";
    private static Connection connection;
    private static boolean isInitialized = false;

    private DatabaseManager() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);

            if (!isInitialized) {
                initializeDatabase(connection);
                isInitialized = true;
            }
        }
        return connection;
    }

    private static void initializeDatabase(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            // Enable foreign key constraints
            stmt.execute("PRAGMA foreign_keys = ON");

            // patients table (simplificada, incluindo notes diretamente)
            stmt.execute("CREATE TABLE IF NOT EXISTS patients (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "cpf TEXT NOT NULL," +
                    "phone TEXT NOT NULL," +
                    "email TEXT," +
                    "birth_date TEXT," +
                    "address TEXT," +
                    "notes TEXT," +
                    "created_at TEXT DEFAULT (datetime('now', 'localtime'))," +
                    "UNIQUE(cpf)," +
                    "UNIQUE(phone))");

            // dentists
            stmt.execute("CREATE TABLE IF NOT EXISTS dentists (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "cpf TEXT NOT NULL," +
                    "specialization TEXT," +
                    "phone TEXT," +
                    "email TEXT," +
                    "UNIQUE(cpf))");

            // employees
            stmt.execute("CREATE TABLE IF NOT EXISTS employees (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "cpf TEXT NOT NULL," +
                    "role TEXT NOT NULL," +
                    "phone TEXT," +
                    "email TEXT," +
                    "UNIQUE(cpf))");

            // treatments
            stmt.execute("CREATE TABLE IF NOT EXISTS treatments (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "cost REAL NOT NULL DEFAULT 0.0," +
                    "description TEXT)");

            // exams (agora referenciando patients diretamente)
            stmt.execute("CREATE TABLE IF NOT EXISTS exams (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "patient_id INTEGER NOT NULL," +
                    "type TEXT NOT NULL," +
                    "requested_by INTEGER NOT NULL," +
                    "request_date TEXT DEFAULT (datetime('now', 'localtime'))," +
                    "result_path TEXT," +
                    "status TEXT NOT NULL DEFAULT 'REQUESTED'," +
                    "notes TEXT," +
                    "FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE," +
                    "FOREIGN KEY (requested_by) REFERENCES dentists(id))");

            // appointments
            stmt.execute("CREATE TABLE IF NOT EXISTS appointments (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "patient_id INTEGER NOT NULL," +
                    "dentist_id INTEGER NOT NULL," +
                    "employee_id INTEGER NOT NULL," +
                    "date_time TEXT NOT NULL," +
                    "duration INTEGER NOT NULL DEFAULT 30 CHECK (duration > 0 AND duration <= 240)," +
                    "status TEXT NOT NULL DEFAULT 'SCHEDULED' CHECK (status IN ('SCHEDULED', 'COMPLETED', 'CANCELLED', 'NO_SHOW'))," +
                    "notes TEXT," +
                    "total_price REAL NOT NULL CHECK (total_price >= 0)," +
                    "created_at TEXT DEFAULT (datetime('now', 'localtime'))," +
                    "FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE," +
                    "FOREIGN KEY (dentist_id) REFERENCES dentists(id)," +
                    "FOREIGN KEY (employee_id) REFERENCES employees(id))");

            // appointment_treatments
            stmt.execute("CREATE TABLE IF NOT EXISTS appointment_treatments (" +
                    "appointment_id INTEGER NOT NULL," +
                    "treatment_id INTEGER NOT NULL," +
                    "quantity INTEGER DEFAULT 1 CHECK (quantity > 0)," +
                    "notes TEXT," +
                    "PRIMARY KEY (appointment_id, treatment_id)," +
                    "FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE CASCADE," +
                    "FOREIGN KEY (treatment_id) REFERENCES treatments(id))");

            // Indexes
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_appointments_patient ON appointments(patient_id)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_appointments_dentist ON appointments(dentist_id)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_appointments_datetime ON appointments(date_time)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_exams_patient ON exams(patient_id)");

        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                isInitialized = false;
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    // Reset database (for development/testing)
    public static void resetDatabase() {
        try {
            Connection conn = getConnection();
            try (Statement stmt = conn.createStatement()) {
                // Disable foreign keys temporarily
                stmt.execute("PRAGMA foreign_keys = OFF");

                // Drop all tables (ordem inversa por causa das constraints)
                String[] tables = {
                        "appointment_treatments",
                        "appointments",
                        "exams",
                        "treatments",
                        "employees",
                        "dentists",
                        "patients"  // Removida a tabela patient_profiles
                };

                for (String table : tables) {
                    try {
                        stmt.execute("DROP TABLE IF EXISTS " + table);
                    } catch (SQLException e) {
                        System.err.println("Error dropping table " + table + ": " + e.getMessage());
                    }
                }

                // Re-enable foreign keys
                stmt.execute("PRAGMA foreign_keys = ON");
            }

            // Reset the initialization flag
            isInitialized = false;

            // Reinitialize database
            initializeDatabase(conn);
            isInitialized = true;

        } catch (SQLException e) {
            System.err.println("Error resetting database: " + e.getMessage());
        }
    }
}