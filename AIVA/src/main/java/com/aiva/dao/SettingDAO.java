package com.aiva.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.aiva.model.Setting;

/**
 * Data Access Object for Setting entities
 */
public class SettingDAO {

    
    
    /**
     * Creates a new setting in the database
     * @param setting Setting object to create
     * @return The created setting with ID populated
     * @throws SQLException if database operation fails
     */

    public Setting createSetting(Setting setting) throws SQLException {
        String sql = "INSERT INTO settings (user_id, setting_key, setting_value) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            if (setting.getUserId() != null) {
                pstmt.setInt(1, setting.getUserId());
            } else {
                pstmt.setNull(1, Types.INTEGER);
            }
            
            pstmt.setString(2, setting.getKey());
            pstmt.setString(3, setting.getValue());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating setting failed, no rows affected.");
            }
            
            // Use SQLite's last_insert_rowid() instead of getGeneratedKeys()
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                if (rs.next()) {
                    setting.setId(rs.getInt(1));
                } else {
                    throw new SQLException("Creating setting failed, no ID obtained.");
                }
            }
            
            return setting;
        }
    }
    
    /**
     * Gets a setting by key for a specific user
     * @param userId User ID (can be null for global settings)
     * @param key Setting key
     * @return Setting object or null if not found
     * @throws SQLException if database operation fails
     */
    public Setting getSettingByKey(Integer userId, String key) throws SQLException {
        String sql = "SELECT * FROM settings WHERE setting_key = ? AND (user_id = ? OR user_id IS NULL) " +
                     "ORDER BY user_id DESC LIMIT 1";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, key);
            
            if (userId != null) {
                pstmt.setInt(2, userId);
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToSetting(rs);
                } else {
                    return null;
                }
            }
        }
    }
    
    /**
     * Gets all settings for a user
     * @param userId User ID (can be null for global settings)
     * @return List of settings for the user
     * @throws SQLException if database operation fails
     */
    public List<Setting> getSettingsByUserId(Integer userId) throws SQLException {
        String sql = "SELECT * FROM settings WHERE user_id = ? OR user_id IS NULL ORDER BY setting_key";
        List<Setting> settings = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            if (userId != null) {
                pstmt.setInt(1, userId);
            } else {
                pstmt.setNull(1, Types.INTEGER);
            }
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    settings.add(mapResultSetToSetting(rs));
                }
            }
            
            return settings;
        }
    }
    
    /**
     * Updates a setting in the database
     * @param setting Setting object to update
     * @return true if successful, false otherwise
     * @throws SQLException if database operation fails
     */
    public boolean updateSetting(Setting setting) throws SQLException {
        String sql = "UPDATE settings SET setting_value = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, setting.getValue());
            pstmt.setInt(2, setting.getId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }
    
    /**
     * Updates or creates a setting by key
     * @param userId User ID (can be null for global settings)
     * @param key Setting key
     * @param value Setting value
     * @return true if successful, false otherwise
     * @throws SQLException if database operation fails
     */
    public boolean saveSettingByKey(Integer userId, String key, String value) throws SQLException {
        Setting existing = getSettingByKey(userId, key);
        
        if (existing != null) {
            existing.setValue(value);
            return updateSetting(existing);
        } else {
            Setting newSetting = new Setting();
            newSetting.setUserId(userId);
            newSetting.setKey(key);
            newSetting.setValue(value);
            return createSetting(newSetting) != null;
        }
    }
    
    /**
     * Deletes a setting from the database
     * @param id Setting ID to delete
     * @return true if successful, false otherwise
     * @throws SQLException if database operation fails
     */
    public boolean deleteSetting(int id) throws SQLException {
        String sql = "DELETE FROM settings WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }
    
    /**
     * Maps a ResultSet row to a Setting object
     * @param rs ResultSet positioned at the row to map
     * @return Mapped Setting object
     * @throws SQLException if database operation fails
     */
    private Setting mapResultSetToSetting(ResultSet rs) throws SQLException {
        Setting setting = new Setting();
        setting.setId(rs.getInt("id"));
        
        int userId = rs.getInt("user_id");
        if (!rs.wasNull()) {
            setting.setUserId(userId);
        }
        
        setting.setKey(rs.getString("setting_key"));
        setting.setValue(rs.getString("setting_value"));
        
        return setting;
    }
}
