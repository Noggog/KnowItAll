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
public class SettingsColors extends SettingsPanel {

    LColorSetting articleBackground;
    LColorSetting articleText;
    LSlider articleAlpha;
    LImagePane example;
    ArticleDisplay article;

    public SettingsColors (Dimension size) {
	setSize(size);
	
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
		article.repaint();
	    }
	});
	place(articleText);

	articleAlpha = slider("Article Transparency", Settings.ArticleTrans, 0, 100);
	articleAlpha.addChangeListener(new ChangeListener() {

	    @Override
	    public void stateChanged(ChangeEvent e) {
		article.repaint();
	    }
	});
	place(articleAlpha);
	
	example = new LImagePane(GUI.getBackground());
	example.setSize(350, getHeight());
	example.setLocation(getWidth() - example.getWidth(), 0);
	add(example);
	
	article = new ArticleDisplay();
	article.load(Database.getArticle());
	article.setSize(600, 150);
	article.setLocation(20, 20);
	article.setVisible(true);
	example.add(article);
    }
}
