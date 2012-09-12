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
import org.apache.ecs.html.*;

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
		+ "margin-bottom: 15px;"
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

	    Html html = new Html().addElement(new Head());
	    Body body = new Body();

//	    //Table to hold Page Number, Icon, Vertical Image
//	    Table t = new Table();
//	    t.addAttribute("border", 1);
//	    t.addAttribute("width", 5);
//	    t.addAttribute("align", "right");
//	    // Page Number and Icon cells
//	    TR pageIconTR = new TR();
//	    pageIconTR.addElement(new TD().addElement(a.getPageNum().toString()));
//	    pageIconTR.addElement(new TD().addElement(a.getIcon()));
//	    t.addElement(pageIconTR);
//	    // Vertical image cell
//	    TR vertImageTR = new TR();
//	    vertImageTR.addElement(new TD().addElement(a.getImage()).addAttribute("colspan", "2"));
//	    t.addElement(vertImageTR);
//	    body.addElement(t);

	    body.addElement(new Font(6).addElement(new B().addElement(a.getName()))).addElement(new BR());
	    // SubCategories
	    if (!subCategoriesList.isEmpty()) {
		for (String[] sc : subCategoriesList) {
		    body.addElement(new Font(3).addElement(new B().addElement(sc[0] + " : ") + sc[1] + new BR()));
		}
		body.addElement(new BR());
	    }
	    // Grid
	    if (!gridList.isEmpty()) {
		Table grid = new Table();
		grid.addAttribute("border", "1");
		body.addElement(grid);
		TR gridLabels = new TR();
		grid.addElement(gridLabels);
		TR gridItems = new TR();
		grid.addElement(gridItems);
		for (String[] item : gridList) {
		    gridLabels.addElement(new TD().addElement(item[0]));
		    gridItems.addElement(new TD().addElement(item[1]));
		}
	    }

	    // Short Content
	    if (!a.getShort().equals("")) {
		body.addElement(new Font(3).addElement(new B()).addElement(a.getShort() + new BR()));
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
