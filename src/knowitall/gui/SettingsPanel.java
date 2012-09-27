/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Color;
import knowitall.KnowItAll;
import lev.gui.LCheckBox;
import lev.gui.LPanel;
import lev.gui.Setting;
import lev.gui.resources.LFonts;

/**
 *
 * @author Justin Swanson
 */
public class SettingsPanel extends LPanel {

    public static LCheckBox cBox (String title, Enum s) {
	LCheckBox box = new LCheckBox(title, LFonts.MyriadPro(14), Color.RED);
	if (s != null) {
	    box.tie(s, KnowItAll.save);
	}
	return box;
    }
}
