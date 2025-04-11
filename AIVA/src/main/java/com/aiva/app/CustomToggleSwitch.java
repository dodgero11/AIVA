package com.aiva.app;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JToggleButton;

/**
 * A custom toggle switch component
 */
public class CustomToggleSwitch extends JToggleButton {
    private Color onColor = new Color(138, 43, 226); // Purple
    private Color offColor = new Color(200, 200, 200);
    private Color thumbColor = Color.WHITE;
    
    public CustomToggleSwitch() {
        super();
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        
        // Set initial state
        setSelected(true);
        
        addItemListener(e -> repaint());
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw background
        if (isSelected()) {
            g2.setColor(onColor);
        } else {
            g2.setColor(offColor);
        }
        
        int width = getWidth();
        int height = getHeight();
        
        g2.fillRoundRect(0, 0, width, height, height, height);
        
        // Draw thumb
        g2.setColor(thumbColor);
        if (isSelected()) {
            g2.fillOval(width - height, 0, height, height);
        } else {
            g2.fillOval(0, 0, height, height);
        }
        
        g2.dispose();
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(40, 20);
    }
    
    public void setOnColor(Color color) {
        this.onColor = color;
        repaint();
    }
    
    public void setOffColor(Color color) {
        this.offColor = color;
        repaint();
    }
    
    public void setThumbColor(Color color) {
        this.thumbColor = color;
        repaint();
    }
}