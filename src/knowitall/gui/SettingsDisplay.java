/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JSplitPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import knowitall.Database;
import knowitall.KIASave.Settings;
import lev.gui.LColorSetting;
import lev.gui.LImagePane;
import lev.gui.LLabel;
import lev.gui.LSlider;

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
    LLabel dividerLabel;
    LColorSetting dividerColor;
    LSlider dividerAlpha;
    // Examples
    ArticleDisplay article;
    ArticleTooltip tooltip;
    ArticleTree tree;
    Dimmer dimmer;
    JSplitPane divider;

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
		GUI.repaintContent();
	    }
	});
	place(articleBackground);

	articleText = color("Text", Settings.ArticleFont);
	articleText.addActionListener(new Runnable() {
	    @Override
	    public void run() {
		article.setBodyFontColor(articleText.getValue());
		article.repaint();
		GUI.repaintContent();
	    }
	});
	place(articleText);

	articleLinkText = color("Links", Settings.ArticleLinkFont);
	articleLinkText.addActionListener(new Runnable() {
	    @Override
	    public void run() {
		article.setLinkFontColor(articleLinkText.getValue());
		article.repaint();
		GUI.repaintContent();
	    }
	});
	place(articleLinkText);

	articleAlpha = slider("Transparency", Settings.ArticleTrans, 0, 100);
	articleAlpha.addChangeListener(new ChangeListener() {
	    @Override
	    public void stateChanged(ChangeEvent e) {
		article.repaint();
		GUI.repaintContent();
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
		GUI.repaintContent();
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
		GUI.repaintContent();
	    }
	});
	place(treeText);

	treeTextSel = color("Text Selected", Settings.TreeFontSelected);
	treeTextSel.addActionListener(new Runnable() {
	    @Override
	    public void run() {
		tree.setForeground(treeTextSel.getValue(), true);
		tree.repaint();
		GUI.repaintContent();
	    }
	});
	place(treeTextSel);

	treeAlpha = slider("Transparency", Settings.TreeTrans, 0, 100);
	treeAlpha.addChangeListener(new ChangeListener() {
	    @Override
	    public void stateChanged(ChangeEvent e) {
		tree.repaint();
		GUI.repaintContent();
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

	last.y += 45;

	dividerLabel = new LLabel("Divider", headerFont, Color.BLACK);
	place(dividerLabel);

	dividerColor = color("Background", Settings.DividerColor);
	dividerColor.addActionListener(new Runnable() {
	    @Override
	    public void run() {
		divider.repaint();
		GUI.repaintContent();
	    }
	});
	place(dividerColor);

	dividerAlpha = slider("Transparency", Settings.DividerTrans, 0, 100);
	dividerAlpha.addChangeListener(new ChangeListener() {
	    @Override
	    public void stateChanged(ChangeEvent e) {
		divider.repaint();
		GUI.repaintContent();
	    }
	});
	place(dividerAlpha);

	// Example
	example = new LImagePane();
	setBackgroundImage(GUI.getBackground());
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

	divider = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	divider.setSize(example.getWidth(), 30);
	divider.setLocation(0, dimmer.getY() + dimmer.getHeight() + 58);
	example.add(divider);

	pane.setSize(getWidth(), last.y + 20);
	pane.setPreferredSize(pane.getSize());
	example.setSize(350, pane.getHeight());

    }

    public void setBackgroundImage(BufferedImage img) {
	example.setImage(img);
	example.setSize(350, getHeight());
    }
    
    @Override
    public void updateColors() {
	article.fetchBodyFontColor();
	article.fetchLinkFontColor();
	tooltip.fetchBodyFontColor();
	tooltip.fetchLinkFontColor();
	tree.fetchColors();
    }
}
