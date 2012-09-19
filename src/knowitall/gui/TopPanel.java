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
    KIAProgressPane progressPane;

    TopPanel() {
	tooltip = new ArticleTooltip();
	GUI.tooltip = tooltip;
	add(tooltip);

	progressPane = new KIAProgressPane();
	GUI.progressPane = progressPane;
	add(progressPane);
    }

    @Override
    public void remeasure(Dimension size) {
	setSize(size);
	progressPane.remeasure(getSize());
    }

    public boolean isActive() {
	return tooltip.isVisible() || progressPane.isVisible();
    }
}
