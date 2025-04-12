package com.aiva.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SidebarPanel extends JPanel {
    private AIVAApplication app;
    private JButton videoGalleryBtn;
    private JButton createVideoBtn;
    private JButton performanceStatsBtn;
    private JPanel userPanel;
    
    // Track the currently selected button
    private JButton selectedButton = null;
    
    // Colors for button states
    private final Color BUTTON_SELECTED_COLOR = new Color(138, 43, 226); // Purple
    private final Color BUTTON_SELECTED_TEXT_COLOR = Color.WHITE;
    private final Color BUTTON_HOVER_COLOR = new Color(203, 195, 227); // Light purple
    private final Color BUTTON_NORMAL_COLOR = Color.WHITE;
    private final Color BUTTON_NORMAL_TEXT_COLOR = Color.DARK_GRAY;
    
    public SidebarPanel(AIVAApplication app) {
        this.app = app;
        
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(185, 0));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(230, 230, 230)));
        
        // Create the top panel with logo
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel logoLabel = new JLabel("AIVA", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 48));
        logoLabel.setForeground(new Color(60, 40, 80));
        topPanel.add(logoLabel, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Create navigation panel
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(Color.WHITE);
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        // Video gallery button
        videoGalleryBtn = createNavButton("Video gallery", "icons/video.png");
        videoGalleryBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, videoGalleryBtn.getPreferredSize().height));
        videoGalleryBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        videoGalleryBtn.addActionListener(e -> {
            selectButton(videoGalleryBtn);
            app.showScreen("VIDEO_PRODUCTION");
        });
        
        // Create video button
        createVideoBtn = createNavButton("Create a new video", "icons/plus.png");
        createVideoBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, createVideoBtn.getPreferredSize().height));
        createVideoBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        createVideoBtn.addActionListener(e -> {
            selectButton(createVideoBtn);
            app.showScreen("WELCOME");
        });
        
        // Performance stats button
        performanceStatsBtn = createNavButton("Performance Statistics", "icons/stats.png");
        performanceStatsBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, performanceStatsBtn.getPreferredSize().height));
        performanceStatsBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        performanceStatsBtn.addActionListener(e -> {
            selectButton(performanceStatsBtn);
            // Add action for performance stats if needed
        });
        
        navPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        navPanel.add(videoGalleryBtn);
        navPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        navPanel.add(createVideoBtn);
        navPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        navPanel.add(performanceStatsBtn);
        
        add(navPanel, BorderLayout.CENTER);
        
        // Set initial selected button
        selectButton(createVideoBtn);
    }
    
    private JButton createNavButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBackground(BUTTON_NORMAL_COLOR);
        button.setForeground(BUTTON_NORMAL_TEXT_COLOR);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button != selectedButton) {
                    button.setBackground(BUTTON_HOVER_COLOR);
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (button != selectedButton) {
                    button.setBackground(BUTTON_NORMAL_COLOR);
                }
            }
        });
        
        return button;
    }
    
    public void selectButton(JButton button) {
        // Deselect the previously selected button
        if (selectedButton != null) {
            selectedButton.setBackground(BUTTON_NORMAL_COLOR);
            selectedButton.setForeground(BUTTON_NORMAL_TEXT_COLOR);
        }
        
        // Select the new button
        selectedButton = button;
        selectedButton.setBackground(BUTTON_SELECTED_COLOR);
        selectedButton.setForeground(BUTTON_SELECTED_TEXT_COLOR);
    }    
}