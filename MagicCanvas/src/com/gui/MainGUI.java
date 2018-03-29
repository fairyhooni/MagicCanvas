/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package com.gui;

import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.main.MCPaint;
import com.model.CanvasEllipse2D;
import com.model.CanvasFreeLine2D;
import com.model.CanvasFreeStyle2D;
import com.model.CanvasHistory;
import com.model.CanvasImage2D;
import com.model.CanvasLine2D;
import com.model.CanvasPathShape;
import com.model.CanvasRectShape;
import com.model.CanvasRectangle2D;
import com.model.CanvasTriangle2D;
import com.model.Components;
import com.model.Drawable;

import java.util.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.net.URL;

public class MainGUI {
	private static final String certificationTextForSerialize = 
			"/* Copyright by Choon ** Program : Magic Canvas ** Certification code : HOONI1352";		//need for (de)serialize
	
	private URL frameIconURL = getClass().getResource("/ImageIcon/magicwand.png");

	
	private URL newIconURL = getClass().getResource("/ImageIcon/new_file.png");
	private URL openIconURL = getClass().getResource("/ImageIcon/open_file.png");
	private URL saveIconURL = getClass().getResource("/ImageIcon/save_file.png");
	private URL exitIconURL = getClass().getResource("/ImageIcon/exit_program.png");
	
	private URL shapeIconURL = getClass().getResource("/ImageIcon/Polygon.png");
	private URL lineIconURL = getClass().getResource("/ImageIcon/Line.png");
	private URL ellipseIconURL = getClass().getResource("/ImageIcon/Ellipse.png");
	private URL rectangleIconURL = getClass().getResource("/ImageIcon/Rectangle.png");
	private URL triangleIconURL = getClass().getResource("/ImageIcon/Triangle.png");
	private URL freeLineIconURL = getClass().getResource("/ImageIcon/FreeLine.png");
	private URL freeStyleIconURL = getClass().getResource("/ImageIcon/FreeStyle.png");
	
	private URL sortFwdIconURL = getClass().getResource("/ImageIcon/sort_forward.png");
	private URL sortBwdIconURL = getClass().getResource("/ImageIcon/sort_backward.png");
	
	private URL undoIconURL = getClass().getResource("/ImageIcon/undo_icon&32.png");
	private URL redoIconURL = getClass().getResource("/ImageIcon/redo_icon&32.png");
	private URL duplicateIconURL = getClass().getResource("/ImageIcon/duplicate.png");
	
	private URL rotateIconURL = getClass().getResource("/ImageIcon/reload_icon&16.PNG");
	private URL cautionIconURL = getClass().getResource("/ImageIcon/caution.PNG");
	private URL pencilIconURL = getClass().getResource("/ImageIcon/Pencil-icon.PNG");
	private URL refreshIconURL = getClass().getResource("/ImageIcon/refresh_icon&16.PNG");
	
	
	private Components components;						//model manager					
	private CanvasHistory drawPanelHistory;				//this is history manager for undo & redo
	
	private JFrame projFrame;						//main frame
	private DrawPanel drawPanel;					//drawable panel(canvas) 
	
	private JMenuItem newMI;
	private JMenuItem exitMI;
	private JMenuItem openImageMI;
	private JMenuItem openProjectMI;
	private JMenuItem saveToImageMI;
	private JMenuItem saveToProjectMI;
	
	//	shape buttons
	private JButton lineButton;
	private JButton ellipseButton;
	private JButton rectangleButton;
	private JButton triangleButton;
	private JButton freeLineButton;
	private JButton freeStyleButton;
	
	private JButton lineColorButton;
	private JButton fillColorButton;
	
	private JButton lineColorApplyButton;
	private JButton fillColorApplyButton;
	
	private ButtonGroup buttonGroup;
	private JRadioButton lineTransparencyButton;
	private JRadioButton fillTransparencyButton;
	private JSlider transparencySlider;

	//	current selected color
	private Color currLineColor;
	private Color currFillColor;
	
	private JLabel cursorPosLabel;
	
	//	shape order change button
	private JButton sortFwdButton;
	private JButton sortBwdButton;
	
	private JButton undoButton;
	private JButton redoButton;
	
	//	copy shape
	private JButton duplicateButton;
	
	
	
	//model connect to gui
	@SuppressWarnings("unchecked")
	public MainGUI(Components comp){
		components = comp;
		drawPanelHistory = new CanvasHistory((ArrayList<Drawable>)components.getComponentList().clone());
	}
	
	//	build view
	public void guiBuild(){

		projFrame = new JFrame("Magic Canvas");
		
		ImageIcon frameIcon = new ImageIcon(frameIconURL);
		projFrame.setIconImage(frameIcon.getImage());
		
		
		/* menu bar */
		JMenuBar menuBar = new JMenuBar();
		
		
		JMenu fileMenu = new JMenu("File");
		
		Font fileFont = new Font("Consolas", Font.BOLD, 15);
		fileMenu.setFont(fileFont);
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		
		ImageIcon newIcon = new ImageIcon(newIconURL);
		ImageIcon openIcon = new ImageIcon(openIconURL);
		ImageIcon saveIcon = new ImageIcon(saveIconURL);
		ImageIcon exitIcon = new ImageIcon(exitIconURL);
		
		JMenu openMenu = new JMenu("Open");
		JMenu saveMenu = new JMenu("Save");
		
		openMenu.setIcon(openIcon);
		saveMenu.setIcon(saveIcon);
		
		newMI = new JMenuItem("New", newIcon);
		exitMI = new JMenuItem("Exit", exitIcon);
		openImageMI = new JMenuItem("Open image");
		openProjectMI = new JMenuItem("Open project");
		saveToImageMI = new JMenuItem("Save to image");
		saveToProjectMI = new JMenuItem("Save to project");
		
		newMI.setMnemonic(KeyEvent.VK_N);
		exitMI.setMnemonic(KeyEvent.VK_E);
		openMenu.setMnemonic(KeyEvent.VK_O);
		saveMenu.setMnemonic(KeyEvent.VK_S);
		
		newMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		exitMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		openProjectMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		saveToProjectMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		
		openMenu.add(openImageMI);
		openMenu.add(openProjectMI);
	
		saveMenu.add(saveToImageMI);
		saveMenu.add(saveToProjectMI);
		
		
		fileMenu.add(newMI);
		fileMenu.add(openMenu);
		fileMenu.add(saveMenu);
		fileMenu.addSeparator();
		fileMenu.add(exitMI);
		
		
		menuBar.add(fileMenu);
		
		
		/* draw(canvas) panel */
		
		drawPanel = new DrawPanel();
		
		
		
		/* menu panel */
		
		JPanel menuPanel = new JPanel();
		menuPanel.setPreferredSize(new Dimension(1000 ,120));
		menuPanel.setBackground(Color.lightGray);
		menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT ,10 ,5));
	
		
		/* shape select panel */
		
		JPanel shapeSelectPanel = new JPanel();			//shape select panel
		shapeSelectPanel.setPreferredSize(new Dimension(76,107));
		
		ImageIcon shapeIcon = new ImageIcon(shapeIconURL);
		JLabel shapeLabel = new JLabel(shapeIcon);
		shapeLabel.setPreferredSize(new Dimension(shapeIcon.getIconWidth(),shapeIcon.getIconHeight()));
		// create shape button 
		ImageIcon lineIcon = new ImageIcon(lineIconURL);
		ImageIcon ellipseIcon = new ImageIcon(ellipseIconURL);
		ImageIcon rectangleIcon = new ImageIcon(rectangleIconURL);
		ImageIcon triangleIcon = new ImageIcon(triangleIconURL);
		ImageIcon freeLineIcon = new ImageIcon(freeLineIconURL);
		ImageIcon freeStyleIcon = new ImageIcon(freeStyleIconURL);
		
		lineButton = new JButton(lineIcon);
		ellipseButton = new JButton(ellipseIcon);
		rectangleButton = new JButton(rectangleIcon);
		triangleButton = new JButton(triangleIcon);
		freeLineButton = new JButton(freeLineIcon);
		freeStyleButton = new JButton(freeStyleIcon);
		
		
		lineButton.setPreferredSize(new Dimension(lineIcon.getIconWidth(),lineIcon.getIconHeight()));
		ellipseButton.setPreferredSize(new Dimension(ellipseIcon.getIconWidth(),ellipseIcon.getIconHeight()));
		rectangleButton.setPreferredSize(new Dimension(rectangleIcon.getIconWidth(),rectangleIcon.getIconHeight()));
		triangleButton.setPreferredSize(new Dimension(triangleIcon.getIconWidth(),triangleIcon.getIconHeight()));
		freeLineButton.setPreferredSize(new Dimension(freeLineIcon.getIconWidth(),freeLineIcon.getIconHeight()));
		freeStyleButton.setPreferredSize(new Dimension(freeStyleIcon.getIconWidth(), freeStyleIcon.getIconHeight()));
		
		
		lineButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		ellipseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		rectangleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		triangleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		freeLineButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		freeStyleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		lineButton.setToolTipText("Line");
		ellipseButton.setToolTipText("Ellipse");
		rectangleButton.setToolTipText("Rectangle");
		triangleButton.setToolTipText("Triangle");
		freeLineButton.setToolTipText("Free Line");
		freeStyleButton.setToolTipText("Free Style");
		
		
		
		shapeSelectPanel.add(shapeLabel);
		shapeSelectPanel.add(lineButton);
		shapeSelectPanel.add(ellipseButton);
		shapeSelectPanel.add(rectangleButton);
		shapeSelectPanel.add(triangleButton);
		shapeSelectPanel.add(freeLineButton);
		shapeSelectPanel.add(freeStyleButton);
		
		/* shape select panel end */
		
		
		/* shape color panel */
		
		JPanel shapeColorPanel = new JPanel();
		shapeColorPanel.setPreferredSize(new Dimension(200,107));
		shapeColorPanel.setBorder(new TitledBorder("Shape color"));
		
		lineColorButton = new JRoundButton("Line Color");
		fillColorButton = new JRoundButton("Fill Color");
		
		// fit button size
		fillColorButton.setPreferredSize(lineColorButton.getPreferredSize());
		
		lineColorButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		fillColorButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		/* make it paint its background color */
		lineColorApplyButton = new JButton(){
		    public void paintComponent(Graphics g) {
		        g.setColor(getBackground());
		        g.fillRect(0, 0, getWidth(), getHeight());
		    }
		};
		fillColorApplyButton = new JButton() {
		    public void paintComponent(Graphics g) {
		        g.setColor(getBackground());
		        g.fillRect(0, 0, getWidth(), getHeight());
		    }
		};
	
		
		lineColorApplyButton.setBackground(Color.BLACK);
		fillColorApplyButton.setBackground(Color.GREEN);
		lineColorApplyButton.setOpaque(false);
		fillColorApplyButton.setOpaque(false);
		lineColorApplyButton.setPreferredSize(new Dimension(60, 30));
		fillColorApplyButton.setPreferredSize(new Dimension(60, 30));
		lineColorApplyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		fillColorApplyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		
		
		shapeColorPanel.add(lineColorButton);
		shapeColorPanel.add(lineColorApplyButton);
		shapeColorPanel.add(fillColorButton);
		shapeColorPanel.add(fillColorApplyButton);
		
		/* shape color panel end */
		
		
		
		/* alpha regulation panel */
		
		JPanel alphaPanel = new JPanel();
		alphaPanel.setPreferredSize(new Dimension(250,107));
		alphaPanel.setBorder(new TitledBorder("Adjust transparency"));
		
		lineTransparencyButton = new JRadioButton("Line    ");
		fillTransparencyButton = new JRadioButton("Fill    ");
		
		buttonGroup = new ButtonGroup();
		buttonGroup.add(lineTransparencyButton);
		buttonGroup.add(fillTransparencyButton);
		
		Font font = new Font("Consolas", Font.PLAIN, 12);
		
		transparencySlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
		transparencySlider.setPreferredSize(new Dimension(180, 45));
		transparencySlider.setMinorTickSpacing(5);
		transparencySlider.setMajorTickSpacing(20);
		transparencySlider.setPaintTicks(true);
		transparencySlider.setPaintLabels(true);
		
		alphaPanel.add(lineTransparencyButton);
		alphaPanel.add(fillTransparencyButton);
		alphaPanel.add(transparencySlider);
		
		
	
		/* Cursor position panel */
		JPanel cursorPosPanel = new JPanel();
		cursorPosPanel.setPreferredSize(new Dimension((int) drawPanel.getPreferredSize().getWidth(), 25));
		cursorPosPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		cursorPosLabel = new JLabel();
		cursorPosLabel.setPreferredSize(new Dimension(100, 25));
		
		cursorPosPanel.add(cursorPosLabel);
		/* Cursor position panel end */
		
		
		/* ToolPanel */
		JPanel toolPanel = new JPanel();
		toolPanel.setPreferredSize(new Dimension(50, (int) drawPanel.getPreferredSize().getHeight()));
		
		ImageIcon sortFwdIcon = new ImageIcon(sortFwdIconURL);
		ImageIcon sortBwdIcon = new ImageIcon(sortBwdIconURL);
		
		sortFwdButton = new JButton(sortFwdIcon);
		sortBwdButton = new JButton(sortBwdIcon);
		sortFwdButton.setBackground(Color.WHITE);
		sortBwdButton.setBackground(Color.WHITE);
		sortFwdButton.setPreferredSize(new Dimension(sortFwdIcon.getIconWidth(), sortFwdIcon.getIconHeight()));
		sortBwdButton.setPreferredSize(new Dimension(sortBwdIcon.getIconWidth(), sortBwdIcon.getIconHeight()));
		sortFwdButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sortBwdButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sortFwdButton.setToolTipText("sort forward");
		sortBwdButton.setToolTipText("sort backward");
		
		
		
		ImageIcon undoIcon = new ImageIcon(undoIconURL);
		ImageIcon redoIcon = new ImageIcon(redoIconURL);
		
		undoButton = new JButton(undoIcon);
		redoButton = new JButton(redoIcon);
		undoButton.setBackground(Color.WHITE);
		redoButton.setBackground(Color.WHITE);
		undoButton.setPreferredSize(new Dimension(undoIcon.getIconWidth(), undoIcon.getIconHeight()));
		redoButton.setPreferredSize(new Dimension(redoIcon.getIconWidth(), redoIcon.getIconHeight()));
		undoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		redoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		undoButton.setToolTipText("undo (Alt+Z)");
		redoButton.setToolTipText("redo (Alt+Y)");
		undoButton.setMnemonic(KeyEvent.VK_Z);
		redoButton.setMnemonic(KeyEvent.VK_Y);
		undoButton.setEnabled(false);
		redoButton.setEnabled(false);
		
		
		ImageIcon duplicateIcon = new ImageIcon(duplicateIconURL);
		
		duplicateButton = new JButton(duplicateIcon);
		duplicateButton.setBackground(Color.WHITE);
		duplicateButton.setPreferredSize(new Dimension(duplicateIcon.getIconWidth(), duplicateIcon.getIconHeight()));
		duplicateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		duplicateButton.setToolTipText("duplicate the shape");
		
		
		
		
		toolPanel.add(sortFwdButton);
		toolPanel.add(sortBwdButton);
		
		JSeparator separator1 = new JSeparator(JSeparator.HORIZONTAL);
		separator1.setPreferredSize(new Dimension(40,2));
		separator1.setBorder(new LineBorder(Color.ORANGE));
		toolPanel.add(separator1);
		
		toolPanel.add(undoButton);
		toolPanel.add(redoButton);

		JSeparator separator2 = new JSeparator(JSeparator.HORIZONTAL);
		separator2.setPreferredSize(new Dimension(40,2));
		separator2.setBorder(new LineBorder(Color.ORANGE));
		toolPanel.add(separator2);
		
		toolPanel.add(duplicateButton);
		
		
		/* ToolPanel end */
		
		
		menuPanel.add(shapeSelectPanel);
		menuPanel.add(shapeColorPanel);
		menuPanel.add(alphaPanel);
		
		
		projFrame.setJMenuBar(menuBar);
		projFrame.getContentPane().add(BorderLayout.CENTER, drawPanel);
		projFrame.getContentPane().add(BorderLayout.NORTH, menuPanel);
		projFrame.getContentPane().add(BorderLayout.SOUTH, cursorPosPanel);
		projFrame.getContentPane().add(BorderLayout.WEST, toolPanel);
		projFrame.setSize(1200,900);
		projFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Dimension frameSize = projFrame.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		chainViewWithListener();
		projFrame.setLocation((screenSize.width - frameSize.width)/2, 0);
		projFrame.setVisible(true);
		
	}
	
	
	//add listeners
	public void chainViewWithListener(){
		//hotkey listener
		projFrame.addKeyListener(new CanvasKeyListener());
		
		//menuItem listener
		newMI.addActionListener(new newMIListener());
		exitMI.addActionListener(new ExitMIListener());
		openImageMI.addActionListener(new OpenImageMIListener());
		openProjectMI.addActionListener(new OpenProjectMIListener());
		saveToImageMI.addActionListener(new SaveToImageMIListener());
		saveToProjectMI.addActionListener(new SaveToProjectMIListener());
		
		//drawPanel listener
		CanvasMouseListener cml = new CanvasMouseListener();
		drawPanel.addMouseListener(cml);
		drawPanel.addMouseMotionListener(cml);
		
		//cursor position listener
		CursorPosListener cpl = new CursorPosListener();
		drawPanel.addMouseListener(cpl);
		drawPanel.addMouseMotionListener(cpl);
		
		
		//shape select button listener
		lineButton.addActionListener(new ShapeButtonListener());
		ellipseButton.addActionListener(new ShapeButtonListener());
		rectangleButton.addActionListener(new ShapeButtonListener());
		triangleButton.addActionListener(new ShapeButtonListener());
		freeLineButton.addActionListener(new ShapeButtonListener());
		freeStyleButton.addActionListener(new ShapeButtonListener());
		
		
		//color choice button listener
		lineColorButton.addActionListener(new LineColorChooseButtonListener());
		fillColorButton.addActionListener(new FillColorChooseButtonListener());
		
		
		//color apply button listener
		lineColorApplyButton.addActionListener(new ColorApplyButtonListener());
		fillColorApplyButton.addActionListener(new ColorApplyButtonListener());
		
		//adjust transparency listener
//		alphaRegulationSlider.addChangeListener(l);
		
		//shape reassign button listener
		sortFwdButton.addActionListener(new SortFwdButtonListener());
		sortBwdButton.addActionListener(new SortBwdButtonListener());
		
		//undo&redo button listener
		undoButton.addActionListener(new UndoButtonListener());
		redoButton.addActionListener(new RedoButtonListener());
		
		//duplicate button listener
		duplicateButton.addActionListener(new DuplicateButtonListener());
		
		//transparency slider listener
		lineTransparencyButton.addActionListener(new LineTransparencyButtonListener());
		fillTransparencyButton.addActionListener(new FillTransparencyButtonListener());
		transparencySlider.addChangeListener(new TransparencySliderListener());
	}
	
	
	//	drawable panel(this is canvas)
	private class DrawPanel extends JPanel{
		@Override
		public void paintComponent(Graphics g){
			Graphics2D g2 = (Graphics2D)g;
			ArrayList<Drawable> componentList = components.getComponentList();
			
			/* set background color */
			g2.setColor(new Color(255,255,255));
			g2.fillRect(0, 0, this.getWidth(), this.getHeight());
			
			/* draw components */
			for(Drawable component : componentList){
				g2.setTransform(new AffineTransform());			//	reset transform
				component.draw(g2);
			}
			
			/* draw focus line */
			for(Drawable component : componentList){
				g2.setTransform(new AffineTransform());
				
				if(component.getIsSelected()){					//	if clicked shape
					Rectangle2D[] regulateRect = component.getRegulateSign();
					Ellipse2D rotateSign = component.getRotateSign();
					//Only Line2D, draw regulate rectangle at both ends of line 
					if(component instanceof CanvasLine2D){
						
						g2.setColor(Color.GRAY);
						g2.setStroke(new BasicStroke(2));
						for(Rectangle2D rect: regulateRect){
							g2.draw(rect);
						}
					}else{
					
						g2.setColor(Color.GRAY);
						
						AffineTransform at = AffineTransform.getRotateInstance(component.getFeature().getRotTheta(),
								component.getBounds2D().getCenterX(), component.getBounds2D().getCenterY());
						g2.setTransform(at);
						
						//	draw dash border
						float[] dash = new float[]{5,5,5,5};
						g2.setStroke(new BasicStroke(1,0,BasicStroke.JOIN_MITER,1.0f,dash, 0));
						g2.draw(component.getBounds2D());
						
						//	draw regulate sign
						g2.setStroke(new BasicStroke(2));
						for(Rectangle2D rect : regulateRect){
							g2.draw(rect);
						}
						
						//	draw rotate sign
						g2.setColor(new Color(0,0,0));
						g2.setStroke(new BasicStroke(1));
						g2.drawLine((int)component.getBounds2D().getCenterX(), (int)component.getBounds2D().getMinY(),
								(int)component.getBounds2D().getCenterX(), (int)component.getBounds2D().getMinY() - 15);
						ImageIcon rotateIcon = new ImageIcon(rotateIconURL);
						g2.drawImage(rotateIcon.getImage(), (int)rotateSign.getMinX(), (int)rotateSign.getMinY(), null);	
					}
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	/////////////////////////////
	/* Graphic Controller part */
	/////////////////////////////
	
	/*
	**	MenuItem listener part
	*/
	
	//	show warning dialog & return user's response (related to save this project) 
	public int confirmSaving(){
		ImageIcon cautionIcon = new ImageIcon(cautionIconURL);
		
		Font font = new Font("Consolas", Font.PLAIN, 15);
		JLabel saveLabel = new JLabel("Do you want to save?");
		saveLabel.setFont(font);
		
		String[] options = {"Save", "Do not save", "Cancel"};
		
		int response = JOptionPane.showOptionDialog(projFrame, saveLabel, "Magic Canvas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, cautionIcon, options, options[0]);
		
		return response;
	}
	
	//	show warning dialog & return user's response (related to file overwrite)
	public int confirmFileOverwrite(File file){
		ImageIcon cautionIcon = new ImageIcon(cautionIconURL);
		
		Font font = new Font("Consolas", Font.PLAIN, 15);
		JLabel fileNameLabel = new JLabel("The file named " + "'" + file.getName() + "'" + " already exist.\n");
		fileNameLabel.setFont(font);
		
		String[] options = {"File overwrite", "Rename", "Cancel"};
		
		int response = JOptionPane.showOptionDialog(projFrame, fileNameLabel, "Magic Canvas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, cautionIcon, options, options[1]);
		
		return response;
	}
	
	
	//decide file name
	public File fileNaming(JFileChooser fileChooser){
		File selectFile = null;
		boolean fileChooseEndFlag = false;
		do{
			int result = fileChooser.showSaveDialog(projFrame);
			
			if(result == JFileChooser.APPROVE_OPTION){
				selectFile = fileChooser.getSelectedFile();
				selectFile = new File(selectFile.toString() + "." + 
				((FileNameExtensionFilter)fileChooser.getFileFilter()).getExtensions()[0]);		//concatenate file name with extension
				
				if(selectFile.isFile()){
					int choice = confirmFileOverwrite(selectFile);
					switch (choice) {
					case 0: fileChooseEndFlag = true;
							break;
					case 2: selectFile = null;
							fileChooseEndFlag = true;
							break;
					}
				}
				else
					fileChooseEndFlag = true;
			}else
				fileChooseEndFlag = true;
			
		}while(!fileChooseEndFlag);
		
		
		return selectFile;
	}
	
	//	open saved project
	public void openProjectFile(File file){
		if(file == null | !file.exists()){
			System.out.println("Fail loading. File is not exists.");
			return;
		}
		components.reset();
		
		//	deserialize project file 
		ObjectInputStream mainIn = null;
		try{
			mainIn = new ObjectInputStream(new FileInputStream(file));
			int numOfComponents = mainIn.readInt();
			for(int i = 0; i < numOfComponents; i++){
				Drawable component = (Drawable)mainIn.readObject();
				components.addComponent(component);
			}
			mainIn.close();
		} catch(Exception ex){
			ex.printStackTrace();
		}
		
		//deselect all component
		ListIterator lI = components.getComponentList().listIterator();
		while(lI.hasNext()){	
			Drawable c = (Drawable)lI.next();
			if(c.getIsSelected()){
				c.setIsSelected(false);
			}
		}

		
		removeDrawPanelListener();
		
		CanvasMouseListener cml = new CanvasMouseListener();
		drawPanel.addMouseListener(cml);
		drawPanel.addMouseMotionListener(cml);
		
		//initialize history
		drawPanelHistory = new CanvasHistory(Components.cloneComponentList(components.getComponentList()));
		
		undoButton.setEnabled(false);
		redoButton.setEnabled(false);
		drawPanel.updateUI();
		projFrame.requestFocus();
	}
	
	
	private class newMIListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new MCPaint().main(null);			//	new program window
		}
	}
	
	private class ExitMIListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			int response = confirmSaving();				//	ask whether save or not before exit the program
			
			switch(response){
			case 0 : new SaveToProjectMIListener().actionPerformed(e);		//	save
			case 1 : projFrame.dispose();									//	do not save
			case 2 :														//	cancel
			}
		}
	}
	
	//	load image file
	private class OpenImageMIListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			//	setting file chooser's filter
			JFileChooser fileChooser = new JFileChooser("Open Image");
			
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.setFileFilter(new FileNameExtensionFilter("All picture file (*.png;*.jpg;*.jpeg;*.gif)", "PNG", "JPG", "JPEG", "GIF"));
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG(Portable Network Graphics) (*.png)", "PNG"));
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JPEG format (*.jpg;*.jpeg)", "JPG", "JPEG"));
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("GIF(Graphics Interchange Format) (*.gif)", "GIF"));
			
			int result = fileChooser.showOpenDialog(projFrame);
			
			//	if open file selected,
			if(result == JFileChooser.APPROVE_OPTION){
				drawPanel.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				
				//	read image
				CanvasImage2D img = null;
				try {
					img = new CanvasImage2D(ImageIO.read(fileChooser.getSelectedFile()));
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				
				//	resize original image's dimension according to canvas dimension
				double resizedWidth = img.getBounds2D().getWidth();
				double resizedHeight = img.getBounds2D().getHeight();
				double widthRate = img.getBounds2D().getWidth() / (drawPanel.getWidth() * 0.8);
				double heightRate = img.getBounds2D().getHeight() / (drawPanel.getHeight() * 0.8);			
				if(Math.max(widthRate, heightRate) > 1){
					resizedWidth *= 1.0 / Math.max(widthRate, heightRate);
					resizedHeight *= 1.0 / Math.max(widthRate, heightRate);
				}
				
				//	specify image's position
				Point2D startP = new Point2D.Double(drawPanel.getSize().getWidth()/2.0 - resizedWidth/2.0,
						drawPanel.getSize().getHeight()/2.0 - resizedHeight/2.0);
				Point2D endP = new Point2D.Double(startP.getX() + resizedWidth, startP.getY() + resizedHeight);			
				img.setBothEnds(startP, endP);
				
				
				
				components.addComponent(img);
				
				
				
				//////////////////////////////////////////////////////
				
				/* reset all component's select state */
				ListIterator lI = components.getComponentList().listIterator();
				while(lI.hasNext()){	
					Drawable c = (Drawable)lI.next();
					if(c.getIsSelected()){
						c.setIsSelected(false);
					}
				}
				
				img.setIsSelected(true);						//focus on created shape	
				
				
				removeDrawPanelListener();
				
				CanvasMouseListener cml = new CanvasMouseListener();
				cml.focusOn(img);
				drawPanel.addMouseListener(cml);
				drawPanel.addMouseMotionListener(cml);
				
				drawPanelHistory.addHistory(Components.cloneComponentList(components.getComponentList()));
				undoButton.setEnabled(true);
				redoButton.setEnabled(false);
				drawPanel.updateUI();
				projFrame.requestFocus();
			}
		}
	}
	
	//	load project file
	private class OpenProjectMIListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			JFileChooser fileChooser = new JFileChooser("Open Project");
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.setFileFilter(new FileNameExtensionFilter("Serialized format (*.ser)", "ser"));
			
			
			int result = fileChooser.showOpenDialog(projFrame);
			
			
			if(result == JFileChooser.APPROVE_OPTION){
				drawPanel.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				
				//	if there is no content
				//	load project in current program window
				//	if not
				//	load project in new program window
				if(components.getComponentList().size() == 0 && drawPanelHistory.getHistoryStack().size() <= 1){				
					openProjectFile(fileChooser.getSelectedFile());
				}else{
					String[] args = {fileChooser.getSelectedFile().getPath()};
					new MCPaint().main(args);
				}
			}
		}
	}
	
	
	//	save current project to image file
	private class SaveToImageMIListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JFileChooser fileChooser = new JFileChooser("Save to image");	
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.setFileFilter(new FileNameExtensionFilter("PNG(Portable Network Graphics) (*.png)", "PNG"));
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JPEG format (*.jpg;*.jpeg)", "JPG", "JPEG"));
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("GIF(Graphics Interchange Format) (*.gif)", "gif"));
			
			
			//file naming 
			File selectFile = fileNaming(fileChooser);
			
			
			if(selectFile != null){
				//	print out draw panel to image file 
				BufferedImage outputImage = new BufferedImage(drawPanel.getWidth(), drawPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2 = outputImage.createGraphics();
				drawPanel.print(g2);
				g2.dispose();
				
				try {
					ImageIO.write(outputImage, ((FileNameExtensionFilter)fileChooser.getFileFilter()).getExtensions()[0], selectFile);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	private class SaveToProjectMIListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JFileChooser fileChooser = new JFileChooser("Save to project");		
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.setFileFilter(new FileNameExtensionFilter("SER(Serialized format) (*.ser)", "ser"));
			
			
			//file naming 
			File selectFile = fileNaming(fileChooser);
			
			
			if(selectFile != null){
				drawPanel.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				
				//	serialize 
				ObjectOutputStream mainOut = null;
				try{
					mainOut = new ObjectOutputStream(new FileOutputStream(selectFile));
					mainOut.writeInt(components.getComponentList().size());
					for(Drawable component : components.getComponentList()){
						mainOut.writeObject(component);
					}
					mainOut.close();
				} catch(IOException ex){
					ex.printStackTrace();
				}
			}
		}
	}
	
	
	
	//	shape button part
	
	//	remove all mouse listener of draw panel(canvas) except cursor position listener
    public void removeDrawPanelListener(){
    	CursorPosListener cpl = new CursorPosListener();
		for(MouseListener ml : drawPanel.getMouseListeners()){
			drawPanel.removeMouseListener(ml);
			drawPanel.addMouseListener(cpl);
		}
		
		for(MouseMotionListener mml : drawPanel.getMouseMotionListeners()){
			drawPanel.removeMouseMotionListener(mml);
			drawPanel.addMouseMotionListener(cpl);
		}
	}
    
    //	shape select button
    private class ShapeButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			removeDrawPanelListener();
			
			//	setting view
			currLineColor = Color.BLACK;
			currFillColor = Color.GREEN;
			drawPanel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			
			
			//	catch what shape button is selected
			//	and generate new mouse listener of draw panel
			JButton clickButton = (JButton)e.getSource();
			ShapeDrawListener sdl = null;
			FreeDrawListener fdl = null;
			switch(clickButton.getToolTipText()){
					case "Line" : sdl = new ShapeDrawListener(CanvasLine2D.class.getName());
				 	break;
					case "Ellipse" : sdl = new ShapeDrawListener(CanvasEllipse2D.class.getName());
	 		  		break;
					case "Rectangle" : sdl = new ShapeDrawListener(CanvasRectangle2D.class.getName());
	 		  		break;
					case "Triangle" : sdl = new ShapeDrawListener(CanvasTriangle2D.class.getName());
	 		  		break;
					case "Free Line" : fdl = new FreeDrawListener(CanvasFreeLine2D.class.getName());
					Toolkit t = Toolkit.getDefaultToolkit();
					Image image = t.getImage(pencilIconURL);
					Point hotspot = new Point(8,24);
					Cursor cursor = t.createCustomCursor(image, hotspot, "rotate");
					drawPanel.setCursor(cursor);
	 		  		break;
					case "Free Style" : fdl = new FreeDrawListener(CanvasFreeStyle2D.class.getName());
	 		  		break;
			}
			
			if(sdl != null){
				drawPanel.addMouseListener(sdl);
				drawPanel.addMouseMotionListener(sdl);	
			}else if(fdl != null){
				drawPanel.addMouseListener(fdl);
				drawPanel.addMouseMotionListener(fdl);	
			}
		}
    }

    
	/* color choose listener */
	
	private class LineColorChooseButtonListener implements ActionListener{

		public LineColorChooseButtonListener(){
			currLineColor = Color.BLACK;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Color selectColor = JColorChooser.showDialog(projFrame, "Line Color", Color.BLACK);
			if(selectColor != null){
				currLineColor = selectColor;
				applyToFocusShape();
			}
			applyToLabel();
			drawPanel.updateUI();
			projFrame.requestFocus();
		}
		
		public void applyToLabel(){
			lineColorApplyButton.setBackground(currLineColor);
		}
		
		public void applyToFocusShape(){
			ArrayList<Drawable> componentList = components.getComponentList();
			for(Drawable component : componentList){
				if(component.getIsSelected()){
					component.getFeature().setOutlineColor(currLineColor);
					
					drawPanelHistory.addHistory(Components.cloneComponentList(componentList));
					undoButton.setEnabled(true);
					redoButton.setEnabled(false);
				}
			}
		}
	}
	
	
	private class FillColorChooseButtonListener implements ActionListener{
		
		public FillColorChooseButtonListener(){
			currFillColor = Color.GREEN;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub	
			Color selectColor = JColorChooser.showDialog(projFrame, "Fill Color", Color.BLACK);
			if(selectColor != null){
				currFillColor = selectColor;
				applyToFocusShape();
			}
			applyToLabel();
			drawPanel.updateUI();
			projFrame.requestFocus();
		}
		
		public void applyToLabel(){
			fillColorApplyButton.setBackground(currFillColor);
		}
		
		public void applyToFocusShape(){
			ArrayList<Drawable> componentList = components.getComponentList();
			for(Drawable component : componentList){
				if(component.getIsSelected()){
					component.getFeature().setInnerColor(currFillColor);
					
					drawPanelHistory.addHistory(Components.cloneComponentList(componentList));
					undoButton.setEnabled(true);
					redoButton.setEnabled(false);
				}
			}
		}
	}
	
	
	/* color apply listener */
	
	private class ColorApplyButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			ArrayList<Drawable> componentList = components.getComponentList();
			for(Drawable component : componentList){
				if(component.getIsSelected()){
					Color c = ((JButton)e.getSource()).getBackground();
					
					if(e.getSource() == lineColorApplyButton)
						component.getFeature().setOutlineColor(c);
					else if(e.getSource() == fillColorApplyButton)
						component.getFeature().setInnerColor(c);
					
					drawPanelHistory.addHistory(Components.cloneComponentList(componentList));
					undoButton.setEnabled(true);
					redoButton.setEnabled(false);
				}
			}
			drawPanel.updateUI();
			projFrame.requestFocus();
		}
	}
	
	
	/* sort button listener*/
	
	private class SortFwdButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			ArrayList<Drawable> componentList = components.getComponentList();
			for(int i = 0; i<componentList.size() - 1 ;i++){
				if(componentList.get(i).getIsSelected()){
					Drawable shape = componentList.get(i + 1);
					componentList.remove(i + 1);
					componentList.add(i, shape);
					
					drawPanelHistory.addHistory(Components.cloneComponentList(componentList));
					undoButton.setEnabled(true);
					redoButton.setEnabled(false);
					break;
				}
			}
			drawPanel.updateUI();
			projFrame.requestFocus();
		}
	}
	
	
	private class SortBwdButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			ArrayList<Drawable> componentList = components.getComponentList();
			for(int i = 1; i<componentList.size();i++){
				if(componentList.get(i).getIsSelected()){
					Drawable shape = componentList.get(i - 1);
					componentList.remove(i - 1);
					componentList.add(i, shape);
					
					drawPanelHistory.addHistory(Components.cloneComponentList(componentList));
					undoButton.setEnabled(true);
					redoButton.setEnabled(false);
					break;
				}
			}
			drawPanel.updateUI();
			projFrame.requestFocus();
		}
	}
	
	
	/* undo & redo button listener */
	private class UndoButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			drawPanelHistory.moveToPrevHistory();
			components.setComponentList(Components.cloneComponentList(drawPanelHistory.getCurrHistory()));
			
			//reset focus component 
			for(Drawable comp : components.getComponentList()){
				if(comp.getIsSelected()){
					removeDrawPanelListener();
					
					CanvasMouseListener cml = new CanvasMouseListener();
					cml.focusOn(comp);
					drawPanel.addMouseListener(cml);
					drawPanel.addMouseMotionListener(cml);
					
					break;
				}
			}
			
			//button setting
			if(drawPanelHistory.hasPrevHistory() == false){
				undoButton.setEnabled(false);
			}
			redoButton.setEnabled(true);
			
			drawPanel.updateUI();
			projFrame.requestFocus();
		}
	}
	
	private class RedoButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			drawPanelHistory.moveToNextHistory();
			components.setComponentList(Components.cloneComponentList(drawPanelHistory.getCurrHistory()));
			
			//reset focus component 
			for(Drawable comp : components.getComponentList()){
				if(comp.getIsSelected()){
					removeDrawPanelListener();
					
					CanvasMouseListener cml = new CanvasMouseListener();
					cml.focusOn(comp);
					drawPanel.addMouseListener(cml);
					drawPanel.addMouseMotionListener(cml);
					
					break;
				}
			}
			
			//button setting
			if(drawPanelHistory.hasNextHistory() == false){
				redoButton.setEnabled(false);
			}
			undoButton.setEnabled(true);
			
			drawPanel.updateUI();
			projFrame.requestFocus();
		}
	}


	/* duplicate button listener */
	private class DuplicateButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			for(Drawable comp : components.getComponentList()){
				if(comp.getIsSelected()){
					comp.setIsSelected(false);
					
					Drawable duplicate = comp.getClone();
					AffineTransform translate = AffineTransform.getTranslateInstance(10.0, 10.0);
					duplicate.transform(translate);
					components.addComponent(duplicate);
					duplicate.setIsSelected(true);
					
					removeDrawPanelListener();
					CanvasMouseListener cml = new CanvasMouseListener();
					cml.focusOn(duplicate);
					drawPanel.addMouseListener(cml);
					drawPanel.addMouseMotionListener(cml);
					
					drawPanelHistory.addHistory(Components.cloneComponentList(components.getComponentList()));
					undoButton.setEnabled(true);
					redoButton.setEnabled(false);
					drawPanel.updateUI();
					projFrame.requestFocus();
					
					break;
				}
			}
		}
	}
	
	
	private class LineTransparencyButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JRadioButton source = (JRadioButton)e.getSource();
			
			int currTransparency = 0;
			
			if(source.isSelected()){
				for(Drawable comp : components.getComponentList()){
					if(comp.getIsSelected()){
						currTransparency = comp.getFeature().getOutlineTransparency();
						break;
					}
				}
				
				transparencySlider.setValue(currTransparency);
			}
		}
	}
	
	private class FillTransparencyButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JRadioButton source = (JRadioButton)e.getSource();
			
			int currTransparency = 0;
			
			if(source.isSelected()){
				for(Drawable comp : components.getComponentList()){
					if(comp.getIsSelected()){
						currTransparency = comp.getFeature().getInnerTransparency();
						break;
					}
				}
				
				transparencySlider.setValue(currTransparency);
			}
		}
	}
	
	private class TransparencySliderListener implements ChangeListener{
		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			JSlider source = (JSlider)e.getSource();
			int currValue = source.getValue();
			
			Drawable focusComp = null;
			for(Drawable comp : components.getComponentList()){
				if(comp.getIsSelected()){
					focusComp = comp;
					if(lineTransparencyButton.isSelected()){
						comp.getFeature().setOutlineTransparency(currValue);
					}else if(fillTransparencyButton.isSelected()){
						comp.getFeature().setInnerTransparency(currValue);
					}
					break;
				}
			}
			
			if(!source.getValueIsAdjusting() & focusComp != null){
				drawPanelHistory.addHistory(Components.cloneComponentList(components.getComponentList()));
				undoButton.setEnabled(true);
				redoButton.setEnabled(false);
				projFrame.requestFocus();
			}
			
			drawPanel.updateUI();
		}
	}
	
	
	/* cursor position listener */
	private class CursorPosListener implements MouseListener, MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			Point currP = e.getPoint();
			cursorPosLabel.setText("x : " + currP.x + ",  y : " + currP.y);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			Point currP = e.getPoint();
			cursorPosLabel.setText("x : " + currP.x + ",  y : " + currP.y);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			cursorPosLabel.setText("");
		}
	}
	
	
	/* shape draw listener */
	
	private class ShapeDrawListener implements MouseListener, MouseMotionListener{
		private String shapeType;
		private Point startP;
		
		
		public ShapeDrawListener(String shapeT){
			shapeType = shapeT;
		}
		
		public CanvasRectShape constructComponent(){
			try {
				Class cl = Class.forName(shapeType);
				Constructor constructor = cl.getConstructor(new Class[] {});
				Object instance = constructor.newInstance();
				
				CanvasRectShape shape = (CanvasRectShape)instance;
				
				return shape;
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			return null;			//if class not found
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			Point endP = e.getPoint();
			CanvasRectShape currShape = (CanvasRectShape)components.getComponentList().get(components.getComponentList().size() - 1);
			currShape.setBothEnds(startP, endP);
			
			//Translucently when drawing
			Color fillColorWhenDrawing = new Color(currFillColor.getRed(),currFillColor.getGreen(),currFillColor.getBlue(),100);
			Color lineColorwhenDrawing = new Color(currLineColor.getRed(),currLineColor.getGreen(),currLineColor.getBlue(),100);
			currShape.getFeature().setInnerColor(fillColorWhenDrawing);
			currShape.getFeature().setOutlineColor(lineColorwhenDrawing);
			
			drawPanel.updateUI();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			this.startP = e.getPoint();
			CanvasRectShape component = constructComponent();
			components.addComponent(component);
		}

		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			Point endP = e.getPoint();
			CanvasRectShape currShape = (CanvasRectShape)components.getComponentList().get(components.getComponentList().size() - 1);
			currShape.setBothEnds(startP, endP);
			currShape.getFeature().setInnerColor(currFillColor);
			currShape.getFeature().setOutlineColor(currLineColor);
			/* reset all component's select state */
			ListIterator lI = components.getComponentList().listIterator();
			while(lI.hasNext()){	
				Drawable c = (Drawable)lI.next();
				if(c.getIsSelected()){
					c.setIsSelected(false);
				}
			}
			
			currShape.setIsSelected(true);						//focus on created shape		
			
			removeDrawPanelListener();
			drawPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			
			
			CanvasMouseListener cml = new CanvasMouseListener();
			cml.focusOn(currShape);
			drawPanel.addMouseListener(cml);
			drawPanel.addMouseMotionListener(cml);
			
			drawPanelHistory.addHistory(Components.cloneComponentList(components.getComponentList()));
			undoButton.setEnabled(true);
			redoButton.setEnabled(false);
			drawPanel.updateUI();
			projFrame.requestFocus();
			
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
		
	
	/* free draw listener */
	
	private class FreeDrawListener implements MouseListener, MouseMotionListener{
		private String penType;
		private boolean drawStartedFlag;
		
		
		public FreeDrawListener(String penT){
			penType = penT;
			drawStartedFlag = false;
		}
		
		public CanvasPathShape constructComponent(){
			try {
				Class cl = Class.forName(penType);
				Constructor constructor = cl.getConstructor(new Class[] {});
				Object instance = constructor.newInstance();
				
				CanvasPathShape pen = (CanvasPathShape)instance;
				
				return pen;
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			return null;			//if class not found
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			Point currP = e.getPoint();
			CanvasPathShape currPen = (CanvasPathShape)components.getComponentList().get(components.getComponentList().size() - 1);
			currPen.appendPoint(currP);
			
			//Translucently when drawing
			Color fillColorWhenDrawing = new Color(currFillColor.getRed(),currFillColor.getGreen(),currFillColor.getBlue(),100);
			Color lineColorwhenDrawing = new Color(currLineColor.getRed(),currLineColor.getGreen(),currLineColor.getBlue(),100);
			currPen.getFeature().setInnerColor(fillColorWhenDrawing);
			currPen.getFeature().setOutlineColor(lineColorwhenDrawing);
			
			drawPanel.updateUI();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			Point currP = e.getPoint();
			if(penType.equals(CanvasFreeStyle2D.class.getName()) && drawStartedFlag){
				CanvasFreeStyle2D currPen = (CanvasFreeStyle2D)components.getComponentList().get(components.getComponentList().size() - 1);
				currPen.preview(currP);
				
				//Translucently when drawing
				Color fillColorWhenDrawing = new Color(currFillColor.getRed(),currFillColor.getGreen(),currFillColor.getBlue(),100);
				Color lineColorwhenDrawing = new Color(currLineColor.getRed(),currLineColor.getGreen(),currLineColor.getBlue(),100);
				currPen.getFeature().setInnerColor(fillColorWhenDrawing);
				currPen.getFeature().setOutlineColor(lineColorwhenDrawing);
				
				drawPanel.updateUI();
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			Point currP = e.getPoint();
			if(penType.equals(CanvasFreeStyle2D.class.getName())){
				if(e.getClickCount() == 1){
					mouseDragged(e);
				}else if(e.getClickCount() == 2){
					CanvasPathShape currPen = (CanvasPathShape)components.getComponentList().get(components.getComponentList().size() - 1);
					
					
					//if it is shape(not one point)
					if(currPen.getBounds2D().getWidth() * currPen.getBounds2D().getHeight() != 0){
						currPen.appendPoint(currP);

						currPen.getFeature().setInnerColor(currFillColor);
						currPen.getFeature().setOutlineColor(currLineColor);
						
						/* reset all component's select state */
						ListIterator lI = components.getComponentList().listIterator();
						while(lI.hasNext()){	
							Drawable c = (Drawable)lI.next();
							if(c.getIsSelected()){
								c.setIsSelected(false);
							}
						}
						
						currPen.setIsSelected(true);
						CanvasMouseListener cml = new CanvasMouseListener();
						cml.focusOn(currPen);
						drawPanel.addMouseListener(cml);
						drawPanel.addMouseMotionListener(cml);
						
						projFrame.requestFocus();
					}

					
					///////////////////////

					drawPanel.removeMouseListener(this);
					drawPanel.removeMouseMotionListener(this);
					drawPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					
					drawPanelHistory.addHistory(Components.cloneComponentList(components.getComponentList()));
					undoButton.setEnabled(true);
					redoButton.setEnabled(false);
					drawPanel.updateUI();
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
			Point currP = e.getPoint();
			//if this point is initial point,
			if(drawStartedFlag == false){		
				CanvasPathShape component = constructComponent();
				component.appendPoint(currP);
				components.addComponent(component);
			}else {
				mouseDragged(e);
			}
			
			drawStartedFlag = true;
			
			
			if(penType.equals(CanvasFreeLine2D.class.getName())){
				Toolkit t = Toolkit.getDefaultToolkit();
				
				Image image = t.getImage(pencilIconURL);
				Point hotspot = new Point(8,24);
				
				Cursor cursor = t.createCustomCursor(image, hotspot, "rotate");
			
				drawPanel.setCursor(cursor);
			}
			
			
			drawPanel.updateUI();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			Point currP = e.getPoint();
			if(penType.equals(CanvasFreeLine2D.class.getName())){
				CanvasPathShape currPen = (CanvasPathShape)components.getComponentList().get(components.getComponentList().size() - 1);
				currPen.appendPoint(currP);
				
				currPen.getFeature().setInnerColor(currFillColor);
				currPen.getFeature().setOutlineColor(currLineColor);
				
				/* reset all component's select state */
				ListIterator lI = components.getComponentList().listIterator();
				while(lI.hasNext()){	
					Drawable c = (Drawable)lI.next();
					if(c.getIsSelected()){
						c.setIsSelected(false);
					}
				}
				
				currPen.setIsSelected(true);
				
				removeDrawPanelListener();
				drawPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				
				CanvasMouseListener cml = new CanvasMouseListener();
				cml.focusOn(currPen);
				drawPanel.addMouseListener(cml);
				drawPanel.addMouseMotionListener(cml);
				
				drawPanelHistory.addHistory(Components.cloneComponentList(components.getComponentList()));
				undoButton.setEnabled(true);
				redoButton.setEnabled(false);
				drawPanel.updateUI();
				projFrame.requestFocus();
			}
			else if(penType.equals(CanvasFreeStyle2D.class.getName())){
				mouseDragged(e);
			}
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	
	/* canvas Mouse listener */
	
	private class CanvasMouseListener implements MouseListener, MouseMotionListener{
		private static final int SCALING = 1;
		private static final int MOVING = 2;
		private static final int ROTATING = 3;
		
		private int editMode;
		private int scaleNum;
		
		private Point initialP;
		
		private Drawable focusShape;
		private Drawable originalShape;
		
		private Point2D originalMinP;
		private Point2D originalMaxP;
		
		private boolean dragFlag;			//for checking whether current state is saved
		
		
		public void focusOn(Drawable comp){
			focusShape = comp;
			originalShape = focusShape.getClone();
			originalMinP = new Point2D.Double(originalShape.getBounds2D().getMinX(), originalShape.getBounds2D().getMinY());
			originalMaxP = new Point2D.Double(originalShape.getBounds2D().getMaxX(), originalShape.getBounds2D().getMaxY());
			
			
			if(lineTransparencyButton.isSelected())
				transparencySlider.setValue(focusShape.getFeature().getOutlineTransparency());
			else if(fillTransparencyButton.isSelected())
				transparencySlider.setValue(focusShape.getFeature().getInnerTransparency());
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {			//search clicked polygon and clicked flag up
			// TODO Auto-generated method stub
			
			Point currP = e.getPoint();
			ArrayList<Drawable> componentList = components.getComponentList();
		    
			/* reset select state */
			ListIterator lI = componentList.listIterator();
			while(lI.hasNext()){
				Drawable component = (Drawable)lI.next();
				if(component.getIsSelected()){
					component.setIsSelected(false);
				}
			}
			
			editMode = 0;
			scaleNum = 0;
			focusShape = null;
			originalShape = null;
			originalMinP = null;
			originalMaxP= null;
			
			/* search for selected polygon */
			ListIterator reverseI = componentList.listIterator(componentList.size());
			while(reverseI.hasPrevious()){
				Drawable component = (Drawable) reverseI.previous();
				
				Point transP = new Point();
				try{
					AffineTransform at = AffineTransform.getRotateInstance(-component.getFeature().getRotTheta(), 
						component.getBounds2D().getCenterX(), component.getBounds2D().getCenterY());
					at.transform(currP, transP);
				}catch(NullPointerException ex){
					transP = currP;
				}
				
				if(component.contains(transP.x, transP.y)){
					component.setIsSelected(true);
					focusOn(component);
					break;
				}	
			}
			
			/* if nothing selected */
			if(focusShape == null)
				transparencySlider.setValue(0);
			
			drawPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			drawPanel.updateUI();
			projFrame.requestFocus();
		}

		
		@Override
		public void mousePressed(MouseEvent e) {
			dragFlag = false;
			
			Point pressP = e.getPoint();
			ArrayList<Drawable> componentList = components.getComponentList();
			
			//check if press scaling sign or rotating sign
			for(Drawable component : componentList){
				Point transP = new Point();
				try{
					AffineTransform at = AffineTransform.getRotateInstance(-component.getFeature().getRotTheta(), 
						component.getBounds2D().getCenterX(), component.getBounds2D().getCenterY());
					at.transform(pressP, transP);
				}catch(NullPointerException ex){
					transP = pressP;
				}
				
				if(component.getIsSelected()){
					//scaling sign
					int num = 0;
					for(Rectangle2D rect : component.getRegulateSign()){
						if(rect.contains(transP)){
							editMode = SCALING;
							scaleNum = num;
							drawPanel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
						}
						num++;
					}
					
					//rotating sign
					if(component.getRotateSign().contains(transP)){
						editMode = ROTATING;
//						drawPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
						
						Toolkit t = Toolkit.getDefaultToolkit();
						
						Image image = t.getImage(refreshIconURL);
						Point hotspot = new Point(16,16);
						
						Cursor cursor = t.createCustomCursor(image, hotspot, "rotate");
					
						drawPanel.setCursor(cursor);
					}
				}
			}
			
			//check if press shape
			if(editMode == 0){
				mouseClicked(e);
				if(focusShape != null){			//press shape	
					initialP = e.getPoint();
					editMode = MOVING;
					drawPanel.setCursor(new Cursor(Cursor.MOVE_CURSOR));
				}
			}
			
			/* reset select state */
			ListIterator lI = componentList.listIterator();
			while(lI.hasNext()){
				Drawable component = (Drawable)lI.next();
				if(component.getIsSelected()){
					component.setIsSelected(false);
				}
			}
			
			drawPanel.updateUI();
		}
				

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if(editMode == SCALING || editMode == MOVING || editMode == ROTATING){
				focusOn(focusShape);
				focusShape.setIsSelected(true);
				editMode = 0;
				scaleNum = 0;
				
				if(dragFlag){
					drawPanelHistory.addHistory(Components.cloneComponentList(components.getComponentList()));
					undoButton.setEnabled(true);
					redoButton.setEnabled(false);
				}
			}
		
			drawPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			drawPanel.updateUI();
			
			projFrame.requestFocus();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			Point currP = e.getPoint();
			AffineTransform rotate = new AffineTransform();
			
			if(editMode != 0){
				dragFlag = true;
				focusShape.setShape(originalShape.getShape());
				if(focusShape instanceof CanvasImage2D){
//					((CanvasImage2D) focusShape).setImage( ((CanvasImage2D)originalShape).getImage() );
					((CanvasImage2D) focusShape).setVerticalReverse(((CanvasImage2D)originalShape).getVerticalReverse());
					((CanvasImage2D) focusShape).setHorizontalReverse(((CanvasImage2D)originalShape).getHorizontalReverse());
				}
				
				rotate = AffineTransform.getRotateInstance(focusShape.getFeature().getRotTheta(), 
						(originalMaxP.getX() + originalMinP.getX())/2.0 , (originalMaxP.getY() + originalMinP.getY())/2.0);
			}
			
			
			
			if(editMode == MOVING){
				int translateX = currP.x - initialP.x;
				int translateY = currP.y - initialP.y;
				AffineTransform translate = AffineTransform.getTranslateInstance(translateX, translateY);
				
				focusShape.transform(translate);
			}
			
			else if(editMode == SCALING){
				if(focusShape instanceof CanvasLine2D){
					CanvasLine2D focusLine = (CanvasLine2D)focusShape;
					switch(scaleNum){
					case 0 : focusLine.setBothEnds(currP, ((Line2D)focusLine.getShape()).getP2());
					         break;
					case 1 : focusLine.setBothEnds(((Line2D)focusLine.getShape()).getP1(), currP);
							 break;
					}
				}
				else{
					Point2D anchorP = new Point2D.Double();
					Point2D newEndP1 = new Point2D.Double(), newEndP2 = new Point2D.Double();
					Point2D rREndP1 = new Point2D.Double(), rREndP2 = new Point2D.Double();
					double rotTheta, dist;
					boolean horizontalFlip = false, verticalFlip = false;
					AffineTransform reverseRotate;
					rotTheta = focusShape.getFeature().getRotTheta();
					
					
					Point2D transP1 = new Point2D.Double(), transP2 = new Point2D.Double(),
							transP3 = new Point2D.Double(), transP4 = new Point2D.Double();
					rotate.transform(new Point2D.Double(focusShape.getBounds2D().getMinX(), focusShape.getBounds2D().getMinY()),
							transP1);
					rotate.transform(new Point2D.Double(focusShape.getBounds2D().getMaxX(), focusShape.getBounds2D().getMinY()),
							transP2);
					rotate.transform(new Point2D.Double(focusShape.getBounds2D().getMinX(), focusShape.getBounds2D().getMaxY()),
							transP3);
					rotate.transform(new Point2D.Double(focusShape.getBounds2D().getMaxX(), focusShape.getBounds2D().getMaxY()),
							transP4);
					
					Line2D northOutLine = new Line2D.Double(transP1, transP2);
					Line2D southOutLine = new Line2D.Double(transP3, transP4);
					Line2D eastOutLine = new Line2D.Double(transP2, transP4);
					Line2D westOutLine = new Line2D.Double(transP1, transP3);
					
					
					switch(scaleNum){
						case 0 : newEndP1 = currP;
								 newEndP2 = transP4;
								 
								 if(eastOutLine.relativeCCW(currP) == 1)
									 horizontalFlip = true;
								 if(southOutLine.relativeCCW(currP) == -1)
									 verticalFlip = true;
								 break;
						case 7 : newEndP1 = transP1;
								 newEndP2 = currP;
								 
								 if(westOutLine.relativeCCW(currP) == -1)
									 horizontalFlip = true;
								 if(northOutLine.relativeCCW(currP) == 1)
									 verticalFlip = true;
								 break;
						case 2 : newEndP1 = currP;
								 newEndP2 = transP3;
								 
								 if(westOutLine.relativeCCW(currP) == -1)
									 horizontalFlip = true;
								 if(southOutLine.relativeCCW(currP) == -1)
									 verticalFlip = true;
								 break;
						case 5 : newEndP1 = transP2;
								 newEndP2 = currP;
								 
								 if(eastOutLine.relativeCCW(currP) == 1)
									 horizontalFlip = true;
								 if(northOutLine.relativeCCW(currP) == 1)
									 verticalFlip = true;
								 break;
						
								 
								 
						
						case 1 : dist = southOutLine.ptLineDist(currP);
								 
							     if(southOutLine.relativeCCW(currP) == -1){
							    	 dist = -dist;
							    	 verticalFlip = true;
							     }
								 newEndP1 = new Point2D.Double(transP3.getX() + dist * Math.sin(rotTheta),
							     		 transP3.getY() - dist * Math.cos(rotTheta));
								 newEndP2 = transP4;					 
								 break;
						case 6 : dist = northOutLine.ptLineDist(currP);
							     
							     if(northOutLine.relativeCCW(currP) == 1){
							    	 dist = -dist;
							    	 verticalFlip = true;
							     }
							   
								 newEndP1 = transP1;
								 newEndP2 = new Point2D.Double(transP2.getX() - dist * Math.sin(rotTheta),
							    		 transP2.getY() + dist * Math.cos(rotTheta));			 
					         	 break;
						case 3 : dist = eastOutLine.ptLineDist(currP);
							     
							     if(eastOutLine.relativeCCW(currP) == 1){
							    	 dist = -dist;
							    	 horizontalFlip = true;
							     }
							     
								 newEndP1 = new Point2D.Double(transP2.getX() - dist * Math.cos(rotTheta),
							    		 transP2.getY() - dist * Math.sin(rotTheta));
								 newEndP2 = transP4;			     
						     	 break;
						case 4 : dist = westOutLine.ptLineDist(currP);
					     		 
					     		 if(westOutLine.relativeCCW(currP) == -1){
					     			 dist = -dist;
					     			 horizontalFlip = true;
					     		 }
					     		 newEndP1 = transP1;
					     		 newEndP2 = new Point2D.Double(transP3.getX() + dist * Math.cos(rotTheta),
					     				 transP3.getY() + dist * Math.sin(rotTheta));
								 break;
					}
					anchorP.setLocation((newEndP1.getX() + newEndP2.getX())/2.0, (newEndP1.getY() + newEndP2.getY())/2.0);	
					reverseRotate = AffineTransform.getRotateInstance(-focusShape.getFeature().getRotTheta()
								 , anchorP.getX(), anchorP.getY());
					rREndP1 = reverseRotate.transform(newEndP1, null);
					rREndP2 = reverseRotate.transform(newEndP2, null);
					
					
					focusShape.editShape(rREndP1, rREndP2, horizontalFlip, verticalFlip);					
				}
			}
			else if(editMode == ROTATING){
				double rotTheta = Math.atan2(currP.x - focusShape.getBounds2D().getCenterX(), focusShape.getBounds2D().getCenterY() - currP.y);
				focusShape.getFeature().setRotTheta(rotTheta);
			}
			
			
			drawPanel.updateUI();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
			Point currP = e.getPoint();
			
			boolean defaultCursorFlag = true;
			
			
			//if mouse cursor is on the regulateSign or rotateSign, change the cursor icon. 
			
			if(focusShape != null){
				Point transP = new Point();
				try{
					AffineTransform at = AffineTransform.getRotateInstance(-focusShape.getFeature().getRotTheta(), 
						focusShape.getBounds2D().getCenterX(), focusShape.getBounds2D().getCenterY());
					at.transform(currP, transP);
				}catch(NullPointerException ex){
					transP = currP;
				}
				
				if(focusShape instanceof CanvasLine2D){
					for(int i = 0; i < 2 ; i++){
						if(focusShape.getRegulateSign()[i].contains(transP)){				 
							defaultCursorFlag = false;
							double gradientSign = (focusShape.getBounds2D().getMinX() - focusShape.getBounds2D().getMaxX()) 
									* (focusShape.getBounds2D().getMinY() - focusShape.getBounds2D().getMaxY());
							if(gradientSign >= 0)
								drawPanel.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
							else
								drawPanel.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
						}
					}
				}
				else{
					//check regulate sign
					for(int i = 0; i < 8 ; i++){
						if(focusShape.getRegulateSign()[i].contains(transP)){
							defaultCursorFlag = false;
							switch(i){
								case 0 :
								case 7 : drawPanel.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
								         break;
								case 2 :
								case 5 : drawPanel.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
										 break;
								case 1 :
								case 6 : drawPanel.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
								         break;
								case 3 :
								case 4 : drawPanel.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
								         break;
							}
						}
					}
						
					//check rotate sign
					if(focusShape.getRotateSign().contains(transP)){
						defaultCursorFlag = false;
						drawPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
					}
				}
			}
			
			
			//if mouse cursor is on the shape, change the cursor icon.
			if(defaultCursorFlag){
				ArrayList<Drawable> componentList = components.getComponentList();
				ListIterator reverseI = componentList.listIterator(componentList.size());
				while(reverseI.hasPrevious()){	
					Drawable component = (Drawable) reverseI.previous();
					Point transP = new Point();
					try{
						AffineTransform at = AffineTransform.getRotateInstance(-component.getFeature().getRotTheta(), 
							component.getBounds2D().getCenterX(), component.getBounds2D().getCenterY());
						at.transform(currP, transP);
					}catch(NullPointerException ex){
						transP = currP;
					}
					
					
					if(component.contains(transP.x, transP.y)){	
						defaultCursorFlag = false;
						drawPanel.setCursor(new Cursor(Cursor.MOVE_CURSOR));
						break;
					}
				}
			}
			
			if(defaultCursorFlag)					
				drawPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			
			drawPanel.updateUI();	
		}
	}
	
	
	/* canvas key listener */
	
	private class CanvasKeyListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			int keyCode = e.getKeyCode();
//			if(KeyEvent.getKeyText(keyCode) == "Delete"){
			if(keyCode == 8) {
				ArrayList<Drawable> componentList = components.getComponentList();
				for(Drawable component : componentList){
					if(component.getIsSelected()){
						componentList.remove(component);
						drawPanelHistory.addHistory(Components.cloneComponentList(componentList));
						undoButton.setEnabled(true);
						redoButton.setEnabled(false);
						break;
					}
				}
				drawPanel.updateUI();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
