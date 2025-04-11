package com.aiva.app;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class AIVAApplication {
    private JFrame mainFrame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private SidebarPanel sidebarPanel;
    
    // Screen panels
    private WelcomeScreen welcomeScreen;
    private VideoProductionScreen videoProductionScreen;
    
    public static void main(String[] args) {
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
    
    public void createAndShowGUI() {
        // Create the main application window
        mainFrame = new JFrame("AIVA");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 650);
        mainFrame.setMinimumSize(new Dimension(900, 600));
        
        // Create the main panel with BorderLayout
        JPanel contentPanel = new JPanel(new BorderLayout());
        
        // Create the sidebar
        sidebarPanel = new SidebarPanel(this);
        contentPanel.add(sidebarPanel, BorderLayout.WEST);
        
        // Create card layout for switching between screens
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Create screens
        welcomeScreen = new WelcomeScreen();
        videoProductionScreen = new VideoProductionScreen();
        
        // Add screens to card layout
        mainPanel.add(welcomeScreen, "WELCOME");
        mainPanel.add(videoProductionScreen, "VIDEO_PRODUCTION");
        
        // Show welcome screen by default
        cardLayout.show(mainPanel, "WELCOME");
        
        contentPanel.add(mainPanel, BorderLayout.CENTER);
        
        // Add the main panel to the frame
        mainFrame.add(contentPanel);
        
        // Center the window on screen
        mainFrame.setLocationRelativeTo(null);
        
        // Display the window
        mainFrame.setVisible(true);
    }
    
    // Method to switch between screens
    public void showScreen(String screenName) {
        cardLayout.show(mainPanel, screenName);
    }
}