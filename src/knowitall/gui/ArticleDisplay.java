/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Dimension;
import javax.swing.SwingUtilities;
import javax.swing.text.html.StyleSheet;
import knowitall.Article;
import lev.gui.LHTMLPane;
import lev.gui.LPanel;
import lev.gui.LTextArea;

/**
 *
 * @author Justin Swanson
 */
public class ArticleDisplay extends LPanel {

    Article article;
    LHTMLPane htmlContent;

    public ArticleDisplay() {
	htmlContent = new LHTMLPane();
	htmlContent.setLocation(0, 0);
	htmlContent.setVisible(true);
	StyleSheet ss = htmlContent.getStyleSheet();
	ss.addRule("body {"
		+ "margin-top: 0px;"
		+ "margin-right: 10px;"
		+ "margin-bottom: 15px;"
		+ "margin-left: 10px;}");
	ss.addRule("table { border-style: hidden; }");
//	ss.addRule("td { border-style: dotted; }");
	add(htmlContent);
	setOpaque(true);
    }

    public void load(Article a) {
	article = a;
	setVisible(false);
	if (a != null) {
	    htmlContent.setText(a.getHTML());
	}
    }

    @Override
    public void remeasure(Dimension size) {
	if (article != null) {
	    htmlContent.setSize(size.width - 2);
	    setSize(size.width, htmlContent.getBottom());
	} else {
	    setSize(1, 1);
	}
    }

    int position(LTextArea area, Dimension size, int y) {
	if (area.isVisible()) {
	    area.setLocation(Spacings.articleDispay, y);
	    area.setSize(size.width - 2 * Spacings.articleDispay, 30);
	    area.setSize(area.getPreferredSize());
	    y = area.getBottom();
	}
	return y;
    }
}
