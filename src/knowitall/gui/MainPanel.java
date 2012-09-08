/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import skyproc.gui.SPDefaultGUI;

/**
 *
 * @author Justin Swanson
 */
public class MainPanel extends PanelTemplate {

    Image logo;
    SearchBar search;

    static int spacing = 10;

    MainPanel() {
	super();
	setLayout(null);

	search = new SearchBar();
	add(search);

	logo = Toolkit.getDefaultToolkit().getImage(SPDefaultGUI.class.getResource("SkyProc Logo Small.png"));

	setOpaque(false);
	setVisible(true);
    }

    @Override
    public void remeasure(Dimension size) {
	super.setSize(size);
	search.setSize(getWidth() - spacing * 3 - 87, search.getHeight());
	search.setLocation(spacing * 2 + 87, spacing);
    }

    @Override
    public void paintComponent(Graphics g) {
	g.drawImage(logo, spacing, spacing + search.getHeight() / 2 - 12, 87, 29, this); // at location 50,10
    }
}
