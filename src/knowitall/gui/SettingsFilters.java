/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public SettingsFilters(Dimension size) {
	super(size);

	addHelp(size);

	last.x = 200;
	last.y += 10;
	align(Align.Center);

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

	placeAdd(openLastPackage);
	placeAdd(mergeSources);
	placeAdd(linkedArticlesBelow);
	placeAdd(tooltips);
//	placeAdd(shortenGrids);
    }
}
