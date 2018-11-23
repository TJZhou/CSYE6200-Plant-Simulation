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
public class BGCanvas extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(BGCanvas.class.getName());
	private long counter = 0L;

	/**
	 * CellAutCanvas constructor
	 */
	public BGCanvas() {

	}

	/**
	 * The UI thread calls this method when the screen changes, or in response to a
	 * user initiated call to repaint();
	 */
	public void paint(Graphics g) {
		super.paint(g);
		this.setBackground(Color.GRAY);
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
		// System.out.println(BGApp.BGSetCount);
		if (BGApp.bgs.getBgSet().isEmpty() == false) {
			for (int i = 0; i < BGApp.bgs.getBgSet().get(0).getBgs().size(); i++) {
				BGStem st = BGApp.bgs.getBgSet().get(0).getBgs().get(i); // get the current BGStem;
				paintLine(g2d, BGApp.color, st);
			}
		}
	}

	/**
	 * A convenience routine to set the color and draw a line
	 * 
	 * @param g2d    the 2D Graphics context
	 * @param startx the line start position on the x-Axis
	 * @param starty the line start position on the y-Axis
	 * @param endx   the line end position on the x-Axis
	 * @param endy   the line end position on the y-Axis
	 * @param color  the line color
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

	@Override
	public void update(Observable o, Object arg) {
		this.repaint();
	}
}
