/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.*;
import java.util.Enumeration;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.StyleSheet;
import knowitall.Article;
import knowitall.KIASave.Settings;
import knowitall.KnowItAll;
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
    StyleSheet ss;
    static LinkListener listener = new LinkListener();
    static Font articleFont = LFonts.MyriadPro(16);

    public ArticleDisplay() {
	htmlContent = new LHTMLPane();
	htmlContent.setLocation(0, Spacings.article);
	htmlContent.setVisible(true);
	ss = htmlContent.getStyleSheet();
	ss.addRule("body {"
		+ "margin-top: 0px;"
		+ "margin-right: 10px;"
		+ "margin-bottom: 15px;"
		+ "margin-left: 10px;}");
	ss.addRule("table { border-style: hidden; }");
	setBodyFontColor(KnowItAll.save.getColor(Settings.ArticleFont));
	htmlContent.addHyperLinkListener(listener);
	htmlContent.honorDisplayProperties();
	htmlContent.setFont(articleFont);
	htmlContent.setOpaque(false);
	htmlContent.transparentBackground();
	add(htmlContent);
    }

    public final void setBodyFontColor(Color c) {
	Style s = ss.getStyle("body");
	s.addAttribute(StyleConstants.Foreground, c);
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
	float trans = (float)(KnowItAll.save.getInt(Settings.ArticleTrans) / 100.0);
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
	    super.setSize(size.width, htmlContent.getBottom() + Spacings.article);
	} else {
	    super.setSize(1, 1);
	}
    }

    @Override
    public void setSize(int x, int y) {
	htmlContent.setSize(x, y);
	super.setSize(x, y);
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
