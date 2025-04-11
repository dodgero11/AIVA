package com.aiva.app;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ItemEvent;

import javax.swing.JToggleButton;

/**
 * A custom toggle button with rounded corners
 */
public class RoundedToggleButton extends JToggleButton {
    private int radius = 10;
    private Color selectedBg;
    private Color unselectedBg;
    private Color selectedFg;
    private Color unselectedFg;
    
    public RoundedToggleButton(String text) {
        super(text);
        init();
    }
    
    private void init() {
        selectedBg = new Color(138, 43, 226); // Purple
        unselectedBg = new Color(90, 50, 120); // Darker purple
        selectedFg = Color.WHITE;
        unselectedFg = Color.WHITE;
        
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        
        addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                setForeground(selectedFg);
            } else {
                setForeground(unselectedFg);
            }
            repaint();
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (isSelected()) {
            g2.setColor(selectedBg);
        } else {
            g2.setColor(unselectedBg);
        }
        
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        
        // Draw text
        FontMetrics metrics = g2.getFontMetrics(getFont());
        int x = (getWidth() - metrics.stringWidth(getText())) / 2;
        int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        
        if (isSelected()) {
            g2.setColor(selectedFg);
        } else {
            g2.setColor(unselectedFg);
        }
        
        g2.setFont(getFont());
        g2.drawString(getText(), x, y);
        
        g2.dispose();
    }
    
    public void setCornerRadius(int radius) {
        this.radius = radius;
        repaint();
    }
    
    public void setSelectedColors(Color bg, Color fg) {
        this.selectedBg = bg;
        this.selectedFg = fg;
        if (isSelected()) {
            setForeground(fg);
        }
        repaint();
    }
    
    public void setUnselectedColors(Color bg, Color fg) {
        this.unselectedBg = bg;
        this.unselectedFg = fg;
        if (!isSelected()) {
            setForeground(fg);
        }
        repaint();
    }
}