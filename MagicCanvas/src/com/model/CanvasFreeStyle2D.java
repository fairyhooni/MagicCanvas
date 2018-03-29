package com.model;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

public class CanvasFreeStyle2D extends CanvasFreeLine2D {
	private static final long serialVersionUID = 1L;
	
	public void preview(Point2D p){
		path = (Path2D.Double)previousPath.clone();
		path.lineTo(p.getX(), p.getY());
		
		if(checkEnclosed()){
			path.closePath();
		}
	}
}
