/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.io.*;
import java.util.*;
import knowitall.Debug.Logs;
import lev.LMergeMap;
import lev.Ln;
import lev.gui.LSwingTreeNode;

/**
 *
 * @author Justin Swanson
 */
public class Article implements Comparable {

    public Category category;
    File specFile;
    ArrayList<String> names = new ArrayList<>(1);
    Set<Article> linked = new TreeSet<>();
    String html;
    String htmlShort;
    // Temporary
    LinkString content;
    LinkString shortContent;
    LinkString intro;
    Map<String, LinkString> subCategories = new HashMap<>();
    Map<String, LinkString> grid = new HashMap<>();
    String allContent = "";
    ArrayList<SourcePair> sources = new ArrayList<>(1);
    boolean blockLinking;
    public boolean divideContent;
    public boolean spacedSubcategories;

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
	if (Database.hasArticle(this)) {
	    Article orig = Database.getArticle(this);
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
		Debug.log.logSpecial(log, "Article", getName() + " - An article already existed under that name.");
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
	    ArticleSpec spec = KnowItAll.gson.fromJson(new InputStreamReader(new FileInputStream(specF), "ISO8859-1"), ArticleSpec.class);
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
	    names.add(spec.name);
	    intro = new LinkString(spec.intro);
	    shortContent = new LinkString(spec.shortContent);
	    subCategories = getAttributes(spec.extraSubCategories);
	    grid = getAttributes(spec.grid);
	    names.addAll(getAKA(spec.aka));
	    content = new LinkString(spec.content);
	    sources.get(0).page = spec.pageNumber;
	    blockLinking = spec.blockLinking;
	    divideContent = spec.divideContent;
	    spacedSubcategories = spec.spacedSubcategories;
	    return false;
	} catch (FileNotFoundException ex) {
	    Debug.log.logSpecial(Logs.BLOCKED_ARTICLES, "Article", "Skipped because its spec could not be found: " + specF);
	} catch (com.google.gson.JsonSyntaxException ex) {
	    Debug.log.logSpecial(Logs.BLOCKED_ARTICLES, "Article", "Skipped because it had a badly formatted spec: " + specF);
	} catch (UnsupportedEncodingException ex) {
	    Debug.log.logSpecial(Logs.BLOCKED_ARTICLES, "Article", "Skipped because it had unsupported encoding: " + specF);
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
	if (!a.intro.isEmpty()) {
	    intro = a.intro;
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
	return names.get(0);
    }

    public ArrayList<String> getNames() {
	return names;
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

    ArrayList<String> getAttributes(String[] strs) {
	ArrayList<String> out = new ArrayList<>(strs.length);
	for (String s : strs) {
	    s = s.trim();
	    if (!"".equals(s)) {
		out.add(s);
	    }
	}
	return out;
    }

    public ArrayList<String> getAKA(String[] strs) {
	ArrayList<String> tmp = getAttributes(strs);
	ArrayList<String> out = new ArrayList<>();
	for (String s : tmp) {
	    Article a = Database.getArticle(s);
	    // If no article exists already with AKA
	    if (a == null) {
		out.add(s);
		Debug.log.logSpecial(Logs.ACCEPTED_AKA, "AKA", "AKA " + s + " accepted for: " + this);
	    } else if (Debug.log.logging() && !equals(a)) {
		Debug.log.logSpecial(Logs.BLOCKED_ARTICLES, "AKA", "AKA blocked: " + s);
		Debug.log.logSpecial(Logs.BLOCKED_ARTICLES, "AKA", "   Orig: " + a);
		Debug.log.logSpecial(Logs.BLOCKED_ARTICLES, "AKA", "    New: " + this);
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

    public String getIntro() {
	return intro.toString();
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
	LMergeMap<Integer, Article> lengthSort = new LMergeMap<>(true);
	for (Article a : linked) {
	    lengthSort.put(Integer.MAX_VALUE - a.getName().length(), a);
	}

	// Add article's own name to be the first to be matched
	lengthSort.put(0, this);

	for (LinkString s : allLinkable()) {
	    for (Article a : lengthSort.valuesFlat()) {
		s.addLink(a);
	    }
	    // Remove the links to itself
	    s.removeLink(this);
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
//	if ((a.getName().equals("Plaguebearer") && b.getName().equals("Daemonic Presence"))
//		|| a.getName().equals("Daemonic Presence") && b.getName().equals("Plaguebearer")) {
//	    int wer = 23;
//	}
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

    public class SourcePair {

	SourcePair(Source src) {
	    this.src = src;
	}
	Source src;
	int page = 0;
    }


}
