/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
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

    LPanel mainPanel;
    LPanel topPanel;
    LPanel dimmer;

    public MainFrame() {
	super("Know It All");
    }

    public void createGUI() throws IOException {
	setSize(defaultSize());
	setLocation(defaultLocation());
	super.remeasure();
	background.setImage(KnowItAll.internalFiles + "background.jpg");
	GUI.mainPanel = new MainPanel();
	mainPanel = GUI.mainPanel;
	add(mainPanel, 0);

	GUI.dimmer = new Dimmer();
	dimmer = GUI.dimmer;
	add(dimmer, 0);

	GUI.topPanel = new TopPanel();
	topPanel = GUI.topPanel;
	add(topPanel, 0);

	remeasure();
	setVisible(true);
	setBackground(Color.BLACK);
    }

    @Override
    public void remeasure() {
	mainPanel.remeasure(getRealSize());
	topPanel.remeasure(getRealSize());
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
