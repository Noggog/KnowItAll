/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.imageio.ImageIO;
import lev.Ln;
import lev.gui.LSwingTreeNode;

/**
 *
 * @author Justin Swanson
 */
public class CategoryIndex extends LSwingTreeNode implements Comparable {

    String name;
    Set<String> subCategorySet = new HashSet<>();
    ArrayList<String> subCategoryOrder = new ArrayList<>();
    Set<String> gridSet = new HashSet<>();
    ArrayList<String> gridOrder = new ArrayList<>();
    BufferedImage icon;
    String iconPath = "";

    public CategoryIndex(File src) {
	name = src.getName();
	for (File specF : src.listFiles()) {
	    if (Ln.isFileType(specF, "JSON") || Ln.isFileType(specF, "TXT")) {
		load(specF);
	    } else if (Ln.isFileType(specF, "JPG")
		    || Ln.isFileType(specF, "JPEG")
		    || Ln.isFileType(specF, "PNG")) {
		try {
		    icon = ImageIO.read(specF);
		    iconPath = specF.getAbsolutePath();
		} catch (IOException ex) {
		    Debug.log.logError("Category Icon", "Failed to load image: " + specF);
		}
	    }
	}
    }

    public CategoryIndex(String name) {
	this.name = name;
    }

    final public boolean load(File specF) {
	if (specF.isFile()) {
	    try {
		// Load Spec
		CategorySpec spec = KnowItAll.gson.fromJson(new FileReader(specF), CategorySpec.class);
		if (spec == null) {
		    String error = "Skipped because it had a null spec: " + specF;
		    Debug.log.logError("CategoryIndex", error);
		    Debug.log.logSpecial(Debug.Logs.BLOCKED_ARTICLES, "Category", error);
		    return false;
		}
		// Get Category Name
		if (spec.name == null || spec.name.equals("")) {
		    spec.name = specF.getName().substring(0, specF.getName().indexOf('.'));
		}
		// Load specfile in
		merge(spec.extraSubCategories, subCategorySet, subCategoryOrder);
		merge(spec.grid, gridSet, gridOrder);

	    } catch (FileNotFoundException ex) {
		Debug.log.logException(ex);
	    }
	}
	return true;
    }

    public void inherit(CategoryIndex rhs) {
	merge(rhs.gridOrder.toArray(new String[0]), gridSet, gridOrder);
	merge(rhs.subCategoryOrder.toArray(new String[0]), subCategorySet, subCategoryOrder);
    }

    public void merge(String[] strs, Set<String> set, ArrayList<String> order) {
	for (String s : strs) {
	    String su = s.toUpperCase();
	    if (!set.contains(su)) {
		set.add(su);
		order.add(s);
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
	final CategoryIndex other = (CategoryIndex) obj;
	if (!this.getName().equalsIgnoreCase(other.getName())) {
	    return false;
	}
	return true;
    }

    public String getName() {
	return name;
    }

    @Override
    public String toString() {
	return getName();
    }

    @Override
    public int compareTo(Object o) {
	CategoryIndex rhs = (CategoryIndex) o;
	if (equals(rhs)) {
	    return 0;
	}
	return getName().compareTo(rhs.getName());
    }

    public String getIcon() {
	return iconPath;
    }
}
