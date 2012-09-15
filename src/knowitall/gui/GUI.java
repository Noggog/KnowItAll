/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import knowitall.Article;
import knowitall.Database;

/**
 *
 * @author Justin Swanson
 */
public class GUI {

    static MainPanel mainPanel;
    static ContentPanel contentPanel;
    static SearchBar search;

    public static void displayArticles(boolean on) {
	if (contentPanel != null) {
	    contentPanel.displayArticles(on);
	}
    }

    public static void updateContentDisplay() {
	contentPanel.update();
    }

    public static void loadArticle(String name) {
	if (Database.hasArticle(name)) {
	    Article a = Database.getArticle(name);
	    search.setText(a.getName());
	    setArticle(a);
	}
    }

    public static void setArticle(Article a) {
	contentPanel.updateContent(a);
    }
}
