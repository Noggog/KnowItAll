/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.StyleSheet;
import knowitall.KIASave.Settings;
import knowitall.KnowItAll;
import lev.gui.LHTMLPane;
import lev.gui.LPanel;
import lev.gui.resources.LFonts;

/**
 *
 * @author Justin Swanson
 */
public class ArticlePane extends LPanel {
    
    LHTMLPane htmlContent;
    StyleSheet ss;
    static Font articleFont = LFonts.MyriadPro(16);
    
    ArticlePane () {
	super();
	htmlContent = new LHTMLPane();
	htmlContent.setVisible(true);
	ss = htmlContent.getStyleSheet();
	ss.addRule("a {}");
	setLinkFontColor(KnowItAll.save.getColor(Settings.LinkFont));
	htmlContent.honorDisplayProperties();
	htmlContent.setFont(articleFont);
	htmlContent.setOpaque(false);
	htmlContent.transparentBackground();
    }

    public final void setBodyFontColor(Color c) {
	Style s = ss.getStyle("body");
	s.addAttribute(StyleConstants.Foreground, c);
    }
    
    public final void setLinkFontColor(Color c) {
	Style s = ss.getStyle("a");
	s.addAttribute(StyleConstants.Foreground, c);
    }
    
    public void setSelectable(boolean on) {
	htmlContent.setEnabled(false);
    }
}
