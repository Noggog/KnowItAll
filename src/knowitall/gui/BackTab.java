/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import knowitall.Article;
import lev.gui.LLabel;
import lev.gui.LPanel;
import lev.gui.resources.LFonts;

/**
 *
 * @author Justin Swanson
 */
public class BackTab extends LPanel {

    static public int height = 30;
    static public int margin = 15;
    static Color active = Color.WHITE;
    static Color disabled = Color.GRAY;
    LLabel title;
    Article a;

    BackTab() {
	super();
	title = new LLabel("Z", LFonts.MyriadProBold(16), disabled);
	title.setLocation(margin, height / 2 - title.getHeight() / 2);
	this.addMouseListener(new BackTabListener());
	add(title);
	setVisible(false);
    }

    public void load(Article a) {
	if (a != null) {
	    this.a = a;
	    title.setText(a.getName());
	    setSize(title.getWidth() + margin, height);
	    setVisible(true);
	} else {
	    setVisible(false);
	}
    }

    class BackTabListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
	    GUI.setArticle(a);
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	    title.setFontColor(active);
	}

	@Override
	public void mouseExited(MouseEvent e) {
	    title.setFontColor(disabled);
	}

    }
}
