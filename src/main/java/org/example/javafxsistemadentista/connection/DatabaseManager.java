package org.example.javafxsistemadentista.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:clinic.db";
    private static Connection connection;
    private static boolean isInitialized = false;

    // Private constructor to prevent instantiation
    private DatabaseManager() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);

            // Only initialize the database if it hasn't been initialized yet
            if (!isInitialized) {
                initializeDatabase(connection);
                isInitialized = true;
            }
        }
        return connection;
    }

    private static void initializeDatabase(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            // Enable foreign keys
            stmt.execute("PRAGMA foreign_keys = ON");

            // Create patients table
            stmt.execute("CREATE TABLE IF NOT EXISTS patients (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "cpf TEXT NOT NULL," +
                    "phone TEXT NOT NULL," +
                    "email TEXT," +
                    "birth_date TEXT," +
                    "address TEXT," +
                    "created_at TEXT DEFAULT (datetime('now', 'localtime'))," +
                    "UNIQUE(cpf)," +
                    "UNIQUE(phone))");

            // Create patient_profiles table
            stmt.execute("CREATE TABLE IF NOT EXISTS patient_profiles (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "patient_id INTEGER NOT NULL," +
                    "blood_type TEXT," +
                    "allergies TEXT," +
                    "notes TEXT," +
                    "FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE," +
                    "UNIQUE(patient_id))");

            // Create dentists table
            stmt.execute("CREATE TABLE IF NOT EXISTS dentists (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "cpf TEXT NOT NULL," +
                    "specialization TEXT," +
                    "phone TEXT," +
                    "email TEXT," +
                    "UNIQUE(cpf))");

            // Create employees table
            stmt.execute("CREATE TABLE IF NOT EXISTS employees (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "cpf TEXT NOT NULL," +
                    "name TEXT NOT NULL," +
                    "role TEXT NOT NULL," +
                    "phone TEXT," +
                    "email TEXT," +
                    "UNIQUE(cpf))");

            // Create treatments table
            stmt.execute("CREATE TABLE IF NOT EXISTS treatments (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "cost REAL NOT NULL DEFAULT 0.0," +
                    "description TEXT)");

            // Create exams table
            stmt.execute("CREATE TABLE IF NOT EXISTS exams (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "patient_id INTEGER NOT NULL," +
                    "type TEXT NOT NULL," +
                    "requested_by INTEGER NOT NULL," +
                    "request_date TEXT DEFAULT (datetime('now', 'localtime'))," +
                    "result_path TEXT," +
                    "status TEXT NOT NULL DEFAULT 'Requested'," +
                    "notes TEXT," +
                    "FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE," +
                    "FOREIGN KEY (requested_by) REFERENCES dentists(id))");

            // Create appointments table
            stmt.execute("CREATE TABLE IF NOT EXISTS appointments (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "patient_id INTEGER NOT NULL," +
                    "dentist_id INTEGER NOT NULL," +
                    "employee_id INTEGER NOT NULL," +
                    "date_time TEXT NOT NULL," +
                    "duration INTEGER NOT NULL DEFAULT 30," +
                    "status TEXT NOT NULL DEFAULT 'Scheduled'," +
                    "price REAL DEFAULT 0.0," +
                    "notes TEXT," +
                    "created_at TEXT DEFAULT (datetime('now', 'localtime'))," +
                    "FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE," +
                    "FOREIGN KEY (dentist_id) REFERENCES dentists(id)," +
                    "FOREIGN KEY (employee_id) REFERENCES employees(id))");

            // Create appointment_treatments table
            stmt.execute("CREATE TABLE IF NOT EXISTS appointment_treatments (" +
                    "appointment_id INTEGER NOT NULL," +
                    "treatment_id INTEGER NOT NULL," +
                    "quantity INTEGER DEFAULT 1," +
                    "notes TEXT," +
                    "PRIMARY KEY (appointment_id, treatment_id)," +
                    "FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE CASCADE," +
                    "FOREIGN KEY (treatment_id) REFERENCES treatments(id))");

            // Create indexes
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
                isInitialized = false;  // Reset initialization flag when connection is closed
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    // Optional: Reset database (for development/testing)
    public static void resetDatabase() {
        try {
            Connection conn = getConnection();
            try (Statement stmt = conn.createStatement()) {
                // Disable foreign keys temporarily
                stmt.execute("PRAGMA foreign_keys = OFF");

                // Drop all tables
                String[] tables = {
                        "appointment_treatments",
                        "appointments",
                        "exams",
                        "treatments",
                        "employees",
                        "dentists",
                        "patient_profiles",
                        "patients"
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