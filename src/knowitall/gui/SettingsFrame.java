/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Color;
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
    boolean init = false;

    public SettingsFrame() {
	super("Settings");
    }

    public void open() {
	if (!init) {
	    init();
	}
	setLocation(centerScreen());
	setVisible(true);
    }

    void init() {
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
	tabs.setBackground(Color.GRAY);
	tabs.addTab("Filters", filters);
	getContentPane().add(tabs);
    }
}
