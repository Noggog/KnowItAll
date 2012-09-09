/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Justin Swanson
 */
public class ArticleSpec {

    public String name;
    public String[][] extraSubCategories;
    public String content;
    public String shortContent;

    public void noNull() {
	name = noNull(name);
	extraSubCategories = noNull(extraSubCategories);
	content = noNull(content);
	shortContent = noNull(shortContent);
    }

    public String noNull(String s) {
	if (s == null) {
	    return "";
	}
	return s;
    }

    public String[][] noNull(String[][] s) {
	if (s == null) {
	    return new String[0][];
	}
	return s;
    }

    public void clean() {
	noNull();
	name = name.trim();

	// SubCategories
	ArrayList<String[]> tmp = new ArrayList<>(extraSubCategories.length);
	for (String[] s : extraSubCategories) {
	    if (s.length != 2) {
		continue;
	    }
	    for (int i = 0; i < s.length; i++) {
		s[i] = s[i].trim();
	    }
	    tmp.add(s);
	}
	extraSubCategories = new String[tmp.size()][];
	int i = 0;
	for (String[] s : tmp) {
	    extraSubCategories[i++] = s;
	}

	shortContent = cleanContentStr(shortContent);
	content = cleanContentStr(content);
    }

    public String cleanContentStr(String in) {
	if (in.equals("")) {
	    return in;
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

    public Set<String> getWords() {
	HashSet<String> out = new HashSet<>();
	for (String[] s : extraSubCategories) {
	    if (s.length == 2) {
		out.addAll(Parsing.getWords(s[1]));
	    }
	}
	out.addAll(Parsing.getWords(content));
	out.addAll(Parsing.getWords(shortContent));
	return out;
    }
}
