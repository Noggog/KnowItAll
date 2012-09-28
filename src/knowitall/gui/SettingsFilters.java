/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;
import knowitall.KIASave.Settings;
import lev.gui.LCheckBox;

/**
 *
 * @author Justin Swanson
 */
public class SettingsFilters extends SettingsPanel {

    LCheckBox openLastPackage;
    LCheckBox mergeSources;
    LCheckBox linkedArticlesBelow;
    LCheckBox tooltips;
    LCheckBox shortenGrids;

    public SettingsFilters() {

	last.x = SettingsFrame.help.getX() - 20;
	last.y += 10;
	alignRight(true);

	openLastPackage = cBox("Load On Startup", Settings.OpenLastOnStartup);
	mergeSources = cBox("Merge Sources", Settings.MergeSources);
	mergeSources.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		GUI.regenerateTree();
	    }
	});
	linkedArticlesBelow = cBox("Linked Articles Below", Settings.LinkedArticles);
	linkedArticlesBelow.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		GUI.reloadArticle();
	    }
	});
	tooltips = cBox("Tooltips On", Settings.ToolTipsOn);
	shortenGrids = cBox("Shorten Grids", Settings.ShortenGrids);

	place(openLastPackage);
	place(mergeSources);
	place(linkedArticlesBelow);
	place(tooltips);
//	place(shortenGrids);
    }
}
