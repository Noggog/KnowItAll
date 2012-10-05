/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Font;
import javax.swing.text.html.StyleSheet;
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
	htmlContent.honorDisplayProperties();
	htmlContent.setFont(articleFont);
	htmlContent.setOpaque(false);
	htmlContent.transparentBackground();
    }
}
