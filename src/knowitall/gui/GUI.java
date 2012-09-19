/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.Collection;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import knowitall.Article;
import knowitall.Database;
import lev.gui.LSwingTree;

/**
 *
 * @author Justin Swanson
 */
public class GUI {

    public static MainFrame frame;
    static MainPanel mainPanel;
    static Dimmer dimmer;
    static TopPanel topPanel;
    static ContentPanel contentPanel;
    static SearchBar search;
    static BackTabManager backTabManager;
    static ArticleTooltip tooltip;
    static LSwingTree tree;
    static KIAProgressPane progressPane;

    public static void displayArticles(boolean on) {
	if (contentPanel != null) {
	    contentPanel.displayArticles(on);
	}
    }

    public static void updateContentDisplay() {
	contentPanel.update();
    }

    public static void setArticle(String name) {
	if (Database.hasArticle(name)) {
	    Article a = Database.getArticle(name);
	    setArticle(a);
	}
    }

    public static void setArticle(Article a) {
	if (a.equals(contentPanel.target)) {
	    return;
	}
	if (!search.getText().equals(a.getName())) {
	    search.setText(a.getName());
	}
	hideTooltip();
	backTabManager.remove(a);
	if (contentPanel.target != null) {
	    backTabManager.putOnArticle(contentPanel.target);
	}
	backTabManager.readjust();
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
	Point mouse = MouseInfo.getPointerInfo().getLocation();
	tooltip.setLocation(mouse.x - mainPanel.getLocationOnScreen().x - tooltip.getWidth() / 2, mouse.y - mainPanel.getLocationOnScreen().y + 20);

	// Bump tooltip on screen if it's off
	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		if (tooltip.getY() + tooltip.getHeight() > mainPanel.getHeight()) {
		    tooltip.setLocation(tooltip.getX(), MouseInfo.getPointerInfo().getLocation().y - tooltip.getHeight() - 65);
		}
		if (tooltip.getX() + tooltip.getWidth() > mainPanel.getWidth()) {
		    // If too far right
		    tooltip.setLocation(mainPanel.getWidth() - tooltip.getWidth() - 30, tooltip.getY());
		} else if (tooltip.getX() < 0) {
		    // If too far left
		    tooltip.setLocation(30, tooltip.getY());
		}
	    }
	});
	// Make dimmer and tooltip visible.
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
		tooltip.setVisible(false);
		dimmer.setVisible(false);
	    }
	});
    }

    public static void regenerateTree() {
	tree.setRoot(Database.getTree());
	tree.expandToDepth(2);
    }

    public static int dividerLocation() {
	return mainPanel.split.getDividerLocation();
    }

    public static void finalizeArticles(Collection<Article> articles) {
	search.suggestions(articles);
    }

    public static void loaded() {
	GUI.regenerateTree();
	GUI.updateContentDisplay();
	mainPanel.setVisible(true);
	hideProgress();
    }

    public static void hideProgress() {
	mainPanel.setVisible(true);
	progressPane.setVisible(false);
	dimmer.setVisible(false);
    }

    public static void showProgress() {
	mainPanel.setVisible(false);
	progressPane.setVisible(true);
	dimmer.setVisible(true);
    }

    public static void setProgressMax(final int max, final String title) {
	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		progressPane.reset();
		progressPane.setMax(max, title);
	    }
	});
    }

    public static void updateProcessed(final String title) {
	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		progressPane.processed(title);
	    }
	});
    }

    public static void incrementProgress() {
	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		progressPane.increment();
	    }
	});
    }

    public static void incrementProgress(final String title) {
	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		progressPane.increment(title);
	    }
	});
    }
}
