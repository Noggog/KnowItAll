/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import knowitall.Debug.Logs;
import lev.gui.LSwingTreeNode;

/**
 *
 * @author Justin Swanson
 */
public class Category extends LSwingTreeNode implements Comparable {

    CategorySpec spec;
    Set<String> subCategorySet = new HashSet<>();
    ArrayList<String> subCategoryOrder = new ArrayList<>();

    Category() {
    }

    public boolean load(LSwingTreeNode node, File categoryDir) {
	if (node instanceof Category) {
	    mergeSubCategories(((Category) node).spec.extraSubCategories);
	}
	File specF = new File(categoryDir.getPath() + "/" + CategorySpec.categorySpecFilename);
	if (specF.isFile()) {
	    try {
		spec = KnowItAll.gson.fromJson(new FileReader(specF), CategorySpec.class);
		if (spec == null) {
		    String error = "Skipped because it had a null spec: " + categoryDir;
		    Debug.log.logError("Category", error);
		    Debug.log.logSpecial(Logs.BLOCKED_ARTICLES, "Category", error);
		    return false;
		}
		if (spec.name == null || spec.name.equals("")) {
		    spec.name = categoryDir.getName();
		}
		if (Database.categories.contains(this)) {
		    String error = "Skipped because Database already had a category with the same name: " + spec.name + " (" + categoryDir + ")";
		    Debug.log.logError("Category", error);
		    Debug.log.logSpecial(Logs.BLOCKED_ARTICLES, "Category", error);
		} else {
		    mergeSubCategories(spec.extraSubCategories);
		    return true;
		}
	    } catch (FileNotFoundException ex) {
		Debug.log.logException(ex);
	    }
	} else {
	    Debug.log.log("Category", "Assumed defaults because it had no spec: " + categoryDir);
	    spec = new CategorySpec();
	    spec.name = categoryDir.getName();
	    return true;
	}
	return false;
    }

    public void mergeSubCategories(String[] strs) {
	for (String s : strs) {
	    String su = s.toUpperCase();
	    if (!subCategorySet.contains(su)) {
		subCategorySet.add(su);
		subCategoryOrder.add(s);
	    }
	}
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 83 * hash + Objects.hashCode(getName());
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
	final Category other = (Category) obj;
	if (!this.getName().equalsIgnoreCase(other.getName())) {
	    return false;
	}
	return true;
    }

    public String getName() {
	return spec.name;
    }

    @Override
    public String toString() {
	return getName();
    }

    @Override
    public int compareTo(Object o) {
	Category rhs = (Category) o;
	if (equals(rhs)) {
	    return 0;
	}
	return spec.name.compareTo(rhs.spec.name);
    }

    public String getIcon() {
	return "Icon";
    }
}
