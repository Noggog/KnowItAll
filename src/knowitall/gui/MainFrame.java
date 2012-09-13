/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import knowitall.KnowItAll;
import lev.gui.LFrame;
import lev.gui.LPanel;
import lev.gui.Lg;

/**
 *
 * @author Justin Swanson
 */
public class MainFrame extends LFrame {

    LPanel activePanel;

    public MainFrame() {
	super("Know It All");
    }

    public void createGUI() throws IOException {
	setSize(defaultSize());
	setLocation(defaultLocation());
	super.remeasure ();
	background.setImage(KnowItAll.internalFiles + "background.jpg");
	GUI.mainPanel = new MainPanel();
	activePanel = GUI.mainPanel;
	add(activePanel,0);
	remeasure ();
	setVisible(true);
	setBackground(Color.BLACK);
    }

    @Override
    public void remeasure () {
	if (activePanel != null) {
	    activePanel.remeasure(getRealSize());
	}
    }

    public Dimension defaultSize() {
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension max = new Dimension(1980, 2600);
	return Lg.calcSize(max.getWidth(), max.getHeight(), screen.width - 5, screen.height - 40);
    }

    @Override
    public void validate() {
	super.validate();
	remeasure();
    }
}
