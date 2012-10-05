/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Dimension;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import knowitall.Database;
import knowitall.KIASave.Settings;
import lev.gui.LColorSetting;
import lev.gui.LImagePane;
import lev.gui.LSlider;

/**
 *
 * @author Justin Swanson
 */
public class SettingsDisplay extends SettingsPanel {

    LColorSetting articleBackground;
    LColorSetting articleText;
    LColorSetting linkText;
    LSlider articleAlpha;
    
    LColorSetting tooltipBackground;
    LColorSetting tooltipText;
    LSlider tooltipAlpha;
    
    LImagePane example;
    ArticleDisplay article;
    ArticleTooltip tooltip;

    public SettingsDisplay (Dimension size) {
	setSize(size);
	setPreferredSize(new Dimension(size.width - 24, size.height));

	last.x = 200;
	last.y += 10;
	align(Align.Center);

	articleBackground = color("Article Background", Settings.ArticleBack);
	articleBackground.addActionListener(new Runnable(){

	    @Override
	    public void run() {
		article.repaint();
	    }
	});
	place(articleBackground);

	articleText = color("Article Text", Settings.ArticleFont);
	articleText.addActionListener(new Runnable(){

	    @Override
	    public void run() {
		article.setBodyFontColor(articleText.getValue());
		article.repaint();
	    }
	});
	place(articleText);

	linkText = color("Links", Settings.LinkFont);
	linkText.addActionListener(new Runnable(){

	    @Override
	    public void run() {
		article.setLinkFontColor(linkText.getValue());
		tooltip.setLinkFontColor(linkText.getValue());
		article.repaint();
		tooltip.repaint();
	    }
	});
	place(linkText);

	articleAlpha = slider("Article Transparency", Settings.ArticleTrans, 0, 100);
	articleAlpha.addChangeListener(new ChangeListener() {

	    @Override
	    public void stateChanged(ChangeEvent e) {
		article.repaint();
	    }
	});
	place(articleAlpha);

	tooltipBackground = color("Tooltip Background", Settings.ToolBack);
	tooltipBackground.addActionListener(new Runnable(){

	    @Override
	    public void run() {
		tooltip.repaint();
	    }
	});
	place(tooltipBackground);
	
	tooltipText = color("Tooltip Text", Settings.ToolFont);
	tooltipText.addActionListener(new Runnable(){

	    @Override
	    public void run() {
		tooltip.setBodyFontColor(tooltipText.getValue());
		tooltip.repaint();
	    }
	});
	place(tooltipText);
	
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
	tooltip.setSize(400, 150);
	tooltip.setLocation(20, article.getY() + article.getHeight() + 20);
	tooltip.setVisible(true);
	example.add(tooltip);
    }
}
