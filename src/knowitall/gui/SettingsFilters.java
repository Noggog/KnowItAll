/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import knowitall.KIASave.Settings;
import lev.gui.LCheckBox;
import lev.gui.LHelpPanel;
import lev.gui.resources.LFonts;
import lev.gui.resources.LImages;

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

	Rectangle helpA = new Rectangle(250, 0, 350, size.height);
	help = new LHelpPanel(helpA, LFonts.MyriadProBold(25), Color.BLACK, Color.DARK_GRAY, LImages.arrow(true, true), 0);
	help.setTitleOffset(3);
	help.setXOffsets(10, 21);
	add(help);

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

	place(openLastPackage);
	place(mergeSources);
	place(linkedArticlesBelow);
	place(tooltips);
//	place(shortenGrids);
    }
}
