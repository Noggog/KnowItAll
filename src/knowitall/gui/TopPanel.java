/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Dimension;
import javax.swing.JProgressBar;
import lev.gui.LPanel;

/**
 *
 * @author Justin Swanson
 */
public class TopPanel extends LPanel {

    ArticleTooltip tooltip;
    JProgressBar progress;
    
    static int progressMargin = 75;

    TopPanel() {
	tooltip = new ArticleTooltip();
	GUI.tooltip = tooltip;
	add(tooltip, 0);
	
	progress = new JProgressBar();
	GUI.progress = progress;
	add(progress);
    }

    @Override
    public void remeasure(Dimension size) {
	super.remeasure(size);
	progress.setLocation(75, getHeight() / 2 - progress.getHeight() / 2);
	progress.setSize(getWidth() - 2 * progressMargin, 30);
    }
    
    public boolean isActive() {
	return tooltip.isVisible() || progress.isVisible();
    }
}
