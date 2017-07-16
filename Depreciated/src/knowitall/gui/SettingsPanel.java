/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import knowitall.KnowItAll;
import lev.gui.*;
import lev.gui.resources.LFonts;
import lev.gui.resources.LImages;

/**
 *
 * @author Justin Swanson
 */
public class SettingsPanel extends LPanel {

    LHelpPanel help;
    ArrayList<LUserSetting> settings = new ArrayList<>();
    LPanel pane;
    LScrollPane displayScroll;

    SettingsPanel(Dimension size) {
	super();

	pane = new LPanel();
	pane.setPreferredSize(size);
	displayScroll = new LScrollPane(pane);
	displayScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	add(displayScroll);

	setSize(size);
	displayScroll.setSize(size);
    }

    public void addHelp(Dimension size) {
	Rectangle helpA = new Rectangle(250, 0, 350, size.height);
	help = new LHelpPanel(helpA, LFonts.MyriadProBold(25), Color.BLACK, Color.DARK_GRAY, LImages.arrow(true, true), 0);
	help.setTitleOffset(3);
	help.setXOffsets(10, 21);
	add(help);
    }
    
    public LCheckBox cBox(String title, Enum s) {
	LCheckBox box = new LCheckBox(title, GUI.settingsFont, Color.BLACK);
	box.setOffset(-1);
	if (s != null) {
	    box.tie(s, KnowItAll.save, help, true);
	}
	return box;
    }

    public LColorSetting color(String title, Enum s) {
	LColorSetting out = new LColorSetting(title, GUI.settingsFont, Color.BLACK, Color.BLUE);
	out.tie(s, KnowItAll.save);
	return out;
    }

    public LSlider slider(String title, Enum s, int min, int max) {
	LSlider out = new LSlider(title, GUI.settingsFont, Color.BLACK, min, max, 0);
	out.tie(s, KnowItAll.save);
	return out;
    }

    @Override
    public void placeAdd(Component c) {
	setPlacement(c);
	pane.Add(c);
	if (c instanceof LUserSetting) {
	    settings.add((LUserSetting) c);
	}
    }

    @Override
    public void setPreferredSize(Dimension size) {
	super.setPreferredSize(size);
	pane.setPreferredSize(size);
    }

    public void updateColors() {
	
    }
}
