/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.util.Collection;

/**
 *
 * @author Justin Swanson
 */
public class Tools {

    public static void deleteAllWithName(Collection<Article> articles, String name) {
	for (Article a : articles) {
	    if (a.getName().equalsIgnoreCase(name)) {
		if (a.spec.src.isFile()) {
		    a.spec.src.delete();
		}
	    }
	}
    }
}
