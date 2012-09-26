/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Color;
import javax.swing.JCheckBox;
import lev.gui.LCheckBox;
import lev.gui.LPanel;
import lev.gui.resources.LFonts;

/**
 *
 * @author Justin Swanson
 */
public class SettingsPanel extends LPanel {

    public static LCheckBox cBox (String title) {
	LCheckBox box = new LCheckBox(title, LFonts.MyriadPro(14), Color.RED);
	return box;
    }
}
