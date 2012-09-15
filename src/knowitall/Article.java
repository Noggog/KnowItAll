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
    ArticleContent content;
    String name;
    ArticleContent shortContent;
    ArrayList<String[]> subCategories = new ArrayList<>();
    ArrayList<String[]> grid = new ArrayList<>();
    int pageNumber;
    Set<String> words = new HashSet<>();
    Set<Article> linked = new TreeSet<>();
    ArticleHTML html = new ArticleHTML();

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
	    shortContent = new ArticleContent(spec.shortContent);
	    subCategories = getAttributes(spec.extraSubCategories);
	    grid = getAttributes(spec.grid);
	    content = new ArticleContent(spec.content);
	    pageNumber = spec.pageNumber;
	    html.load(this);
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
	return content.get();
    }

    public String getShort() {
	return shortContent.get();
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

    public String getHTML() {
	return html.getHTML();
    }

    public void cleanInit() {
	words = null;
    }

    public Set<Article> getLinks() {
	return linked;
    }

    public void linkTo(Article a) {
	if (!equals(a) && words.contains(a.getName().toUpperCase())) {
	    linked.add(a);
	}
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
	if (b.getName().equalsIgnoreCase(b.getName())) {
	    return 0;
	}
	return this.getName().compareTo(b.getName());
    }
}
