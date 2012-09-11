/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.text.html.StyleSheet;
import knowitall.Article;
import lev.gui.LHTMLPane;
import lev.gui.LPanel;
import lev.gui.LTextArea;
import org.apache.ecs.html.Body;
import org.apache.ecs.html.Font;
import org.apache.ecs.html.H1;
import org.apache.ecs.html.Head;
import org.apache.ecs.html.Html;
import org.apache.ecs.html.Title;

/**
 *
 * @author Justin Swanson
 */
public class ArticleDisplay extends LPanel {

    Article article;
    LHTMLPane htmlContent;

    public ArticleDisplay() {
	htmlContent = new LHTMLPane();
	htmlContent.setLocation(0, 0);
	htmlContent.setVisible(true);
	StyleSheet ss = htmlContent.getStyleSheet();
	ss.addRule("body {"
		+ "margin-top: 0px;"
		+ "margin-right: 10px;"
		+ "margin-bottom: 20px;"
		+ "margin-left: 10px;}");
	add(htmlContent);
	setOpaque(true);
    }

    public void load(Article a) {
	if (a == null) {
	    setVisible(false);
	} else {
	    setVisible(true);
	    article = a;
	    ArrayList<String[]> subCategoriesList = a.getSubcategories();
	    ArrayList<String[]> gridList = a.getGrid();

	    Html html = new Html()
              .addElement(new Head());
	    Body body = new Body()
		    //Article Name
              .addElement(new H1(article.getName()));
	    // SubCategories
	    for (String[] sc : subCategoriesList) {
              body.addElement(new Font(3).addElement("<b>" + sc[0] + "</b> : " + sc[1] + "<br>"));
	    }
	    if (!subCategoriesList.isEmpty()) {
		body.addElement("<br>");
	    }
	    // Grid
	    for (String[] sc : gridList) {
              body.addElement(new Font(3).addElement("<b>" + sc[0] + "</b> : " + sc[1] + "<br>"));
	    }
	    if (!gridList.isEmpty()) {
		body.addElement("<br>");
	    }

	    // Short Content
	    if (!a.getShort().equals("")) {
		body.addElement(new Font(3).addElement("<b>" + a.getShort() + "</b><br>"));
	    }
	    // Content
	    body.addElement(new Font(3).addElement(a.getContent()));
		    html.addElement(body);
	    htmlContent.setText(html.toString());
	}
    }

    @Override
    public void remeasure(Dimension size) {
	if (isVisible()) {
	    htmlContent.setSize(size.width - 2);
	    setSize(size.width, htmlContent.getBottom());
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
