package com.model;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public interface Drawable extends Serializable {
	//getter
	public Rectangle2D[] getRegulateSign();
	public Ellipse2D getRotateSign();
	public boolean getIsSelected();
	public CanvasFeature getFeature();
	public Rectangle2D getBounds2D();
	public Shape getShape();
	
	//setter
	public void setIsSelected(boolean select);
	public void setFeature(CanvasFeature feature);
	public void setShape(Shape s);
	
	//
	public void transform(AffineTransform at);
	
	//test method
	public void editShape(Point2D end1, Point2D end2, boolean horizontalFlip, boolean verticalFlip);
	
	public boolean contains(double x, double y);
	public void draw(Graphics2D g2);
	public Drawable getClone();
}
