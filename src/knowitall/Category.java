/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import lev.Ln;
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

    public String getName() {
	return toString();
    }

    @Override
    public void print(int depth) {
	Debug.log.log(getName(), Ln.getNAmount(depth, "   ") + "Category: " + getName() + " ==============");
	super.print(depth);
    }
}
