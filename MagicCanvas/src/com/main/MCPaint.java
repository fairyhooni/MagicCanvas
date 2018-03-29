package com.main;

import java.io.File;

import javax.swing.SwingUtilities;

import com.gui.MainGUI;
import com.model.Components;


public class MCPaint {

//	public static void main(String[] args) {
//		SwingUtilities.invokeLater(new DocCollaborator());
//	}
//
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		Components component = new Components();
//		MainGUI gui = new MainGUI(component);
//		
//		gui.guiBuild();
//		
//	}
	
	public static void main(String[] args){
		Components component = new Components();					//initialize model
		MainGUI gui = new MainGUI(component);						//initialize view & controller
		gui.guiBuild();												//visualize view & start program
		
		
		if(args != null && args.length != 0)						//if parameter passed,
			gui.openProjectFile(new File(args[0]));					//open the saved project 
	}
}
