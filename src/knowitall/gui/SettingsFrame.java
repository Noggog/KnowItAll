/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Color;
import java.awt.Font;
import lev.gui.LCheckBox;
import lev.gui.LFrame;
import lev.gui.resources.LFonts;
import lev.gui.resources.LImages;

/**
 *
 * @author Justin Swanson
 */
public class SettingsFrame extends LFrame {

    LCheckBox openLastPackage;
    static Font settingsFont = LFonts.MyriadPro(15);

    public SettingsFrame() {
	super("Settings");
	setSize(500, 400);
	this.setResizable(false);

	openLastPackage = new LCheckBox("Open Last Package", settingsFont, Color.BLACK);
	openLastPackage.setOffset(-3);
	add(openLastPackage);
    }

}
