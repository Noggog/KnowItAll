/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Dimension;
import lev.gui.LButton;
import lev.gui.LList;
import lev.gui.Lg;

/**
 *
 * @author Justin Swanson
 */
public class CategoryEditor extends SettingsPanel {

    LList existing;
    LButton newCat;
    LButton remove;

    public CategoryEditor(Dimension size) {
	super(size);

	existing = new LList("Existing");
	existing.setLocation(10, 10);
	existing.setSize(200, 200);
	Add(existing);
	
	remove = new LButton("Remove");
	Add(remove);

	newCat = new LButton("New");
	Add(newCat);
	
	int xspacing = Lg.getSpacing(true, existing.getWidth(), remove, newCat);
	remove.setLocation(xspacing, 0);
	newCat.setLocation(remove.getRight() + xspacing, 0);
    }
    
    void readjust(Dimension size) {
	existing.setSize(existing.getWidth(), size.height - 30 - remove.getHeight());
	remove.setLocation(remove.getX(), existing.);
    }

    @Override
    public void setSize(int x, int y) {
	super.setSize(x, y);
    }
}
