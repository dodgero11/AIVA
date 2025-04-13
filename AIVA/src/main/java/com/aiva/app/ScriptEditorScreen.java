package com.aiva.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.aiva.bll.VideoManager;
import com.aiva.model.Video;
import com.aiva.service.FileService;

/**
 * Screen for editing AI-generated scripts before final video generation
 */
public class ScriptEditorScreen extends JPanel {
    private AIVAApplication application;
    private VideoManager videoService;
    private int userId;
    private String originalPrompt;
    private String scriptFilePath;
    private Video currentVideo;
    private FileService fileService;
    
    private JTextArea scriptTextArea;
    private JButton saveButton;
    private JButton generateVideoButton;
    private JButton cancelButton;
    
    public ScriptEditorScreen(AIVAApplication application, int userId) {
        this.application = application;
        this.userId = userId;
        this.videoService = new VideoManager();
        this.fileService = new FileService();
        
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));
        
        // Create main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(245, 245, 250));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Header
        JLabel headerLabel = new JLabel("Edit Generated Script");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(60, 40, 80));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Instructions
        JLabel instructionsLabel = new JLabel("Review and edit the AI-generated script below. When you're satisfied, click 'Generate Video'.");
        instructionsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        instructionsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        // Script text area
        scriptTextArea = new JTextArea();
        scriptTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        scriptTextArea.setLineWrap(true);
        scriptTextArea.setWrapStyleWord(true);
        scriptTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(scriptTextArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(245, 245, 250));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        cancelButton = new JButton("Cancel");
        saveButton = new JButton("Save Script");
        generateVideoButton = new JButton("Generate Video");
        
        // Style the buttons
        generateVideoButton.setFont(new Font("Arial", Font.BOLD, 14));
        generateVideoButton.setForeground(Color.WHITE);
        generateVideoButton.setBackground(new Color(138, 43, 226));
        generateVideoButton.setFocusPainted(false);
        generateVideoButton.setBorderPainted(false);
        
        saveButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(generateVideoButton);
        
        // Add components to content panel
        contentPanel.add(headerLabel);
        contentPanel.add(instructionsLabel);
        contentPanel.add(scrollPane);
        contentPanel.add(buttonPanel);
        
        // Add content panel to main panel
        add(new JScrollPane(contentPanel), BorderLayout.CENTER);
        
        // Add action listeners
        cancelButton.addActionListener(e -> application.showScreen("WELCOME"));
        
        saveButton.addActionListener(e -> {
            try {
                saveScriptToFile();
                JOptionPane.showMessageDialog(this, 
                    "Script saved successfully!", 
                    "Save Complete", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error saving script: " + ex.getMessage(), 
                    "Save Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        generateVideoButton.addActionListener(e -> {
            try {
                // Save the script first
                saveScriptToFile();
                
                // Update video status and proceed to video generation
                if (currentVideo != null) {
                    videoService.updateVideoStatus(currentVideo.getId(), "generating");
                    
                    // Show the video production screen
                    application.showScreen("VIDEO_PRODUCTION");
                    
                    // Start the actual video generation in a background thread
                    new Thread(() -> {
                        try {
                            // Simulate video generation (in a real app, this would call your AI video generation)
                            Thread.sleep(2000);
                            
                            // Update the video with completion info
                            String filePath = "videos/" + currentVideo.getId() + ".mp4";
                            String thumbnailPath = "thumbnails/" + currentVideo.getId() + ".jpg";
                            videoService.completeVideo(currentVideo.getId(), filePath, thumbnailPath);
                            
                            // Refresh the video production screen
                            SwingUtilities.invokeLater(() -> {
                                // In a real app, you would refresh the video production screen here
                            });
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            videoService.updateVideoStatus(currentVideo.getId(), "failed");
                        }
                    }).start();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error processing script: " + ex.getMessage(), 
                    "Processing Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    /**
     * Loads a script for the given prompt
     * @param prompt The user's original prompt
     */
    public void loadScriptForPrompt(String prompt) {
        this.originalPrompt = prompt;
        
        try {
            // Create a video entry in the database
            currentVideo = videoService.createVideo(prompt, prompt, userId);
            
            if (currentVideo == null) {
                throw new IOException("Failed to create video entry");
            }
            
            // Create directories if they don't exist
            File scriptsDir = new File("scripts");
            if (!scriptsDir.exists()) {
                scriptsDir.mkdirs();
            }
            
            // Define file paths
            String templateScriptPath = "templates/script_template.txt";
            scriptFilePath = "scripts/script_" + currentVideo.getId() + ".txt";
            
            // Check if template exists, if not create a simple one
            File templateFile = new File(templateScriptPath);
            if (!templateFile.exists()) {
                fileService.writeStringToFile(templateScriptPath, 
                    "# [TITLE]\n\n" +
                    "## Introduction\n" +
                    "Welcome to this video about [TOPIC].\n\n" +
                    "## Main Content\n" +
                    "[CONTENT]\n\n" +
                    "## Conclusion\n" +
                    "Thank you for watching!\n");
            }
            
            // Read the template
            String templateContent = fileService.readFileToString(templateScriptPath);
            
            // Replace placeholders with actual content
            String scriptContent = templateContent
                .replace("[TITLE]", generateTitle(prompt))
                .replace("[TOPIC]", prompt)
                .replace("[CONTENT]", generateContent(prompt));
            
            // Save the initial script
            fileService.writeStringToFile(scriptFilePath, scriptContent);
            
            // Load the script into the text area
            scriptTextArea.setText(scriptContent);
            scriptTextArea.setCaretPosition(0);
            
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error loading script: " + e.getMessage(), 
                "Loading Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Saves the current script to file
     */
    private void saveScriptToFile() throws IOException {
        if (scriptFilePath != null) {
            fileService.writeStringToFile(scriptFilePath, scriptTextArea.getText());
        } else {
            throw new IOException("No script file path defined");
        }
    }
    
    /**
     * Generates a title from the prompt
     * In a real app, this would use AI
     */
    private String generateTitle(String prompt) {
        // In a real app, this would use AI to generate a title
        return "Video About " + prompt;
    }
    
    /**
     * Generates content from the prompt
     * In a real app, this would use AI
     */
    private String generateContent(String prompt) {
        // In a real app, this would use AI to generate content
        return "This is an AI-generated script about " + prompt + ".\n\n" +
               "The script would contain detailed information about the topic, " +
               "including key points, examples, and explanations.\n\n" +
               "Feel free to edit this script to better match your needs.";
    }
}
