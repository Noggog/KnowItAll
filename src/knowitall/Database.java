/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
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
	GUI.progressShow();
	LooseFileLoader load = new LooseFileLoader(run);
	load.execute();
    }

    public static void loadLooseFiles() {
	loadLooseFiles(null);
    }

    static class LooseFileLoader extends SwingWorker<Integer, Article> {

	Runnable runAfter = null;

	LooseFileLoader() {
	}

	LooseFileLoader(Runnable runAfter) {
	    this.runAfter = runAfter;
	}

	@Override
	protected Integer doInBackground() throws Exception {
	    loadPackage();
	    if (runAfter != null) {
		runAfter.run();
	    }
	    doneLoading();
	    return 0;
	}
    }

    static void loadPackage() {
	File seeds = new File(packages);
	// For the first package
	for (File packageF : seeds.listFiles()) {
	    // Set up progress
	    ArrayList<File> files = Ln.generateFileList(packageF, false);
	    GUI.progressSetMax(files.size());
	    GUI.progressSetTitle("Loading Package: " + packageF.getName());

	    // Start importing Category Index Files
	    articleTree = new CategoryIndex(packageF.getName());
	    File categoryIndices = new File(packageF.getPath() + "/" + categoryIndexPath);
	    // If category index exists
	    if (categoryIndices.isDirectory()) {
		// Load Category Indices
		indexTree = new CategoryIndex(categoryIndices);
		loadCategoryIndex(indexTree, categoryIndices);
		// Load content
		for (File sourceDir : packageF.listFiles()) {
		    // For every Source
		    if (sourceDir.isDirectory() && !sourceDir.getName().equalsIgnoreCase(categoryIndexPath)) {
			Source src = new Source(sourceDir);
			articleTree.add(src);
			// For every category in source
			for (File categoryDir : sourceDir.listFiles()) {
			    loadCategoryFolder(src, categoryDir);
			}
		    }
		}
	    }
	    break;
	}
	GUI.progressProcessed("Finishing Up");
    }

    static void loadCategoryIndex(CategoryIndex parent, File dir) {
	for (File f : dir.listFiles()) {
	    if (f.isDirectory()) {
		CategoryIndex cat = new CategoryIndex(f);
		GUI.progressIncrement(cat.getName());
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

    static void loadCategoryFolder(LSwingTreeNode node, File categoryDir) {
	try {
	    // Find matching category index
	    CategoryIndex index = Database.categoryIndex.get(categoryDir.getName().toUpperCase());
	    if (index != null) {
		Category curCategory = new Category(index);
		node.add(curCategory);
		GUI.progressProcessed("Processing: " + curCategory.getName());
		for (File f : categoryDir.listFiles()) {
		    if (f.isFile()
			    && (Ln.isFileType(f, "JSON") || Ln.isFileType(f, "TXT"))) {
			loadArticle(curCategory, f);
		    } else if (f.isDirectory()) {
			loadCategoryFolder(curCategory, f);
		    }
		}
	    } else {
		Debug.log.logSpecial(Logs.BLOCKED_ARTICLES, "Category", "Blocked because it didn't have a matching category index: " + categoryDir);
	    }
	} catch (Exception e) {
	    Debug.log.logError("Load Category Folder", "Error loading " + categoryDir);
	    Debug.log.logException(e);
	}
    }

    static void loadArticle(Category c, File articleF) {
	try {
	    Article a = new Article(c);
	    if (a.load(articleF)) {
		c.add(a);
		articles.put(a.getName().toUpperCase(), a);
	    }
	} catch (Exception e) {
	    Debug.log.logError("Load Article", "Error loading " + articleF);
	    Debug.log.logException(e);
	}
	GUI.progressIncrement();
    }

    public static void doneLoading() {
	GUI.progressSetTitle("Linking");
	Collection<Article> allArticles = articles.values();
	GUI.progressSetValueRemaining(allArticles.size());
	for (Article a : allArticles) {
	    try {
		for (Article rhs : allArticles) {
		    a.linkTo(rhs);
		}
		a.createLinks();
	    } catch (Exception e) {
		Debug.log.logError("Database Create Links", "Error creating links for " + a);
		Debug.log.logException(e);
	    }
	    a.clean();
	    GUI.progressIncrement();
	    GUI.progressProcessed("Linked " + a.getName());
	}
	GUI.finalizeArticles(articles.values());
	printArticles();
	GUI.loaded();
	int wer = 23;
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
