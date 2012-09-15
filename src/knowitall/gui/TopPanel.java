/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import lev.gui.LPanel;

/**
 *
 * @author Justin Swanson
 */
public class TopPanel extends LPanel {

    ArticleTooltip tooltip;

    TopPanel() {
	tooltip = new ArticleTooltip();
//	tooltip.setSize(100, 100);
//	tooltip.setOpaque(true);
//	tooltip.setVisible(true);
	GUI.tooltip = tooltip;
	add(tooltip, 0);
    }

}
