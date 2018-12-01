package edu.neu.csye6200.ui;

import net.java.dev.designgridlayout.DesignGridLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Biological Plant Growth Simulation application
 * 
 * @author Tianju Zhou NUID 001420546
 * 
 * ----------------------Function Description-------------------------
 * A java swing application to simulate the process of plant growth.
 * 
 * Before press the start button, users can change some basic settings
 * which include growth rule, growth generation, plant color, whether 
 * to show growth process and the stems' length and radian. Also users
 * can press random button to set parameters randomly
 * 
 * After press the start button, the panel will show the picture of the 
 * plant, and it can show growth process when the growth process box is 
 * not "no process".
 * 
 * When the growth process box is "fast", "middle" or "slow", then users  
 * can press stop button to pause the draw process; when the draw process
 * is paused, users can press resume button to start again.
 * 
 * In the draw process, it is not able to resize the window. After the 
 * draw process is done, it is able to resize the window.
 * 
 * In the file menu, users can click “exit” to exit the application,
 * and in the help menu, users can click "about" to see the helping
 * file: Readme.md.
 * 
 * During the draw process, There are two variables that can be modified 
 * in real time: growth rate and color.
 * 
 * All the information will be printed in the JTextField in the InfoPanel -
 * at the right side of the main panel
 * -------------------------------------------------------------------
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
			Logger.getLogger("").addHandler(handler);	//add global fileHandler

		} catch (SecurityException e) {
			log.warning("SecurityException occurs in constructor PlantSimUI");
			e.printStackTrace();
		} catch (IOException e) {
			log.warning("IOException occurs in constructor PlantSimUI");
			e.printStackTrace();
		}

		frame.setSize(1500, 800); // initial Frame size
		frame.setTitle("PlantApp");
		menuMgr.createDefaultActions(); // Set up default menu items
		showUI(); // Cause the Swing Dispatch thread to display the JFrame
	}

	/**
	 * Create a main panel that will hold the bulk of our application display
	 * 
	 * * @return JPanel
	 */
	@SuppressWarnings("deprecation")
	@Override
	public JPanel getMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(BorderLayout.WEST, getSettingPanel());
		bgPanel = new BGCanvas();
		mainPanel.add(BorderLayout.CENTER, bgPanel);	// set menuPanel to the left and mainPanel to the right
		bgs.addObserver(bgPanel); /// add observer
		mainPanel.add(BorderLayout.EAST, getInfoPanel());
		return mainPanel;
	}

	/**
	 * Create a info panel (at right side) that will hold JTextArea
	 * 
	 * @return JPanel
	 */
	private JPanel getInfoPanel() {
		infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension(250, 800));
		infoPanel.setLayout(null);
		// add a scrollPane to hold JTextArea
		infoScrollPane = new JScrollPane();
		infoScrollPane.setBounds(15, 25, 220, 400);
		infoPanel.add(infoScrollPane);
		// add a textArea to output info
		infoTextArea = new JTextArea();
		infoTextArea.setBounds(15, 25, 220, 400);
		infoScrollPane.setViewportView(infoTextArea);
		// info
		JLabel info = new JLabel("       Author: Tianju Zhou");
		info.setBounds(80, 600, 200, 50);
		infoPanel.add(info);
		info = new JLabel("Northeastern University");
		info.setBounds(80, 650, 200, 50);
		infoPanel.add(info);
		info = new JLabel("         NUID:001420546");
		info.setBounds(80, 700, 200, 50);
		infoPanel.add(info);
		return infoPanel;
	}

	/**
	 * Create a menuPanel (at left side) that will hold control buttons, sliders and comboBoxs
	 * 
	 * @return JPanel
	 */
	public JPanel getSettingPanel() {
		settingPanel = new JPanel();
		settingPanel.setPreferredSize(new Dimension(250, 800)); // the size of menu panel is 250*800
		DesignGridLayout playout =  new DesignGridLayout(settingPanel);
		
		// ruleBox and action listener
		ruleBox = new JComboBox<String>(rules);
		ruleBox.setModel(new DefaultComboBoxModel<String>(rules));
		ruleBox.addActionListener(e -> {ruleBoxAction();});
		playout.row().grid().empty();	//using DesignGridLayout
		playout.row().grid(new JLabel("rule")).add(ruleBox);
		
		//jTextField: input the number of generation
		genTextField = new JTextField();
		genTextField.setText("0~9");
		playout.row().grid().empty();	
		playout.row().grid(new JLabel("generation")).add(genTextField);
		
		//colorBox and action listener
		colorBox = new JComboBox<String>();	
		colorBox.setModel(new DefaultComboBoxModel<String>(colors));
		colorBox.addActionListener(e -> {colorBoxAction();});
		playout.row().grid().empty();
		playout.row().grid(new JLabel("color")).add(colorBox);
		
		//growthBox and action listner
		growthBox = new JComboBox<String>();
		growthBox.setModel(new DefaultComboBoxModel<String>(growthRates));
		growthBox.addActionListener(e->{growthBoxAction();});
		playout.row().grid().empty();
		playout.row().grid(new JLabel("growth process")).add(growthBox);
		
		// side length slide control and action listener
		lengthSlider = new JSlider(5, 31);
		lengthSlider.addChangeListener(e -> {
			sideLengthGrow = 1.0 + 1.0 / lengthSlider.getValue();
		});
		playout.row().grid().empty();
		playout.row().grid(new JLabel("length")).add(lengthSlider);
		
		// radian slide control and action listener
		radianSlider = new JSlider(20, 150);
		radianSlider.addChangeListener(e -> {
			sideRotateRadian = Math.PI / (17 - ((double) radianSlider.getValue()) / 10);
		});
		playout.row().grid().empty();
		playout.row().grid(new JLabel("radian")).add(radianSlider);
		
		// basic instruction about midLength
		playout.row().grid().empty();	
		playout.row().grid((new JLabel("Only available at"))).add(new JLabel("rule 2/3"));
		// middle length slide control and action listener
		midLengthSlider = new JSlider(5, 31, 5);
		midLengthSlider.addChangeListener(e -> {
			midLengthGrow = 1.0 + 1.0 / midLengthSlider.getValue();
		});
		playout.row().grid().empty();
		playout.row().grid(new JLabel("midLength")).add(midLengthSlider);
		
		// basic instruction about midRadian
		playout.row().grid().empty();	
		playout.row().grid((new JLabel("Only available at"))).add(new JLabel("rule 3"));
		// middle radian slide control and action listener
		midRadianSlider = new JSlider(20, 150, 20);
		midRadianSlider.addChangeListener(e -> {
			midRotateRadian = Math.PI / (17 - ((double) midRadianSlider.getValue()) / 10);
		});
		playout.row().grid().empty();
		playout.row().grid(new JLabel("midRadian")).add(midRadianSlider);
		
		// create random button instances
		playout.row().grid().empty();
		randomBtn = new JButton("    Random    ");
		randomBtn.addActionListener(e -> {
			randomBtnAction();
		});
		playout.row().grid().empty();
		playout.row().center().add(randomBtn);
	
		// create start button instances
		startBtn = new JButton("    Start    "); 
		startBtn.addActionListener(e -> {
			startBtnAction();
		});
		playout.row().grid().empty();
		playout.row().center().add(startBtn);

		// create stop button instances
		stopBtn = new JButton("    Stop    ");
		stopBtn.addActionListener(e -> {
			// change the status of stop button; stop->continue; continue->stop
			bgPanel.mypause();
			if(isRestart == false)
				infoTextArea.insert("Simulation process is paused.\n\n", 0);
		});
		playout.row().grid().empty();
		playout.row().center().add(stopBtn);

		// create resume button instances
		resumeBtn = new JButton("    Resume    ");
		resumeBtn.addActionListener(e -> {
			// change the status of stop button; stop->continue; continue->stop
			bgPanel.myresume();
			if(isRestart == false)
				infoTextArea.insert("Simulation process continues.\n\n", 0);
		});
		playout.row().grid().empty();
		playout.row().center().add(resumeBtn);
		
		settingPanel.setBackground(Color.WHITE);
		return settingPanel;
	}

	/**
	 * random button: set all parameters randomly
	 */
	private void randomBtnAction() {
		infoTextArea.insert("Set parameters randomly\n\n", 0);

		// random of ruleBox: rule1/2/3
		int index = (int) (Math.random() * 3);
		ruleBox.setSelectedIndex(index);

		// random textField: generation of 0-9
		index = (int) (Math.random() * 10);
		genTextField.setText(String.valueOf(index));

		// random of coloreBox: 7 different colors
		index = (int) (Math.random() * 7);
		colorBox.setSelectedIndex(index);

		// random of growthBox: speed of growth - no process/fast/middle/low
		index = (int) (Math.random() * 4);
		growthBox.setSelectedIndex(index);

		// random of length growth
		index = (int) (Math.random() * 27 + 5);
		lengthSlider.setValue(index);

		// random of radian rotation
		index = (int) (Math.random() * 131 + 20);
		radianSlider.setValue(index);

		// random of mid length rotation
		index = (int) (Math.random() * 27 + 5);

		midLengthSlider.setValue(index);

		// random of mid radian rotation
		index = (int) (Math.random() * 131 + 20);
		midRadianSlider.setValue(index);
	}

	//doing this action when choosing from growthBox
	private void growthBoxAction() {
		switch (growthBox.getSelectedIndex()) {
		case 0:
			growthRate = 0; break;
		case 1:
			growthRate = 1; break;
		case 2:
			growthRate = 10; break;
		case 3:
			growthRate = 50; break;
		}
	}

	// doing this action when choosing from ruleBox
	private void ruleBoxAction() {
		switch (ruleBox.getSelectedIndex()) {
		case 0:
			rule = "rule1"; break;
		case 1:
			rule = "rule2"; break;
		case 2:
			rule = "rule3"; break;
		}
	}

	// doing this action when choosing from color box
	private void colorBoxAction() {
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
	}

	// doing this action when pressing start button
	private void startBtnAction() {
		if(isRestart == true) {	// the process is done and can start again
			try {
				int num = Integer.parseInt(genTextField.getText());
				System.out.print(genTextField.getText().trim());
				if (num >= 0 && num <= 9) { // ensure that the generation is 0-9
					infoTextArea.insert("Simulation Starts.\n\n", 0);
					isPause = false; // reset isStop, isRestart, isSimCompelte to false
					isSimComplete = false;
					isRestart = false;
					frame.setResizable(false);// set frame resizable false
					generation = Integer.parseInt(genTextField.getText());
					bgs.genrationSet(rule); // generate stems according to rules
					bgPanel.paint(bgPanel.getGraphics());
				}
				else {	// input less than 0 or greater than 9 
					log.warning("invalid input");
					JOptionPane.showMessageDialog(null, " Please input generations again (0~9). ", " Invalid Input", JOptionPane.ERROR_MESSAGE); 
				}
			}catch(NumberFormatException ex) {	//input is not a integer
				log.warning("invalid input");
				JOptionPane.showMessageDialog(null, " Please input generations again (0~9). ", " Invalid Input", JOptionPane.ERROR_MESSAGE); 
			}
		}
		else {	// cannot be restart when the isRestart is false
			JOptionPane.showMessageDialog(null, " Please wait the process to be done or "
					+ "set the growth process to \"no process\" \nto finishi the drawing process immediately. ",
					" Cannot restart now", JOptionPane.ERROR_MESSAGE); 
		}
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
		new PlantApp();
		log.info("PlantApp started");
	}
}
