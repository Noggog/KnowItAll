/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    SettingsColors colors;
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
	colors = new SettingsColors(getSize());

	tabs = new JTabbedPane();
	tabs.setSize(getSize());
	tabs.setBackground(Color.GRAY);
	tabs.addTab("General", filters);
	tabs.addTab("Colors", colors);
	getContentPane().add(tabs);

	init = true;
    }

    void accept() {
	GUI.setArticleFontColor(colors.articleText.getValue());
	GUI.setTooltipFontColor(colors.tooltipText.getValue());
    }
}
