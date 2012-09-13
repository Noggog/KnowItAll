/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.swing.SwingWorker;
import lev.Ln;
import lev.gui.LSwingTreeNode;

/**
 *
 * @author Justin Swanson
 */
public class Database {

    public static String source = "Seed Data/";
    public static Set<Category> categories = new TreeSet<>();
    public static LSwingTreeNode articleTree;
    public static Map<String, Article> articles = new TreeMap<>();

    public static void reloadArticles() {
	clear();
	loadLooseFiles();
    }

    public static void clear() {
	categories.clear();
	articles.clear();
	articleTree = new LSwingTreeNode();
    }

    public static void loadLooseFiles() {
	LooseFileLoader load = new LooseFileLoader();
	load.execute();
    }

    public static void doneLoading() {
	int wer = 23;
    }

    static class LooseFileLoader extends SwingWorker<Integer, Article> {

	@Override
	protected void done() {
	    doneLoading();
	}

	@Override
	protected Integer doInBackground() throws Exception {
	    File seeds = new File(source);
	    for (File categoryDir : seeds.listFiles()) {
		loadCategory(articleTree, categoryDir);
	    }
	    printArticles();
	    return 0;
	}

	void loadCategory(LSwingTreeNode node, File categoryDir) {
	    try {
		Category curCategory = new Category();
		if (curCategory.load(node, categoryDir)) {
		    node.add(curCategory);
		    categories.add(curCategory);

		    for (File f : categoryDir.listFiles()) {
			if (f.isDirectory()) {
			    // If articles folder
			    if (f.getName().equalsIgnoreCase("Articles")) {
				loadCategoryContents(curCategory, f);
			    } else // Otherwise assume it's a category folder
			    {
				loadCategory(curCategory, f);
			    }
			}
		    }
		}
	    } catch (Exception e) {
		Debug.log.logException(e);
	    }
	}

	void loadCategoryContents(Category category, File articleDir) {
	    if (articleDir.isDirectory()) {
		for (File articleSpec : articleDir.listFiles()) {
		    try {
			if (articleSpec.isFile()
				&& Ln.isFileType(articleSpec, "JSON")) {
			    Article a = new Article(category);
			    if (a.load(articleSpec)) {
				publishArticle(category, a);
			    }
			}
		    } catch (Exception e) {
			Debug.log.logException(e);
		    }
		}
	    }
	}

	void publishArticle(Category c, Article a) {
	    for (Article rhs : Database.articles.values()) {
		Article.link(a, rhs);
	    }
	    c.add(a);
	    articles.put(a.getName().toUpperCase(), a);
	}
    }

    public static void printArticles() {
	if (Debug.log.logging()) {
	    Debug.log.newLog("DatabaseContents.txt");
	    for (Object cn : articleTree.getAllObjects()) {
		printCategory(0, (Category) cn);
	    }
	}
    }

    public static void printCategory(int depth, Category c) {
	String depthStr = Ln.getNAmount(depth, "   ");
	Debug.log.log("Category", depthStr + "========= Category: " + c + "===========");

	Set<Category> categories = new TreeSet<>();
	Set<Article> articles = new TreeSet<>();
	for (Object o : c.getAllObjects()) {
	    if (o instanceof Category) {
		categories.add((Category) o);
	    } else if (o instanceof Article) {
		articles.add((Article) o);
	    }
	}

	int i = 1;
	for (Article a : articles) {
	    Debug.log.log("Article", depthStr + "     " + i++ + ": " + a);
	}

	for (Category subC : categories) {
	    printCategory(depth + 1, subC);
	}
    }
}
