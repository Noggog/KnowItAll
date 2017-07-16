/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Dimension;
import lev.gui.LComboBox;

/**
 *
 * @author Justin Swanson
 */
public class WizardArticle extends WizardFrame {
    
    SettingsPanel sourceChooser;
    
    public WizardArticle() {
	super();
	
//	name = new LTextField("Name", GUI.settingsFont, Color.BLACK);
//	background.placeAdd(name);
    }
    
    @Override
    public void open() {
	super.open();
	openSource();
    }
    
    @Override
    public void init() {
	
	Dimension size = getRealSize();
	
	sourceChooser = new SettingsPanel(size);
	sourceChooser.addHelp(size);
	
	LComboBox source = new LComboBox("Source");
	source.setSize(200, 25);
	sourceChooser.placeAdd(source);
	
	sourceChooser.setSize(size);
	getContentPane().add(sourceChooser);
	
	init = true;
    }
    
    public void openSource() {
	
    }
}
