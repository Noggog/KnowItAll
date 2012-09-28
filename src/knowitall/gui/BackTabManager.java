/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import knowitall.Article;
import lev.gui.LPanel;
import lev.gui.resources.LImages;

/**
 *
 * @author Justin Swanson
 */
public class BackTabManager extends LPanel {

    static private int history = 25;
    LinkedList<Article> articles = new LinkedList<>();
    ArrayList<BackTab> tabs = new ArrayList<>(history);
    BufferedImage img;
    static private int xOffset = 20;

    public BackTabManager() {
	super();
	for (int i = 0; i < history; i++) {
	    BackTab b = new BackTab();
	    tabs.add(b);
	    add(b);
	}
	img = LImages.arrow(true, false);
    }

    public void putOnArticle(Article a) {
	articles.remove(a);
	articles.addFirst(a);
	if (articles.size() > history) {
	    articles.subList(history, history + 1).clear();
	}
    }

    public void readjust() {
	int i = 0;
	for (Article a : articles) {
	    BackTab t = tabs.get(i++);
	    t.load(a);
	}

	while (i < tabs.size()) {
	    tabs.get(i++).setVisible(false);
	}

	int x = xOffset;
	for (BackTab t : tabs) {
	    if (t.isVisible()) {
		t.setLocation(x, 0);
		x += t.getWidth();
	    } else {
		break;
	    }
	}
    }

    public void remove(Article a) {
	articles.remove(a);
    }

    @Override
    public void paint(Graphics g) {
	if (!articles.isEmpty()) {
	    g.drawImage(img, 10, 0, null);
	}
	super.paint(g);
    }
}
