/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.SwingWorker;
import lev.LMergeMap;
import lev.Ln;

/**
 *
 * @author Justin Swanson
 */
public class Database {

    public static String source = "Seed Data/";
    public static Set<Category> categories = new HashSet<>();
    public static LMergeMap<Category, Article> articleMap = new LMergeMap<>(true);
    public static Map<String, Article> articles = new TreeMap<>();

    public static void loadLooseFiles() {
	LooseFileLoader load = new LooseFileLoader();
	load.execute();
    }

    static class LooseFileLoader extends SwingWorker<Integer, Article> {

	@Override
	protected void done() {
	    Map<String, Article> tmp = articles;
	    int wer = 23;
	}

	@Override
	protected Integer doInBackground() throws Exception {
	    File seeds = new File(source);
	    for (File categoryDir : seeds.listFiles()) {
		try {
		    Category category = new Category();
		    if (category.load(categoryDir)) {
			categories.add(category);
			loadCategoryContents(category, new File(categoryDir.getPath() + "/Articles"));
		    }
		} catch (Exception e) {
		    Debug.log.logException(e);
		}
	    }
	    printArticles();
	    return 0;
	}

	void loadCategoryContents(Category category, File articleDir) {
	    if (articleDir.isDirectory()) {
		for (File articleSpec : articleDir.listFiles()) {
		    try {
			if (articleSpec.isFile()
				&& Ln.isFileType(articleSpec, "JSON")) {
			    Article a = new Article(category);
			    if (a.load(articleSpec)) {
				publishArticle(a);
			    }
			}
		    } catch (Exception e) {
			Debug.log.logException(e);
		    }
		}
	    }
	}

	void publishArticle(Article a) {
	    for (Article rhs : Database.articles.values()) {
		Article.link(a,rhs);
	    }
	    Database.articleMap.put(a.category, a);
	    Database.articles.put(a.getName().toUpperCase(), a);
	}
    }

    public static void printArticles() {
	if (Debug.log.logging()) {
	    Debug.log.newLog("DatabaseContents.txt");
	    for (Category c : articleMap.keySet()) {
		Debug.log.log("Category", "========= Category: " + c + "===========");
		int i = 1;
		for (Article a : articleMap.get(c)) {
		    Debug.log.log("Category", "     " + i++ + ": " + a);
		}
	    }
	}
    }
}
