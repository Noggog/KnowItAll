/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import knowitall.Debug.Logs;

/**
 *
 * @author Justin Swanson
 */
public class ArticleSpec {

    File src;
    public String name = "";
    public String[][] extraSubCategories = new String[0][];
    public String content = "";
    public String shortContent = "";
    public int pageNumber = -1;
    public boolean GMOnly = false;
    public String[][] grid = new String[0][];
    public String[] aka = new String[0];
    public String[] forceLinkTo = new String[0];
    public boolean blockLinking;
    public String source = "";
    public String intro = "";
    public boolean spacedSubcategories = false;
    public boolean divideContent = false;

    public void clean(Category c) {
	name = name.trim();

	// SubCategories
	Map<String, String> tmp = new HashMap<>(extraSubCategories.length);
	for (String[] s : extraSubCategories) {
	    // Block bad subcategories
	    if (s.length != 2 || !c.index.subCategorySet.contains(s[0].toUpperCase()) || "".equals(s[1])) {
		// Log the block
		if (Debug.log.logging() && !"".equals(s[1])) {
		    String blocked = "";
		    for (String s2 : s) {
			blocked += s2 + " | ";
		    }
		    Debug.log.logSpecial(Logs.BLOCKED_ARTICLES, "Blocked SubCategory", "Blocked Subcategory from category " + c.index.getName() + ", article spec " + src + ": " + blocked);
		}
		continue;
	    }
	    // Trim categories
	    for (int i = 0; i < s.length; i++) {
		s[i] = s[i].trim();
	    }
	    tmp.put(s[0], s[1]);
	}
	extraSubCategories = new String[tmp.size()][];
	int i = 0;
	for (String key : c.index.subCategoryOrder) {
	    if (tmp.containsKey(key)) {
		extraSubCategories[i++] = new String[]{key, tmp.get(key)};
	    }
	}

	// Content
//	shortContent = cleanContentStr(shortContent);
//	content = cleanContentStr(content);
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
		    int index = in.length() - 1;
		    if (index > 0 && in.charAt(in.length() - 1) != ' ') {
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
//	    byte b = (byte)c;
//	    switch (b) {
//
//	    }
	    in += c;
	}
	return in;
    }

    public Set<String> getWords() {
	Set<String> out = new TreeSet<>();
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
