/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTabbedPane;
import knowitall.KnowItAll;
import lev.gui.LButton;
import lev.gui.LFrame;
import lev.gui.LHelpPanel;
import lev.gui.resources.LFonts;
import lev.gui.resources.LImages;

/**
 *
 * @author Justin Swanson
 */
public class SettingsFrame extends LFrame {

    JTabbedPane tabs;
    LButton cancel;
    LButton accept;
    static public LHelpPanel help;
    SettingsFilters filters;
    boolean init = false;

    public SettingsFrame() {
	super("Settings");
    }

    public void open() {
	if (!init) {
	    init();
	}
	KnowItAll.save.saveToCancelSave();
	setLocation(centerScreen());
	setVisible(true);
    }

    void init() {
	setSize(500, 400);
	this.setResizable(false);

	accept = new LButton("Accept");
	accept.setLocation(getRealWidth() - 10 - accept.getWidth(), getRealHeight() - 10 - accept.getHeight());
	accept.addActionListener(new ActionListener(){

	    @Override
	    public void actionPerformed(ActionEvent e) {
		setVisible(false);
	    }
	});
	getContentPane().add(accept);

	cancel = new LButton("Cancel");
	cancel.setLocation(accept.getX() - 10 - cancel.getWidth(), accept.getY());
	cancel.addActionListener(new ActionListener(){

	    @Override
	    public void actionPerformed(ActionEvent e) {
		setVisible(false);
		KnowItAll.save.revertToCancel();
	    }
	});
	getContentPane().add(cancel);

	Rectangle helpA = new Rectangle(200, 30, 300, getHeight());
	help = new LHelpPanel(helpA, LFonts.MyriadProBold(25), Color.BLACK, Color.DARK_GRAY, LImages.arrow(true, true), 0);
	help.setTitleOffset(3);
	help.setXOffsets(10, 21);
	getContentPane().add(help);

	filters = new SettingsFilters();

	tabs = new JTabbedPane();
	tabs.setSize(getSize());
	tabs.setBackground(Color.GRAY);
	tabs.addTab("All", filters);
	getContentPane().add(tabs);

	init = true;
    }
}
