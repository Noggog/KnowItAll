/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Font;
import javax.swing.JTextField;
import lev.gui.resources.LFonts;

/**
 *
 * @author Justin Swanson
 */
public class SearchBar extends JTextField {

    JTextField field;

    public SearchBar() {
	init();
    }

    final void init() {
	field = new JTextField();
	field.setFont(LFonts.MyriadPro(20));
	add(field);
	setSize(5, 30);
	field.setVisible(true);
	setVisible(true);
    }

    @Override
    final public void setSize(int x, int y) {
	super.setSize(x, y);
	field.setSize(x, y);
    }
}
