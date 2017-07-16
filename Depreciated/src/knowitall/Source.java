/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.io.File;
import java.util.ArrayList;
import lev.Ln;
import lev.gui.LSwingTreeNode;

/**
 *
 * @author Justin Swanson
 */
public class Source extends LSwingTreeNode {
    String name;
    File src;
    ArrayList<String> overrides = new ArrayList<>();

    Source (File src) {
	name = src.getName();
	this.src = src;
    }

    public void load(File specF) {

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

    public String getName() {
	return toString();
    }
}
