package edu.neu.csye6200.ui;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import javax.swing.JPanel;
import edu.neu.csye6200.bg.*;

/**
 * A sample canvas that draws a rainbow of lines
 * 
 * @author MMUNSON
 */
@SuppressWarnings("deprecation")
public class BGCanvas extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(BGCanvas.class.getName());
	private long counter = 0L;
	
	/**
	 * CellAutCanvas constructor
	 */
	public BGCanvas() {
		// this.setBackground(Color.GRAY);
	}

	/**
	 * The UI thread calls this method when the screen changes, or in response to a
	 * user initiated call to repaint();
	 */
	public void paint(Graphics g) {
		super.paint(g);
		this.setBackground(Color.LIGHT_GRAY);
		drawBG(g); // Our Added-on drawing
	}

	/**
	 * Draw the CA graphics panel
	 * 
	 * @param g
	 */
	public void drawBG(Graphics g) {
		log.info("Drawing BG " + counter++);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (BGApp.isSimComplete == false) {
			new Thread(new Runnable() {
				public void run() {
					drawPlant(g2d);
				}
			}).start();
		}

		// when the jpanel is repainted and do not need thread to control
		// in this way, we can remain the picture we paint
		if (BGApp.isSimComplete == true) {
			for (int i = 0; i < BGApp.bgs.getBgSet().get(0).getBgs().size(); i++) {
				BGStem st = BGApp.bgs.getBgSet().get(0).getBgs().get(i);
				paintLine(g2d, BGApp.color, st);
			}
		}
	}

	/**
	 *  Extra data from bgSet and draw stem based on the data
	 *  If the isStop parameter is true, then stop the thread
	 *  When the draw process is done: set isStop false, set setResizable true, set isSimComplete true
	 *  
	 * @param g2d
	 */
	private void drawPlant(Graphics2D g2d) {
		try {
			// the first time, the canvas is initialized without stem data
			if (BGApp.bgs.getBgSet().isEmpty() == false) {
				for (int i = 0; i < BGApp.bgs.getBgSet().get(0).getBgs().size(); i++) {
					BGStem st = BGApp.bgs.getBgSet().get(0).getBgs().get(i); // get the current BGStem;
					paintLine(g2d, BGApp.color, st); // paint on the canvas
					// show growth process
					Thread.sleep(BGApp.growthRate);
					// if the flag isStop is true; then stop the thread
					synchronized(this) {
						while (BGApp.isPause == true) {
							wait();
						}
					}
				}
				// when the draw process is done
				BGApp.isPause = false;
				BGApp.frame.setResizable(true);
				BGApp.isSimComplete = true;
				BGApp.isRestart = true;
			}
		}
		catch (InterruptedException e ) {
			e.printStackTrace();
		}
	}
	
	// pause the thread.
	synchronized void mypause() {
		BGApp.isPause = true;
	}
	
	//continue the thread
	synchronized void myresume() {
		BGApp.isPause = false;
		notifyAll();
	}

	//stop the thread
	void mystop() {
		Thread.currentThread().stop();
	}
	
	/**
	 * A convenience routine to set the color and draw a line
	 * 
	 * @param g2d   the 2D Graphics context
	 * @param st    the instance of BGstem which we need to draw
	 * @param color the line color
	 */
	private void paintLine(Graphics2D g2d, Color color, BGStem st) {
		Dimension size = getSize();
		g2d.setColor(BGApp.color);
		Line2D line;
		line = new Line2D.Double(st.getLocationX() + size.getWidth() / 2, -st.getLocationY() + size.getHeight(),
				(st.getLocationX() + st.getLength() * Math.cos(st.getRadians()) + size.getWidth() / 2),
				-(st.getLocationY() + st.getLength() * Math.sin(st.getRadians())) + size.getHeight());
		g2d.draw(line);
	}

	/**
	 * update method: changes from class BGGenerationSet can be observed here
	 * not been used here
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {
	}
}
