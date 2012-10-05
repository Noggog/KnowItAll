/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import knowitall.Article;
import knowitall.Debug;
import knowitall.KIASave.Settings;
import knowitall.KnowItAll;
import lev.gui.LTextArea;

/**
 *
 * @author Justin Swanson
 */
public class ArticleDisplay extends ArticlePane {

    Article article;
    static LinkListener listener = new LinkListener();

    public ArticleDisplay() {
	super();
	htmlContent.setLocation(0, Spacings.article);
	ss.addRule("body {"
		+ "margin-top: 0px;"
		+ "margin-right: 10px;"
		+ "margin-bottom: 15px;"
		+ "margin-left: 10px;}");
	ss.addRule("table { border-style: hidden; }");
	setBodyFontColor(KnowItAll.save.getColor(Settings.ArticleFont));
	setLinkFontColor(KnowItAll.save.getColor(Settings.ArticleLinkFont));
	htmlContent.addHyperLinkListener(listener);
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
	float trans = (float) (KnowItAll.save.getInt(Settings.ArticleTrans) / 100.0);
	if (trans < 1) {
	    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, trans));
	}
	g2.setPaint(KnowItAll.save.getColor(Settings.ArticleBack));
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
	    int bottom = htmlContent.getBottom();
	    super.setSize(size.width, bottom + Spacings.article);
	    if (Debug.log.logging()) {
		Debug.log.log("Article Remeasure", "Article : " + article);
		Debug.log.log("Article Remeasure", "  Size: " + size);
		Debug.log.log("Article Remeasure", "  htmlContent bottom: " + bottom);
		Debug.log.log("Article Remeasure", "  Final size: " + getSize());
	    }
	} else {
	    super.setSize(1, 1);
	}
    }

    @Override
    public void setSize(int x, int y) {
	htmlContent.setSize(x, y);
	super.setSize(x, y);
    }

    @Override
    public void setEnabled(boolean on) {
	htmlContent.setEnabled(on);
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
