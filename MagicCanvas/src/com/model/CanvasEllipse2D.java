/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package com.model;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;

import javax.swing.border.LineBorder;

public class CanvasEllipse2D extends CanvasRectShape {
	private static final long serialVersionUID = 1L;
	
	
	//constructor
	public CanvasEllipse2D(){
		shape = new Ellipse2D.Double();
	}
	
	
	//
	@Override
	public void setBothEnds(Point2D p1, Point2D p2) {
		// TODO Auto-generated method stub
		((Ellipse2D.Double)shape).setFrameFromDiagonal(p1, p2);
	}	
	
	@Override
	public void setShape(Shape s){
		Ellipse2D e = (Ellipse2D.Double)s;
		shape = (Ellipse2D.Double)e.clone();
	}
	
	@Override
	public Shape getShape(){
		return (Shape) ((Ellipse2D.Double)shape).clone();
	}
	
	@Override
	public Drawable getClone() {
		// TODO Auto-generated method stub
		CanvasEllipse2D clone = new CanvasEllipse2D();

		clone.shape = getShape();

		clone.setIsSelected(this.getIsSelected());
		
		clone.setFeature(new CanvasFeature(this.getFeature()));
		
		return clone;
	}
}
