/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.util.Objects;
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

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 97 * hash + Objects.hashCode(this.index);
	return hash;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final Category other = (Category) obj;
	if (!Objects.equals(this.index, other.index)) {
	    return false;
	}
	return true;
    }

}
