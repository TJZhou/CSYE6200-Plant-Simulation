package edu.neu.csye6200.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;


/**
 * A Test application for the Wolfram Biological Growth application
 * @author MMUNSON
 */
public class PlantApp extends BGApp {

    /**
     * Sample app constructor
     */
    public PlantApp() {
    	try {
			log.info("APP start");
			Handler handler = new FileHandler(logBase);
			Logger.getLogger("").addHandler(handler);
			
		} catch (SecurityException e) {
			log.warning("SecurityException occurs in constructor PlantSimUI");
			e.printStackTrace();
		} catch (IOException e) {
			log.warning("IOException occurs in constructor PlantSimUI");
			e.printStackTrace();
		}
    	
    	frame.setSize(1300, 800); // initial Frame size
		frame.setTitle("WolfApp");	
		menuMgr.createDefaultActions(); // Set up default menu items	
    	showUI(); // Cause the Swing Dispatch thread to display the JFrame
    }
   
    /**
     * Create a main panel that will hold the bulk of our application display
     */
	@Override
	public JPanel getMainPanel() {
	
		mainPanel = new JPanel();
    	mainPanel.setLayout(new BorderLayout());
    	mainPanel.add(BorderLayout.WEST, getMenuPanel());
    	
    	bgPanel = new BGCanvas();
    	//bgPanel.setBackground(Color.GRAY);
    	mainPanel.add(BorderLayout.CENTER, bgPanel);
    	
    	bgs.addObserver(bgPanel);	///add observer
    	
    	return mainPanel;
	}
    
	/**
	 * Create a top panel that will hold control buttons
	 * @return
	 */
    public JPanel getMenuPanel() {
    	menuPanel = new JPanel();
		menuPanel.setLayout(null);
		menuPanel.setPreferredSize(new Dimension(300,800)); //the size of menu panel is 300*800
		
		startBtn = new JButton("Start"); // create start button instances
		startBtn.addActionListener(e->{
			
			bgs.genrationSet(rule);		//generate stem according to rules
			//bgPanel.repaint();
			System.out.println(BGSetCount);
	
			BGApp.BGSetCount++;
			
			//bgPanel = new BGCanvas();
			//BGSetCount++;
				//pause =false;		//pause equals false; it's able to draw line
				//startBtnAction();
		});		
		stopBtn = new JButton("Stop"); // create stop button instances
		stopBtn.addActionListener(e->{
			//pause = true;
		});
		resetBtn = new JButton("Reset"); // create reset button instances
		resetBtn.addActionListener(e->{
			bgPanel.repaint();
		});
		
		ruleLabel = new JLabel("rule");		//ruleBox and action listener
		ruleBox = new JComboBox<String>(rules);
		ruleBox.setModel(new DefaultComboBoxModel<String>(rules));
		ruleBox.addActionListener(e->{
			switch (ruleBox.getSelectedItem().toString()) {
			case "rule1":
				System.out.print("rule1");
				rule = "rule1";break;		
			case "rule2":
				System.out.print("rule2");
				rule = "rule2"; generation = 8;break;
			case "rule3":
				System.out.print("rule3");
				rule = "rule3"; generation = 7;break;
			}
		});
		
		colorLabel = new JLabel("color");	//colorBox and action listener
		colorBox = new JComboBox();
		colorBox.setModel(new DefaultComboBoxModel(colors));	
		colorBox.addActionListener(e->{
			switch (colorBox.getSelectedIndex()) {
			case 0:
				System.out.print("black");
				color = Color.white; break;
			case 1:
				System.out.print("white");
				color = Color.black; break;
			case 2:
				System.out.print("red");
				color = Color.red; break;
			case 3:
				System.out.print("blue");
				color = Color.blue; break;
			case 4:
				System.out.print("green");
				color = Color.green; break;
			case 5:
				System.out.print("yellow");
				color = Color.yellow; break;
			case 6:
				System.out.print("cyan");
				color = Color.cyan; break;		
			}	
		});

		lengthLabel = new JLabel("length"); //length slide control and action listener
		lengthSlider = new JSlider(5,31);	
		lengthSlider.addChangeListener(e->{
			sideLengthGrow = 1.0 + 1.0 / lengthSlider.getValue();		
		});
		
		radianLabel = new JLabel("radian"); //radian slide control and action listener
		radianSlider = new JSlider(20, 150);
		radianSlider.addChangeListener(e->{
			sideRotateRadian = Math.PI/(17 - ((double)radianSlider.getValue())/10);
		});
		
		info = new JLabel("Only available at rule 2/3");
		info.setBounds(25, 350, 300, 40);
		menuPanel.add(info);
		//mid length and radian slide control and action listener; only available at rule 4
		midLengthLabel = new JLabel("midLength");
		midLengthSlider = new JSlider(5,31);	
		midLengthSlider.addChangeListener(e->{
			midLengthGrow = 1.0 + 1.0 / midLengthSlider.getValue();		
		});
		
		info = new JLabel("Only available at rule 3");
		info.setBounds(25, 425, 300, 40);
		menuPanel.add(info);
		midRadianLabel = new JLabel("midRadian");
		midRadianSlider = new JSlider(20, 150);
		midRadianSlider.addChangeListener(e->{
			midRotateRadian = Math.PI/ (17 - ((double)midRadianSlider.getValue())/10);
		});
		
		info = new JLabel("<html>Author: Tianju Zhou<br/><br/> NUID: 001420546</html>");
		
		//set location and size of every component
		ruleLabel.setBounds(50, 50, 60, 40);
		ruleBox.setBounds(100, 50, 150, 40);
		colorLabel.setBounds(50, 125, 60, 40);
		colorBox.setBounds(100, 125, 150, 40);
		lengthLabel.setBounds(50, 175, 60, 40);
		lengthSlider.setBounds(100, 175, 150, 40);
		radianLabel.setBounds(50, 250, 60, 40);
		radianSlider.setBounds(100, 250, 150, 40);
		midLengthLabel.setBounds(25, 325, 200, 40);
		midLengthSlider.setBounds(100, 325, 150, 40);
		midRadianLabel.setBounds(25, 400, 200, 40);
		midRadianSlider.setBounds(100, 400, 150, 40);
		startBtn.setBounds(75, 475, 150, 40);
		stopBtn.setBounds(75, 550, 150, 40);
		resetBtn.setBounds(75, 625, 150,40);
		info.setBounds(20, 675, 300, 100);
		
		//add every component to menuPanel 
		menuPanel.add(startBtn);
		menuPanel.add(stopBtn);
		menuPanel.add(resetBtn);
		menuPanel.add(ruleLabel);
		menuPanel.add(ruleBox);		
		menuPanel.add(colorLabel);
		menuPanel.add(colorBox);
		menuPanel.add(lengthLabel);
		menuPanel.add(lengthSlider);
		menuPanel.add(radianLabel);
		menuPanel.add(radianSlider);
		menuPanel.add(midLengthLabel);
		menuPanel.add(midLengthSlider);
		menuPanel.add(midRadianLabel);
		menuPanel.add(midRadianSlider);
		menuPanel.add(info);
		
		menuPanel.setBackground(Color.WHITE);
		return menuPanel;
    }
    
	
	@Override
	public void actionPerformed(ActionEvent ae) {
	/*	log.info("We received an ActionEvent " + ae);
		if (ae.getSource() == startBtn)
			System.out.println("Start pressed");
		else if (ae.getSource() == stopBtn)
			System.out.println("Stop pressed");*/
	}

	@Override
	public void windowOpened(WindowEvent e) {
		log.info("Window opened");
	}

	@Override
	public void windowClosing(WindowEvent e) {	
		log.info("Window closing");
	}



	@Override
	public void windowClosed(WindowEvent e) {
		log.info("Window closed");
	}



	@Override
	public void windowIconified(WindowEvent e) {
		log.info("Window iconified");
	}



	@Override
	public void windowDeiconified(WindowEvent e) {	
		log.info("Window deiconified");
	}



	@Override
	public void windowActivated(WindowEvent e) {
		log.info("Window activated");
	}



	@Override
	public void windowDeactivated(WindowEvent e) {	
		log.info("Window deactivated");
	}
	
	/**
	 * Sample Wolf application starting point
	 * @param args
	 */
	public static void main(String[] args) {
		PlantApp wapp = new PlantApp();
		
		log.info("WolfApp started");
	}


}
