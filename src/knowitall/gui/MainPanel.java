/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import lev.gui.LPanel;
import lev.gui.LScrollPane;
import skyproc.gui.SPDefaultGUI;

/**
 *
 * @author Justin Swanson
 */
public class MainPanel extends LPanel {

    Image logo;
    SearchBar search;
    LScrollPane scroll;
    ContentPanel content;

    MainPanel() {
	super();

	search = new SearchBar();
	search.setLocation(Spacings.mainPanel * 2 + 87, Spacings.mainPanel);
	search.addDocumentListener(new DocumentListener() {

	    @Override
	    public void insertUpdate(DocumentEvent e) {
		content.updateContent(search.getText());
	    }

	    @Override
	    public void removeUpdate(DocumentEvent e) {
		content.updateContent(search.getText());
	    }

	    @Override
	    public void changedUpdate(DocumentEvent e) {
		content.updateContent(search.getText());
	    }
	});
	add(search);

	content = new ContentPanel();

	scroll = new LScrollPane(content);
	scroll.setLocation(0, search.getBottom() + Spacings.mainPanel);
	scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	add(scroll);

	logo = Toolkit.getDefaultToolkit().getImage(SPDefaultGUI.class.getResource("SkyProc Logo Small.png"));

    }

    @Override
    public void remeasure(Dimension size) {
	super.setSize(size);
	search.setSize(getWidth() - Spacings.mainPanel * 3 - 87, search.getHeight());
	scroll.setSize(getWidth(), getHeight() - search.getBottom() - Spacings.mainPanel);
	content.setPreferredSize(new Dimension(scroll.getWidth(),50));
    }

    @Override
    public void paintComponent(Graphics g) {
	g.drawImage(logo, Spacings.mainPanel, Spacings.mainPanel + search.getHeight() / 2 - 12, 87, 29, this); // at location 50,10
    }
}
