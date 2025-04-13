package com.aiva.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.aiva.bll.UserManager;
import com.aiva.model.User;

public class LoginScreen extends JPanel {
    private AIVAApplication application;
    private UserManager userManager;
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    
    public LoginScreen(AIVAApplication application) {
        this.application = application;
        this.userManager = new UserManager();
        
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));
        
        // Create a panel for the login form with some padding
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(new Color(245, 245, 250));
        formPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        
        // App logo/title
        JLabel titleLabel = new JLabel("AIVA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(new Color(138, 43, 226));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        // Login form panel
        JPanel loginFormPanel = new JPanel();
        loginFormPanel.setLayout(new BoxLayout(loginFormPanel, BoxLayout.Y_AXIS));
        loginFormPanel.setBackground(Color.WHITE);
        loginFormPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        loginFormPanel.setMaximumSize(new Dimension(400, 300));
        loginFormPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Login header
        JLabel loginLabel = new JLabel("Log in to your account");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 18));
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Username field
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // Password field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // Login button
        loginButton = new JButton("Log In");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(138, 43, 226));
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect to login button
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(new Color(148, 53, 236));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(new Color(138, 43, 226));
            }
        });
        
        // Register section
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        registerPanel.setBackground(Color.WHITE);
        registerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        JLabel registerLabel = new JLabel("Don't have an account?");
        registerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 14));
        registerButton.setForeground(new Color(138, 43, 226));
        registerButton.setBackground(Color.WHITE);
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        registerPanel.add(registerLabel);
        registerPanel.add(registerButton);
        
        // Add components to login form panel
        loginFormPanel.add(loginLabel);
        loginFormPanel.add(usernameLabel);
        loginFormPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        loginFormPanel.add(usernameField);
        loginFormPanel.add(passwordLabel);
        loginFormPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        loginFormPanel.add(passwordField);
        loginFormPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginFormPanel.add(loginButton);
        loginFormPanel.add(registerPanel);
        
        // Add components to main form panel
        formPanel.add(titleLabel);
        formPanel.add(loginFormPanel);
        
        // Add form panel to main panel
        add(new JScrollPane(formPanel), BorderLayout.CENTER);
        
        // Add action listeners
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> showRegisterDialog());
        
        // Add enter key listener to password field
        passwordField.addActionListener(e -> handleLogin());
    }
    
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter both username and password.", 
                "Login Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        User user = userManager.authenticateUser(username, password);
        
        if (user != null) {
            // Login successful
            application.showMainApplication(user);
        } else {
            // Login failed
            JOptionPane.showMessageDialog(this, 
                "Invalid username or password. Please try again.", 
                "Login Failed", 
                JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }
    
    private void showRegisterDialog() {
        // Create a custom dialog for registration
        JDialog registerDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Register New Account", true);
        registerDialog.setSize(400, 350);
        registerDialog.setLocationRelativeTo(this);
        registerDialog.setResizable(false);
        
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        usernameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Email field
        JLabel emailLabel = new JLabel("Email (optional):");
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JTextField emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Confirm password field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        confirmPasswordLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        confirmPasswordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        JButton cancelButton = new JButton("Cancel");
        JButton registerButton = new JButton("Register");
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(registerButton);
        
        // Add components to dialog panel
        dialogPanel.add(usernameLabel);
        dialogPanel.add(usernameField);
        dialogPanel.add(emailLabel);
        dialogPanel.add(emailField);
        dialogPanel.add(passwordLabel);
        dialogPanel.add(passwordField);
        dialogPanel.add(confirmPasswordLabel);
        dialogPanel.add(confirmPasswordField);
        dialogPanel.add(buttonPanel);
        
        // Add action listeners
        cancelButton.addActionListener(e -> registerDialog.dispose());
        
        registerButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            
            // Validate input
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(registerDialog, 
                    "Username and password are required.", 
                    "Registration Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(registerDialog, 
                    "Passwords do not match.", 
                    "Registration Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Register the user
            User newUser = userManager.registerUser(username, password, email.isEmpty() ? null : email);
            
            if (newUser != null) {
                JOptionPane.showMessageDialog(registerDialog, 
                    "Registration successful! You can now log in.", 
                    "Registration Complete", 
                    JOptionPane.INFORMATION_MESSAGE);
                registerDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(registerDialog, 
                    "Registration failed. Username may already be taken.", 
                    "Registration Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        registerDialog.add(dialogPanel);
        registerDialog.setVisible(true);
    }
}
