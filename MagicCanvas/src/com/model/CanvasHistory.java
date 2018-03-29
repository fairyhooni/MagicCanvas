package com.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

//	undo & redo manager
//	implemented using stack
//	stack is consist of component list

public class CanvasHistory {
	private Stack<ArrayList<Drawable>> historyStack;
	private int currIndex;
	
	
	public CanvasHistory(ArrayList<Drawable> initState){
		historyStack = new Stack<ArrayList<Drawable>>();
		historyStack.push(initState);
		currIndex = 0;
	}
	
	
	public Stack<ArrayList<Drawable>> getHistoryStack(){
		return historyStack;
	}
	
	public ArrayList<Drawable> getCurrHistory(){
		return historyStack.get(currIndex);
	}
	
	public void setHistoryStack(Stack<ArrayList<Drawable>> h){
		historyStack = h;
	}
	

	
	public void addHistory(ArrayList<Drawable> history){
		while(historyStack.size() != currIndex + 1){
			historyStack.pop();
		}
		historyStack.push(history);
		currIndex++;
	}
	
	
	public void moveToPrevHistory(){
		if(currIndex > 0)
			currIndex--;
	}
	
	public void moveToNextHistory(){
		if(currIndex < historyStack.size() - 1)
			currIndex++;
	}
	
	public boolean hasPrevHistory(){
		if(currIndex > 0)
			return true;
		return false;
	}
	
	public boolean hasNextHistory(){
		if(currIndex < historyStack.size() - 1)
			return true;
		return false;
	}
}
