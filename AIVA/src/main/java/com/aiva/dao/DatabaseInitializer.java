package com.aiva.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Initializes the database schema for the AIVA application
 */
public class DatabaseInitializer {

    /**
     * Creates all necessary tables if they don't exist
     */
    public static void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Create users table
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL UNIQUE, " +
                "password_hash TEXT NOT NULL, " +
                "email TEXT UNIQUE, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
            stmt.execute(createUsersTable);
            
            // Create videos table
            String createVideosTable = "CREATE TABLE IF NOT EXISTS videos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL, " +
                "prompt TEXT NOT NULL, " +
                "status TEXT NOT NULL, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "completed_at TIMESTAMP, " +
                "file_path TEXT, " +
                "thumbnail_path TEXT, " +
                "user_id INTEGER, " +
                "FOREIGN KEY (user_id) REFERENCES users (id)" +
                ")";
            stmt.execute(createVideosTable);
            
            // Create settings table
            String createSettingsTable = "CREATE TABLE IF NOT EXISTS settings (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "setting_key TEXT NOT NULL, " +
                "setting_value TEXT, " +
                "FOREIGN KEY (user_id) REFERENCES users (id), " +
                "UNIQUE(user_id, setting_key)" +
                ")";
            stmt.execute(createSettingsTable);
            
            // Create ai_models table
            String createModelsTable = "CREATE TABLE IF NOT EXISTS ai_models (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL UNIQUE, " +
                "description TEXT, " +
                "is_active BOOLEAN DEFAULT 1" +
                ")";
            stmt.execute(createModelsTable);
            
            // Insert default AI models
            String insertDefaultModels = "INSERT OR IGNORE INTO ai_models (name, description) VALUES " +
                "('Dall-E3', 'OpenAI''s advanced image generation model'), " +
                "('Stable Diffusion', 'Open source image generation model')";
            stmt.execute(insertDefaultModels);
            
            System.out.println("Database initialized successfully");
            
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Clears all tables in the database
    public static void clearDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Clear users table
            String clearUsersTable = "DELETE FROM users";
            stmt.execute(clearUsersTable);
            
            // Clear videos table
            String clearVideosTable = "DELETE FROM videos";
            stmt.execute(clearVideosTable);
            
            // Clear settings table
            String clearSettingsTable = "DELETE FROM settings";
            stmt.execute(clearSettingsTable);
            
            // Clear ai_models table
            String clearModelsTable = "DELETE FROM ai_models";
            stmt.execute(clearModelsTable);
            
            System.out.println("Database cleared successfully");
            
        } catch (SQLException e) {
            System.err.println("Error clearing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
