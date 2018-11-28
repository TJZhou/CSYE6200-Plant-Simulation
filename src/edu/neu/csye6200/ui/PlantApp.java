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
import javax.swing.JSlider;
import javax.swing.JTextField;

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
	 * @return JPanel
	 */
	public JPanel getMenuPanel() {
		menuPanel = new JPanel();
		menuPanel.setPreferredSize(new Dimension(250, 800)); // the size of menu panel is 300*800
		DesignGridLayout playout =  new DesignGridLayout(menuPanel);
		
		ruleBox = new JComboBox<String>(rules);// ruleBox and action listener
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
		playout.row().grid().empty();	//using DesignGridLayout
		playout.row().grid(new JLabel("rule")).add(ruleBox);
		
		genTextField = new JTextField();//jTextField: input the number of generation
		genTextField.setText("    0~9");
		playout.row().grid().empty();	
		playout.row().grid(new JLabel("generation")).add(genTextField);
		
		colorBox = new JComboBox<String>();	//colorBox and action listener
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
		playout.row().grid().empty();
		playout.row().grid(new JLabel("color")).add(colorBox);
		
		growthBox = new JComboBox<String>();	//growthBox and action listner
		growthBox.addItem("false");
		growthBox.addItem("true");
		growthBox.addActionListener(e->{
			switch (growthBox.getSelectedIndex()) {
			case 0:
				growthRate = 0; break;
			case 1:
				growthRate = 1; break;
			}
		});
		playout.row().grid().empty();
		playout.row().grid(new JLabel("growth process")).add(growthBox);
		
		lengthSlider = new JSlider(5, 31);// length slide control and action listener
		lengthSlider.addChangeListener(e -> {
			sideLengthGrow = 1.0 + 1.0 / lengthSlider.getValue();
		});
		playout.row().grid().empty();
		playout.row().grid(new JLabel("length")).add(lengthSlider);
		
		radianSlider = new JSlider(20, 150);// radian slide control and action listener
		radianSlider.addChangeListener(e -> {
			sideRotateRadian = Math.PI / (17 - ((double) radianSlider.getValue()) / 10);
		});
		playout.row().grid().empty();
		playout.row().grid(new JLabel("radian")).add(radianSlider);
		
		playout.row().grid().empty();	// basic instruction about midLength
		playout.row().grid((new JLabel("Only available at"))).add(new JLabel("rule 2/3"));
		
		midLengthSlider = new JSlider(5, 31, 5);
		midLengthSlider.addChangeListener(e -> {
			midLengthGrow = 1.0 + 1.0 / midLengthSlider.getValue();
		});
		playout.row().grid().empty();
		playout.row().grid(new JLabel("midLength")).add(midLengthSlider);
		
		playout.row().grid().empty();	// basic instruction about midRadian
		playout.row().grid((new JLabel("Only available at"))).add(new JLabel("rule 3"));
		
		midRadianSlider = new JSlider(20, 150, 20);
		midRadianSlider.addChangeListener(e -> {
			midRotateRadian = Math.PI / (17 - ((double) midRadianSlider.getValue()) / 10);
		});
		playout.row().grid().empty();
		playout.row().grid(new JLabel("midRadian")).add(midRadianSlider);
		
		startBtn = new JButton("    Start    "); // create start button instances
		startBtn.addActionListener(e -> {
			try {
				int num = Integer.parseInt(genTextField.getText());
				if(num>=0&&num<=9) {
					isPause = false;	//reset isStop and isSimCompelte to false
					isSimComplete = false;
					frame.setResizable(false);// set frame resizable false
					generation = Integer.parseInt(genTextField.getText());
					bgs.genrationSet(rule); // generate stems according to rules
					bgPanel.paint(bgPanel.getGraphics());
				}
				else {
					log.warning("invalid input");
					JOptionPane.showMessageDialog(null, " Please input again (0~9). ", " Invalid Input", JOptionPane.ERROR_MESSAGE); 
				}
					
			}catch(NumberFormatException ex) {
				log.warning("invalid input");
				JOptionPane.showMessageDialog(null, " Please input again (0~9). ", " Invalid Input", JOptionPane.ERROR_MESSAGE); 
			}
		});
		playout.row().grid().empty();
		playout.row().center().add(startBtn);
		
		stopBtn = new JButton("    Stop    "); // create stop button instances
		stopBtn.addActionListener(e -> {
			//change the status of stop button; stop->continue; continue->stop
				bgPanel.mypause();
		});
		playout.row().grid().empty();
		playout.row().center().add(stopBtn);
		
		resumeBtn = new JButton("    Resume    "); // create stop button instances
		resumeBtn.addActionListener(e -> {
			//change the status of stop button; stop->continue; continue->stop
				bgPanel.myresume();
		});
		playout.row().grid().empty();
		playout.row().center().add(resumeBtn);
		
		playout.row().grid().empty();	// info
		playout.row().grid().empty();
		playout.row().grid().empty();
		playout.row().grid((new JLabel("Author: Tianju"))).add((new JLabel("Zhou")));
		playout.row().grid((new JLabel("ID:001420546")));
		
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
		log.info("PlantApp started");
	}

}
