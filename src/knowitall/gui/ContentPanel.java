/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Dimension;
import java.util.ArrayList;
import knowitall.Article;
import knowitall.Database;
import lev.gui.LPanel;
import lev.gui.LSwingWorker;

/**
 *
 * @author Justin Swanson
 */
public class ContentPanel extends LPanel {

    Article target;
    ArrayList<ArticleDisplay> displays = new ArrayList<>();

    ContentPanel() {
	for (int i = 0; i < 10; i++) {
	    addDisplay();
	}
    }

    public void updateContent(Article a) {
	if (a != null) {
	    target = a;
	} else if (target != null) {
	    target = Database.articles.get(target.getName().toUpperCase());
	} else {
	    return;
	}
	Updater update = new Updater();
	update.execute();
    }

    public void update() {
	updateContent(null);
    }

    void displayArticles(ArrayList<Article> articles) {
	while (displays.size() < articles.size()) {
	    addDisplay();
	}

	for (int i = 0; i < articles.size(); i++) {
	    displays.get(i).load(articles.get(i));
	}

	for (int i = articles.size(); i < displays.size(); i++) {
	    displays.get(i).load(null);
	}
    }

    public void compactContent() {
	int totalHeight = 0;
	for (ArticleDisplay display : displays) {
	    display.setLocation(0, totalHeight);
	    display.remeasure(getSize());
	    totalHeight += display.getHeight() + Spacings.contentPanel;
	}
	setSize(getWidth(), totalHeight);
	setPreferredSize(getSize());
    }

    @Override
    public void remeasure(Dimension size) {
	setSize(size);
	compactContent();
    }

    final void addDisplay() {
	ArticleDisplay d = new ArticleDisplay();
	displays.add(d);
	d.setVisible(false);
	add(d);
    }

    class Updater extends LSwingWorker<Integer, String> {

	Updater() {
	    super(true);
	}

	@Override
	protected Integer doInBackground() throws Exception {
	    ArrayList<Article> articles = new ArrayList<>();
	    if (target != null) {
		articles.add(target);
		for (Article link : target.getLinks()) {
		    articles.add(link);
		}
	    }
	    displayArticles(articles);
	    compactContent();
	    return 0;
	}
    }
}
