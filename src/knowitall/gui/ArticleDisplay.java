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
    LTextArea shortContent;
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

	shortContent = new LTextArea(Color.BLACK);
	shortContent.setFont(LFonts.MyriadProBold(15));
	shortContent.setLineWrap(true);
	shortContent.setWrapStyleWord(true);
	add(shortContent);

	content = new LTextArea(Color.BLACK);
	content.setLineWrap(true);
	content.setWrapStyleWord(true);
	add(content);
	setOpaque(true);
    }

    public void load(Article a) {
	if (a == null) {
	    setVisible(false);
	} else {
	    setVisible(true);
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
	    shortContent.setText(a.getShort());
	    shortContent.setVisible(!shortContent.getText().equals(""));
	}
    }

    @Override
    public void remeasure(Dimension size) {
	if (isVisible()) {
	    int y = title.getBottom();
	    y = position(subCategories, size, y);
	    y = position(shortContent, size, y);
	    position(content, size, y);
	    setSize(size.width, content.getY() + content.getHeight() + Spacings.articleDispay);
	} else {
	    setSize(1, 1);
	}
    }

    int position(LTextArea area, Dimension size, int y) {
	if (area.isVisible()) {
	    area.setLocation(Spacings.articleDispay, y);
	    area.setSize(size.width - 2 * Spacings.articleDispay, 30);
	    area.setSize(area.getPreferredSize());
	    y = area.getBottom();
	}
	return y;
    }
}
