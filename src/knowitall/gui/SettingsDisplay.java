/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import knowitall.Database;
import knowitall.KIASave.Settings;
import lev.gui.*;

/**
 *
 * @author Justin Swanson
 */
public class SettingsDisplay extends SettingsPanel {

    LLabel articles;
    LColorSetting articleBackground;
    LColorSetting articleText;
    LColorSetting articleLinkText;
    LSlider articleAlpha;
    LLabel tooltips;
    LColorSetting tooltipBackground;
    LColorSetting tooltipText;
    LColorSetting tooltipLinkText;
    LSlider tooltipAlpha;
    LLabel treeLabel;
    LColorSetting treeBackground;
    LColorSetting treeBackgroundSel;
    LColorSetting treeText;
    LColorSetting treeTextSel;
    LSlider treeAlpha;
    LImagePane example;
    LLabel dimmerLabel;
    LSlider dimmerAlpha;
    ArticleDisplay article;
    ArticleTooltip tooltip;
    LTree tree;
    Dimmer dimmer;

    public SettingsDisplay(Dimension size) {
	super(size);

	last.x = 160;
	last.y += 15;
	align(Align.Center);

	articles = new LLabel("Articles", headerFont, Color.BLACK);
	place(articles);

	articleBackground = color("Background", Settings.ArticleBack);
	articleBackground.addActionListener(new Runnable() {
	    @Override
	    public void run() {
		article.repaint();
	    }
	});
	place(articleBackground);

	articleText = color("Text", Settings.ArticleFont);
	articleText.addActionListener(new Runnable() {
	    @Override
	    public void run() {
		article.setBodyFontColor(articleText.getValue());
		article.repaint();
	    }
	});
	place(articleText);

	articleLinkText = color("Links", Settings.ArticleLinkFont);
	articleLinkText.addActionListener(new Runnable() {
	    @Override
	    public void run() {
		article.setLinkFontColor(articleLinkText.getValue());
		article.repaint();
	    }
	});
	place(articleLinkText);

	articleAlpha = slider("Transparency", Settings.ArticleTrans, 0, 100);
	articleAlpha.addChangeListener(new ChangeListener() {
	    @Override
	    public void stateChanged(ChangeEvent e) {
		article.repaint();
	    }
	});
	place(articleAlpha);

	last.y += 32;

	tooltips = new LLabel("Tooltips", headerFont, Color.BLACK);
	place(tooltips);

	tooltipBackground = color("Background", Settings.ToolBack);
	tooltipBackground.addActionListener(new Runnable() {
	    @Override
	    public void run() {
		tooltip.repaint();
	    }
	});
	place(tooltipBackground);

	tooltipText = color("Text", Settings.ToolFont);
	tooltipText.addActionListener(new Runnable() {
	    @Override
	    public void run() {
		tooltip.setBodyFontColor(tooltipText.getValue());
		tooltip.repaint();
	    }
	});
	place(tooltipText);

	tooltipLinkText = color("Link Text", Settings.ToolLinkFont);
	tooltipLinkText.addActionListener(new Runnable() {
	    @Override
	    public void run() {
		tooltip.setLinkFontColor(tooltipLinkText.getValue());
		tooltip.repaint();
	    }
	});
	place(tooltipLinkText);

	last.y += 45;

	treeLabel = new LLabel("Tree", headerFont, Color.BLACK);
	place(treeLabel);

	treeBackground = color("Background", Settings.TreeBack);
	treeBackground.addActionListener(new Runnable() {
	    @Override
	    public void run() {
		tree.setBackground(treeBackground.getValue(), false);
		tree.repaint();
	    }
	});
	place(treeBackground);

//	treeBackgroundSel = color("Background Selected", Settings.TreeBackSelected);
//	treeBackgroundSel.addActionListener(new Runnable() {
//
//	    @Override
//	    public void run() {
//		tree.setBackground(treeBackgroundSel.getValue(), true);
//		tree.repaint();
//	    }
//	});
//	place(treeBackgroundSel);

	treeText = color("Text", Settings.TreeFont);
	treeText.addActionListener(new Runnable() {
	    @Override
	    public void run() {
		tree.setForeground(treeText.getValue(), false);
		tree.repaint();
	    }
	});
	place(treeText);

	treeTextSel = color("Text Selected", Settings.TreeFontSelected);
	treeTextSel.addActionListener(new Runnable() {
	    @Override
	    public void run() {
		tree.setForeground(treeTextSel.getValue(), true);
		tree.repaint();
	    }
	});
	place(treeTextSel);

	treeAlpha = slider("Transparency", Settings.TreeTrans, 0, 100);
	treeAlpha.addChangeListener(new ChangeListener() {
	    @Override
	    public void stateChanged(ChangeEvent e) {
		tree.repaint();
	    }
	});
	place(treeAlpha);

	last.y += 45;

	dimmerLabel = new LLabel("Dimmer", headerFont, Color.BLACK);
	place(dimmerLabel);

	dimmerAlpha = slider("Transparency", Settings.DimmerTrans, 0, 100);
	dimmerAlpha.addChangeListener(new ChangeListener() {
	    @Override
	    public void stateChanged(ChangeEvent e) {
		dimmer.setVisibleOverride(false);
		dimmer.repaint();
		dimmer.setVisibleOverride(true);
	    }
	});
	place(dimmerAlpha);

	// Example
	example = new LImagePane(GUI.getBackground());
	example.setSize(350, getHeight());
	example.setLocation(getWidth() - example.getWidth(), 0);
	pane.add(example);

	article = new ArticleDisplay();
	article.load(Database.getDisplayArticle());
	article.setSize(400, 150);
	article.setLocation(20, 20);
	article.setVisible(true);
	example.add(article);

	tooltip = new ArticleTooltip();
	tooltip.load(Database.getDisplayArticle());
	tooltip.setSize(400, 135);
	tooltip.setRealSize(400, 135);
	tooltip.setLocation(20, article.getY() + article.getHeight() + 20);
	tooltip.setVisible(true);
	example.add(tooltip);

	tree = new ArticleTree();
	tree.setRoot(Database.getTree());
	tree.expand(true);
	tree.setLocation(20, tooltip.getY() + tooltip.getHeight() + 20);
	tree.setSize(400, 165);
	example.add(tree);

	dimmer = new Dimmer();
	dimmer.setLocation(0, tree.getY() + tree.getHeight() + 20);
	dimmer.setSize(400, 100);
	dimmer.setVisibleOverride(true);
	example.add(dimmer);

	pane.setSize(getWidth(), last.y + 20);
	pane.setPreferredSize(pane.getSize());
	example.setSize(350, pane.getHeight());

    }
}
