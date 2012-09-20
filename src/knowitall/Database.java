/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private static ArrayList<Source> loadOrder = new ArrayList<>();

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
	loadOrder.clear();
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
	for (File packageDir : seeds.listFiles()) {

	    // Set up progress
	    ArrayList<File> files = Ln.generateFileList(packageDir, false);
	    GUI.progressSetMax(files.size());
	    GUI.progressSetTitle("Loading Package: " + packageDir.getName());

	    // Start importing Category Index Files
	    articleTree = new CategoryIndex(packageDir.getName());
	    File categoryIndices = new File(packageDir.getPath() + "/" + categoryIndexPath);

	    // If category index exists
	    if (categoryIndices.isDirectory()) {

		// Load Category Indices
		indexTree = new CategoryIndex(categoryIndices);
		loadCategoryIndex(indexTree, categoryIndices);

		// Obtain Load Order
		loadOrder = loadOrder(packageDir);

		// Load content
		for (Source s : loadOrder) {
		    loadSource(s);
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

    static ArrayList<Source> loadOrder(File packageDir) {
	ArrayList<Source> tmp = new ArrayList<>();
	ArrayList<Source> out = new ArrayList<>();
	for (File src : packageDir.listFiles()) {
	    if (src.isDirectory() && !src.getName().equalsIgnoreCase(categoryIndexPath)) {
		tmp.add(new Source(src));
	    }
	}

	// Get string order
	ArrayList<String> order = new ArrayList<>();
	File loadOrder = new File(packageDir.getPath() + "/loadorder.txt");
	if (loadOrder.exists()) {
	    try {
		order = Ln.loadFileToStrings(loadOrder, true);
	    } catch (IOException ex) {
		Debug.log.logException(ex);
	    }
	}

	// Put sources in order on the out list
	for (String s : order) {
	    for (Source src : tmp) {
		if (src.name.equalsIgnoreCase(s)) {
		    out.add(src);
		    tmp.remove(src);
		    break;
		}
	    }
	}

	// Add the rest
	out.addAll(tmp);

	return out;
    }

    static void loadSource(Source source) {
	articleTree.add(source);
	// For every category in source
	for (File categoryDir : source.src.listFiles()) {
	    loadCategoryFolder(source, source, categoryDir);
	}
    }

    static void loadCategoryFolder(Source source, LSwingTreeNode node, File categoryDir) {
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
			loadArticle(source, curCategory, f);
		    } else if (f.isDirectory()) {
			loadCategoryFolder(source, curCategory, f);
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

    static void loadArticle(Source s, Category c, File articleF) {
	try {
	    Article a = new Article(s, c);
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
