package com.aiva.model;

import java.time.LocalDateTime;

/**
 * Represents a video in the AIVA application
 */
public class Video {
    private int id;
    private String title;
    private String prompt;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private String filePath;
    private String thumbnailPath;
    private int userId;
    
    public Video() {}
    
    public Video(int id, String title, String prompt, String status, LocalDateTime createdAt, 
                 LocalDateTime completedAt, String filePath, String thumbnailPath, int userId) {
        this.id = id;
        this.title = title;
        this.prompt = prompt;
        this.status = status;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
        this.filePath = filePath;
        this.thumbnailPath = thumbnailPath;
        this.userId = userId;
    }
    
    // Getters and setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getPrompt() {
        return prompt;
    }
    
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
    
    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public String getThumbnailPath() {
        return thumbnailPath;
    }
    
    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", prompt='" + prompt + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
