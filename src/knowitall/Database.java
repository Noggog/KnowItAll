/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.SwingWorker;
import knowitall.Debug.Logs;
import knowitall.gui.GUI;
import lev.Ln;
import lev.gui.LSwingTreeNode;

/**
 *
 * @author Justin Swanson
 */
public class Database {

    public final static String packages = "Packages";
    private static String categoryIndexPath = "KIA Category Index";
    private static CategoryIndex indexTree;
    private static Map<String, CategoryIndex> categoryIndex = new TreeMap<>();
    private static LSwingTreeNode articleTree;
    private static Map<String, Article> articles = new TreeMap<>();

    public static void reloadArticles(Runnable run) {
	GUI.displayArticles(false);
	clear();
	loadLooseFiles(run);
    }

    public static void reloadArticles() {
	reloadArticles(null);
    }

    public static void clear() {
	categoryIndex.clear();
	articles.clear();
	articleTree = new LSwingTreeNode();
	GUI.regenerateTree();
    }

    public static LSwingTreeNode getTree() {
	return articleTree;
    }

    public static void loadLooseFiles(Runnable run) {
	LooseFileLoader load = new LooseFileLoader(run);
	load.execute();
    }

    public static void loadLooseFiles() {
	loadLooseFiles(null);
    }

    public static void doneLoading() {
	for (Article a : articles.values()) {
	    a.linkText();
	    a.clean();
	}
	printArticles();
	GUI.regenerateTree();
	GUI.updateContentDisplay();
	int wer = 23;
    }

    static class LooseFileLoader extends SwingWorker<Integer, Article> {

	Runnable runAfter = null;

	@Override
	protected void done() {
	    if (runAfter != null) {
		runAfter.run();
	    }
	    doneLoading();
	}

	LooseFileLoader() {
	}

	LooseFileLoader(Runnable runAfter) {
	    this.runAfter = runAfter;
	}

	@Override
	protected Integer doInBackground() throws Exception {
	    File seeds = new File(packages);
	    for (File packageF : seeds.listFiles()) {
		articleTree = new CategoryIndex(packageF.getName());
		File categoryIndices = new File(packageF.getPath() + "/" + categoryIndexPath);
		if (categoryIndices.isDirectory()) {
		    // Load Category Indices
		    indexTree = new CategoryIndex(categoryIndices);
		    loadCategoryIndex(indexTree, categoryIndices);
		    // Load sources and articles
		    for (File sourceDir : packageF.listFiles()) {
			if (sourceDir.isDirectory() && !sourceDir.getName().equalsIgnoreCase(categoryIndexPath)) {
			    Source src = new Source(sourceDir);
			    articleTree.add(src);
			    for (File categoryDir : sourceDir.listFiles()) {
				loadCategoryFolder(src, categoryDir);
			    }
			}
		    }
		}
		break;
	    }
	    return 0;
	}

	void loadCategoryIndex(CategoryIndex parent, File dir) {
	    // Load children categories
	    for (File f : dir.listFiles()) {
		if (f.isDirectory()) {
		    CategoryIndex cat = new CategoryIndex(f);
		    if (!categoryIndex.containsKey(cat.getName().toUpperCase())) {
			cat.inherit(parent);
			parent.add(cat);
			categoryIndex.put(cat.toString().toUpperCase(), cat);
			loadCategoryIndex(cat, f);
		    } else {
			String error = "Skipped because Database already had a category with the same name: " + cat.getName() + " (" + f + ")";
			Debug.log.logError("CategoryIndex", error);
			Debug.log.logSpecial(Debug.Logs.BLOCKED_ARTICLES, "Category", error);
		    }
		}
	    }
	}

	void loadCategoryFolder(LSwingTreeNode node, File categoryDir) {
	    try {
		CategoryIndex index = Database.categoryIndex.get(categoryDir.getName().toUpperCase());
		if (index != null) {
		    Category curCategory = new Category(index);
		    node.add(curCategory);
		    for (File f : categoryDir.listFiles()) {
			if (f.isFile()
				&& Ln.isFileType(f, "JSON")) {
			    Article a = new Article(curCategory);
			    if (a.load(f)) {
				publishArticle(curCategory, a);
			    }
			} else if (f.isDirectory()) {
			    loadCategoryFolder(curCategory, f);
			}
		    }
		} else {
		    Debug.log.logSpecial(Logs.BLOCKED_ARTICLES, "Category", "Blocked because it didn't have a matching category index: " + categoryDir);
		}
	    } catch (Exception e) {
		Debug.log.logException(e);
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

    public static Article getArticle(String name) {
	return articles.get(name.toUpperCase());
    }

    public static boolean hasArticle(String name) {
	return articles.containsKey(name.toUpperCase());
    }

    public static void printArticles() {
	if (Debug.log.logging()) {
	    Debug.log.newLog("DatabaseContents.txt");
	    articleTree.print(0);
	}
    }
}
