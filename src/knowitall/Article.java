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
    String name = "";
    ArticleSpec spec;
    Set<String> words = new HashSet<>();
    Set<Article> linked = new TreeSet<>();
    ArticleHTML html = new ArticleHTML();

    Article(Category c) {
	category = c;
    }

    public boolean load(File specF) {
	try {
	    spec = KnowItAll.gson.fromJson(new FileReader(specF), ArticleSpec.class);
	    if (spec != null && spec.name != null && !spec.name.equals("")) {
		spec.src = specF;
		spec.clean(category);
		words = spec.getWords();
		html.load(this);
		return true;
	    }
	} catch (FileNotFoundException ex) {
	} catch (com.google.gson.JsonSyntaxException ex) {
	    Debug.log.logException(ex);
	}
	Debug.log.logMain("Article Load", "Blocked Article: " + name + " with specfile path: " + specF.getPath());
	Debug.log.logSpecial(Logs.BLOCKED_ARTICLES, name, "Blocked article: " + name + " with specfile path: " + specF.getPath());
	return false;
    }

    public String getName() {
	return spec.name;
    }

    @Override
    public String toString() {
	return getName();
    }

    public String getContent() {
	return spec.content;
    }

    public String getShort() {
	return spec.shortContent;
    }

    public ArrayList<String[]> getSubcategories() {
	return getAttributes(spec.extraSubCategories);
    }

    public ArrayList<String[]> getGrid() {
	return getAttributes(spec.grid);
    }

    public ArrayList<String[]> getAttributes(String[][] strs) {
	ArrayList<String[]> out = new ArrayList<>(strs.length);
	for (String[] sub : strs) {
	    if (sub.length == 2) {
		out.add(sub);
	    }
	}
	return out;
    }

    public Integer getPageNum() {
	return spec.pageNumber;
    }

    public String getIcon() {
	return category.getIcon();
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
	hash = 97 * hash + Objects.hashCode(this.spec.name);
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
	if (!Objects.equals(this.spec.name, other.spec.name)) {
	    return false;
	}
	return true;
    }

    @Override
    public int compareTo(Object o) {
	Article b = (Article) o;
	return this.getName().compareTo(b.getName());
    }
}
