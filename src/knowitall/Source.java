/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.io.File;
import lev.Ln;
import lev.gui.LSwingTreeNode;

/**
 *
 * @author Justin Swanson
 */
public class Source extends LSwingTreeNode {
    String name;
    Source (File src) {
	name = src.getName();
    }

    @Override
    public String toString() {
	return name;
    }

    @Override
    public void print(int depth) {
	Debug.log.log(name, Ln.getNAmount(depth, "   ") + "Source: " + name + " ==============");
	super.print(depth);
    }
}
