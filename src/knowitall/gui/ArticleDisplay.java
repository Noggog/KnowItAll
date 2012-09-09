/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import knowitall.Article;
import lev.gui.LLabel;
import lev.gui.LPanel;
import lev.gui.LTextArea;
import lev.gui.resources.LFonts;

/**
 *
 * @author Justin Swanson
 */
public class ArticleDisplay extends LPanel {

    Article article;
    LTextArea content;
    LTextArea subCategories;
    LLabel title;

    public ArticleDisplay() {
	title = new LLabel("Temp", LFonts.MyriadProBold(30), Color.BLACK);
	title.setLocation(Spacings.articleDispay, Spacings.articleDispay);
	add(title);

	subCategories = new LTextArea(Color.BLACK);
	subCategories.setLocation(Spacings.articleDispay, title.getBottom());
	subCategories.setLineWrap(true);
	subCategories.setWrapStyleWord(true);
	subCategories.setFont(LFonts.MyriadProBold(15));
	add(subCategories);

	content = new LTextArea(Color.BLACK);
	content.setLineWrap(true);
	content.setWrapStyleWord(true);
	add(content);
	setOpaque(true);
    }

    public void load(Article a) {
	article = a;
	title.setText(a.getName());
	content.setText(a.getContent());
	String subCategoriesStr = "";
	ArrayList<String[]> subCategoriesList = a.getSubcategories();
	for (String[] subCategory : subCategoriesList) {
	    subCategoriesStr += subCategory[0] + " : " + subCategory[1] + "\n";
	}
	subCategories.setText(subCategoriesStr);
	subCategories.setVisible(!subCategories.getText().equals(""));
    }

    @Override
    public void remeasure(Dimension size) {
	if (subCategories.isVisible()) {
	    subCategories.setSize(size.width - 2 * Spacings.articleDispay, 30);
	    subCategories.setSize(subCategories.getPreferredSize());
	    content.setLocation(Spacings.articleDispay, subCategories.getBottom());
	} else {
	    content.setLocation(Spacings.articleDispay, title.getBottom());
	}
	content.setSize(size.width - 2 * Spacings.articleDispay, 30);
	content.setSize(content.getPreferredSize());
	setSize(size.width, content.getY() + content.getHeight() + Spacings.articleDispay);
    }
}
