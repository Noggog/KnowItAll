/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import knowitall.KIASave.Settings;
import knowitall.KnowItAll;
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

    public SettingsFilters () {

	openLastPackage = cBox("Load on Startup", Settings.OpenLastOnStartup);
	mergeSources = cBox("Merge Sources", Settings.MergeSources);
	linkedArticlesBelow = cBox("Linked Articles Below", Settings.LinkedArticles);
	tooltips = cBox("Tooltips On", Settings.ToolTipsOn);
	shortenGrids = cBox("Shorten Grids", Settings.ShortenGrids);

//	add(openLastPackage);
	add(mergeSources);

	setSize(500, 400);
    }

}
