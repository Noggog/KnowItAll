/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import lev.gui.LPanel;

/**
 *
 * @author Justin Swanson
 */
public class Dimmer extends LPanel {

    Dimmer() {
	setOpaque(true);
	setBackground(Color.BLACK);
	setSize(5000,5000);
	setVisible(false);
    }

    @Override
    public void paint(Graphics g) {
	Graphics2D g2 = (Graphics2D) g.create();
	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
	super.paint(g2);
	g2.dispose();
    }
}
