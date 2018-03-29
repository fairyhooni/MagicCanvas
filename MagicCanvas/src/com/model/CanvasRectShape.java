package com.model;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

//this shape is generated
//by rectangular bounding
public abstract class CanvasRectShape implements Drawable{
	protected Shape shape;
	private Rectangle2D[] regulateSign;			//Rectangles for regulating size of shape
	private Ellipse2D rotateSign;				//ellipse for rotating shape
	private boolean isSelected;					//whether this shape is focused
	private CanvasFeature feature;				//additional feature of shape
	
	//constructor
	public CanvasRectShape(){
		isSelected = false;
		feature = new CanvasFeature();
	}
	
	abstract public void setBothEnds(Point2D p1, Point2D p2);
	
	//getter
	public Rectangle2D[] getRegulateSign(){
		calcRegulateSign();
		return regulateSign;
	}
		
	public Ellipse2D getRotateSign(){
		calcRotateSign();
		return rotateSign;
	}
		
	public boolean getIsSelected(){
		return isSelected;
	}
		
	public CanvasFeature getFeature(){
		return feature;
	}
		
	public Rectangle2D getBounds2D(){
		return shape.getBounds2D();
	}
		
	//setter
	public void setIsSelected(boolean select){
		isSelected = select;
	}
	
	public void setFeature(CanvasFeature feature){
		this.feature = feature;
	}
	
	protected void setRegulateSign(Rectangle2D[] sign){
		regulateSign = sign;
	}
	
	//calculate position of regulating signs
	//overriding in the CanvasLine2D class
	//so access modifier is protected
	protected void calcRegulateSign(){
		Point2D[] regulateP = new Point2D[8];
		for(int i=0; i<8 ; i++){
			regulateP[i] = new Point2D.Double();
		}
		
			
		regulateP[0].setLocation(getBounds2D().getMinX(), getBounds2D().getMinY());
		regulateP[1].setLocation(getBounds2D().getCenterX(), getBounds2D().getMinY());
		regulateP[2].setLocation(getBounds2D().getMaxX(), getBounds2D().getMinY());
		regulateP[3].setLocation(getBounds2D().getMinX(), getBounds2D().getCenterY());
		regulateP[4].setLocation(getBounds2D().getMaxX(), getBounds2D().getCenterY());
		regulateP[5].setLocation(getBounds2D().getMinX(), getBounds2D().getMaxY());
		regulateP[6].setLocation(getBounds2D().getCenterX(), getBounds2D().getMaxY());
		regulateP[7].setLocation(getBounds2D().getMaxX(), getBounds2D().getMaxY());
		
		
		regulateSign = new Rectangle2D[8];
		for(int i = 0;i<8;i++){
			regulateSign[i] = new Rectangle2D.Double(regulateP[i].getX() - 3, regulateP[i].getY() - 3, 6, 6);
		}
	}
	
	//calculate position of rotating sign
	private void calcRotateSign(){
		Point2D centerP = new Point2D.Double(getBounds2D().getCenterX(), getBounds2D().getMinY() - 22);
		rotateSign = new Ellipse2D.Double(centerP.getX() - 8, centerP.getY() - 8, 16, 16);
	}
	
	@Override
	public boolean contains(double x, double y){
		return shape.contains(x, y);
	}
	
	@Override
	public void transform(AffineTransform at){
		Point2D end1 = new Point2D.Double(getBounds2D().getMinX(), getBounds2D().getMinY());
		Point2D end2 = new Point2D.Double(getBounds2D().getMaxX(), getBounds2D().getMaxY());
		
		setBothEnds(at.transform(end1, null), at.transform(end2, null));
	}
	
	@Override
	public void editShape(Point2D end1, Point2D end2, boolean horizontalFlip, boolean verticalFlip){
		setBothEnds(end1, end2);
	}
	
	
	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		
		int thickness = getFeature().getThickness();
		
		AffineTransform at = AffineTransform.getRotateInstance(getFeature().getRotTheta(),
				getBounds2D().getCenterX(), getBounds2D().getCenterY());
		g2.setTransform(at);
		
		// draw inner triangle
		g2.setColor(getFeature().getInnerColor());
		g2.fill(shape);
		
		// draw outer triangle 
		g2.setColor(getFeature().getOutlineColor());
		g2.setStroke(new BasicStroke(thickness));
		g2.draw(shape);
	}
}
