package com.model;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class CanvasImage2D implements Drawable {
	private static final long serialVersionUID = 1L;
	
	private transient BufferedImage img;
	private Rectangle2D imgBounds;
	private Rectangle2D[] regulateSign;			//Rectangles for regulating size of shape
	private Ellipse2D rotateSign;				//ellipse for rotating shape
	private boolean isSelected;					//whether this shape is focused
	private CanvasFeature feature;				//additional feature of shape

	
	private boolean horizontalReverse;
	private boolean verticalReverse;
	
	
	public CanvasImage2D(BufferedImage image){
		img = image;
		
		imgBounds = new Rectangle2D.Double(0, 0, img.getWidth(), img.getHeight());
		isSelected = false;
		feature = new CanvasFeature();
		feature.setOutlineTransparency(100);
		
		horizontalReverse = false;
		verticalReverse = false;
	}
	
	
	@Override
	public Rectangle2D[] getRegulateSign() {
		// TODO Auto-generated method stub
		calcRegulateSign();
		return regulateSign;
	}

	@Override
	public Ellipse2D getRotateSign() {
		calcRotateSign();
		return rotateSign;
	}

	@Override
	public boolean getIsSelected() {
		return isSelected;
	}

	@Override
	public CanvasFeature getFeature() {
		return feature;
	}

	@Override
	public Rectangle2D getBounds2D() {
		return imgBounds.getBounds2D();
	}

	@Override
	public Shape getShape() {
		return (Shape) ((Rectangle2D.Double)imgBounds).clone();
	}
	
	public boolean getVerticalReverse(){
		return this.verticalReverse;
	}
	
	public boolean getHorizontalReverse(){
		return this.horizontalReverse;
	}

	@Override
	public void setIsSelected(boolean select) {
		// TODO Auto-generated method stub
		isSelected = select;
	}

	@Override
	public void setFeature(CanvasFeature canvasFeature) {
		// TODO Auto-generated method stub
		feature = canvasFeature;
	}
	
	public void setBothEnds(Point2D p1, Point2D p2){
		imgBounds.setFrameFromDiagonal(p1, p2);
	}

	@Override
	public void setShape(Shape s) {
		// TODO Auto-generated method stub
		Rectangle2D e = (Rectangle2D.Double)s;
		imgBounds = (Rectangle2D.Double)e.clone();
	}
	
	public void setVerticalReverse(boolean b){
		this.verticalReverse = b;
	}
	
	public void setHorizontalReverse(boolean b){
		this.horizontalReverse = b;
	}
	
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
	
	//calculate position of rotating sign
	private void calcRotateSign(){
		Point2D centerP = new Point2D.Double(getBounds2D().getCenterX(), getBounds2D().getMinY() - 22);
		rotateSign = new Ellipse2D.Double(centerP.getX() - 8, centerP.getY() - 8, 16, 16);
	}

	
	@Override
	public void transform(AffineTransform at) {
		Point2D end1 = new Point2D.Double(getBounds2D().getMinX(), getBounds2D().getMinY());
		Point2D end2 = new Point2D.Double(getBounds2D().getMaxX(), getBounds2D().getMaxY());
		
		setBothEnds(at.transform(end1, null), at.transform(end2, null));
	}

	@Override
	public void editShape(Point2D end1, Point2D end2, boolean horizontalFlip, boolean verticalFlip) {
		setBothEnds(end1, end2);
		
		if(horizontalFlip)
			horizontalReverse = !horizontalReverse;
		
		if(verticalFlip)
			verticalReverse = !verticalReverse;	
	}

	@Override
	public boolean contains(double x, double y) {
		// TODO Auto-generated method stub
		return imgBounds.contains(x, y);
	}
	
	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub

		AffineTransform rotate = AffineTransform.getRotateInstance(feature.getRotTheta(), 
				getBounds2D().getCenterX(), getBounds2D().getCenterY());
		g2.setTransform(rotate);
		
		int minX = (int)getBounds2D().getMinX();
		int minY = (int)getBounds2D().getMinY();
		
		if(horizontalReverse){
			AffineTransform hFlip = AffineTransform.getScaleInstance(-1, 1);
			g2.transform(hFlip);
			minX = -(int)getBounds2D().getMaxX();
		}
		
		if(verticalReverse){
			AffineTransform vFlip = AffineTransform.getScaleInstance(1, -1);
			g2.transform(vFlip);
			minY = -(int)getBounds2D().getMaxY();
		}
		
		float opacity = (float) ((100 - feature.getInnerTransparency())/100.0);
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		g2.drawImage(img, minX, minY,
				(int)getBounds2D().getWidth(), (int)getBounds2D().getHeight(), null);
		
		
		
		g2.setTransform(rotate);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		int thickness = feature.getThickness();
		g2.setStroke(new BasicStroke(thickness));
		g2.setColor(feature.getOutlineColor());
		g2.draw(imgBounds);
	}
	
	@Override
	public Drawable getClone() {
		CanvasImage2D clone = new CanvasImage2D(img);

		clone.horizontalReverse = horizontalReverse;
		
		clone.verticalReverse = verticalReverse;
		
		clone.imgBounds = (Rectangle2D) getShape();
		
		clone.isSelected = isSelected;
		
		clone.setFeature(new CanvasFeature(feature));
		
		return clone;
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException{
		oos.defaultWriteObject();
		
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	    ImageIO.write(img, "png", buffer);
	    buffer.flush();
	    oos.writeInt(buffer.size()); // Prepend image with byte count
	    buffer.writeTo(oos);         // Write image
	    buffer.close();
	}
	
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ois.defaultReadObject();
        
	    int size = ois.readInt(); // Read byte count
	    byte[] buffer = new byte[size];
	    ois.readFully(buffer); // Make sure you read all bytes of the image
        img = ImageIO.read(new ByteArrayInputStream(buffer));
    }	
}
