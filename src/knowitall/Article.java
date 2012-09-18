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
import lev.LMergeMap;
import lev.Ln;
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
    ArrayList<String> allContent = new ArrayList<>();

    Article(Category c) {
	category = c;
    }

    public boolean load(File specF) {
	try {
	    ArticleSpec spec = KnowItAll.gson.fromJson(new FileReader(specF), ArticleSpec.class);
	    if (spec == null) {
		String error = "Skipped because it had a null spec: " + specF;
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
	    if (Database.hasArticle(name)) {
		String error = "Skipped because an article already existed with that name: " + name + "  | File: " + specF;
		Debug.log.logSpecial(Logs.BLOCKED_ARTICLES, "Article", error);
		return false;
	    }
	    shortContent = spec.shortContent;
	    subCategories = getAttributes(spec.extraSubCategories);
	    grid = getAttributes(spec.grid);
	    content = spec.content;
	    pageNumber = spec.pageNumber;
	    blockLinking = spec.blockLinking;
	    reload();
	    return true;
	} catch (FileNotFoundException ex) {
	    Debug.log.logSpecial(Logs.BLOCKED_ARTICLES, "Article", "Skipped because its spec could not be found: " + specF);
	} catch (com.google.gson.JsonSyntaxException ex) {
	    Debug.log.logSpecial(Logs.BLOCKED_ARTICLES, "Article", "Skipped because it had a badly formatted spec: " + specF);
	}
	return false;
    }

    public void reload() {
	createAllContent();
	reloadHTML();
    }

    public void createAllContent() {
	allContent.add(shortContent);
	allContent.add(content);
	for (String[] s : grid) {
	    allContent.add(s[1]);
	}
	for (String[] s : subCategories) {
	    allContent.add(s[1]);
	}
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
	content = null;
	shortContent = null;
	subCategories = null;
	grid = null;
	allContent = null;
    }

    public Set<Article> getLinks() {
	return linked;
    }

    public void linkTo(Article a) {
	if (!equals(a) && !a.blockLinking && contentContains(a.getName())) {
//	    if (a.getName().equalsIgnoreCase("Lord of Change") || getName().equalsIgnoreCase("Lord of Change")) {
//		int wer = 23;
//	    }
	    linked.add(a);
	}
    }

    public void linkText() {
//	if (getName().equalsIgnoreCase("Lord of change")) {
//	    int wer = 23;
//	}

	// Sort articles from longest to shortest
	LMergeMap<Integer, Article> lengthSort = new LMergeMap<>(false, true);
	for (Article a : linked) {
	    lengthSort.put(Integer.MAX_VALUE - a.getName().length(), a);
	}

	for (Article a : lengthSort.valuesFlat()) {
	    linkStrings(a.getName());
	}
	reloadHTML();
    }

    public void reloadHTML() {
	html = ArticleHTML.load(this, true);
	htmlShort = ArticleHTML.load(this, false);
    }

    public void linkStrings(String in) {
	content = linkStringHTML(content, in);
	shortContent = linkStringHTML(shortContent, in);
	for (String[] s : subCategories) {
	    s[1] = linkStringHTML(s[1], in);
	}
	for (String[] s : grid) {
	    s[1] = linkStringHTML(s[1], in);
	}
    }

    public String linkStringHTML(String content, String in) {
	ArrayList<Integer> locations = getStringLocations(content, in);
	int index;
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

    public boolean contentContains(String in) {
	for (String s : allContent) {
	    if (!getStringLocations(s, in).isEmpty()) {
		return true;
	    }
	}
	return false;
    }

    public ArrayList<Integer> getStringLocations(String content, String in) {
	in = in.toUpperCase();
	String contentUp = content.toUpperCase();
//	if (in.equals("CHARGE") && getName().equalsIgnoreCase("TRYGON")) {
//	    int wer = 23;
//	}

	ArrayList<Integer> locations = new ArrayList<>();
	int pos = 0;
	int index = contentUp.indexOf(in);
	while (index != -1) {
	    // Make sure the string isn't a suffix of a different word
	    if (index == 0 || !Character.isAlphabetic(contentUp.charAt(index - 1))) {
		locations.add(index + pos);
	    }
	    pos = pos + index + in.length();
	    contentUp = contentUp.substring(index + in.length());
	    index = contentUp.indexOf(in);
	}
	return locations;
    }

    public static void link(Article a, Article b) {
//	String f = "Lord of Change";
//	String s = "Combat Master";
//	if ((a.getName().equalsIgnoreCase(f) || b.getName().equalsIgnoreCase(f))
//		&& (a.getName().equalsIgnoreCase(s) || b.getName().equalsIgnoreCase(s))) {
//	    int wer = 23;
//	}
	a.linkTo(b);
	b.linkTo(a);
    }

    @Override
    public void print(int depth) {
	Debug.log.log(getName(), Ln.getNAmount(depth, "   ") + "Article: " + getName() + " ==============");
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
