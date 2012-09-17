/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
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
    private final static int fadeHeight = 15;
    private static Ellipse2D.Float circle = new Ellipse2D.Float(0, 0, 2 * fadeHeight, 2 * fadeHeight);

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
	int margin = 2 * Spacings.articleDispay;
	pane.compactContent(x - margin);
	pane.setSize(pane.getWidth(), pane.getHeight() + 5);
	super.setSize(pane.getWidth() + margin, pane.getBottom() + Spacings.articleDispay + 15);
    }

    private AlphaComposite makeComposite(float alpha) {
	return (AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    }

    @Override
    public void paint(Graphics g) {
	Graphics2D g2d = (Graphics2D) g.create();
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g2d.setPaint(Color.WHITE);
	int doubleFade = 2 * fadeHeight;
	// Top left circle
	circle.x = 0;
	circle.y = 0;
	g2d.fill(circle);
	// Bottom left circle
	circle.y = getHeight() - doubleFade;
	g2d.fill(circle);
	// Top Right circle
	circle.x = getWidth() - circle.width;
	circle.y = 0;
	g2d.fill(circle);
	// Bottom right circle
	circle.y = getHeight() - doubleFade;
	g2d.fill(circle);
	// Fill 1
	h.width = getWidth() - doubleFade;
	h.x = fadeHeight;
	h.y = 0;
	h.height = getHeight();
	g2d.fill(h);
	// Fill 2
	h.width = getWidth();
	h.height = getHeight() - doubleFade;
	h.x = 0;
	h.y = fadeHeight;
	g2d.fill(h);
	super.paint(g2d);
	g2d.dispose();
    }
}
