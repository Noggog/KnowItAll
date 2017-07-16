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
public class ArticleNode extends LSwingTreeNode {

    Article a;

    ArticleNode (Article a) {
	this.a = a;
    }

    @Override
    public void print(int depth) {
	Debug.log.log(a.getName(), Ln.getNAmount(depth, "   ") + "Article: " + a.getName() + " ==============");
    }

    @Override
    public String toString() {
	return a.getName();
    }
}
