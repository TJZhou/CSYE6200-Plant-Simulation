package edu.neu.csye6200.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class testSwing extends JFrame {

  public testSwing() {
    JPanel panel = new JPanel();
    getContentPane().add(panel);
    setSize(450, 450);

    JButton button = new JButton("press");
    panel.add(button);
  }

  public void paint(Graphics g) {
    super.paint(g); 
    Graphics2D g2 = (Graphics2D) g;
    Line2D lin = new Line2D.Float(100, 100, 250, 260);
    g2.draw(lin);
  }

  public static void main(String[] args) {
	  testSwing s = new testSwing();
    s.setVisible(true);
  }
}