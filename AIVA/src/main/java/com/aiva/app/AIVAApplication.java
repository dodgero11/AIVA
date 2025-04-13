package com.aiva.app;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.aiva.dao.DatabaseInitializer;
import com.aiva.model.User;
import com.aiva.service.SessionManager;

public class AIVAApplication {
    private JFrame mainFrame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private SidebarPanel sidebarPanel;
    private JPanel contentPanel;
    
    // Screen panels
    private WelcomeScreen welcomeScreen;
    private VideoProductionScreen videoProductionScreen;
    private LoginScreen loginScreen;
    
    public AIVAApplication() {
        // Create the main application frame
        mainFrame = new JFrame("AIVA Video Generator");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 650);
        mainFrame.setMinimumSize(new Dimension(900, 600));
        
        // Center the frame on screen
        mainFrame.setLocationRelativeTo(null);
    }
    
    public void createAndShowGUI() {
        // First show the login screen
        showLoginScreen();
    }
    
    private void showLoginScreen() {
        // Clear any existing content
        mainFrame.getContentPane().removeAll();
        
        // Create and add login screen
        loginScreen = new LoginScreen(this);
        mainFrame.getContentPane().add(loginScreen);
        
        // Display the frame
        mainFrame.setVisible(true);
    }
    
    public void showMainApplication(User user) {
        // Store the user in session
        SessionManager.setCurrentUser(user);
        
        // Clear the frame
        mainFrame.getContentPane().removeAll();
        
        // Create the main panel with BorderLayout
        contentPanel = new JPanel(new BorderLayout());
        
        // Create the sidebar
        sidebarPanel = new SidebarPanel(this);
        contentPanel.add(sidebarPanel, BorderLayout.WEST);
        
        // Create card layout for switching between screens
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Create screens with user ID
        welcomeScreen = new WelcomeScreen(user.getId());
        videoProductionScreen = new VideoProductionScreen();
        
        // Add screens to card layout
        mainPanel.add(welcomeScreen, "WELCOME");
        mainPanel.add(videoProductionScreen, "VIDEO_PRODUCTION");
        
        // Show welcome screen by default
        cardLayout.show(mainPanel, "WELCOME");
        
        contentPanel.add(mainPanel, BorderLayout.CENTER);
        
        // Add the main panel to the frame
        mainFrame.getContentPane().add(contentPanel);
        
        // Refresh the frame
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    
    // Method to switch between screens
    public void showScreen(String screenName) {
        cardLayout.show(mainPanel, screenName);
    }
    
    // Method to handle logout
    public void logout() {
        // Clear the session
        SessionManager.logout();
        
        // Show login screen again
        showLoginScreen();
    }
    
    public static void main(String[] args) {
        // Initialize database
        DatabaseInitializer.initializeDatabase();
        
        // Use SwingUtilities to ensure UI updates happen on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel for better native appearance
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            AIVAApplication app = new AIVAApplication();
            app.createAndShowGUI();
        });
    }
}
