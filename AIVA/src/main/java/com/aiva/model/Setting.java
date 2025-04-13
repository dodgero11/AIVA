package com.aiva.model;

/**
 * Represents a user setting in the AIVA application
 */
public class Setting {
    private int id;
    private Integer userId; // Can be null for global settings
    private String key;
    private String value;
    
    public Setting() {}
    
    public Setting(int id, Integer userId, String key, String value) {
        this.id = id;
        this.userId = userId;
        this.key = key;
        this.value = value;
    }
    
    // Getters and setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return "Setting{" +
                "id=" + id +
                ", userId=" + userId +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
