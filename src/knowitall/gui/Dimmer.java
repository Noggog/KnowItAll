/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import knowitall.KIASave.Settings;
import knowitall.KnowItAll;
import lev.gui.LPanel;

/**
 *
 * @author Justin Swanson
 */
public class Dimmer extends LPanel {

    Dimmer() {
	setSize(5000, 5000);
	super.setVisible(false);
    }

    @Override
    public void paint(Graphics g) {
	Graphics2D g2 = (Graphics2D) g.create();
	g2.setPaint(Color.BLACK);
	float trans = KnowItAll.save.getInt(Settings.DimmerTrans) / 100f;
	if (trans < 1) {
	    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, trans));
	}
	g2.fillRect(0, 0, getWidth(), getHeight());
	super.paint(g2);
	g2.dispose();
    }

    @Override
    public void setVisible(boolean on) {
	super.setVisible(GUI.topPanel.isActive());
    }
    
    public void setVisibleOverride(boolean on) {
	super.setVisible(true);
    }
}
