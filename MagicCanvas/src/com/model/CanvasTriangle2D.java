/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package com.model;


import java.io.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class CanvasTriangle2D extends CanvasRectShape{
	private static final long serialVersionUID = 1L;
	
	
	public CanvasTriangle2D(){
		shape = new Polygon();
	}

	
	@Override
	public void setBothEnds(Point2D p1, Point2D p2) {
		// TODO Auto-generated method stub
		Rectangle2D bounds = new Rectangle2D.Double();
		bounds.setFrameFromDiagonal(p1, p2);
		
		((Polygon)shape).reset();
		
		int[] xpoints = new int[3];
		int[] ypoints = new int[3];
			
		xpoints[0] = (int)bounds.getMinX();
		xpoints[1] = (int)bounds.getMaxX();
		xpoints[2] = (int)bounds.getCenterX();
		ypoints[0] = (int)bounds.getMaxY();
		ypoints[1] = (int)bounds.getMaxY();
		ypoints[2] = (int)bounds.getMinY();
	
		for(int i =0; i<3; i++){
			((Polygon)shape).addPoint(xpoints[i], ypoints[i]);
		}
	}

	@Override
	public void setShape(Shape s) {
		// TODO Auto-generated method stub
		Polygon p = new Polygon();
		p.npoints = ((Polygon)s).npoints;
		p.xpoints = ((Polygon)s).xpoints.clone();
		p.ypoints = ((Polygon)s).ypoints.clone();
		
		shape = p;
	}

	@Override
	public Shape getShape() {
		// TODO Auto-generated method stub
		Polygon p = new Polygon();
		p.npoints = ((Polygon)shape).npoints;
		p.xpoints = ((Polygon)shape).xpoints.clone();
		p.ypoints = ((Polygon)shape).ypoints.clone();
		
		return p;
	}
	
	@Override
	public Drawable getClone() {
		CanvasTriangle2D clone = new CanvasTriangle2D();

		clone.shape = getShape();

		clone.setIsSelected(this.getIsSelected());
		
		clone.setFeature(new CanvasFeature(this.getFeature()));
		
		return clone;
	}

	
	@Override
	public void transform(AffineTransform at){
		((Polygon)shape).translate((int)at.getTranslateX(), (int)at.getTranslateY());
	}
	
	
	@Override
	public void editShape(Point2D end1, Point2D end2, boolean horizontalFlip, boolean verticalFlip){
		
		Rectangle2D bounds = new Rectangle2D.Double();
		bounds.setFrameFromDiagonal(end1, end2);
		
		int[] xpoints = new int[3];
		int[] ypoints = new int[3];
			
		xpoints[0] = (int)bounds.getMinX();
		xpoints[1] = (int)bounds.getMaxX();
		xpoints[2] = (int)bounds.getCenterX();		
		
		if(((Polygon)shape).ypoints[0] == getBounds2D().getMinY()){
			ypoints[0] = (int)bounds.getMinY();
			ypoints[1] = (int)bounds.getMinY();
			ypoints[2] = (int)bounds.getMaxY();
		}else if(((Polygon)shape).ypoints[0] == getBounds2D().getMaxY()){
			ypoints[0] = (int)bounds.getMaxY();
			ypoints[1] = (int)bounds.getMaxY();
			ypoints[2] = (int)bounds.getMinY();
		}
		
		
		if(verticalFlip){
			for(int i = 0; i<3; i++){
				ypoints[i] = (int) (2 * bounds.getCenterY() - ypoints[i]);
			}
		}
		
		((Polygon)shape).reset();
		for(int i =0; i<3; i++){
			((Polygon)shape).addPoint(xpoints[i], ypoints[i]);
		}
	}
}