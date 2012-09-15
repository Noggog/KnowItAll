/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import knowitall.Database;
import knowitall.Debug;
import lev.gui.LImagePane;
import lev.gui.LPanel;
import lev.gui.LScrollPane;
import skyproc.gui.SPDefaultGUI;

/**
 *
 * @author Justin Swanson
 */
public class MainPanel extends LPanel {

    LImagePane logo;
    SearchBar search;
    LScrollPane scroll;
    ContentPanel content;

    MainPanel() {
	super();

	content = new ContentPanel();
	GUI.contentPanel = content;

	search = new SearchBar(content);
	search.setLocation(Spacings.mainPanel * 2 + 87, Spacings.mainPanel);
	add(search);

	scroll = new LScrollPane(content);
	scroll.setLocation(0, search.getBottom() + Spacings.mainPanel);
	scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	scroll.setBorder(BorderFactory.createEmptyBorder());
	add(scroll);

	try {
	    logo = new LImagePane(SPDefaultGUI.class.getResource("SkyProc Logo Small.png"));
	    logo.addMouseListener(new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent e) {
		    Database.reloadArticles(new UpdateContent());
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	    });
	    logo.setLocation(Spacings.mainPanel, search.getY() + search.getHeight() / 2 - logo.getHeight() / 2 + 4);
	    add(logo);
	} catch (IOException ex) {
	    Debug.log.logException(ex);
	}

    }

    public class UpdateContent implements Runnable {

	@Override
	public void run() {
	    GUI.updateContentDisplay();
	}
    }

    @Override
    public void remeasure(final Dimension size) {
	super.setSize(size);
	search.setSize(getWidth() - Spacings.mainPanel * 3 - 87, search.getHeight());
	scroll.setSize(getWidth(), getHeight() - search.getBottom() - Spacings.mainPanel);
	content.setPreferredSize(new Dimension(scroll.getWidth(), 50));
	content.remeasure(size);
	SwingUtilities.invokeLater(
		new Runnable() {
		    @Override
		    public void run() {
		    }
		});
    }
}
