/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import knowitall.KnowItAll;
import lev.gui.LButton;
import lev.gui.LFrame;
import lev.gui.LScrollPane;

/**
 *
 * @author Justin Swanson
 */
public class SettingsFrame extends LFrame {

    JTabbedPane tabs;
    LButton cancel;
    LButton accept;
    SettingsFilters filters;
    SettingsDisplay display;
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
	setSize(700, 500);
	this.setResizable(false);

	cancel = new LButton("Cancel");
	cancel.setLocation(20, getRealHeight() - 10 - cancel.getHeight());
	cancel.addActionListener(new ActionListener(){

	    @Override
	    public void actionPerformed(ActionEvent e) {
		setVisible(false);
		KnowItAll.save.revertToCancel();
	    }
	});
	getContentPane().add(cancel);

	accept = new LButton("Accept");
	accept.setLocation(cancel.getRight() + 10, cancel.getY());
	accept.addActionListener(new ActionListener(){

	    @Override
	    public void actionPerformed(ActionEvent e) {
		accept();
		setVisible(false);
	    }
	});
	getContentPane().add(accept);

	filters = new SettingsFilters(getSize());
	display = new SettingsDisplay(getSize());
	LScrollPane displayScroll = new LScrollPane(display);
	displayScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	tabs = new JTabbedPane();
	tabs.setSize(getSize().width - 6, getSize().height - 28);
	tabs.setBackground(Color.GRAY);
	tabs.addTab("General", filters);
	tabs.addTab("Colors", displayScroll);
	getContentPane().add(tabs);

	init = true;
    }

    void accept() {
	GUI.setArticleFontColor(display.articleText.getValue());
	GUI.setTooltipFontColor(display.tooltipText.getValue());
	GUI.setLinkFontColor(display.linkText.getValue());
    }
}
