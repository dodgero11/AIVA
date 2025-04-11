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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class WelcomeScreen extends JPanel {
    private JTextField promptField;
    private JToggleButton videoToggle;
    private JToggleButton imageToggle;
    private JButton generateButton;
    private JPanel advancedSettingsPanel;
    private boolean advancedSettingsVisible = false;
    
    public WelcomeScreen() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));
        
        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(245, 245, 250));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        
        // Welcome header
        JLabel welcomeLabel = new JLabel("Welcome to AIVA");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32));
        welcomeLabel.setForeground(new Color(60, 40, 80));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Toggle buttons panel
        JPanel togglePanel = new JPanel();
        togglePanel.setLayout(new BoxLayout(togglePanel, BoxLayout.X_AXIS));
        togglePanel.setBackground(new Color(245, 245, 250));
        togglePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        togglePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        
        // Create toggle button group
        ButtonGroup toggleGroup = new ButtonGroup();
        
        // Video toggle button
        videoToggle = new JToggleButton("Video");
        videoToggle.setFont(new Font("Arial", Font.BOLD, 14));
        videoToggle.setForeground(Color.WHITE);
        videoToggle.setBackground(new Color(138, 43, 226));
        videoToggle.setFocusPainted(false);
        videoToggle.setBorderPainted(false);
        videoToggle.setSelected(true);
        videoToggle.setPreferredSize(new Dimension(120, 40));
        videoToggle.setMaximumSize(new Dimension(120, 40));
        
        // Image toggle button
        imageToggle = new JToggleButton("Image");
        imageToggle.setFont(new Font("Arial", Font.BOLD, 14));
        imageToggle.setForeground(Color.WHITE);
        imageToggle.setBackground(new Color(90, 50, 120));
        imageToggle.setFocusPainted(false);
        imageToggle.setBorderPainted(false);
        imageToggle.setPreferredSize(new Dimension(120, 40));
        imageToggle.setMaximumSize(new Dimension(120, 40));
        
        // Add toggle buttons to group
        toggleGroup.add(videoToggle);
        toggleGroup.add(imageToggle);
        
        // Add toggle buttons to panel with spacing
        togglePanel.add(Box.createHorizontalGlue());
        togglePanel.add(videoToggle);
        togglePanel.add(Box.createRigidArea(new Dimension(20, 0)));
        togglePanel.add(imageToggle);
        togglePanel.add(Box.createHorizontalGlue());
        
        // Prompt input field
        promptField = new JTextField("Enter prompt here");
        promptField.setFont(new Font("Arial", Font.PLAIN, 14));
        promptField.setForeground(Color.GRAY);
        promptField.setBackground(new Color(230, 230, 230));
        promptField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        promptField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        promptField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add focus listener to clear placeholder text
        promptField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (promptField.getText().equals("Enter prompt here")) {
                    promptField.setText("");
                    promptField.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (promptField.getText().isEmpty()) {
                    promptField.setText("Enter prompt here");
                    promptField.setForeground(Color.GRAY);
                }
            }
        });
        
        // Generate button
        generateButton = new JButton("Generate");
        generateButton.setFont(new Font("Arial", Font.BOLD, 14));
        generateButton.setForeground(Color.WHITE);
        generateButton.setBackground(new Color(138, 43, 226));
        generateButton.setFocusPainted(false);
        generateButton.setBorderPainted(false);
        generateButton.setPreferredSize(new Dimension(150, 40));
        generateButton.setMaximumSize(new Dimension(150, 40));
        generateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
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
        
        // Separator
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setForeground(new Color(200, 200, 200));
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Advanced settings header panel
        JPanel advancedHeaderPanel = new JPanel(new BorderLayout());
        advancedHeaderPanel.setBackground(new Color(245, 245, 250));
        advancedHeaderPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        advancedHeaderPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel advancedLabel = new JLabel("Advance setting");
        advancedLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel arrowLabel = new JLabel("▼");
        arrowLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        advancedHeaderPanel.add(advancedLabel, BorderLayout.WEST);
        advancedHeaderPanel.add(arrowLabel, BorderLayout.EAST);
        
        // Make the advanced header clickable
        advancedHeaderPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                advancedSettingsVisible = !advancedSettingsVisible;
                advancedSettingsPanel.setVisible(advancedSettingsVisible);
                arrowLabel.setText(advancedSettingsVisible ? "▲" : "▼");
                revalidate();
                repaint();
            }
        });
        
        // Advanced settings panel
        advancedSettingsPanel = new JPanel();
        advancedSettingsPanel.setLayout(new BoxLayout(advancedSettingsPanel, BoxLayout.Y_AXIS));
        advancedSettingsPanel.setBackground(new Color(245, 245, 250));
        advancedSettingsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        advancedSettingsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Frame size setting
        JPanel frameSizePanel = createSettingPanel("Frame size", "1920x1080");
        
        // Generator model setting
        JPanel generatorPanel = createSettingPanel("Generator model", "Dall-E3");
        
        // Art style setting
        JPanel artStylePanel = createSettingPanel("Art style", "Stock photo");
        
        // AI voice setting
        JPanel aiVoicePanel = createSettingPanel("AI voice", null);
        JToggleButton aiVoiceToggle = new JToggleButton();
        aiVoiceToggle.setSelected(true);
        aiVoiceToggle.setPreferredSize(new Dimension(40, 20));
        aiVoiceToggle.setMaximumSize(new Dimension(40, 20));
        aiVoiceToggle.setBackground(new Color(138, 43, 226));
        aiVoicePanel.add(aiVoiceToggle, BorderLayout.EAST);
        
        // Add settings to advanced panel
        advancedSettingsPanel.add(frameSizePanel);
        advancedSettingsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        advancedSettingsPanel.add(generatorPanel);
        advancedSettingsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        advancedSettingsPanel.add(artStylePanel);
        advancedSettingsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        advancedSettingsPanel.add(aiVoicePanel);
        
        // Hide advanced settings by default
        advancedSettingsPanel.setVisible(false);
        
        // Add components to content panel
        contentPanel.add(welcomeLabel);
        contentPanel.add(togglePanel);
        contentPanel.add(promptField);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(generateButton);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPanel.add(separator);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(advancedHeaderPanel);
        contentPanel.add(advancedSettingsPanel);
        
        // Add content panel to main panel
        add(new JScrollPane(contentPanel), BorderLayout.CENTER);
    }
    
    private JPanel createSettingPanel(String labelText, String valueText) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(new Color(245, 245, 250));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        
        panel.add(label, BorderLayout.WEST);
        
        if (valueText != null) {
            JTextField valueField = new JTextField(valueText);
            valueField.setFont(new Font("Arial", Font.PLAIN, 14));
            valueField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
            ));
            valueField.setEditable(false);
            valueField.setBackground(Color.WHITE);
            valueField.setPreferredSize(new Dimension(100, 25));
            valueField.setMaximumSize(new Dimension(100, 25));
            
            panel.add(valueField, BorderLayout.EAST);
        }
        
        return panel;
    }
}