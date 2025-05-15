package org.example.javafxsistemadentista;

import org.example.javafxsistemadentista.connection.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseReset {

    public static void resetDatabase() {
        String dropSql = "DROP TABLE IF EXISTS patients";

        String createSql = """
            CREATE TABLE IF NOT EXISTS patients (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                cpf TEXT NOT NULL,
                phone TEXT NOT NULL,
                email TEXT,
                birth_date TEXT,
                address TEXT,
                created_at TEXT DEFAULT (datetime('now', 'localtime')),
                UNIQUE(cpf),
                UNIQUE(phone)
            );
            """;

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(dropSql);
            stmt.executeUpdate(createSql);

            System.out.println("Banco de dados resetado com sucesso.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
