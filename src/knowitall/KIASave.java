/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.awt.Color;
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
	Add(Settings.StartWidth,		0);
	Add(Settings.StartHeight,		0);
	Add(Settings.DividerLocation,		250);
	Add(Settings.LastPackage,		".");
	Add(Settings.OpenLastOnStartup,		true);
	Add(Settings.MergeSources,		true);
	Add(Settings.LinkedArticles,		true);
	Add(Settings.LinkDirectionUsed,		false);
	Add(Settings.ToolTipsOn,		true);
	Add(Settings.ShortenGrids,		true);

	//Colors
	Add(Settings.ArticleBack,		Color.WHITE);
	Add(Settings.ArticleFont,		Color.BLACK);
	Add(Settings.ArticleLinkFont,		Color.BLUE);
	Add(Settings.ArticleTrans,		100);
	Add(Settings.ToolBack,			Color.WHITE);
	Add(Settings.ToolFont,			Color.BLACK);
	Add(Settings.ToolLinkFont,		Color.BLUE);
	Add(Settings.ToolTrans,			100);
	Add(Settings.SearchBack,		Color.WHITE);
	Add(Settings.SearchFont,		Color.BLACK);
	Add(Settings.TreeBack,			Color.WHITE);
	Add(Settings.TreeBackSelected,		new Color(57,105,138));
	Add(Settings.TreeFont,			Color.BLACK);
	Add(Settings.TreeFontSelected,		Color.WHITE);
	Add(Settings.TreeTrans,			100);
	Add(Settings.DimmerTrans,		50);
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
	ShortenGrids,

	// Colors
	ArticleBack,
	ArticleFont,
	ArticleTrans,
	ArticleLinkFont,
	ToolBack,
	ToolFont,
	ToolLinkFont,
	ToolTrans,
	SearchBack,
	SearchFont,
	TreeFont,
	TreeFontSelected,
	TreeBack,
	TreeBackSelected,
	TreeTrans,
	DimmerTrans,
	;
    }

}
