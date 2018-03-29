

package com.model;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;

public class CanvasLine2D extends CanvasRectShape {
	private static final long serialVersionUID = 1L;
	

	//constructor
	public CanvasLine2D(){
		shape = new Line2D.Double();
	}
	
	
	//calculate position of regulating signs
	protected void calcRegulateSign(){
		
		Point2D[] regulateP = new Point2D.Double[2];
		regulateP[0] = new Point2D.Double();
		regulateP[1] = new Point2D.Double();
		
		regulateP[0].setLocation(((Line2D.Double)shape).getP1());
		regulateP[1].setLocation(((Line2D.Double)shape).getP2());
		
		Rectangle2D[] regulateSign = new Rectangle2D.Double[2];
		
		for(int i = 0;i<2;i++){
			regulateSign[i] = new Rectangle2D.Double(regulateP[i].getX() - 3, regulateP[i].getY() - 3, 6, 6);
		}
		
		setRegulateSign(regulateSign);
	}
	
	@Override
	public boolean contains(double x, double y){
		Rectangle2D pointerArea = new Rectangle2D.Double(x - 5, y - 5, 10, 10);
		return pointerArea.intersectsLine(((Line2D.Double)shape));
	}
	
	@Override
	public void transform(AffineTransform at){
		Point2D end1 = ((Line2D.Double)shape).getP1();
		Point2D end2 = ((Line2D.Double)shape).getP2();
		
		setBothEnds(at.transform(end1, null), at.transform(end2, null));
	}
	
	
	
	@Override
	public void setBothEnds(Point2D p1, Point2D p2) {
		// TODO Auto-generated method stub
		((Line2D.Double)shape).setLine(p1, p2);
	}
	
	
	@Override
	public Drawable getClone() {
		// TODO Auto-generated method stub
		CanvasLine2D clone = new CanvasLine2D();

		clone.shape = (Shape) ((Line2D.Double)shape).clone();
		
		clone.setIsSelected(this.getIsSelected());
		
		clone.setFeature(new CanvasFeature(this.getFeature()));
		
		return clone;
	}


	@Override
	public Shape getShape() {
		// TODO Auto-generated method stub
		return (Shape) ((Line2D.Double)shape).clone();
	}


	@Override
	public void setShape(Shape s) {
		// TODO Auto-generated method stub
		Line2D l = (Line2D.Double)s;
		shape = (Line2D.Double)l.clone();
	}
}
