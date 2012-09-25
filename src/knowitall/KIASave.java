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
	Add(Settings.SeparateSources,		false,	    false);
	Add(Settings.LinkedArticles,		true,	    false);
	Add(Settings.LinkDirectionUsed,		false,	    false);
	Add(Settings.ToolTipsOn,		true,	    false);
	Add(Settings.ShortenGrids,		true,	    false);
    }

    @Override
    protected void initHelp() {
	this.helpInfo.put(Settings.OpenLastOnStartup, "Opens the last used package automatically when starting up.");
    }

    public static enum Settings {
	StartWidth,
	StartHeight,
	DividerLocation,
	LastPackage,
	OpenLastOnStartup,
	SeparateSources,
	LinkedArticles,
	LinkDirectionUsed,
	ToolTipsOn,
	ShortenGrids
	;
    }

}
