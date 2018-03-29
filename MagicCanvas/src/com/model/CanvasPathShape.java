package com.model;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

//this shape is generated
//by pen
public abstract class CanvasPathShape implements Drawable{
	protected Path2D path;
	private Rectangle2D[] regulateSign;			
	private Ellipse2D rotateSign;
	private boolean isSelected;
	private boolean isEnclosed;
	private CanvasFeature feature;
	
	
	public CanvasPathShape(){
		isSelected = false;
		isEnclosed = false;
		feature = new CanvasFeature();
	}
	
	
	
	
	abstract public void appendPoint(Point2D p);
	
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
		
	public boolean getIsEnclosed(){
		return isEnclosed;
	}
			
	public CanvasFeature getFeature(){
		return feature;
	}
			
	public Rectangle2D getBounds2D(){
		return path.getBounds2D();
	}
	
	//setter
	public void setIsSelected(boolean select){
		isSelected = select;
	}
		
	protected void setIsEnclosed(boolean enclose){
		isEnclosed = enclose;
	}
		
	public void setFeature(CanvasFeature feature){
		this.feature = feature;
	}
	
	
	
	
		
	//calculate position of regulating rectangles
	private void calcRegulateSign(){
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
		
	private void calcRotateSign(){
		Point2D centerP = new Point2D.Double(getBounds2D().getCenterX(), getBounds2D().getMinY() - 22);
		rotateSign = new Ellipse2D.Double(centerP.getX() - 8, centerP.getY() - 8, 16, 16);
	}
		
	
	@Override
	public boolean contains(double x, double y){
		if(isEnclosed){
			return path.contains(x, y);
		}
		else{
			ArrayList<double[]> pointList = new ArrayList<double[]>();
			Rectangle2D pointerArea = new Rectangle2D.Double(x - 5, y - 5, 10, 10);
			
			double[] coords;
			for(PathIterator pi = path.getPathIterator(null); !pi.isDone(); pi.next()){
				coords = new double[2];
				pi.currentSegment(coords);
				pointList.add(coords);                        
			}
			
			for(int i = 0; i < pointList.size() - 1; i++){
				if(pointerArea.intersectsLine(pointList.get(i)[0], pointList.get(i)[1], pointList.get(i+1)[0], pointList.get(i+1)[1])){
					return true;
				}
			}
			
			return false;
		}
	}
	
	@Override 
	public void transform(AffineTransform at){
		path.transform(at);
	}
	
	@Override
	public void editShape(Point2D end1, Point2D end2, boolean horizontalFlip, boolean verticalFlip){
		Rectangle2D newBounds = new Rectangle2D.Double();
		newBounds.setFrameFromDiagonal(end1, end2);
		
		double scaleX = newBounds.getWidth() / getBounds2D().getWidth();
		double scaleY = newBounds.getHeight() / getBounds2D().getHeight();
		
		if(horizontalFlip)
			scaleX = -scaleX;
		
		if(verticalFlip)
			scaleY = -scaleY;
		
		AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
		
		path.transform(scaleTransform);
		
		
		AffineTransform translateTransform = AffineTransform.getTranslateInstance(
				newBounds.getMinX() - getBounds2D().getMinX(), newBounds.getMinY() - getBounds2D().getMinY());
		
		path.transform(translateTransform);
	}
	
	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		
		//if first point meet last point, fill the shape.
		
		AffineTransform at = AffineTransform.getRotateInstance(getFeature().getRotTheta(),
				getBounds2D().getCenterX(), getBounds2D().getCenterY());
		g2.setTransform(at);
		
		if(isEnclosed){
			g2.setColor(feature.getInnerColor());
			g2.fill(path);
		}
		
		int thickness = feature.getThickness();
		g2.setStroke(new BasicStroke(thickness));
		g2.setColor(feature.getOutlineColor());
		g2.draw(path);
	}
}