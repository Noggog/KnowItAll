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

	accept = new LButton("Accept");
	accept.setLocation(getRealWidth() - 10 - accept.getWidth(), getRealHeight() - 10 - accept.getHeight());
	accept.addActionListener(new ActionListener(){

	    @Override
	    public void actionPerformed(ActionEvent e) {
		accept();
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

	filters = new SettingsFilters(getSize());
	display = new SettingsDisplay(getSize());
	JScrollPane displayScroll = new JScrollPane(display);
	displayScroll.setVisible(true);
	displayScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	tabs = new JTabbedPane();
	tabs.setSize(getSize().width - 3, getSize().height - 10);
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
