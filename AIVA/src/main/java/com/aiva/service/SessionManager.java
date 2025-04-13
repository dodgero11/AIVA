package com.aiva.service;

import com.aiva.model.User;

/**
 * Manages the current user session
 */
public class SessionManager {
    private static User currentUser;
    
    /**
     * Sets the current user
     * @param user User to set as current
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    
    /**
     * Gets the current user
     * @return Current user or null if not logged in
     */
    public static User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Checks if a user is logged in
     * @return true if a user is logged in, false otherwise
     */
    public static boolean isLoggedIn() {
        return currentUser != null;
    }
    
    /**
     * Logs out the current user
     */
    public static void logout() {
        currentUser = null;
    }
}
