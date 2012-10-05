/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import knowitall.KnowItAll;
import lev.gui.LButton;
import lev.gui.LFrame;
import lev.gui.LPanel;
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

	accept = new LButton("Accept");
	accept.setLocation(getRealWidth() - 10 - accept.getWidth(), 6);
	accept.addActionListener(new ActionListener(){

	    @Override
	    public void actionPerformed(ActionEvent e) {
		accept();
		setVisible(false);
	    }
	});
	
	cancel = new LButton("Cancel");
	cancel.setLocation(accept.getX() - cancel.getWidth() - 10, accept.getY());
	cancel.addActionListener(new ActionListener(){

	    @Override
	    public void actionPerformed(ActionEvent e) {
		setVisible(false);
		KnowItAll.save.revertToCancel();
	    }
	});

	LPanel buttons = new LPanel();
	buttons.setOpaque(true);
	buttons.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	buttons.setLocation(-1, getRealHeight() - 30);
	buttons.setSize(getWidth(), 40 + accept.getHeight());
	getContentPane().add(buttons);
	buttons.add(cancel);
	buttons.add(accept);

	filters = new SettingsFilters(getSize());
	display = new SettingsDisplay(getSize());
	LScrollPane displayScroll = new LScrollPane(display);
	displayScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	tabs = new JTabbedPane();
	tabs.setSize(getSize().width - 6, buttons.getY());
	tabs.setBackground(Color.GRAY);
	tabs.addTab("General", filters);
	tabs.addTab("Colors", displayScroll);
	getContentPane().add(tabs);

	init = true;
    }

    void accept() {
	GUI.setArticleFontColor(display.articleText.getValue());
	GUI.setTooltipFontColor(display.tooltipText.getValue());
	GUI.setArticleLinkFontColor(display.articleLinkText.getValue());
	GUI.setTooltipLinkFontColor(display.tooltipLinkText.getValue());
	GUI.tree.grabColors();
	GUI.tree.repaint();
    }
}
