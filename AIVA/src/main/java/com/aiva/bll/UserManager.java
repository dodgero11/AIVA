package com.aiva.bll;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import com.aiva.dao.UserDAO;
import com.aiva.model.User;

/**
 * Business Logic Layer for User management
 */
public class UserManager {
    private UserDAO userDAO;
    
    public UserManager() {
        this.userDAO = new UserDAO();
    }
    
    /**
     * Registers a new user
     * @param username Username
     * @param password Plain text password
     * @param email Email address
     * @return Created user or null if failed
     */
    public User registerUser(String username, String password, String email) {
        try {
            // Check if username already exists
            User existingUser = userDAO.getUserByUsername(username);
            if (existingUser != null) {
                System.err.println("Username already exists");
                return null;
            }
            
            // Hash the password
            String passwordHash = hashPassword(password);
            
            // Create the user
            User user = new User();
            user.setUsername(username);
            user.setPasswordHash(passwordHash);
            user.setEmail(email);
            
            return userDAO.createUser(user);
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Authenticates a user
     * @param username Username
     * @param password Plain text password
     * @return User object if authentication successful, null otherwise
     */
    public User authenticateUser(String username, String password) {
        try {
            User user = userDAO.getUserByUsername(username);
            if (user != null && verifyPassword(password, user.getPasswordHash())) {
                return user;
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Gets a user by ID
     * @param userId User ID
     * @return User object or null if not found
     */
    public User getUserById(int userId) {
        try {
            return userDAO.getUserById(userId);
        } catch (SQLException e) {
            System.err.println("Error getting user: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Gets all users
     * @return List of all users
     */
    public List<User> getAllUsers() {
        try {
            return userDAO.getAllUsers();
        } catch (SQLException e) {
            System.err.println("Error getting users: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Updates a user's profile
     * @param user User object to update
     * @return true if successful, false otherwise
     */
    public boolean updateUser(User user) {
        try {
            return userDAO.updateUser(user);
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Changes a user's password
     * @param userId User ID
     * @param oldPassword Old password for verification
     * @param newPassword New password
     * @return true if successful, false otherwise
     */
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        try {
            User user = userDAO.getUserById(userId);
            if (user != null && verifyPassword(oldPassword, user.getPasswordHash())) {
                user.setPasswordHash(hashPassword(newPassword));
                return userDAO.updateUser(user);
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error changing password: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Deletes a user
     * @param userId User ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteUser(int userId) {
        try {
            return userDAO.deleteUser(userId);
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Hashes a password using SHA-256
     * @param password Plain text password
     * @return Hashed password
     */
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    
    /**
     * Verifies a password against a hash
     * @param password Plain text password
     * @param hash Hashed password
     * @return true if password matches hash, false otherwise
     */
    private boolean verifyPassword(String password, String hash) {
        return hashPassword(password).equals(hash);
    }
}
