package com.aiva.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class VideoProductionScreen extends JPanel {
    
    public VideoProductionScreen() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));
        
        // Create split pane for the two sections
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(500);
        splitPane.setDividerSize(1);
        splitPane.setBorder(null);
        splitPane.setEnabled(false); // Disable resizing
        
        // Left panel - Video in production
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(245, 245, 250));
        
        JLabel leftHeader = new JLabel("Video in production");
        leftHeader.setFont(new Font("Arial", Font.BOLD, 18));
        leftHeader.setForeground(new Color(138, 43, 226));
        leftHeader.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel leftContent = new JPanel();
        leftContent.setLayout(new BoxLayout(leftContent, BoxLayout.Y_AXIS));
        leftContent.setBackground(new Color(245, 245, 250));
        leftContent.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        // Add video in production item
        JPanel videoItem = createVideoItem("The solar system", "icons/solar-system.jpg");
        leftContent.add(videoItem);
        
        leftPanel.add(leftHeader, BorderLayout.NORTH);
        leftPanel.add(new JScrollPane(leftContent), BorderLayout.CENTER);
        
        // Right panel - Generated Videos
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(245, 245, 250));
        
        JLabel rightHeader = new JLabel("Generated Video");
        rightHeader.setFont(new Font("Arial", Font.BOLD, 18));
        rightHeader.setForeground(new Color(138, 43, 226));
        rightHeader.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel rightContent = new JPanel();
        rightContent.setLayout(new BoxLayout(rightContent, BoxLayout.Y_AXIS));
        rightContent.setBackground(new Color(245, 245, 250));
        rightContent.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        // Add generated video items
        JPanel physicsVideo = createVideoItem("Physics", "icons/physics.jpg");
        JPanel quantumVideo = createVideoItem("Quantum", "icons/quantum.jpg");
        JPanel gravityVideo = createVideoItem("Gravity", "icons/gravity.jpg");
        
        rightContent.add(physicsVideo);
        rightContent.add(Box.createRigidArea(new Dimension(0, 15)));
        rightContent.add(quantumVideo);
        rightContent.add(Box.createRigidArea(new Dimension(0, 15)));
        rightContent.add(gravityVideo);
        
        rightPanel.add(rightHeader, BorderLayout.NORTH);
        rightPanel.add(new JScrollPane(rightContent), BorderLayout.CENTER);
        
        // Add panels to split pane
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        
        // Add split pane to main panel
        add(splitPane, BorderLayout.CENTER);
    }
    
    private JPanel createVideoItem(String title, String imagePath) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(new Color(245, 245, 250));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        
        // Create image panel (placeholder)
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.DARK_GRAY);
        imagePanel.setPreferredSize(new Dimension(120, 70));
        imagePanel.setMaximumSize(new Dimension(120, 70));
        imagePanel.setMinimumSize(new Dimension(120, 70));
        
        // Add placeholder text to image panel
        JLabel imageLabel = new JLabel(title.substring(0, 1));
        imageLabel.setForeground(Color.WHITE);
        imageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        imagePanel.add(imageLabel);
        
        // Create title label
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        
        panel.add(imagePanel);
        panel.add(titleLabel);
        
        return panel;
    }
}