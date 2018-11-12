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

	public static int generation = 7;
	public static double sideLengthGrow = 1.02;
	public static double midLengthGrow = 1.02;
	public static double rotateRadian = Math.PI /12;
	public static String rule = "rule1";
	public static Color color = Color.black; 
	private String rules[] = {"rule1", "rule2", "rule3"};
	private String colors[] = {"black", "white", "red", "blue", "green", "yellow","cyan"};
	private static Logger log = Logger.getLogger(PlantSimUI.class.getName());	
	private JPanel menuPanel = null;
	private JPanel mainPanel = null;
	private JButton startBtn = null;
	private JButton stopBtn = null;
	private JLabel ruleLabel = null;
	private JLabel colorLabel = null;
	private JLabel lengthLabel = null;
	private JLabel radianLabel = null;
	private JComboBox ruleBox = null;
	private JComboBox colorBox = null;
	private JSlider lengthSlider = null;
	private JSlider radianSlider = null;
	
	//log file routine
	private String logBase = "src/edu/neu/csye6200/bg/server.log";
	private BGGenerationSet bgs  = new BGGenerationSet();
	
	//constructor
	public PlantSimUI(){
		try {
			log.info("APP start");
			Handler handler = new FileHandler(logBase);
			Logger.getLogger("").addHandler(handler);
			
		} catch (SecurityException e) {
			log.warning("SecurityException occurs in constructor PlantTest");
			e.printStackTrace();
		} catch (IOException e) {
			log.warning("IOException occurs in constructor PlantTest");
			e.printStackTrace();
		}
	}

	//initialize the GUI
	public void run(){
			setTitle("PlantSimulation");
			setSize(1200, 800);	//set the size to something reasonable
			setResizable(false);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//if we press the close button, exit		
			setLayout(new BorderLayout());
			getContentPane().add(getMenuPanel(), BorderLayout.CENTER);	
			getContentPane().add(getMainPanel(), BorderLayout.EAST);
			setVisible(true);
	}
	
	//create a panel that we'll draw into
	public JPanel getMenuPanel(){
		menuPanel = new JPanel();
		menuPanel.setLayout(null);
		menuPanel.setPreferredSize(new Dimension(300,800));
		startBtn = new JButton("Start");	//create button instances
		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("你按下了" + startBtn.getText());
				bgs.genrationSet(rule);
				Line2D line;
				Graphics2D g2 = (Graphics2D) mainPanel.getGraphics();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(color);
				//traverse and paint all stems
						for (int i = 0; i < ((Math.pow(3, generation) - 1) * 3 / 2) + 1; i++) {
							
							BGStem st = bgs.getBgSet().get(0).getBgs().get(i);
							//stem location
							line = new Line2D.Double(st.getLocationX() + 450, -st.getLocationY() + 800,
									(st.getLocationX() + st.getLength() * Math.cos(st.getRadians()) + 450),
									-(st.getLocationY() + st.getLength() * Math.sin(st.getRadians())) + 800);

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
							if(i == 12)
								g2.setStroke(new BasicStroke(3f));
							g2.draw(line);
						}
			}			
		});
		
		stopBtn = new JButton("Stop");
		ruleLabel = new JLabel("rule");	
		ruleBox = new JComboBox();
		ruleBox.setModel(new DefaultComboBoxModel(rules));
		ruleBox.addActionListener(this);
		colorLabel = new JLabel("color");
		colorBox = new JComboBox();
		colorBox.setModel(new DefaultComboBoxModel(colors));	
		colorBox.addActionListener(this);
		
		lengthLabel = new JLabel("length");
		lengthSlider = new JSlider(1, 2);		
		radianLabel = new JLabel("radian");
		radianSlider = new JSlider(1, 2);	
		
		
		ruleLabel.setBounds(50, 100, 60, 40);
		ruleBox.setBounds(100, 100, 100, 40);
		colorLabel.setBounds(50, 175, 60, 40);
		colorBox.setBounds(100, 175, 100, 40);
		lengthLabel.setBounds(50, 250, 60, 40);
		lengthSlider.setBounds(100, 250, 100, 40);
		radianLabel.setBounds(50, 325, 60, 40);
		radianSlider.setBounds(100, 325, 100, 40);
		startBtn.setBounds(50, 400, 60, 30);
		stopBtn.setBounds(125, 400, 60, 30);
		
		menuPanel.add(startBtn);	//add them to the panel
		menuPanel.add(stopBtn);
		menuPanel.add(ruleLabel);
		menuPanel.add(ruleBox);		
		menuPanel.add(colorLabel);
		menuPanel.add(colorBox);
		menuPanel.add(lengthLabel);
		menuPanel.add(lengthSlider);
		menuPanel.add(radianLabel);
		menuPanel.add(radianSlider);

		
		menuPanel.setBackground(Color.WHITE);
		return menuPanel;
	}
	
	public JPanel getMainPanel() {
		mainPanel = new JPanel();
		mainPanel.paint(getGraphics());
		mainPanel.setBackground(Color.GRAY);
		mainPanel.setPreferredSize(new Dimension(900,300));
		

		return mainPanel;
	}

	/*public void paint(Graphics g) {
		
		super.paint(g);
		Line2D line;
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.CYAN);
		
		//traverse and paint all stems
		for (int i = 0; i < ((Math.pow(3, generation) - 1) * 3 / 2) + 1; i++) {
			
			BGStem st = bgs.getBgSet().get(0).getBgs().get(i);
			//stem location
			line = new Line2D.Double(st.getLocationX() + 600, -st.getLocationY() + 800,
					(st.getLocationX() + st.getLength() * Math.cos(st.getRadians()) + 600),
					-(st.getLocationY() + st.getLength() * Math.sin(st.getRadians())) + 800);

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
			if(i == 12)
				g2.setStroke(new BasicStroke(3f));
			g2.draw(line);
		}
	}
*/	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
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
	System.out.println("MyAppUI is exiting");
	}
}
