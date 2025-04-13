package com.aiva.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Handles database connections for the AIVA application
 */
public class DatabaseConnection {
    private static final String DB_URL = "jdbc:sqlite:aiva.db";
    private static Connection connection;
    
    /**
     * Gets a connection to the SQLite database
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
            // Enable foreign keys
            connection.createStatement().execute("PRAGMA foreign_keys = ON");
        }
        return connection;
    }
    
    /**
     * Closes the database connection if open
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}
