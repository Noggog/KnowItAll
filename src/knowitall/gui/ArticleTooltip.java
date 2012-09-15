/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Dimension;
import knowitall.Article;
import lev.gui.LHTMLPane;
import lev.gui.LPanel;

/**
 *
 * @author Justin Swanson
 */
public class ArticleTooltip extends LPanel {

    LHTMLPane pane;

    ArticleTooltip() {
	pane = new LHTMLPane();
	add(pane);
	setVisible(false);
    }

    public void load(Article a) {
	setVisible(false);
	if (a != null) {
	    pane.setText(a.getHTML(false));
	}
    }

    @Override
    public void setSize(Dimension size) {
	setSize(size.width);
    }

    @Override
    public void setSize(int x , int y) {
	setSize(x);
    }

    public void setSize(int x) {
	pane.setSize(x - 2);
	super.setSize(x, pane.getBottom());
    }
}
