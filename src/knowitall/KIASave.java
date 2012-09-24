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
    }

    @Override
    protected void initHelp() {
    }

    public static enum Settings {
	StartWidth,
	StartHeight,
	DividerLocation,
	LastPackage,
	OpenLastOnStartup
	;
    }

}
