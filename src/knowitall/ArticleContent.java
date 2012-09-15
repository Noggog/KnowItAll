/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.util.ArrayList;

/**
 *
 * @author Justin Swanson
 */
public class ArticleContent {

    ArrayList<String> content = new ArrayList<>();
    String out;

    ArticleContent(String in) {
	content.add(in);
    }

    public String get() {
	if (out == null) {
	    out = "";
	    for (String s : content) {
		out += s;
	    }
	}
	return out;
    }
}
