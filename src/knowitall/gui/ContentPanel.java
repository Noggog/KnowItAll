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
    }

    public void updateContent(String s) {
	s = s.toUpperCase();
	if (Database.articles.containsKey(s)) {
	    target = Database.articles.get(s);
	    Updater update = new Updater();
	    update.execute();
	}
    }

    void displayArticles(ArrayList<Article> articles) {
	while (displays.size() < articles.size()) {
	    ArticleDisplay d = new ArticleDisplay();
	    displays.add(d);
	    add(d);
	}

	for (int i = 0 ; i < articles.size() ; i++) {
	    displays.get(i).load(articles.get(i));
	}

	for (int i = articles.size() ; i < displays.size() ; i++) {
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
    public void remeasure (Dimension size) {
	setSize(size);
	compactContent();
    }

    class Updater extends LSwingWorker<Integer, String> {

	Updater () {
	    super(true);
	}

	@Override
	protected Integer doInBackground() throws Exception {
	    ArrayList<Article> articles = new ArrayList<>();
	    articles.add(target);
	    displayArticles(articles);
	    compactContent();
	    return 0;
	}
    }
}
