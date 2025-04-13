package com.aiva.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.aiva.model.Video;
import com.aiva.service.VideoGenerationService;

public class WelcomeScreen extends JPanel {
    private VideoGenerationService videoService;
    private AIVAApplication application;
    private int userId;
    
    public WelcomeScreen(int currentUserId, AIVAApplication application) {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        // Store the current user's ID
        userId = currentUserId;

        // Store the AIVAApplication instance
        this.application = application;

        // Initialize video generation services
        videoService = new VideoGenerationService();
        
        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(245, 245, 250));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        
        // Welcome header
        JLabel welcomeLabel = new JLabel("Let's make a video");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32));
        welcomeLabel.setForeground(new Color(60, 40, 80));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Prompt input field
        JTextArea promptArea = new JTextArea("Enter theme here");
        promptArea.setFont(new Font("Arial", Font.PLAIN, 14));
        promptArea.setForeground(Color.GRAY);
        promptArea.setBackground(new Color(230, 230, 230));
        promptArea.setLineWrap(true);
        promptArea.setWrapStyleWord(true);
        promptArea.setRows(3);
        promptArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Scroll pane for the prompt area
        JScrollPane scrollPane = new JScrollPane(promptArea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        scrollPane.setPreferredSize(new Dimension(400, 40));
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // Add focus listeners to the prompt area
        promptArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (promptArea.getText().equals("Enter theme here")) {
                    promptArea.setText("");
                    promptArea.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (promptArea.getText().isEmpty()) {
                    promptArea.setText("Enter theme here");
                    promptArea.setForeground(Color.GRAY);
                }
            }
        });
        
        // Generate button
        JButton generateButton = new JButton("Generate");
        generateButton.setFont(new Font("Arial", Font.BOLD, 14));
        generateButton.setForeground(Color.WHITE);
        generateButton.setBackground(new Color(138, 43, 226));
        generateButton.setFocusPainted(false);
        generateButton.setBorderPainted(false);
        generateButton.setPreferredSize(new Dimension(150, 40));
        generateButton.setMaximumSize(new Dimension(150, 40));
        generateButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add action listener to generate button
        generateButton.addActionListener(e -> {
            String prompt = promptArea.getText();
            if (prompt.isEmpty() || prompt.equals("Enter prompt here")) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter a prompt for your video.", 
                    "Empty Prompt", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Show the script editor screen with the prompt
            application.showScriptEditor(prompt);
            
            // Disable button during generation
            generateButton.setEnabled(false);
            generateButton.setText("Generating...");
            
            // Generate video in background thread
            new Thread(() -> {
                Video video = videoService.generateVideo(prompt, userId);
                
                // Update UI on EDT
                SwingUtilities.invokeLater(() -> {
                    if (video != null) {
                        JOptionPane.showMessageDialog(this, 
                            "Video generation started! Video ID: " + video.getId(), 
                            "Success", 
                            JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, 
                            "Failed to start video generation. Please try again.", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                    
                    // Re-enable button
                    generateButton.setEnabled(true);
                    generateButton.setText("Generate");
                });
            }).start();
        });
        
        // Add hover effect to generate button
        generateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (generateButton.isEnabled()) {
                    generateButton.setBackground(new Color(148, 53, 236));
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (generateButton.isEnabled()) {
                    generateButton.setBackground(new Color(138, 43, 226));
                }
            }
        });
        
        // Add hover effect to generate button
        generateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                generateButton.setBackground(new Color(148, 53, 236));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                generateButton.setBackground(new Color(138, 43, 226));
            }
        });
        
        // 
        // Create panel with label and combobox
        JPanel styleChoicesPanel = new JPanel(new BorderLayout(10, 0));
        styleChoicesPanel.setBackground(new Color(245, 245, 250));
        styleChoicesPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Create label
        JLabel frameSizeLabel = new JLabel("Phong cách nội dung:");
        frameSizeLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Create style choices
        String[] styleChoices = {"Trẻ em", "Phổ thông", "Chuyên sâu"};
        JComboBox<String> styleChoicesBox = new JComboBox<>(styleChoices);
        styleChoicesBox.setFont(new Font("Arial", Font.PLAIN, 14));
        styleChoicesBox.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        styleChoicesBox.setPreferredSize(new Dimension(100, 30));
        styleChoicesBox.setMaximumSize(new Dimension(100, 30));

        // Box Listener for Frame Size
        styleChoicesBox.addActionListener(e -> {
            String selectedSize = (String) styleChoicesBox.getSelectedItem();
            if (selectedSize.equals("Trẻ em")) {
                styleChoicesBox.setSelectedItem("Trẻ em");
            } else if (selectedSize.equals("Phổ thông")) {
                styleChoicesBox.setSelectedItem("Phổ thông");
            } else if (selectedSize.equals("Chuyên sâu")) {
                styleChoicesBox.setSelectedItem("Chuyên sâu");
            }
        });

        // Add label and combobox to panel
        styleChoicesPanel.add(frameSizeLabel, BorderLayout.WEST);
        styleChoicesPanel.add(styleChoicesBox, BorderLayout.EAST);

        // Add components to content panel
        contentPanel.add(welcomeLabel);
        contentPanel.add(scrollPane);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(generateButton);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPanel.add(styleChoicesPanel);
        
        // Add content panel to main panel
        add(new JScrollPane(contentPanel), BorderLayout.CENTER);
    }
}