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
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.StyleSheet;
import knowitall.Article;
import lev.gui.LHTMLPane;
import lev.gui.LPanel;
import lev.gui.LTextArea;
import lev.gui.resources.LFonts;

/**
 *
 * @author Justin Swanson
 */
public class ArticleDisplay extends LPanel {

    Article article;
    LHTMLPane htmlContent;
    static LinkListener listener = new LinkListener();

    public ArticleDisplay() {
	htmlContent = new LHTMLPane();
	htmlContent.setLocation(0, Spacings.article);
	htmlContent.setVisible(true);
	StyleSheet ss = htmlContent.getStyleSheet();
	ss.addRule("body {"
		+ "margin-top: 0px;"
		+ "margin-right: 10px;"
		+ "margin-bottom: 15px;"
		+ "margin-left: 10px;}");
	ss.addRule("table { border-style: hidden; }");
	htmlContent.addHyperLinkListener(listener);
	htmlContent.honorDisplayProperties();
	htmlContent.setFont(LFonts.MyriadPro(16));
	htmlContent.setOpaque(false);
	htmlContent.transparentBackground();
	add(htmlContent);
    }

    public void load(Article a) {
	article = a;
	setVisible(false);
	if (a != null) {
	    htmlContent.setText(a.getHTML(true));
	}
    }

    @Override
    public void paint(Graphics g) {
	Graphics2D g2 = (Graphics2D) g.create();
	Composite old = g2.getComposite();
	if (GUI.articleBGtransparancy < 1) {
	    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, GUI.articleBGtransparancy));
	}
	g2.setPaint(Color.WHITE);
	Rectangle r = new Rectangle(getWidth(), getHeight());
	g2.fill(r);
	g2.setComposite(old);
	super.paint(g2);
	g2.dispose();
    }

    @Override
    public void remeasure(Dimension size) {
	if (article != null) {
	    htmlContent.setSize(size.width - 2);
	    setSize(size.width, htmlContent.getBottom() + Spacings.article);
	} else {
	    setSize(1, 1);
	}
    }

    int position(LTextArea area, Dimension size, int y) {
	if (area.isVisible()) {
	    area.setLocation(Spacings.tooltip, y);
	    area.setSize(size.width - 2 * Spacings.tooltip, 30);
	    area.setSize(area.getPreferredSize());
	    y = area.getBottom();
	}
	return y;
    }

    static class LinkListener implements HyperlinkListener {

	@Override
	public void hyperlinkUpdate(HyperlinkEvent h) {
	    if (GUI.mainFrameFocus) {
		HyperlinkEvent.EventType type = h.getEventType();
		if (type == HyperlinkEvent.EventType.ENTERED) {
		    GUI.setTooltip(h.getDescription());
		} else if (type == HyperlinkEvent.EventType.ACTIVATED) {
		    GUI.setArticle(h.getDescription());
		} else if (type == HyperlinkEvent.EventType.EXITED) {
		    GUI.hideTooltip();
		}
	    }
	}
    }
}
