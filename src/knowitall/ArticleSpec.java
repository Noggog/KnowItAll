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
    public String shortContent;

    public void clean() {
	if (name != null) {
	    name = name.trim();
	}
	if (extraSubCategories != null) {
	    for (String[] s : extraSubCategories) {
		for (int i = 0; i < s.length; i++) {
		    s[i] = s[i].trim();
		}
	    }
	}
	shortContent = cleanContentStr(shortContent);
	content = cleanContentStr(content);
    }

    public String cleanContentStr(String in) {
	if (in == null) {
	    return null;
	}
	char[] chars = in.toCharArray();
	in = "";
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
		    if (in.charAt(in.length() - 1) != ' ') {
			in += " ";
		    }
		    continue;
		} else {
		    in += c;
		    continue;
		}
	    }

	    // Else
	    firstNL = true;
	    if (((byte) c) == -3) {
		c = '\'';
	    }
	    in += c;
	}
	return in;
    }
}
