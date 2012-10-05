/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Color;
import java.awt.Dimension;
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
    LColorSetting treeText;
    LSlider treeAlpha;
    
    LImagePane example;
    ArticleDisplay article;
    ArticleTooltip tooltip;
    LTree tree;

    public SettingsDisplay (Dimension size) {
	setSize(size);
	setPreferredSize(new Dimension(size.width - 21, size.height));

	last.x = 200;
	last.y += 15;
	align(Align.Center);

	articles = new LLabel("Articles", headerFont, Color.BLACK);
	place(articles);
	
	articleBackground = color("Background", Settings.ArticleBack);
	articleBackground.addActionListener(new Runnable(){

	    @Override
	    public void run() {
		article.repaint();
	    }
	});
	place(articleBackground);

	articleText = color("Text", Settings.ArticleFont);
	articleText.addActionListener(new Runnable(){

	    @Override
	    public void run() {
		article.setBodyFontColor(articleText.getValue());
		article.repaint();
	    }
	});
	place(articleText);

	articleLinkText = color("Links", Settings.ArticleLinkFont);
	articleLinkText.addActionListener(new Runnable(){

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
	tooltipBackground.addActionListener(new Runnable(){

	    @Override
	    public void run() {
		tooltip.repaint();
	    }
	});
	place(tooltipBackground);
	
	tooltipText = color("Text", Settings.ToolFont);
	tooltipText.addActionListener(new Runnable(){

	    @Override
	    public void run() {
		tooltip.setBodyFontColor(tooltipText.getValue());
		tooltip.repaint();
	    }
	});
	place(tooltipText);
	
	tooltipLinkText = color("Link Text", Settings.ToolLinkFont);
	tooltipLinkText.addActionListener(new Runnable(){

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
	treeBackground.addActionListener(new Runnable(){

	    @Override
	    public void run() {
		tooltip.setBodyFontColor(tooltipText.getValue());
		tooltip.repaint();
	    }
	});
	place(treeBackground);
	
	treeText = color("Text", Settings.TreeFont);
	treeText.addActionListener(new Runnable(){

	    @Override
	    public void run() {
		tooltip.setBodyFontColor(tooltipText.getValue());
		tooltip.repaint();
	    }
	});
	place(treeText);

	treeAlpha = slider("Transparency", Settings.TreeTrans, 0, 100);
	treeAlpha.addChangeListener(new ChangeListener() {

	    @Override
	    public void stateChanged(ChangeEvent e) {
		article.repaint();
	    }
	});
	place(treeAlpha);
	
	// Example
	example = new LImagePane(GUI.getBackground());
	example.setSize(350, getHeight());
	example.setLocation(getWidth() - example.getWidth(), 0);
	add(example);

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
	
	tree = new LTree();
	tree.setRoot(Database.getTree());
	tree.expand(true);
	tree.setLocation(20, tooltip.getY() + tooltip.getHeight() + 20);
	tree.setSize(400, 135);
	example.add(tree);
    }
}
