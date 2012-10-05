/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import knowitall.Article;
import knowitall.KIASave.Settings;
import knowitall.KnowItAll;

/**
 *
 * @author Justin Swanson
 */
public class ArticleTooltip extends ArticlePane {

    private static Rectangle h = new Rectangle(0, 0, 5000, 1);
    private final static int fadeHeight = 15;
    private static Ellipse2D.Float circle = new Ellipse2D.Float(0, 0, 2 * fadeHeight, 2 * fadeHeight);

    ArticleTooltip() {
	ss.addRule("body {}");
	htmlContent.setLocation(Spacings.tooltip, Spacings.tooltip);
	setBodyFontColor(KnowItAll.save.getColor(Settings.ToolFont));
	add(htmlContent);
	setVisible(false);
    }

    public void load(Article a) {
	setVisible(false);
	if (a != null) {
	    htmlContent.setText(a.getHTML(false));
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
	int margin = 2 * Spacings.tooltip;
	htmlContent.compactContent(x - margin);
	htmlContent.setSize(htmlContent.getWidth(), htmlContent.getHeight() + 5);
	super.setSize(htmlContent.getWidth() + margin, htmlContent.getBottom() + Spacings.tooltip + 5);
    }

    @Override
    public void paint(Graphics g) {
	Graphics2D g2d = (Graphics2D) g.create();
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g2d.setPaint(KnowItAll.save.getColor(Settings.ToolBack));
	Composite old = g2d.getComposite();
	float trans = (float)(KnowItAll.save.getInt(Settings.ToolTrans) / 100.0);
	if (trans < 1) {
	    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, trans));
	}
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
	g2d.setComposite(old);
	super.paint(g2d);
	g2d.dispose();
    }
}
