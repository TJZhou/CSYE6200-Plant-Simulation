package edu.neu.csye6200.bg;

import java.awt.BasicStroke;
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

/**
 * @author Tianju Zhou
 *
 */
public class MyAppUI extends JFrame implements Runnable{
	public static int generation = 7;
	public static String rule = "rule2";
	public static double sideLengthGrow = 1.02;
	public static double midLengthGrow = 1.02;
	public static double rotateRadian = Math.PI / 12;
	
	private Logger log = Logger.getLogger(MyAppUI.class.getName());	
	private JPanel jPanel = null;
	private JButton startBtn = null;
	private JButton stopBtn = null;
	
	//constructor
	public MyAppUI(){
		log.info("App Started");
	}

	//initialize the GUI
	public void run(){
		//jFrame = new JFrame();
		setTitle("MyAppUI");
		setSize(1200, 800);	//set the size to something reasonable
		setResizable(false);
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
		g2.setColor(Color.GRAY);
		
		//traverse all stems
		for (int i = 0; i < ((Math.pow(3, generation) - 1) * 3 / 2) + 1; i++) {
			BGStem st = BGStem.stemMap.get(i);
			//stem location
			line = new Line2D.Double(st.getLocationX() + 600, -st.getLocationY() + 800,
					(st.getLocationX() + st.getLength() * Math.cos(st.getRadians()) + 600),
					-(st.getLocationY() + st.getLength() * Math.sin(st.getRadians())) + 800);

			// different generations have different line thickness
//			if(i != 0)
//				g2.setStroke(new BasicStroke( (int)(9 / Math.pow(3, ((int) Math.log((i-1) * 2 / 3 + 1) / Math.log(3))))));
			if (i == 0) {
				g2.setStroke(new BasicStroke(14.0f));
			}
			else if(i>=1&& i<=3)
				g2.setStroke(new BasicStroke(9.0f));
			else if(i>=4&&i<=12)
				g2.setStroke(new BasicStroke(5.0f));
			else if(i>=13&&i<=39)
				g2.setStroke(new BasicStroke(3.0f));
			else if(i>=40&&i<=66)
				g2.setStroke(new BasicStroke(1.0f));
			else
				g2.setStroke(new BasicStroke(0.3f));
			/*if(i == 12)
				g2.setStroke(new BasicStroke(3f));*/
			g2.draw(line);
		}
	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
	Thread thrdPT = new Thread(new PlantTest());
	thrdPT.run();
	thrdPT.join();
	
	Thread thrdUI = new Thread(new MyAppUI());
	thrdUI.run();
	thrdUI.join();
	
	System.out.println("MyAppUI is exiting");

	}
}
