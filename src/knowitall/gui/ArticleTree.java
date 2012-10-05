/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import knowitall.KIASave.Settings;
import knowitall.KnowItAll;
import lev.gui.LTree;

/**
 *
 * @author Justin Swanson
 */
public class ArticleTree extends LTree {

    ArticleTree() {
	super();
	grabColors();
	setOpaque(false);
    }

    public final void grabColors() {
	setForeground(KnowItAll.save.getColor(Settings.TreeFont), false);
	setForeground(KnowItAll.save.getColor(Settings.TreeFontSelected), true);
	setBackground(KnowItAll.save.getColor(Settings.TreeBack), false);
    }

    @Override
    public void paint(Graphics g) {
	Graphics2D g2 = (Graphics2D) g.create();
	g2.setPaint(getBackground());
	Composite old = g2.getComposite();
	float trans = (float) (KnowItAll.save.getInt(Settings.TreeTrans) / 100.0);
	if (trans < 1) {
	    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, trans));
	}
	g2.fillRect(0, 0, getWidth(), getHeight());
	g2.setComposite(old);
	super.paint(g2);
    }
}
