/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Rectangle;
import lev.gui.LFrame;

/**
 *
 * @author Justin Swanson
 */
public class WizardFrame extends LFrame {
    
    public WizardFrame() {
	super("Know It All Wizard");
	Rectangle b = GUI.getGUIbounds();
	int margin = 75;
	setSize(b.width - margin * 2, b.height - margin * 2);
	setLocation(b.x + margin, b.y + margin);
    }
    
}
