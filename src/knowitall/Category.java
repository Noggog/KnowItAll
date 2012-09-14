/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import lev.gui.LSwingTreeNode;

/**
 *
 * @author Justin Swanson
 */
public class Category extends LSwingTreeNode {

    CategoryIndex index;

    Category(CategoryIndex index) {
	this.index = index;
    }

    @Override
    public String toString() {
	return index.getName();
    }
}
