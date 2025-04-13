package com.aiva.bll;

import com.aiva.dao.SettingDAO;
import com.aiva.model.Setting;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Business Logic Layer for Settings management
 */
public class SettingsManager {
    private SettingDAO settingDAO;
    
    // Default settings
    private static final Map<String, String> DEFAULT_SETTINGS = new HashMap<>();
    static {
        DEFAULT_SETTINGS.put("frame_size", "512x512");
        DEFAULT_SETTINGS.put("generator_model", "Dall-E3");
        DEFAULT_SETTINGS.put("art_style", "Stock photo");
        DEFAULT_SETTINGS.put("ai_voice_enabled", "true");
    }
    
    public SettingsManager() {
        this.settingDAO = new SettingDAO();
    }
    
    /**
     * Gets a setting value for a user
     * @param userId User ID (can be null for global settings)
     * @param key Setting key
     * @return Setting value or default value if not found
     */
    public String getSetting(Integer userId, String key) {
        try {
            Setting setting = settingDAO.getSettingByKey(userId, key);
            if (setting != null) {
                return setting.getValue();
            }
            
            // Return default value if available
            return DEFAULT_SETTINGS.getOrDefault(key, null);
        } catch (SQLException e) {
            System.err.println("Error getting setting: " + e.getMessage());
            return DEFAULT_SETTINGS.getOrDefault(key, null);
        }
    }
    
    /**
     * Gets all settings for a user as a map
     * @param userId User ID (can be null for global settings)
     * @return Map of setting keys to values
     */
    public Map<String, String> getAllSettings(Integer userId) {
        Map<String, String> settingsMap = new HashMap<>(DEFAULT_SETTINGS);
        
        try {
            List<Setting> settings = settingDAO.getSettingsByUserId(userId);
            for (Setting setting : settings) {
                settingsMap.put(setting.getKey(), setting.getValue());
            }
        } catch (SQLException e) {
            System.err.println("Error getting settings: " + e.getMessage());
        }
        
        return settingsMap;
    }
    
    /**
     * Saves a setting value for a user
     * @param userId User ID (can be null for global settings)
     * @param key Setting key
     * @param value Setting value
     * @return true if successful, false otherwise
     */
    public boolean saveSetting(Integer userId, String key, String value) {
        try {
            return settingDAO.saveSettingByKey(userId, key, value);
        } catch (SQLException e) {
            System.err.println("Error saving setting: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Saves multiple settings for a user
     * @param userId User ID (can be null for global settings)
     * @param settings Map of setting keys to values
     * @return true if all settings were saved successfully, false otherwise
     */
    public boolean saveSettings(Integer userId, Map<String, String> settings) {
        boolean allSuccessful = true;
        
        for (Map.Entry<String, String> entry : settings.entrySet()) {
            boolean success = saveSetting(userId, entry.getKey(), entry.getValue());
            if (!success) {
                allSuccessful = false;
            }
        }
        
        return allSuccessful;
    }
    
    /**
     * Resets a setting to its default value
     * @param userId User ID
     * @param key Setting key
     * @return true if successful, false otherwise
     */
    public boolean resetSetting(Integer userId, String key) {
        String defaultValue = DEFAULT_SETTINGS.get(key);
        if (defaultValue != null) {
            return saveSetting(userId, key, defaultValue);
        }
        return false;
    }
    
    /**
     * Resets all settings to their default values
     * @param userId User ID
     * @return true if successful, false otherwise
     */
    public boolean resetAllSettings(Integer userId) {
        return saveSettings(userId, DEFAULT_SETTINGS);
    }
}
