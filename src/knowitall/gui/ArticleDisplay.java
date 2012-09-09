/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Color;
import java.awt.Dimension;
import knowitall.Article;
import lev.gui.LLabel;
import lev.gui.LPanel;
import lev.gui.LTextArea;
import lev.gui.resources.LFonts;

/**
 *
 * @author Justin Swanson
 */
public class ArticleDisplay extends LPanel {

    Article article;
    LTextArea content;
    LLabel title;


    public ArticleDisplay () {
	title = new LLabel("Temp", LFonts.MyriadProBold(30), Color.BLACK);
	title.setLocation(Spacings.articleDispay, Spacings.articleDispay);
	add(title);
	content = new LTextArea(Color.BLACK);
	content.setLocation(Spacings.articleDispay, title.getBottom());
	content.setLineWrap(true);
	content.setWrapStyleWord(true);
	add(content);
	setOpaque(true);
    }

    public void load(Article a) {
	article = a;
	title.setText(a.getName());
	content.setText(a.getContent());
    }

    @Override
    public void remeasure(Dimension size) {
	content.setSize(size.width - 2 * Spacings.articleDispay, 30);
	content.setSize(content.getPreferredSize());
	setSize(size.width, content.getY() + content.getHeight() + Spacings.articleDispay);
    }
}
