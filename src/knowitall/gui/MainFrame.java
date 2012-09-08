/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JFrame;
import lev.gui.LImagePane;
import lev.gui.Lg;
import lev.gui.resources.LImages;

/**
 *
 * @author Justin Swanson
 */
public class MainFrame extends JFrame {

    LImagePane background;
    PanelTemplate activePanel;

    public MainFrame() {
	super("Know It All");
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	getContentPane().setBackground(Color.BLACK);
	setLayout(null);

	background = new LImagePane();
	add(background);
    }

    public void createGUI() throws IOException {
	defaultSize();
	defaultLocation();
	remeasure ();
//	background.setImage(internalFiles + "background.jpg");
	background.setImage(LImages.multipurpose());
	activePanel = new MainPanel();
	add(activePanel,0);
	remeasure ();
	setVisible(true);
    }

    public void remeasure () {
	background.setMaxSize(getRealWidth(), 0);
	if (activePanel != null) {
	    activePanel.remeasure(getRealSize());
	}
    }

    public Dimension getRealSize() {
	return new Dimension(getRealWidth(), getRealHeight());
    }

    public int getRealWidth() {
	return getWidth() - 16;
    }

    public int getRealHeight () {
	return getHeight() - 38;
    }

    public void defaultSize() {
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension max = new Dimension(1980, 2600);
	Dimension maxSize = Lg.calcSize(max.getWidth(), max.getHeight(), screen.width - 5, screen.height - 40);
	setSize(maxSize);
    }

    public void defaultLocation() {
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	int y = 5;
	int x = screen.width / 2 - getWidth() / 2;
	if (x < 5) {
	    x = 5;
	}
	setLocation(x, y);
    }
}
