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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class WelcomeScreen extends JPanel {
    private JTextField promptField;
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
        JLabel welcomeLabel = new JLabel("Let's make a video");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32));
        welcomeLabel.setForeground(new Color(60, 40, 80));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Prompt input field
        JTextArea promptArea = new JTextArea("Enter prompt here");
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
                if (promptArea.getText().equals("Enter prompt here")) {
                    promptArea.setText("");
                    promptArea.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (promptArea.getText().isEmpty()) {
                    promptArea.setText("Enter prompt here");
                    promptArea.setForeground(Color.GRAY);
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
        
        // Create panel with label and combobox
        JPanel frameSizePanel = new JPanel(new BorderLayout(10, 0));
        frameSizePanel.setBackground(new Color(245, 245, 250));
        frameSizePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Create label
        JLabel frameSizeLabel = new JLabel("Frame size:");
        frameSizeLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Create combobox
        String[] frameSizes = {"512x512", "1024x1024"};
        JComboBox<String> frameSizeBox = new JComboBox<>(frameSizes);
        frameSizeBox.setFont(new Font("Arial", Font.PLAIN, 14));
        frameSizeBox.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        frameSizeBox.setPreferredSize(new Dimension(100, 25));
        frameSizeBox.setMaximumSize(new Dimension(100, 25));

        // Box Listener for Frame Size
        frameSizeBox.addActionListener(e -> {
            String selectedSize = (String) frameSizeBox.getSelectedItem();
            if (selectedSize.equals("512x512")) {
                frameSizeBox.setSelectedItem("512x512");
            } else if (selectedSize.equals("1024x1024")) {
                frameSizeBox.setSelectedItem("1024x1024");
            }
        });

        // Add components to panel
        frameSizePanel.add(frameSizeLabel, BorderLayout.WEST);
        frameSizePanel.add(frameSizeBox, BorderLayout.EAST);

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
        contentPanel.add(scrollPane);
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