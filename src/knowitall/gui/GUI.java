/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.MouseInfo;
import javax.swing.SwingUtilities;
import knowitall.Article;
import knowitall.Database;
import lev.gui.LImagePane;

/**
 *
 * @author Justin Swanson
 */
public class GUI {

    static MainPanel mainPanel;
    static Dimmer dimmer;
    static TopPanel topPanel;
    static ContentPanel contentPanel;
    static SearchBar search;
    static ArticleTooltip tooltip;

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
	hideTooltip();
	contentPanel.updateContent(a);
    }

    public static void setTooltip(String s) {
	if (Database.hasArticle(s)) {
	    setTooltip(Database.getArticle(s));
	}
    }

    public static void setTooltip(Article a) {
	search.setFocusable(false);
	tooltip.load(a);
	tooltip.setSize(mainPanel.getWidth() - 50);
	tooltip.setLocation(25, MouseInfo.getPointerInfo().getLocation().y);
	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		if (tooltip.getY() + tooltip.getHeight() > mainPanel.getHeight()) {
		    tooltip.setLocation(25, MouseInfo.getPointerInfo().getLocation().y - tooltip.getHeight() - 65);
		}
	    }
	});
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		dimmer.setVisible(true);
		tooltip.setVisible(true);
	    }
	});
    }

    public static void hideTooltip() {
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		search.setFocusable(true);
		search.requestFocus();
		dimmer.setVisible(false);
		tooltip.setVisible(false);
	    }
	});
    }
}
