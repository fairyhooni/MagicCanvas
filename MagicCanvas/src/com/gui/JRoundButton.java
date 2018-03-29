package com.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.*;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.border.BevelBorder;


//customizing button
public class JRoundButton extends JButton {
    public JRoundButton(String label) {
        super(label);
       
        // This call causes the JButton not to paint the background.
        // This allows us to paint a round background.
        setContentAreaFilled(false);
        setFocusPainted(false);						
    }
  
    // Paint the round background and label.
    protected void paintComponent(Graphics g) {
    	Graphics2D g2 = (Graphics2D)g;
    	Color startC = null;
    	Color endC = null;
    	if (getModel().isArmed()) {
            // You might want to make the highlight color 
            // a property of the RoundButton class.
    		startC = getBackground();
    		endC = new Color(100,100,100);
        }else if(getModel().isRollover()){
        	startC = getBackground();
        	endC= Color.WHITE;
        }else {
        	startC = getBackground();
        	endC = Color.lightGray;
        }
    	
    	g2.setPaint(new GradientPaint(new Point(0,0),startC, new Point(0, getHeight()/2), endC));
    	g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
    	g2.setPaint(new GradientPaint(new Point(0,getHeight()/2), endC, new Point(0, getHeight()), startC));
    	g.fillRect(0, getHeight()/2 , getWidth() - 1, getHeight() - 1);
    	
        // This call will paint the label and the focus rectangle.
    	super.paintComponent(g);
    }
  
    // Paint the border of the button using a simple stroke.
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 14, 14);    
    }
  
    // Hit detection.
    Shape shape;
    public boolean contains(int x, int y) {
        // If the button has changed size, make a new shape object.
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 14, 14);
        }
        return shape.contains(x, y);
    }
}