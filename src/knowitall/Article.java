/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import knowitall.Debug.Logs;
import lev.gui.LSwingTreeNode;

/**
 *
 * @author Justin Swanson
 */
public class Article extends LSwingTreeNode implements Comparable {

    public Category category;
    File specFile;
    String name;
    Set<Article> linked = new TreeSet<>();
    String html;
    String htmlShort;
    // Temporary
    String content;
    String shortContent;
    ArrayList<String[]> subCategories = new ArrayList<>();
    ArrayList<String[]> grid = new ArrayList<>();
    int pageNumber;
    boolean blockLinking;
    Set<String> words = new TreeSet<>();

    Article(Category c) {
	category = c;
    }

    public boolean load(File specF) {
	try {
	    ArticleSpec spec = KnowItAll.gson.fromJson(new FileReader(specF), ArticleSpec.class);
	    if (spec == null) {
		String error = "Skipped because it had a null spec: " + specF;
		Debug.log.logError("Article", error);
		Debug.log.logSpecial(Logs.BLOCKED_ARTICLES, "Article", error);
		return false;
	    }
	    if (spec.name == null || spec.name.equals("")) {
		spec.name = specF.getName().substring(0, specF.getName().length() - 5);
	    }
	    spec.src = specF;
	    spec.clean(category);
	    specFile = specF;
	    name = spec.name;
	    words = spec.getWords();
	    shortContent = spec.shortContent;
	    subCategories = getAttributes(spec.extraSubCategories);
	    grid = getAttributes(spec.grid);
	    content = spec.content;
	    pageNumber = spec.pageNumber;
	    blockLinking = spec.blockLinking;
	    reloadHTML();
	    return true;
	} catch (FileNotFoundException ex) {
	} catch (com.google.gson.JsonSyntaxException ex) {
	    Debug.log.logException(ex);
	    Debug.log.logSpecial(Logs.BLOCKED_ARTICLES, "Article", "Skipped because it had a badly formatted spec: " + specF);
	}
	return false;
    }

    public String getName() {
	return name;
    }

    @Override
    public String toString() {
	return getName();
    }

    public String getContent() {
	return content;
    }

    public String getShort() {
	return shortContent;
    }

    public ArrayList<String[]> getSubcategories() {
	return subCategories;
    }

    public ArrayList<String[]> getGrid() {
	return grid;
    }

    ArrayList<String[]> getAttributes(String[][] strs) {
	ArrayList<String[]> out = new ArrayList<>(strs.length);
	for (String[] sub : strs) {
	    if (sub.length == 2) {
		out.add(sub);
	    }
	}
	return out;
    }

    public Integer getPageNum() {
	return pageNumber;
    }

    public String getIcon() {
	return category.index.getIcon();
    }

    public String getImage() {
	return "Vert Image";
    }

    public String getHTML(boolean full) {
	if (full) {
	    return html;
	} else {
	    return htmlShort;
	}
    }

    public void clean() {
	words = null;
	content = null;
	shortContent = null;
	subCategories = null;
	grid = null;
    }

    public Set<Article> getLinks() {
	return linked;
    }

    public void linkTo(Article a) {
	if (!equals(a) && words.contains(a.getName().toUpperCase()) && !a.blockLinking) {
	    linked.add(a);
	}
    }

    public void linkText() {
	Map<Integer, Article> lengthSort = new TreeMap<>();
	for (Article a : linked) {
	    lengthSort.put(a.getName().length(), a);
	}

	for (Article a : lengthSort.values()) {
	    linkStrings(a.getName());
	}
	reloadHTML();
    }

    public void reloadHTML() {
	html = ArticleHTML.load(this, true);
	htmlShort = ArticleHTML.load(this, false);
    }

    public void linkStrings(String in) {
	content = linkString(content, in);
	shortContent = linkString(shortContent, in);
	for (String[] s : subCategories) {
	    s[1] = linkString(s[1], in);
	}
	for (String[] s : grid) {
	    s[1] = linkString(s[1], in);
	}
    }

    public String linkString(String content, String in) {
	in = in.toUpperCase();
	String contentUp = content.toUpperCase();

	ArrayList<Integer> locations = new ArrayList<>();
	int pos = 0;
	int index = contentUp.indexOf(in);
	while (index != -1) {
	    locations.add(index + pos);
	    pos = pos + index + in.length();
	    contentUp = contentUp.substring(index + in.length());
	    index = contentUp.indexOf(in);
	}

	for (int i = locations.size() - 1; i >= 0; i--) {
	    index = locations.get(i);
	    content = content.substring(0, index)
		    + ArticleHTML.linkTo(in)
		    + content.substring(index, index + in.length())
		    + "</a>"
		    + content.substring(index + in.length(), content.length());
	}

	return content;
    }

    public static void link(Article a, Article b) {
	a.linkTo(b);
	b.linkTo(a);
    }

    @Override
    public int hashCode() {
	int hash = 3;
	hash = 97 * hash + Objects.hashCode(getName());
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
	final Article other = (Article) obj;
	if (!this.getName().equalsIgnoreCase(other.getName())) {
	    return false;
	}
	return true;
    }

    @Override
    public int compareTo(Object o) {
	Article b = (Article) o;
	if (getName().equalsIgnoreCase(b.getName())) {
	    return 0;
	}
	return this.getName().compareTo(b.getName());
    }
}
