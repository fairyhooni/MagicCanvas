/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package com.model;

import java.awt.image.BufferedImage;
import java.util.*;


public class Components {
	private ArrayList<Drawable> componentList;				//Drawable things list
	
	
	/* constructor */
	public Components(){
		reset();
	}
	
	public void reset(){
		componentList = new ArrayList<Drawable>();
	}
	
	public void setComponentList(ArrayList<Drawable> list){
		componentList = list;
	}
	
	public ArrayList<Drawable> getComponentList(){	
		return this.componentList;
	}
	
	public void addComponent(Drawable component){
		this.componentList.add(component);
	}
	
//	public void updateRecentComponent(Drawable component){
//		int curSize = this.componentList.size();
//		this.componentList.set(curSize - 1, component);
//	}
	
	public void editComponent(Drawable originalComp, Drawable changedComp){		//edit specified component of arraylist
		int index = this.componentList.indexOf(originalComp);
		this.componentList.set(index, changedComp);
	}
	
	public void deleteComponent(Drawable component){
		this.componentList.remove(component);
	}
	
	public void deleteAll(){
		this.componentList.clear();
	}
	
	
	public static ArrayList<Drawable> cloneComponentList(ArrayList<Drawable> componentList){		//deep copy
		ArrayList<Drawable> cloneList = new ArrayList<Drawable>();
		for(Drawable comp : componentList){
			cloneList.add(comp.getClone());
		}
		return cloneList;
	}
}
