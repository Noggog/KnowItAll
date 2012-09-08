/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author Justin Swanson
 */
public abstract class PanelTemplate extends JPanel {

    PanelTemplate () {
	super();
    }

    public abstract void remeasure (Dimension size);
}
