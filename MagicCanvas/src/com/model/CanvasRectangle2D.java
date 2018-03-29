/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package com.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.*;
import java.io.*;

public class CanvasRectangle2D extends CanvasRectShape {
	private static final long serialVersionUID = 1L;
	
	
	//constructor
	public CanvasRectangle2D(){
		shape = new Rectangle2D.Double();
	}

	
	@Override
	public void setBothEnds(Point2D p1, Point2D p2) {
		// TODO Auto-generated method stub
		((Rectangle2D.Double)shape).setFrameFromDiagonal(p1, p2);
	}
	
	@Override
	public void setShape(Shape s) {
		// TODO Auto-generated method stub
		Rectangle2D e = (Rectangle2D.Double)s;
		shape = (Rectangle2D.Double)e.clone();
	}

	
	@Override
	public Shape getShape() {
		return (Shape) ((Rectangle2D.Double)shape).clone();
	}
	
	@Override
	public Drawable getClone() {
		// TODO Auto-generated method stub
		CanvasRectangle2D clone = new CanvasRectangle2D();

		clone.shape = getShape();

		clone.setIsSelected(this.getIsSelected());
		
		clone.setFeature(new CanvasFeature(this.getFeature()));
		
		return clone;
	}

	
}
