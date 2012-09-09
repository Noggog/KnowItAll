/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

/**
 *
 * @author Justin Swanson
 */
public class ArticleSpec {

    public String name;
    public String[][] extraSubCategories;
    public String content;

    public void clean() {
	name = name.trim();
	for (String[] s : extraSubCategories) {
	    for (int i = 0; i < s.length; i++) {
		s[i] = s[i].trim();
	    }
	}
	char[] chars = content.toCharArray();
	content = "";
	boolean firstNL = true;
	for (char c : chars) {
	    if (c == '\r') {
		continue;
	    }

	    // If New line character
	    if (c == '\n') {
		if (firstNL) {
		    // Remove the first New Line character of any set
		    firstNL = false;
		    // Make sure there's a space between the two chars
		    // Between the deleted NL character
		    if (content.charAt(content.length() - 1) != ' ') {
			content += " ";
		    }
		    continue;
		} else {
		    content += c;
		    continue;
		}
	    }

	    // Else
	    firstNL = true;
	    if (((byte) c) == -3) {
		c = '\'';
	    }
	    content += c;
	}
    }
}
