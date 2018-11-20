package edu.neu.csye6200.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import edu.neu.csye6200.bg.BGGenerationSet;
import edu.neu.csye6200.bg.BGStem;

public class plantUI extends BGApp{

private static Logger log = Logger.getLogger(PlantSimUI.class.getName());	
	
	public static int generation = 9;
	public static double sideLengthGrow = 1.05555;
	public static double midLengthGrow = 1.05555;	//default length
	public static double sideRotateRadian = Math.PI /9;
	public static double midRotateRadian = Math.PI/9;	//default radian
	public static String rule = "rule1";	//default rule
	public static Color color = Color.white; 	//default color
	private static int BGSetCount = 0;
	private String rules[] = {"rule1", "rule2", "rule3"};	//rule set
	private String colors[] = {"white", "black", "red", "blue", "green", "yellow","cyan"};	//color set
	//private static boolean pause = false;
	private JPanel menuPanel = null;
	private JPanel mainPanel = null;
	private JButton startBtn = null;
	private JButton stopBtn = null;
	private JButton resetBtn = null;
	private JLabel ruleLabel = null;
	private JLabel colorLabel = null;
	private JLabel lengthLabel = null;
	private JLabel radianLabel = null;
	private JLabel midLengthLabel = null;
	private JLabel midRadianLabel = null;
	private JLabel info = null;
	private JComboBox ruleBox = null;
	private JComboBox colorBox = null;
	private JSlider lengthSlider = null;
	private JSlider midLengthSlider = null; //only available at rule3
	private JSlider radianSlider = null;
	private JSlider midRadianSlider = null;//only available at rule3
	private String logBase = "src/edu/neu/csye6200/bg/server.log";	//log file routine	
	private BGGenerationSet bgs  = BGGenerationSet.generationSet();//singleton pattern
	
	//constructor
	public plantUI(){
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
	}

	//initialize the GUI
	public void run(){
			frame.setTitle("PlantSimulation");
			frame.setSize(1500, 1000);	//set the size to something reasonable
			frame.setResizable(false);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//if we press the close button, exit		
			frame.setLayout(new BorderLayout());
			frame.getContentPane().add(getMenuPanel(), BorderLayout.CENTER);	
			frame.getContentPane().add(getMainPanel(), BorderLayout.EAST);
			frame.setVisible(true);
	}
	
	//JPnael at the left: menu panel
	public JPanel getMenuPanel(){
		menuPanel = new JPanel();
		menuPanel.setLayout(null);
		menuPanel.setPreferredSize(new Dimension(300,1000)); //the size of menu panel is 300*800
		
		startBtn = new JButton("Start"); // create start button instances
		startBtn.addActionListener(e->{
				//pause =false;		//pause equals false; it's able to draw line
				startBtnAction();
		});		
		stopBtn = new JButton("Stop"); // create stop button instances
		stopBtn.addActionListener(e->{
			//pause = true;
		});
		resetBtn = new JButton("Reset"); // create reset button instances
		resetBtn.addActionListener(e->{
			mainPanel.repaint();
		});
		
		ruleLabel = new JLabel("rule");		//ruleBox and action listener
		ruleBox = new JComboBox();
		ruleBox.setModel(new DefaultComboBoxModel(rules));
		ruleBox.addActionListener(e->{
			switch (ruleBox.getSelectedIndex()) {
			case 0:
				System.out.print("rule1");
				rule = "rule1";break;		
			case 1:
				System.out.print("rule2");
				rule = "rule2"; generation = 8;break;
			case 2:
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
		info.setBounds(25, 340, 300, 40);
		menuPanel.add(info);
		//mid length and radian slide control and action listener; only available at rule 4
		midLengthLabel = new JLabel("midLength");
		midLengthSlider = new JSlider(5,31);	
		midLengthSlider.addChangeListener(e->{
			midLengthGrow = 1.0 + 1.0 / midLengthSlider.getValue();		
		});
		
		info = new JLabel("Only available at rule 3");
		info.setBounds(25, 435, 300, 40);
		menuPanel.add(info);
		midRadianLabel = new JLabel("midRadian");
		midRadianSlider = new JSlider(20, 150);
		midRadianSlider.addChangeListener(e->{
			midRotateRadian = Math.PI/ (17 - ((double)midRadianSlider.getValue())/10);
		});
		
		info = new JLabel("<html>Author: Tianju Zhou<br/><br/> NUID: 001420546</html>");
		
		//set location and size of every component
		ruleLabel.setBounds(50, 100, 60, 40);
		ruleBox.setBounds(100, 100, 150, 40);
		colorLabel.setBounds(50, 175, 60, 40);
		colorBox.setBounds(100, 175, 150, 40);
		lengthLabel.setBounds(50, 250, 60, 40);
		lengthSlider.setBounds(100, 250, 150, 40);
		radianLabel.setBounds(50, 300, 60, 40);
		radianSlider.setBounds(100, 300, 150, 40);
		midLengthLabel.setBounds(25, 380, 200, 40);
		midLengthSlider.setBounds(100, 380, 150, 40);
		midRadianLabel.setBounds(25, 475, 200, 40);
		midRadianSlider.setBounds(100, 475, 150, 40);
		startBtn.setBounds(75, 550, 150, 40);
		stopBtn.setBounds(75, 625, 150, 40);
		resetBtn.setBounds(75, 700, 150,40);
		info.setBounds(20, 850, 300, 100);
		
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
	
	//jpanel at the right: main panel
	public JPanel getMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.GRAY);
		mainPanel.setPreferredSize(new Dimension(1200,1000));
		return mainPanel;
	}

	//press startBtn and do some action
	private void startBtnAction() {	
		//super.paint(getGraphics());
		System.out.println("you press" + startBtn.getText());
		bgs.genrationSet(rule);		//generate stem according to rules
		Graphics2D g2 = (Graphics2D) mainPanel.getGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(color);
		drawLine(g2);
		BGSetCount++;
	}

	//draw stems of the plant at the mainPanel
	private void drawLine(Graphics2D g2) {
		Line2D line;
		for (int i = 0; i < bgs.getBgSet().get(BGSetCount).getBgs().size(); i++) {
			BGStem st = bgs.getBgSet().get(BGSetCount).getBgs().get(i); // get the current BGStem
			// stem location
			line = new Line2D.Double(st.getLocationX() + 600, -st.getLocationY() + 1000,
					(st.getLocationX() + st.getLength() * Math.cos(st.getRadians()) + 600),
					-(st.getLocationY() + st.getLength() * Math.sin(st.getRadians())) + 1000);
			g2.draw(line);
			
		
	/*		 try { 
				 Thread.sleep(30); 
				 } catch (InterruptedException e) {
		log.warning("An error occurs at start button"); e.printStackTrace(); } */
				 }
					
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
