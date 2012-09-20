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
    String allContent = "";
    ArrayList<SourcePair> sources = new ArrayList<>(1);
    boolean blockLinking;

    Article(Source s, Category c) {
	sources.add(new SourcePair(s));
	category = c;
    }

    public boolean load(File specF) {
	specFile = specF;
	if (loadSpec(specF)) {
	    // Bad Spec
	    return false;
	}
	// If Database already has article with the name
	// We need to merge or block
	if (Database.hasArticle(name)) {
	    Article orig = Database.getArticle(name);
	    Logs log;
	    String srcs = "";
	    if (Debug.log.logging()) {
		for (SourcePair s : orig.sources) {
		    srcs += s.src.getName() + "  |  ";
		}
	    }
	    // If in the same category, merge.
	    if (orig.category.equals(category)) {
		orig.mergeIn(this);
		log = Logs.MERGED_ARTICLES;
	    } else {
		log = Logs.BLOCKED_ARTICLES;
	    }
	    if (Debug.log.logging()) {
		Debug.log.logSpecial(log, "Article", name + " - An article already existed under that name.");
		Debug.log.logSpecial(log, "Article", "    Orig sources:  " + srcs);
		Debug.log.logSpecial(log, "Article", "    Orig: " + orig.specFile);
		Debug.log.logSpecial(log, "Article", "     New: " + specF);
	    }
	    return false;
	}
	return true;
    }

    public boolean loadSpec(File specF) {
	try {
	    ArticleSpec spec = KnowItAll.gson.fromJson(new FileReader(specF), ArticleSpec.class);
	    if (spec == null) {
		String error = "Skipped because it had a null spec: " + specF;
		Debug.log.logSpecial(Logs.BLOCKED_ARTICLES, "Article", error);
		return true;
	    }
	    if (spec.name == null || spec.name.equals("")) {
		spec.name = specF.getName().substring(0, specF.getName().indexOf('.'));
	    }
	    spec.src = specF;
	    spec.clean(category);
	    name = spec.name;
	    shortContent = new LinkString(spec.shortContent);
	    subCategories = getAttributes(spec.extraSubCategories);
	    grid = getAttributes(spec.grid);
	    content = new LinkString(spec.content);
	    sources.get(0).page = spec.pageNumber;
	    blockLinking = spec.blockLinking;
	    return false;
	} catch (FileNotFoundException ex) {
	    Debug.log.logSpecial(Logs.BLOCKED_ARTICLES, "Article", "Skipped because its spec could not be found: " + specF);
	} catch (com.google.gson.JsonSyntaxException ex) {
	    Debug.log.logSpecial(Logs.BLOCKED_ARTICLES, "Article", "Skipped because it had a badly formatted spec: " + specF);
	}
	return true;
    }

    public void mergeIn(Article a) {
	if (!a.content.isEmpty()) {
	    content = a.content;
	}
	if (!a.shortContent.isEmpty()) {
	    shortContent = a.shortContent;
	}
	for (String s : a.subCategories.keySet()) {
	    subCategories.put(s, a.subCategories.get(s));
	}
	for (String s : a.grid.keySet()) {
	    grid.put(s, a.grid.get(s));
	}
	sources.add(a.getSources().get(0));
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

    public ArrayList<SourcePair> getSources() {
	return sources;
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
	allContent = "";
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
	in = in.toUpperCase();
	if ("".equals(allContent)) {
	    for (LinkString s : allLinkable()) {
		allContent += s.toString().toUpperCase() + " ";
	    }
	}
	return allContent.contains(in);
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

    public class SourcePair {

	SourcePair(Source src) {
	    this.src = src;
	}
	Source src;
	int page = 0;
    }
}
