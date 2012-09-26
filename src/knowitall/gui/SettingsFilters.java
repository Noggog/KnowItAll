/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import knowitall.KIASave.Settings;
import knowitall.KnowItAll;
import lev.gui.LCheckBox;
import net.java.dev.designgridlayout.DesignGridLayout;

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

	DesignGridLayout layout = new DesignGridLayout(this);

	openLastPackage = cBox("Load on Startup");
	mergeSources = cBox("Merge Sources");
	linkedArticlesBelow = cBox("Linked Articles Below");
	tooltips = cBox("Tooltips On");
	shortenGrids = cBox("Shorten Grids");

	layout.row().grid().add(openLastPackage);
	layout.row().grid().add(mergeSources);
	layout.row().grid().add(linkedArticlesBelow);
	layout.row().grid().add(tooltips);
	layout.row().grid().add(shortenGrids);

	setSize(500, 400);
    }

    public void open() {
	openLastPackage.setSelected(KnowItAll.save.getBool(Settings.OpenLastOnStartup));
	mergeSources.setSelected(KnowItAll.save.getBool(Settings.MergeSources));
	linkedArticlesBelow.setSelected(KnowItAll.save.getBool(Settings.LinkedArticles));
	tooltips.setSelected(KnowItAll.save.getBool(Settings.ToolTipsOn));
	shortenGrids.setSelected(KnowItAll.save.getBool(Settings.ShortenGrids));
    }

    public void accept() {

    }
}
