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
 * Biological Plant Growth Simulation application
 * 
 * @author Tianju Zhou NUID 001420546
 */
public class PlantApp extends BGApp {

	/**
	 * constructor
	 * add a global FileHandler to save log info
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
		frame.setTitle("PlantApp");
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
		mainPanel.add(BorderLayout.CENTER, bgPanel);
		bgs.addObserver(bgPanel); /// add observer
		return mainPanel;
	}

	/**
	 * Create a top panel that will hold control buttons, sliders and comboBoxs
	 * 
	 * @return
	 */
	public JPanel getMenuPanel() {
		menuPanel = new JPanel();
		menuPanel.setLayout(null);
		menuPanel.setPreferredSize(new Dimension(300, 800)); // the size of menu panel is 300*800

		startBtn = new JButton("Start"); // create start button instances
		startBtn.addActionListener(e -> {
			//bgPanel.repaint();
			bgs.genrationSet(rule); // generate stems according to rules
			bgPanel.paint(bgPanel.getGraphics());
		});
		stopBtn = new JButton("Stop"); // create stop button instances
		stopBtn.addActionListener(e -> {
		});

		ruleLabel = new JLabel("rule"); // ruleBox and action listener
		ruleBox = new JComboBox<String>(rules);
		ruleBox.setModel(new DefaultComboBoxModel<String>(rules));
		ruleBox.addActionListener(e -> {
			switch (ruleBox.getSelectedItem().toString()) {
			case "rule1":
				rule = "rule1"; break;
			case "rule2":
				rule = "rule2"; break;
			case "rule3":
				rule = "rule3"; break;
			}
		});
		
		genLabel = new JLabel("generation");	//generationBox and action listener
		genBox = new JComboBox<Integer>(generations);
		genBox.setModel(new DefaultComboBoxModel<Integer>(generations));
		genBox.addActionListener(e -> {
			switch (genBox.getSelectedIndex()) {
			case 0:
				generation = 0; break;
			case 1:
				generation = 1; break;
			case 2:
				generation = 2; break;
			case 3:
				generation = 3; break;
			case 4:
				generation = 4; break;
			case 5:
				generation = 5; break;
			case 6:
				generation = 6; break;
			case 7:
				generation = 7; break;
			}
		});
		
		colorLabel = new JLabel("color"); // colorBox and action listener
		colorBox = new JComboBox<String>();
		colorBox.setModel(new DefaultComboBoxModel<String>(colors));
		colorBox.addActionListener(e -> {
			switch (colorBox.getSelectedIndex()) {
			case 0:
				color = Color.white; break;
			case 1:
				color = Color.black; break;
			case 2:
				color = Color.red; break;
			case 3:
				color = Color.blue; break;
			case 4:
				color = Color.green; break;
			case 5:
				color = Color.yellow; break;
			case 6:
				color = Color.cyan; break;
			}
		});
		
		growthLabel = new JLabel("<html>show growth <br/>&nbsp &nbsp process</html>");	// growthRate slide control and action listener
		growthBox = new JComboBox<String>();
		growthBox.addItem("true");
		growthBox.addItem("false");
		growthBox.addActionListener(e->{
			switch (growthBox.getSelectedIndex()) {
			case 0:
				growthRate = 1; break;
			case 1:
				growthRate = 0; break;
			}
		});
		
		lengthLabel = new JLabel("length"); // length slide control and action listener
		lengthSlider = new JSlider(5, 31);
		lengthSlider.addChangeListener(e -> {
			sideLengthGrow = 1.0 + 1.0 / lengthSlider.getValue();
		});

		radianLabel = new JLabel("radian"); // radian slide control and action listener
		radianSlider = new JSlider(20, 150);
		radianSlider.addChangeListener(e -> {
			sideRotateRadian = Math.PI / (17 - ((double) radianSlider.getValue()) / 10);
		});

		info = new JLabel("Only available at rule 2/3");
		info.setBounds(25, 350, 300, 40);
		menuPanel.add(info);
		// mid length and radian slide control and action listener; only available at
		// rule 4
		midLengthLabel = new JLabel("midLength");
		midLengthSlider = new JSlider(5, 31);
		midLengthSlider.addChangeListener(e -> {
			midLengthGrow = 1.0 + 1.0 / midLengthSlider.getValue();
		});

		info = new JLabel("Only available at rule 3");
		info.setBounds(25, 425, 300, 40);
		menuPanel.add(info);
		midRadianLabel = new JLabel("midRadian");
		midRadianSlider = new JSlider(20, 150);
		midRadianSlider.addChangeListener(e -> {
			midRotateRadian = Math.PI / (17 - ((double) midRadianSlider.getValue()) / 10);
		});

		info = new JLabel("<html>Author: Tianju Zhou<br/><br/> NUID: 001420546</html>");

		// set location and size of every component
		ruleLabel.setBounds(50, 30, 60, 40);
		ruleBox.setBounds(120, 30, 150, 40);
		genLabel.setBounds(35,75,70,40);
		genBox.setBounds(120, 75,150,40);
		colorLabel.setBounds(50, 125, 60, 40);
		colorBox.setBounds(120, 125, 150, 40);
		growthLabel.setBounds(30,180,110,40);
		growthBox.setBounds(120,180,150,40);
		lengthLabel.setBounds(50, 230, 60, 40);
		lengthSlider.setBounds(120, 230, 150, 40);
		radianLabel.setBounds(50, 270, 60, 40);
		radianSlider.setBounds(120, 270, 150, 40);
		midLengthLabel.setBounds(25, 325, 200, 40);
		midLengthSlider.setBounds(120, 325, 150, 40);
		midRadianLabel.setBounds(25, 400, 200, 40);
		midRadianSlider.setBounds(120, 400, 150, 40);
		startBtn.setBounds(75, 475, 150, 40);
		stopBtn.setBounds(75, 550, 150, 40);
		info.setBounds(20, 675, 300, 100);

		// add every component to menuPanel
		menuPanel.add(startBtn);
		menuPanel.add(stopBtn);
		menuPanel.add(ruleLabel);
		menuPanel.add(ruleBox);
		menuPanel.add(genLabel);
		menuPanel.add(genBox);
		menuPanel.add(colorLabel);
		menuPanel.add(colorBox);
		menuPanel.add(growthLabel);
		menuPanel.add(growthBox);
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
	 * Biological Plant Growth Simulation  starting point
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		PlantApp pt = new PlantApp();

		log.info("WolfApp started");
	}

}
