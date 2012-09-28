/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import lev.gui.LSaveFile;

/**
 *
 * @author Justin Swanson
 */
public class KIASave extends LSaveFile {

    KIASave (String in) {
	super(in);
    }

    @Override
    protected void initSettings() {
	Add(Settings.StartWidth,		0,	    false);
	Add(Settings.StartHeight,		0,	    false);
	Add(Settings.DividerLocation,		250,	    false);
	Add(Settings.LastPackage,		".",	    false);
	Add(Settings.OpenLastOnStartup,		true,	    false);
	Add(Settings.MergeSources,		true,	    false);
	Add(Settings.LinkedArticles,		true,	    false);
	Add(Settings.LinkDirectionUsed,		false,	    false);
	Add(Settings.ToolTipsOn,		true,	    false);
	Add(Settings.ShortenGrids,		true,	    false);
    }

    @Override
    protected void initHelp() {
	this.helpInfo.put(Settings.OpenLastOnStartup, "Opens the last used package automatically when starting up.");

	this.helpInfo.put(Settings.MergeSources, "On the left display tree, combine all the "
		+ "sources into one big collection, rather than separating the sources.");

	this.helpInfo.put(Settings.LinkedArticles, "Put articles that are referenced "
		+ "in the main article below it.  Turn this off to show only the main article.");

	this.helpInfo.put(Settings.ToolTipsOn, "Show popup tooltips of article links you mouse over.");

	this.helpInfo.put(Settings.ShortenGrids, "Make grids as short as possible by using the shortest known names of articles.");
    }

    public static enum Settings {
	StartWidth,
	StartHeight,
	DividerLocation,
	LastPackage,
	OpenLastOnStartup,
	MergeSources,
	LinkedArticles,
	LinkDirectionUsed,
	ToolTipsOn,
	ShortenGrids
	;
    }

}
