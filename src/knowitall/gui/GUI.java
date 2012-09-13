/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

/**
 *
 * @author Justin Swanson
 */
public class GUI {

    static MainPanel mainPanel ;
    static ContentPanel contentPanel ;

    public static void displayArticles(boolean on) {
	if (contentPanel != null) {
	    contentPanel.displayArticles(on);
	}
    }
}
