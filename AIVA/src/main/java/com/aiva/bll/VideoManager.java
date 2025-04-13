package com.aiva.bll;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.aiva.dao.VideoDAO;
import com.aiva.model.Video;

/**
 * Business Logic Layer for Video management
 */
public class VideoManager {
    private VideoDAO videoDAO;
    
    public VideoManager() {
        this.videoDAO = new VideoDAO();
    }
    
    /**
     * Creates a new video from a prompt
     * @param title Video title
     * @param prompt Video generation prompt
     * @param userId User ID
     * @return Created video or null if failed
     */
    public Video createVideo(String title, String prompt, int userId) {
        try {
            Video video = new Video();
            video.setTitle(title);
            video.setPrompt(prompt);
            video.setStatus("pending"); // Initial status
            video.setUserId(userId);
            
            return videoDAO.createVideo(video);
        } catch (SQLException e) {
            System.err.println("Error creating video: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Gets a video by ID
     * @param videoId Video ID
     * @return Video object or null if not found
     */
    public Video getVideoById(int videoId) {
        try {
            return videoDAO.getVideoById(videoId);
        } catch (SQLException e) {
            System.err.println("Error getting video: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Gets all videos for a user
     * @param userId User ID
     * @return List of videos for the user
     */
    public List<Video> getVideosByUserId(int userId) {
        try {
            return videoDAO.getVideosByUserId(userId);
        } catch (SQLException e) {
            System.err.println("Error getting videos: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Gets all videos
     * @return List of all videos
     */
    public List<Video> getAllVideos() {
        try {
            return videoDAO.getAllVideos();
        } catch (SQLException e) {
            System.err.println("Error getting videos: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Updates a video's status
     * @param videoId Video ID
     * @param status New status
     * @return true if successful, false otherwise
     */
    public boolean updateVideoStatus(int videoId, String status) {
        try {
            return videoDAO.updateVideoStatus(videoId, status);
        } catch (SQLException e) {
            System.err.println("Error updating video status: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Marks a video as completed
     * @param videoId Video ID
     * @param filePath Path to the generated video file
     * @param thumbnailPath Path to the video thumbnail
     * @return true if successful, false otherwise
     */
    public boolean completeVideo(int videoId, String filePath, String thumbnailPath) {
        try {
            Video video = videoDAO.getVideoById(videoId);
            if (video != null) {
                video.setStatus("completed");
                video.setCompletedAt(LocalDateTime.now());
                video.setFilePath(filePath);
                video.setThumbnailPath(thumbnailPath);
                return videoDAO.updateVideo(video);
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error completing video: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Deletes a video
     * @param videoId Video ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteVideo(int videoId) {
        try {
            return videoDAO.deleteVideo(videoId);
        } catch (SQLException e) {
            System.err.println("Error deleting video: " + e.getMessage());
            return false;
        }
    }
}
