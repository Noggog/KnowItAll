/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collection;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import knowitall.Article;
import knowitall.Database;
import knowitall.KIASave;
import knowitall.KIASave.Settings;
import knowitall.KnowItAll;
import lev.Ln;
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
    static OpenPackage openPackage = new OpenPackage();
    static SettingsFrame settingsFrame = new SettingsFrame();
    static boolean defaultPicker = false;
    // State
    static public boolean mainFrameFocus = true;

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
	if (!Ln.containsIgnoreCase(a.getNames(), search.getText())) {
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

    public static void reloadArticle() {
	contentPanel.updateContent(contentPanel.target);
    }

    public static void repaintContent() {
	contentPanel.repaint();
    }

    public static void setTooltip(String s) {
	if (Database.hasArticle(s)) {
	    setTooltip(Database.getArticle(s));
	}
    }

    public static void setTooltip(Article a) {
	if (!KnowItAll.save.getBool(Settings.ToolTipsOn)) {
	    return;
	}
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
		tooltip.setVisible(true);
		dimmer.setVisible(true);
	    }
	});
    }

    public static void setArticleFontColor(Color c) {
	for (ArticleDisplay a : contentPanel.displays) {
	    a.setBodyFontColor(c);
	}
    }
    
    public static void setTooltipFontColor(Color c) {
	tooltip.setBodyFontColor(c);
    }

    public static void hideTooltip() {
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		tooltip.setVisible(false);
		dimmer.setVisible(false);
	    }
	});
    }

    public static void regenerateTree() {
	tree.setRoot(Database.getTree());
	if (KnowItAll.save.getBool(Settings.MergeSources)) {
	    tree.expandToDepth(1);
	} else {
	    tree.expandToDepth(2);
	}
    }

    public static int dividerLocation() {
	return mainPanel.split.getDividerLocation();
    }

    public static void finalizeArticles(Collection<Article> articles) {
	search.suggestions(articles);
    }

    public static void openPackagePicker() {
	if (defaultPicker) {
	    File lastPackage = new File(KnowItAll.save.getStr(KIASave.Settings.LastPackage));

	    if (lastPackage.getPath().equals(".")) {
		File packages = new File(lastPackage.getPath() + "/Packages");
		if (packages.isDirectory()) {
		    lastPackage = packages;
		}
	    }

	    JFileChooser fd = new JFileChooser(lastPackage);
	    fd.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    if (fd.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		KnowItAll.save.setStr(Settings.LastPackage, fd.getSelectedFile().getPath());
		loadPackages();
	    }
	} else {
	    openPackage.setVisible(true);
	    openPackage.setLocation(openPackage.centerScreen());
	    openPackage.loadPackageList();
	}
    }

    public static void openPackage(File p) {
	KnowItAll.save.setStr(Settings.LastPackage, p.getPath());
	Database.loadLooseFiles();
    }

    public static void openSettingsFrame() {
	settingsFrame.open();
    }

    public static void setBackground(final File f) {
	SwingWorker backgroundLoad = new SwingWorker() {
	    @Override
	    protected Object doInBackground() throws Exception {
		frame.getBackgroundPane().setImage(f);
		return null;
	    }
	};
	backgroundLoad.execute();
    }

    public static BufferedImage getBackground() {
	return frame.getBackgroundPane().getImage();
    }

    public static void loadPackages() {
	File target = new File(KnowItAll.save.getStr(Settings.LastPackage));
	if (!target.getPath().equals(".") && target.isDirectory()) {
	    progressShow();
	    Database.loadLooseFiles();
	}
    }

    public static void loaded() {
	GUI.regenerateTree();
	GUI.updateContentDisplay();
	mainPanel.setVisible(true);
	progressHide();
    }

    public static void progressHide() {
	mainPanel.setVisible(true);
	progressPane.setVisible(false);
	dimmer.setVisible(false);
    }

    public static void progressShow() {
	mainPanel.setVisible(false);
	progressPane.setVisible(true);
	dimmer.setVisible(true);
    }

    public static void progressSetMax(final int max) {
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		progressPane.reset();
		progressPane.setMax(max);
	    }
	});
    }

    public static void progressSetTitle(final String title) {
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		progressPane.setTitle(title);
	    }
	});
    }

    public static void progressProcessed(final String title) {
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		progressPane.processed(title);
	    }
	});
    }

    public static void progressIncrement() {
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		progressPane.increment();
	    }
	});
    }

    public static void progressIncrement(final String title) {
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		progressPane.increment(title);
	    }
	});
    }

    public static void progressSetValue(final int value) {
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		progressPane.setValue(value);
	    }
	});
    }

    public static void progressSetValueRemaining(final int value) {
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		progressPane.setValue(progressPane.progress.getMaximum() - value);
	    }
	});
    }
}
