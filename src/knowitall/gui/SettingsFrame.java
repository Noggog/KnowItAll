/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JTabbedPane;
import knowitall.KnowItAll;
import lev.gui.LButton;
import lev.gui.LFrame;
import lev.gui.LPanel;
import lev.gui.LUserSetting;

/**
 *
 * @author Justin Swanson
 */
public class SettingsFrame extends LFrame {

    JTabbedPane tabs;
    LButton cancel;
    LButton accept;
    LButton defaults;
    LButton saved;
    SettingsFilters filters;
    SettingsDisplay display;

    public SettingsFrame() {
	super("Settings");
	setSize(700, 500);
	this.setResizable(false);
    }

    @Override
    public void open() {
	KnowItAll.save.saveToCancelSave();
	setLocation(centerScreen());
	super.open();
    }

    @Override
    protected void init() {

	accept = new LButton("Accept");
	accept.setLocation(getRealWidth() - 10 - accept.getWidth(), 6);
	accept.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		accept();
		setVisible(false);
	    }
	});

	cancel = new LButton("Cancel");
	cancel.setLocation(accept.getX() - cancel.getWidth() - 10, accept.getY());
	cancel.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		setVisible(false);
		KnowItAll.save.revertToCancel();
	    }
	});

	defaults = new LButton("Defaults");
	defaults.setLocation(20, 6);
	defaults.addMouseListener(new MouseListener() {

	    @Override
	    public void mouseClicked(MouseEvent arg0) {
		for (LUserSetting s : getSelectedPanel().settings) {
		    KnowItAll.save.revertToDefault(s);
		}
	    }

	    @Override
	    public void mousePressed(MouseEvent arg0) {
	    }

	    @Override
	    public void mouseReleased(MouseEvent arg0) {
	    }

	    @Override
	    public void mouseEntered(MouseEvent arg0) {
		KnowItAll.save.peekDefaults();
		getSelectedPanel().updateColors();
		tabs.repaint();
		GUI.repaintContent();
	    }

	    @Override
	    public void mouseExited(MouseEvent arg0) {
		KnowItAll.save.clearPeek();
		getSelectedPanel().updateColors();
		tabs.repaint();
		GUI.repaintContent();
	    }
	});

	saved = new LButton("Saved");
	saved.setLocation(defaults.getRight() + 10, defaults.getY());
	saved.addMouseListener(new MouseListener() {

	    @Override
	    public void mouseClicked(MouseEvent arg0) {
		for (LUserSetting s : getSelectedPanel().settings) {
		    KnowItAll.save.revertToSaved(s);
		}
	    }

	    @Override
	    public void mousePressed(MouseEvent arg0) {
	    }

	    @Override
	    public void mouseReleased(MouseEvent arg0) {
	    }

	    @Override
	    public void mouseEntered(MouseEvent arg0) {
		KnowItAll.save.peekSaved();
		getSelectedPanel().updateColors();
		tabs.repaint();
		GUI.repaintContent();
	    }

	    @Override
	    public void mouseExited(MouseEvent arg0) {
		KnowItAll.save.clearPeek();
		getSelectedPanel().updateColors();
		tabs.repaint();
		GUI.repaintContent();
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
	buttons.add(defaults);
	buttons.add(saved);

	int buttonHeight = 30;
	Dimension realSize = new Dimension(getWidth() - 6, buttons.getY() - buttonHeight);

	filters = new SettingsFilters(realSize);
	display = new SettingsDisplay(realSize);

	tabs = new JTabbedPane();
	tabs.setSize(realSize.width, realSize.height + buttonHeight);
	tabs.setBackground(Color.GRAY);
	tabs.addTab("General", filters);
	tabs.addTab("Colors", display);
	getContentPane().add(tabs);

	init = true;
    }

    void accept() {
	GUI.fetchSetColors();
	GUI.tree.repaint();
    }

    @Override
    public void setVisible(boolean t) {
	super.setVisible(t);
	filters.displayScroll.scrollToTop();
	display.displayScroll.scrollToTop();
    }

    SettingsPanel getSelectedPanel() {
	return (SettingsPanel) tabs.getSelectedComponent();
    }
}
