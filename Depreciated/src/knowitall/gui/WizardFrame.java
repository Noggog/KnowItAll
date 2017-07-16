/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.JTabbedPane;
import lev.gui.LFrame;

/**
 *
 * @author Justin Swanson
 */
public class WizardFrame extends LFrame {
    
    JTabbedPane tabs;
    CategoryEditor categoryW;
    WizardArticle articleW;
    
    public WizardFrame() {
	super("KIA Editor");
    }
    
    @Override
    public void open() {
	Rectangle b = GUI.getGUIbounds();
	int margin = 75;
	setSize(b.width - margin * 2, b.height - margin * 2);
	setLocation(b.x + margin, b.y + margin);
	super.open();
    }

    @Override
    protected void init() {
	super.init();
	
	categoryW = new CategoryEditor(getSize());
	
	tabs = new JTabbedPane();
	tabs.setSize(getSize());
	tabs.setBackground(Color.GRAY);
	tabs.addTab("Category Editor", categoryW);
	getContentPane().add(tabs);
    }
    
    
}
