package edu.neu.csye6200.ui;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import edu.neu.csye6200.bg.BGGenerationSet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.logging.Logger;
/**
 * A sample Biological Growth Abstract application class
 * @author MMUNSON
 *
 */
public abstract class BGApp implements ActionListener, WindowListener {
	public static int generation = 0;	//default generation: only one base stem
	public static double sideLengthGrow = 1.05555;
	public static double midLengthGrow = 1.2;	//default length
	public static double sideRotateRadian = Math.PI /9;
	public static double midRotateRadian = Math.PI/15;	//default radian
	public static String rule = "rule1";	//default rule
	public static Color color = Color.white; 	//default color
	public static BGGenerationSet bgs  = BGGenerationSet.generationSet();//singleton pattern
	public static int growthRate = 0;	//default growth rate, 0 means do not have speed limitation
	public static boolean isPause = false;	// judge if we need to stop the growth process
	public static boolean isSimComplete = false; //if the simulation process is complete
	public static boolean isRestart = true;	//if the drawing process can be restarted
	public static JFrame frame = null;
	public static JTextArea infoTextArea = null;
	
	protected JPanel menuPanel = null;
	protected JPanel mainPanel = null;
	protected BGCanvas bgPanel = null;
	protected JPanel infoPanel = null;
	protected MenuManager menuMgr = null;
	protected static Logger log = Logger.getLogger(PlantApp.class.getName());
	protected String logBase = "log/server.log";	//log file routine	
	protected String rules[] = {"rule1", "rule2", "rule3"};	//rule set
	protected String colors[] = {"white", "black", "red", "blue", "green", "yellow","cyan"};	//color set
	protected String growthRates[] = {"no process", "fast", "middle", "slow"}; 	//growth rate set
	protected JScrollPane infoScrollPane = null;	// a scrollPanel to hold JTextArea
	protected JTextField genTextField = null; //input generation into this textField
	protected JButton randomBtn = null;
	protected JButton startBtn = null;
	protected JButton stopBtn = null;
	protected JButton resumeBtn = null;
	protected JComboBox<String> growthBox = null;
	protected JComboBox<String> ruleBox = null;
	protected JComboBox<String> colorBox = null;
	protected JSlider lengthSlider = null;
	protected JSlider midLengthSlider = null; //only available at rule2/3
	protected JSlider radianSlider = null;
	protected JSlider midRadianSlider = null;//only available at rule3
	
	/**
	 * The Biological growth constructor
	 */
	public BGApp() {
		initGUI();
	}
	
	/**
	 * Initialize the Graphical User Interface
	 */
    public void initGUI() {
    	frame = new JFrame();
		frame.setTitle("BGApp");

		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //JFrame.DISPOSE_ON_CLOSE)
		
		// Permit the app to hear about the window opening
		frame.addWindowListener(this); 
		
		menuMgr = new MenuManager(this);
		
		frame.setJMenuBar(menuMgr.getMenuBar()); // Add a menu bar to this application
		
		frame.setLayout(new BorderLayout());
		
		frame.add(getMainPanel(), BorderLayout.CENTER);
		
		infoTextArea.insert("App starts!\n\n", 0);
    }
    
    /**
     * Override this method to provide the main content panel.
     * @return a JPanel, which contains the main content of of your application
     */
    public abstract JPanel getMainPanel();
   
    
    /**
     * A convenience method that uses the Swing dispatch threat to show the UI.
     * This prevents concurrency problems during component initialization.
     */
    public void showUI() {
    	
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
            	frame.setVisible(true); // The UI is built, so display it;
            }
        });
    	
    }
    
    /**
     * Shut down the application
     */
    public void exit() {
    	frame.dispose();
    	System.exit(0);
    }

    /**
	 * Override this method to show an About Dialog
	 */
	public void showHelp() {
		// show read me file
		try {
			Desktop.getDesktop().open(new java.io.File("README.md"));
			} catch (Exception ex) {
			ex.printStackTrace();
		}
    }
}
