/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import knowitall.Article;
import knowitall.Database;
import knowitall.KIASave.Settings;
import knowitall.KnowItAll;
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
	displayArticles(false);
	if (a != null) {
	    target = a;
	} else if (target != null) {
	    target = Database.getArticle(target.getName());
	} else {
	    return;
	}
	Updater update = new Updater();
	update.execute();
    }

    public void update() {
	updateContent(null);
    }

    void loadArticles(ArrayList<Article> articles) {
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

    public void displayArticles(final boolean on) {
	for (final ArticleDisplay display : displays) {
	    if (display.article != null) {
		SwingUtilities.invokeLater(new Runnable() {
		    @Override
		    public void run() {
			display.setVisible(on);
		    }
		});
	    }
	}
    }

    @Override
    public void remeasure(Dimension size) {
	setSize(size);
	compactContent();
    }

    final void addDisplay() {
	final ArticleDisplay d = new ArticleDisplay();
	displays.add(d);
	d.setVisible(false);
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		add(d);
	    }
	});
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
		if (KnowItAll.save.getBool(Settings.LinkedArticles)) {
		    for (Article link : target.getLinks()) {
			articles.add(link);
		    }
		}
	    }
	    loadArticles(articles);
	    compactContent();
	    displayArticles(true);
	    return 0;
	}
    }
}
