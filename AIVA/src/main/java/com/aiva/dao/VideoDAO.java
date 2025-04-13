package com.aiva.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.aiva.model.Video;

/**
 * Data Access Object for Video entities
 */
public class VideoDAO {
    
    /**
     * Creates a new video in the database
     * @param video Video object to create
     * @return The created video with ID populated
     * @throws SQLException if database operation fails
     */
    public Video createVideo(Video video) throws SQLException {
        String sql = "INSERT INTO videos (title, prompt, status, user_id) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, video.getTitle());
            pstmt.setString(2, video.getPrompt());
            pstmt.setString(3, video.getStatus());
            pstmt.setInt(4, video.getUserId());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating video failed, no rows affected.");
            }
            
            // Use SQLite's last_insert_rowid() instead of getGeneratedKeys()
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                if (rs.next()) {
                    video.setId(rs.getInt(1));
                } else {
                    throw new SQLException("Creating video failed, no ID obtained.");
                }
            }
            
            // Get the created_at timestamp
            String getVideo = "SELECT created_at FROM videos WHERE id = ?";
            try (PreparedStatement getStmt = conn.prepareStatement(getVideo)) {
                getStmt.setInt(1, video.getId());
                try (ResultSet rs = getStmt.executeQuery()) {
                    if (rs.next()) {
                        video.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    }
                }
            }
            
            return video;
        }
    }
    
    /**
     * Gets a video by ID
     * @param id Video ID
     * @return Video object or null if not found
     * @throws SQLException if database operation fails
     */
    public Video getVideoById(int id) throws SQLException {
        String sql = "SELECT * FROM videos WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToVideo(rs);
                } else {
                    return null;
                }
            }
        }
    }
    
    /**
     * Gets all videos for a user
     * @param userId User ID
     * @return List of videos for the user
     * @throws SQLException if database operation fails
     */
    public List<Video> getVideosByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM videos WHERE user_id = ? ORDER BY created_at DESC";
        List<Video> videos = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    videos.add(mapResultSetToVideo(rs));
                }
            }
            
            return videos;
        }
    }
    
    /**
     * Gets all videos
     * @return List of all videos
     * @throws SQLException if database operation fails
     */
    public List<Video> getAllVideos() throws SQLException {
        String sql = "SELECT * FROM videos ORDER BY created_at DESC";
        List<Video> videos = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                videos.add(mapResultSetToVideo(rs));
            }
            
            return videos;
        }
    }
    
    /**
     * Updates a video in the database
     * @param video Video object to update
     * @return true if successful, false otherwise
     * @throws SQLException if database operation fails
     */
    public boolean updateVideo(Video video) throws SQLException {
        String sql = "UPDATE videos SET title = ?, prompt = ?, status = ?, " +
                     "completed_at = ?, file_path = ?, thumbnail_path = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, video.getTitle());
            pstmt.setString(2, video.getPrompt());
            pstmt.setString(3, video.getStatus());
            
            if (video.getCompletedAt() != null) {
                pstmt.setTimestamp(4, Timestamp.valueOf(video.getCompletedAt()));
            } else {
                pstmt.setNull(4, Types.TIMESTAMP);
            }
            
            pstmt.setString(5, video.getFilePath());
            pstmt.setString(6, video.getThumbnailPath());
            pstmt.setInt(7, video.getId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }
    
    /**
     * Updates the status of a video
     * @param videoId Video ID
     * @param status New status
     * @return true if successful, false otherwise
     * @throws SQLException if database operation fails
     */
    public boolean updateVideoStatus(int videoId, String status) throws SQLException {
        String sql = "UPDATE videos SET status = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            pstmt.setInt(2, videoId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }
    
    /**
     * Deletes a video from the database
     * @param id Video ID to delete
     * @return true if successful, false otherwise
     * @throws SQLException if database operation fails
     */
    public boolean deleteVideo(int id) throws SQLException {
        String sql = "DELETE FROM videos WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }
    
    /**
     * Maps a ResultSet row to a Video object
     * @param rs ResultSet positioned at the row to map
     * @return Mapped Video object
     * @throws SQLException if database operation fails
     */
    private Video mapResultSetToVideo(ResultSet rs) throws SQLException {
        Video video = new Video();
        video.setId(rs.getInt("id"));
        video.setTitle(rs.getString("title"));
        video.setPrompt(rs.getString("prompt"));
        video.setStatus(rs.getString("status"));
        video.setUserId(rs.getInt("user_id"));
        
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            video.setCreatedAt(createdAt.toLocalDateTime());
        }
        
        Timestamp completedAt = rs.getTimestamp("completed_at");
        if (completedAt != null) {
            video.setCompletedAt(completedAt.toLocalDateTime());
        }
        
        video.setFilePath(rs.getString("file_path"));
        video.setThumbnailPath(rs.getString("thumbnail_path"));
        
        return video;
    }
}
