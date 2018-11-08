package edu.neu.csye6200.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.neu.csye6200.bg.BGStem;
import edu.neu.csye6200.bg.Plant;
import edu.neu.csye6200.bg.PlantTest;

/**
 * @author Tianju Zhou
 *
 */
public class MyAppUI extends JFrame{

	private Logger log = Logger.getLogger(MyAppUI.class.getName());	
	private JFrame jFrame = null;
	private JPanel jPanel = null;
	private JButton startBtn = null;
	private JButton stopBtn = null;
	
	//constructor
	public MyAppUI(){
		log.info("App Started");
		initGUI();
	}

	//initialize the GUI
	private void initGUI(){
		//jFrame = new JFrame();
		setTitle("MyAppUI");
		setSize(1000, 600);	//set the size to something reasonable
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//if we press the close button, exit		
		setLayout(new BorderLayout());
		getContentPane().add(getMainPanel(), BorderLayout.CENTER);	
		setVisible(true);
	}
	
	//create a panel that we'll draw into
	public JPanel getMainPanel(){
		jPanel = new JPanel();
		jPanel.setLayout(new FlowLayout());	//flow from left to right
		startBtn = new JButton("Start");	//create button instances
		stopBtn = new JButton("Stop");
		jPanel.add(startBtn);	//add them to the panel
		jPanel.add(stopBtn);
		
		jPanel.setBackground(Color.WHITE);
		return jPanel;
	}

	public void paint(Graphics g) {
		super.paint(g);
		Line2D line;
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.BLACK);
		for(int i =0 ; i< 4000; i++) {
			BGStem st = BGStem.stemMap.get(i);
			line = new Line2D.Double(st.getLocationX()+500, -st.getLocationY()+500, 
				(st.getLocationX()+st.getLength()*Math.cos(st.getRadians())+500), 
				-(st.getLocationY()+st.getLength()*Math.sin(st.getRadians()))+500);
				g2.draw(line);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	PlantTest pt  =new PlantTest();
	pt.run();
	MyAppUI myApp = new MyAppUI();
	
	System.out.println("MyAppUI is exiting");

	}
}
