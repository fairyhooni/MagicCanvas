package com.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.*;

public class CanvasFreeLine2D extends CanvasPathShape {
	private static final long serialVersionUID = 1L;
	
	
	private Point2D initialP;
	protected Path2D previousPath;
	
	public CanvasFreeLine2D(){
		path = new Path2D.Double();
		initialP = new Point2D.Double();
		previousPath = new Path2D.Double();
	}
	

	
	@Override
	public void appendPoint(Point2D p) {
		// TODO Auto-generated method stub
		//if initial point
		if(path.getCurrentPoint() == null){
			path.moveTo(p.getX(), p.getY());
			initialP = p;
			
			previousPath = (Path2D.Double)path.clone();
			
			
		}
		else{
			path = (Path2D.Double)previousPath.clone();
			path.lineTo(p.getX(), p.getY());
			previousPath = (Path2D.Double)path.clone();
			
			if(checkEnclosed()){
				path.closePath();
			}
		}
	}
	
	protected boolean checkEnclosed(){
		Point2D lastP = path.getCurrentPoint();
		Rectangle2D lastArea = new Rectangle2D.Double(lastP.getX() - 4, lastP.getY() - 4, 8, 8);	
		
		boolean closeFlag = false;
		
		if(lastArea.contains(initialP)){
			closeFlag = true;
		}
		
		setIsEnclosed(closeFlag);
		return closeFlag;
	}
	
	
	
	@Override
	public Drawable getClone() {
		// TODO Auto-generated method stub
		CanvasFreeLine2D clone = new CanvasFreeLine2D();
		
		clone.path = (Path2D) this.path.clone();
		
		clone.setIsSelected(this.getIsSelected());
		
		clone.setIsEnclosed(this.getIsEnclosed());
		
		clone.setFeature(new CanvasFeature(this.getFeature()));
		
		return clone;
	}



	@Override
	public void setShape(Shape s) {
		// TODO Auto-generated method stub
		Path2D p = (Path2D.Double)s;
		path = (Path2D.Double)p.clone();
	}



	@Override
	public Shape getShape() {
		// TODO Auto-generated method stub
		return (Path2D.Double)path.clone();
	}
}
