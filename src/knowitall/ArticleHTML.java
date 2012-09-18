/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.util.Map;
import org.apache.ecs.html.B;
import org.apache.ecs.html.BR;
import org.apache.ecs.html.Body;
import org.apache.ecs.html.Font;
import org.apache.ecs.html.Head;
import org.apache.ecs.html.Html;
import org.apache.ecs.html.TD;
import org.apache.ecs.html.TR;
import org.apache.ecs.html.Table;

/**
 *
 * @author Justin Swanson
 */
public class ArticleHTML {

    static int smallFontSize = 3;

    public static String load(Article a, boolean full) {
	Html html = new Html().addElement(new Head());
	Body body = new Body();

//	generateIconTable(a, body);

	// Title
	body.addElement(new Font(6).addElement(new B().addElement(a.getName()))).addElement(new BR());

	generateSubCategories(a, body);

	generateGrid(a, body, true);

	// Short Content
	if (!a.getShort().equals("")) {
	    body.addElement(new Font(smallFontSize).addElement(new B().addElement(a.getShort())).addElement(new BR()));
	}

	// Content
	if (full || a.getShort().equals("")) {
	    body.addElement(new Font(smallFontSize).addElement(a.getContent()));
	}
	html.addElement(body);

	return html.toString();
    }

    static public void generateSubCategories(Article a, Body body) {
	Map<String, LinkString> subCategoriesMap = a.getSubcategories();
	if (!subCategoriesMap.isEmpty()) {
	    for (String subCat : subCategoriesMap.keySet()) {
		String value = subCategoriesMap.get(subCat).toString();
		body.addElement(new Font(smallFontSize).addElement(new B().addElement(subCat + " : ") + value + new BR()));
	    }
	    body.addElement(new BR());
	}
    }

    static public void generateGrid(Article a, Body body, boolean single) {
	Map<String, LinkString> gridMap = a.getGrid();
	if (!gridMap.isEmpty()) {
	    if (single) {
		Table grid = new Table();
		grid.addAttribute("border", "1");
		body.addElement(new Font(smallFontSize).addElement(grid));
		TR gridLabels = new TR();
		grid.addElement(gridLabels);
		TR gridItems = new TR();
		grid.addElement(gridItems);
		for (String key : gridMap.keySet()) {
		    gridLabels.addElement(new TD().addElement(new B().addElement(key)));
		    gridItems.addElement(new TD().addElement(gridMap.get(key).toString()));
		}
		body.addElement(new BR());
	    } else {
		boolean first = true;
		for (String key : gridMap.keySet()) {
		    Table t = new Table();
		    t.addAttribute("border", "1");
		    if (first) {
			first = false;
			t.addAttribute("style", "float:left");
		    }

		    TD labelCell = new TD();
		    labelCell.addElement(new B().addElement(key));
		    t.addElement(new TR().addElement(labelCell));

		    TD contentCell = new TD();
		    contentCell.addElement(gridMap.get(key).toString());
		    t.addElement(new TR().addElement(contentCell));

		    body.addElement(t);
		}
		body.addElement(new BR());
	    }
	}
    }

    static public void generateIconTable(Article a, Body body) {
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

    static public String linkTo(Article a, String text) {
	return "<a href=\"" + a.getName().toUpperCase() + "\" title=\"" + a.getName().toUpperCase() + "\">" + text + "</a>";
    }
}
