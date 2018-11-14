package edu.neu.csye6200.bg;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/**
 * @author Tianju Zhou NUID 001420546
 */
public class PlantSimUI extends JFrame implements Runnable, ActionListener{
	private static Logger log = Logger.getLogger(PlantSimUI.class.getName());	
	
	public static int generation = 5;
	public static double sideLengthGrow = 1.05;
	public static double midLengthGrow = 1.05;
	public static double sideRotateRadian = Math.PI /12;
	public static double midRotateRadian = Math.PI/6;
	public static String rule = "rule1";
	public static Color color = Color.black; 
	private static int BGSetCount = 0;
	private String rules[] = {"rule1", "rule2", "rule3"};
	private String colors[] = {"black", "white", "red", "blue", "green", "yellow","cyan"};
	private static boolean pause = false;
	private JPanel menuPanel = null;
	private JPanel mainPanel = null;
	private JButton startBtn = null;
	private JButton stopBtn = null;
	private JButton resetBtn = null;
	private JLabel ruleLabel = null;
	private JLabel colorLabel = null;
	private JLabel lengthLabel = null;
	private JLabel radianLabel = null;
	private JLabel info = null;
	private JComboBox ruleBox = null;
	private JComboBox colorBox = null;
	private JSlider lengthSlider = null;
	private JSlider radianSlider = null;
	private String logBase = "src/edu/neu/csye6200/bg/server.log";	//log file routine	
	private BGGenerationSet bgs  = BGGenerationSet.generationSet();//singleton pattern
	
	//constructor
	public PlantSimUI(){
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
			setTitle("PlantSimulation");
			setSize(1500, 1000);	//set the size to something reasonable
			setResizable(false);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//if we press the close button, exit		
			setLayout(new BorderLayout());
			getContentPane().add(getMenuPanel(), BorderLayout.CENTER);	
			getContentPane().add(getMainPanel(), BorderLayout.EAST);
			setVisible(true);
	}
	
	//JPnael at the left: menu panel
	public JPanel getMenuPanel(){
		menuPanel = new JPanel();
		menuPanel.setLayout(null);
		menuPanel.setPreferredSize(new Dimension(300,1000)); //the size of menu panel is 300*800
		
		startBtn = new JButton("Start"); // create start button instances
		startBtn.addActionListener(e->{
				pause =false;		//pause equals false; it's able to draw line
				startBtnAction();
		});		
		stopBtn = new JButton("Stop"); // create stop button instances
		stopBtn.addActionListener(e->{
			pause = true;
		});
		resetBtn = new JButton("Reset"); // create reset button instances
		resetBtn.addActionListener(e->{
			mainPanel.repaint();
		});
		
		ruleLabel = new JLabel("rule");		//ruleBox
		ruleBox = new JComboBox();
		ruleBox.setModel(new DefaultComboBoxModel(rules));
		ruleBox.addActionListener(this);
		
		colorLabel = new JLabel("color");	//colorBox
		colorBox = new JComboBox();
		colorBox.setModel(new DefaultComboBoxModel(colors));	
		colorBox.addActionListener(this);
		
		lengthLabel = new JLabel("length"); //length and radian slide control
		lengthSlider = new JSlider(1, 2);		
		radianLabel = new JLabel("radian");
		radianSlider = new JSlider(1, 2);	
		
		info = new JLabel("<html>Author: Tianju Zhou<br/><br/> NUID: 001420546</html>");
		
		//set location and size of every component
		ruleLabel.setBounds(50, 100, 60, 40);
		ruleBox.setBounds(100, 100, 100, 40);
		colorLabel.setBounds(50, 175, 60, 40);
		colorBox.setBounds(100, 175, 100, 40);
		lengthLabel.setBounds(50, 250, 60, 40);
		lengthSlider.setBounds(100, 250, 100, 40);
		radianLabel.setBounds(50, 325, 60, 40);
		radianSlider.setBounds(100, 325, 100, 40);
		startBtn.setBounds(50, 400, 150, 40);
		stopBtn.setBounds(50, 475, 150, 40);
		resetBtn.setBounds(50, 550, 150,40);
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
		super.paint(getGraphics());
		System.out.println("you press" + startBtn.getText());
		bgs.genrationSet(rule);		//generate stem according to rules
		Graphics2D g2 = (Graphics2D) mainPanel.getGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(color);
		drawLine(g2);
		BGSetCount++;
	}

	//draw stems of the plant
	private void drawLine(Graphics2D g2) {
		Line2D line;
		for(int i = 0; i < bgs.getBgSet().get(BGSetCount).getBgs().size(); i++) {
			while(pause == true) {
				try {
					this.wait();
				} catch (InterruptedException e1) {
					log.warning("An error occurs at start button");
					e1.printStackTrace();
				}
			}	
			//get the current BGStem
			BGStem st = bgs.getBgSet().get(BGSetCount).getBgs().get(i);
			// stem location
			line = new Line2D.Double(st.getLocationX() + 600, -st.getLocationY() + 1000,
					(st.getLocationX() + st.getLength() * Math.cos(st.getRadians()) + 600),
					-(st.getLocationY() + st.getLength() * Math.sin(st.getRadians())) + 1000);
			g2.draw(line);
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				log.warning("An error occurs at start button");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//action appears at the rule box
		if(e.getSource() == ruleBox) {
			switch (ruleBox.getSelectedIndex()) {
			case 0:
				System.out.print("rule1");
				rule = "rule1"; break;
			case 1:
				System.out.print("rule2");
				rule = "rule2"; break;
			case 2:
				System.out.print("rule3");
				rule = "rule3"; break;
			}
		}
		//action appears at the colorBox
		if(e.getSource() == colorBox) {
			switch (colorBox.getSelectedIndex()) {
			case 0:
				System.out.print("black");
				color = Color.black; break;
			case 1:
				System.out.print("white");
				color = Color.white; break;
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
		}
	}	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {

	Thread thrdUI = new Thread(new PlantSimUI());
	thrdUI.run();
	thrdUI.join();
	log.info("My App exits");
	}
}
