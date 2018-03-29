package com.model;

import java.awt.Color;
import java.io.Serializable;

//for feature of shape
public class CanvasFeature implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	private double rotTheta;
	private Color innerColor;
	private Color outlineColor;
	private int thickness;
	
	private int innerTransparency;
	private int outlineTransparency;
	
	//constructor
	public CanvasFeature(){
		rotTheta = 0.0;
		innerTransparency = 0;
		outlineTransparency = 0;
		innerColor = Color.WHITE;
		outlineColor = Color.BLACK;
		thickness = 2;
	}
	
	//copy constructor
	public CanvasFeature(CanvasFeature feature){
		rotTheta = feature.rotTheta;
		innerTransparency = feature.innerTransparency;
		outlineTransparency = feature.outlineTransparency;
		innerColor = new Color(feature.innerColor.getRed(), feature.innerColor.getGreen(), feature.innerColor.getBlue(), feature.innerColor.getAlpha());
		outlineColor = new Color(feature.outlineColor.getRed(), feature.outlineColor.getGreen(), feature.outlineColor.getBlue(), feature.outlineColor.getAlpha());
		thickness = feature.thickness;
	}
	
	
	//getter
	public double getRotTheta(){
		return rotTheta;
	}
	
	public int getInnerTransparency(){
		return innerTransparency;
	}
	
	public int getOutlineTransparency(){
		return outlineTransparency;
	}
	
	public Color getInnerColor(){
		return innerColor;
	}
	
	public Color getOutlineColor(){
		return outlineColor;
	}
	
	public int getThickness(){
		return thickness;
	}
	
	
	//setter
	public void setRotTheta(double theta){
		rotTheta = theta;
	}
	
	public void setInnerTransparency(int transparency){
		innerTransparency = transparency;
		
		int opaque;
		if(transparency >=0 & transparency <= 100){
			opaque = (int) (255.0/100.0 * (100 - transparency));
			innerColor = new Color(innerColor.getRed(), innerColor.getGreen(), innerColor.getBlue(), opaque);
		}
	}
	
	public void setOutlineTransparency(int transparency){
		outlineTransparency = transparency;
		
		int opaque;
		if(transparency >=0 & transparency <= 100){
			opaque = (int) (255.0/100.0 * (100 - transparency));
			outlineColor = new Color(outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue(), opaque);
		}
	}
	
	public void setInnerColor(Color color){
		innerColor = color;
	}
	
	public void setOutlineColor(Color color){
		outlineColor = color;
	}
	
	public void setThickness(int thick){
		thickness = thick;
	}
	
}
