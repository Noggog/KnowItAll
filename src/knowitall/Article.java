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
    LinkString content;
    LinkString shortContent;
    Map<String, LinkString> subCategories = new HashMap<>();
    Map<String, LinkString> grid = new HashMap<>();
    int pageNumber;
    boolean blockLinking;

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
		spec.name = specF.getName().substring(0, specF.getName().indexOf('.'));
	    }
	    spec.src = specF;
	    spec.clean(category);
	    specFile = specF;
	    name = spec.name;
	    if (Database.hasArticle(name)) {
		String error = "Skipped because an article already existed with that name: " + name + " (Orig: " + Database.getArticle(name).specFile + " | Conflict File: " + specF + ")";
		Debug.log.logSpecial(Logs.BLOCKED_ARTICLES, "Article", error);
		return false;
	    }
	    shortContent = new LinkString(spec.shortContent);
	    subCategories = getAttributes(spec.extraSubCategories);
	    grid = getAttributes(spec.grid);
	    content = new LinkString(spec.content);
	    pageNumber = spec.pageNumber;
	    blockLinking = spec.blockLinking;
	    reloadHTML();
	    return true;
	} catch (FileNotFoundException ex) {
	    Debug.log.logSpecial(Logs.BLOCKED_ARTICLES, "Article", "Skipped because its spec could not be found: " + specF);
	} catch (com.google.gson.JsonSyntaxException ex) {
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
	return content.toString();
    }

    public String getShort() {
	return shortContent.toString();
    }

    public Map<String, LinkString> getSubcategories() {
	return subCategories;
    }

    public Map<String, LinkString> getGrid() {
	return grid;
    }

    Map<String, LinkString> getAttributes(String[][] strs) {
	Map<String, LinkString> out = new HashMap<>(strs.length);
	for (String[] sub : strs) {
	    if (sub.length == 2) {
		out.put(sub[0], new LinkString(sub[1]));
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
    }

    public Set<Article> getLinks() {
	return linked;
    }

    public void linkTo(Article a) {
	if (!equals(a) && !a.blockLinking && contentContains(a.getName())) {
	    linked.add(a);
	}
    }

    public void createLinks() {
	// Sort articles from longest to shortest
	LMergeMap<Integer, Article> lengthSort = new LMergeMap<>(false, true);
	for (Article a : linked) {
	    lengthSort.put(Integer.MAX_VALUE - a.getName().length(), a);
	}

	for (Article a : lengthSort.valuesFlat()) {
	    for (LinkString s : allLinkable()) {
		s.addLink(a);
	    }
	}
	reloadHTML();
    }

    public void reloadHTML() {
	html = ArticleHTML.load(this, true);
	htmlShort = ArticleHTML.load(this, false);
    }

    public boolean contentContains(String in) {
	for (LinkString s : allLinkable()) {
	    if (s.contains(in)) {
		return true;
	    }
	}
	return false;
    }

    public ArrayList<LinkString> allLinkable() {
	ArrayList<LinkString> out = new ArrayList<>();
	out.add(content);
	out.add(shortContent);
	out.addAll(subCategories.values());
	out.addAll(grid.values());
	return out;
    }

    public ArrayList<Integer> getStringLocations(String content, String in) {
	in = in.toUpperCase();
	String contentUp = content.toUpperCase();

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
