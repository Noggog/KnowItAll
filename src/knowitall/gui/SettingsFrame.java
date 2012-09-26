/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import javax.swing.JTabbedPane;
import lev.gui.LButton;
import lev.gui.LFrame;

/**
 *
 * @author Justin Swanson
 */
public class SettingsFrame extends LFrame {

    JTabbedPane tabs;
    LButton cancel;
    LButton accept;
    SettingsFilters filters = new SettingsFilters();

    public SettingsFrame() {
	super("Settings");
	setSize(500, 400);
	this.setResizable(false);

	accept = new LButton("Accept");
	accept.setLocation(getRealWidth() - 10 - accept.getWidth(), getRealHeight() - 10 - accept.getHeight());
	getContentPane().add(accept);

	cancel = new LButton("Cancel");
	cancel.setLocation(accept.getX() - 10 - cancel.getWidth(), accept.getY());
	getContentPane().add(cancel);

	tabs = new JTabbedPane();
	tabs.setSize(getSize());
	tabs.addTab("Filters", filters);
	getContentPane().add(tabs);
    }

    public void open() {
	filters.open();
	setLocation(centerScreen());
	setVisible(true);
    }
}
