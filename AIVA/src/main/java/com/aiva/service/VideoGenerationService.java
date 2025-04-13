package com.aiva.service;

import java.util.Map;

import com.aiva.bll.SettingsManager;
import com.aiva.bll.VideoManager;
import com.aiva.model.Video;

/**
 * Service class for video generation functionality
 */
public class VideoGenerationService {
    private VideoManager videoManager;
    private SettingsManager settingsManager;
    
    public VideoGenerationService() {
        this.videoManager = new VideoManager();
        this.settingsManager = new SettingsManager();
    }
    
    /**
     * Generates a video from a prompt
     * @param prompt User prompt for video generation
     * @param userId User ID
     * @return Generated video or null if failed
     */
    public Video generateVideo(String prompt, int userId) {
        // Create a title from the prompt (first 50 chars)
        String title = prompt.length() > 50 ? prompt.substring(0, 47) + "..." : prompt;
        
        // Create the video in the database
        Video video = videoManager.createVideo(title, prompt, userId);
        if (video == null) {
            return null;
        }
        
        // Get user settings
        Map<String, String> settings = settingsManager.getAllSettings(userId);
        String frameSize = settings.get("frame_size");
        String generatorModel = settings.get("generator_model");
        String artStyle = settings.get("art_style");
        boolean aiVoiceEnabled = Boolean.parseBoolean(settings.get("ai_voice_enabled"));
        
        // Start the generation process in a background thread
        new Thread(() -> {
            try {
                // Update status to processing
                videoManager.updateVideoStatus(video.getId(), "processing");
                
                // TODO: Implement actual video generation logic here
                // This would involve calling external APIs or local AI models
                
                // For now, simulate processing time
                Thread.sleep(5000);
                
                // Update the video with completion info
                String filePath = "videos/" + video.getId() + ".mp4";
                String thumbnailPath = "thumbnails/" + video.getId() + ".jpg";
                videoManager.completeVideo(video.getId(), filePath, thumbnailPath);
                
            } catch (Exception e) {
                System.err.println("Error generating video: " + e.getMessage());
                videoManager.updateVideoStatus(video.getId(), "failed");
            }
        }).start();
        
        return video;
    }
    
    /**
     * Gets the current settings for a user
     * @param userId User ID
     * @return Map of setting keys to values
     */
    public Map<String, String> getUserSettings(int userId) {
        return settingsManager.getAllSettings(userId);
    }
    
    /**
     * Updates a user setting
     * @param userId User ID
     * @param key Setting key
     * @param value Setting value
     * @return true if successful, false otherwise
     */
    public boolean updateSetting(int userId, String key, String value) {
        return settingsManager.saveSetting(userId, key, value);
    }
}
