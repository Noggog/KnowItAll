/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.util.ArrayList;
import org.apache.ecs.html.*;

/**
 *
 * @author Justin Swanson
 */
public class ArticleHTML {

    Html html;

    public void load(Article a) {
	html = new Html().addElement(new Head());
	Body body = new Body();

//	generateIconTable(a, body);

	// Title
	body.addElement(new Font(6).addElement(new B().addElement(a.getName()))).addElement(new BR());

	generateGrid(a, body, true);

	// Short Content
	if (!a.getShort().equals("")) {
	    body.addElement(new Font(3).addElement(new B()).addElement(a.getShort() + new BR()));
	}

	// Content
	body.addElement(new Font(3).addElement(a.getContent()));
	html.addElement(body);
    }

    public String getHTML() {
	return html.toString();
    }

    public void generateSubCategories(Article a, Body body) {
	ArrayList<String[]> subCategoriesList = a.getSubcategories();
	if (!subCategoriesList.isEmpty()) {
	    for (String[] sc : subCategoriesList) {
		body.addElement(new Font(3).addElement(new B().addElement(sc[0] + " : ") + sc[1] + new BR()));
	    }
	    body.addElement(new BR());
	}
    }

    public void generateGrid(Article a, Body body, boolean single) {
	ArrayList<String[]> gridList = a.getGrid();
	if (!gridList.isEmpty()) {
	    if (single) {
		Table grid = new Table();
		grid.addAttribute("border", "1");
		body.addElement(grid);
		TR gridLabels = new TR();
		grid.addElement(gridLabels);
		TR gridItems = new TR();
		grid.addElement(gridItems);
		for (String[] item : gridList) {
		    gridLabels.addElement(new TD().addElement(new B().addElement(item[0])));
		    gridItems.addElement(new TD().addElement(item[1]));
		}
		body.addElement(new BR());
	    } else {
		for (String[] item : gridList) {
		    Table t = new Table();
		    t.addAttribute("border", "1");
		    t.addAttribute("align", "left");
		    t.addAttribute("valign", "top");
		    
		    TD labelCell = new TD();
		    labelCell.addElement(new B().addElement(item[0]));
		    t.addElement(new TR().addElement(labelCell));
		    
		    TD contentCell = new TD();
		    contentCell.addElement(item[1]);
		    t.addElement(new TR().addElement(contentCell));
		    
		    body.addElement(t);
		}
		body.addElement(new BR());
	    }
	}
    }

    public void generateIconTable(Article a, Body body) {
	Table t = new Table();
	t.addAttribute("border", 1);
	t.addAttribute("width", 5);
	t.addAttribute("align", "right");
	// Page Number and Icon cells
	TR pageIconTR = new TR();
	pageIconTR.addElement(new TD().addElement(a.getPageNum().toString()));
	pageIconTR.addElement(new TD().addElement(a.getIcon()));
	t.addElement(pageIconTR);
	// Vertical image cell
	TR vertImageTR = new TR();
	vertImageTR.addElement(new TD().addElement(a.getImage()).addAttribute("colspan", "2"));
	t.addElement(vertImageTR);
	body.addElement(t);
    }
}
