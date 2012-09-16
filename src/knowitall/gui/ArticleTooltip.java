/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import knowitall.Article;
import lev.gui.LHTMLPane;
import lev.gui.LPanel;

/**
 *
 * @author Justin Swanson
 */
public class ArticleTooltip extends LPanel {

    LHTMLPane pane;
    private static Rectangle h = new Rectangle(0, 0, 5000, 1);
    private static Rectangle fill = new Rectangle(0, 0, 1, 1);
    private static int fadeHeight = 15;

    ArticleTooltip() {
	pane = new LHTMLPane();
	pane.setLocation(Spacings.articleDispay, Spacings.articleDispay);
	pane.setOpaque(false);
	add(pane);
	setVisible(false);
    }

    public void load(Article a) {
	setVisible(false);
	if (a != null) {
	    pane.setText(a.getHTML(false));
	}
    }

    @Override
    public void setSize(Dimension size) {
	setSize(size.width);
    }

    @Override
    public void setSize(int x, int y) {
	setSize(x);
    }

    public void setSize(int x) {
	pane.setSize(x - 2 * Spacings.articleDispay);
	pane.setSize(pane.getWidth(), pane.getHeight() + 5);
	super.setSize(x, pane.getBottom() + Spacings.articleDispay + 15);
    }

    private AlphaComposite makeComposite(float alpha) {
	return (AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    }

    @Override
    public void paint(Graphics g) {
	Graphics2D g2d = (Graphics2D) g.create();
	Composite originalComposite = g2d.getComposite();
	g2d.setPaint(Color.WHITE);
	int startWidth = getWidth() - fadeHeight * 2;
	for (int i = 0; i < fadeHeight; i++) {
	    double tmp = 1.0 * (fadeHeight - i) / fadeHeight;
	    tmp = Math.asin(tmp);
	    tmp = fadeHeight * Math.cos(tmp);
	    int sin = (int) tmp;
	    tmp = tmp % 1;
	    // Top two circles
	    h.setBounds(fadeHeight - sin, i, startWidth + 2 * sin, 1);
	    g2d.fill(h);
	    // Bottom two circles
	    h.setBounds(h.x, getHeight() - i - 1, h.width, h.height);
	    g2d.fill(h);
	    // Fade edges
	    g2d.setComposite(makeComposite((float) tmp));
	    //Top left
	    fill.setBounds(h.x - 1, i, 1, 1);
	    g2d.fill(fill);
	    //Top Right
	    fill.setBounds(h.x + h.width, i, 1, 1);
	    g2d.fill(fill);
	    //Bottom left
	    fill.setBounds(h.x - 1, getHeight() - i - 1, 1, 1);
	    g2d.fill(fill);
	    //Bottom right
	    fill.setBounds(h.x + h.width, getHeight() - i - 1, 1, 1);
	    g2d.fill(fill);
	    g2d.setComposite(originalComposite);
	}
	// Hack for top line to look better
	g2d.setComposite(makeComposite(0.5f));
	fill.setBounds(fadeHeight - 3, 0, getWidth() - 2 * fadeHeight + 6, 1);
	g2d.fill(fill);
	// Hack for bottom line to look better
	fill.setBounds(fadeHeight - 3, getHeight() - 1, getWidth() - 2 * fadeHeight + 6, 1);
	g2d.fill(fill);
	// Fill middle
	g2d.setComposite(originalComposite);
	h.setBounds(0, fadeHeight, getWidth(), getHeight() - 2 * fadeHeight);
	g2d.fill(h);
	super.paint(g2d);
	g2d.dispose();
    }
    // Faded edges
//    public void paint(Graphics g) {
//	Graphics2D g2d = (Graphics2D) g.create();
//	Composite originalComposite = g2d.getComposite();
//	g2d.setPaint(Color.WHITE);
//	// Fill middle
//	h.setBounds(fadeHeight, fadeHeight, getWidth() - 2 * fadeHeight, getHeight() - 2 * fadeHeight);
//	g2d.fill(h);
//	for (int i = 0; i < fadeHeight; i++) {
//	    g2d.setComposite(makeComposite(i * .067f));
//	    // Fade top
//	    h.setBounds(0, i, getWidth(), 1);
//	    g2d.fill(h);
//	    // Fade Bottom
//	    h.setBounds(0, getHeight() - i - 1, getWidth(), 1);
//	    g2d.fill(h);
//	    // Fade Left
//	    h.setBounds(i, 0, 1, getHeight());
//	    g2d.fill(h);
//	    // Fade Right
//	}
//	g2d.setComposite(originalComposite);
//	super.paint(g2d);
//	g2d.dispose();
//    }
}
