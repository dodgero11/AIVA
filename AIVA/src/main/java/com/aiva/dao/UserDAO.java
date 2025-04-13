package com.aiva.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.aiva.model.User;

/**
 * Data Access Object for User entities
 */
public class UserDAO {
    
    /**
     * Creates a new user in the database
     * @param user User object to create
     * @return The created user with ID populated
     * @throws SQLException if database operation fails
     */
    public User createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password_hash, email) VALUES (?, ?, ?)";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
    
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPasswordHash());
            pstmt.setString(3, user.getEmail());
    
            int affectedRows = pstmt.executeUpdate();
    
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
    
            // Use SQLite's last_insert_rowid() instead of getGeneratedKeys()
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
    
            // Get the created_at timestamp
            String getUser = "SELECT created_at FROM users WHERE id = ?";
            try (PreparedStatement getStmt = conn.prepareStatement(getUser)) {
                getStmt.setInt(1, user.getId());
                try (ResultSet rs = getStmt.executeQuery()) {
                    if (rs.next()) {
                        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    }
                }
            }
    
            return user;
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
            throw e;
        }
    }
    
    
    /**
     * Gets a user by ID
     * @param id User ID
     * @return User object or null if not found
     * @throws SQLException if database operation fails
     */
    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                } else {
                    return null;
                }
            }
        }
    }
    
    /**
     * Gets a user by username
     * @param username Username to search for
     * @return User object or null if not found
     * @throws SQLException if database operation fails
     */
    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                } else {
                    return null;
                }
            }
        }
    }
    
    /**
     * Gets all users
     * @return List of all users
     * @throws SQLException if database operation fails
     */
    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
            
            return users;
        }
    }
    
    /**
     * Updates a user in the database
     * @param user User object to update
     * @return true if successful, false otherwise
     * @throws SQLException if database operation fails
     */
    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET username = ?, password_hash = ?, email = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPasswordHash());
            pstmt.setString(3, user.getEmail());
            pstmt.setInt(4, user.getId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }
    
    /**
     * Deletes a user from the database
     * @param id User ID to delete
     * @return true if successful, false otherwise
     * @throws SQLException if database operation fails
     */
    public boolean deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }
    
    /**
     * Maps a ResultSet row to a User object
     * @param rs ResultSet positioned at the row to map
     * @return Mapped User object
     * @throws SQLException if database operation fails
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setEmail(rs.getString("email"));
        
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            user.setCreatedAt(createdAt.toLocalDateTime());
        }
        
        return user;
    }
}
