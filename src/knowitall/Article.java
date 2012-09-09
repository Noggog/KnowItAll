/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author Justin Swanson
 */
public class Article {

    public Category category;
    ArticleSpec spec;

    Article(Category c) {
	category = c;
    }

    public boolean load(File specF) {
	try {
	    spec = KnowItAll.gson.fromJson(new FileReader(specF), ArticleSpec.class);
	    if (spec != null && spec.name != null && !spec.name.equals("")) {
		spec.clean();
		return true;
	    }
	} catch (FileNotFoundException ex) {
	    Debug.log.logException(ex);
	}
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
	if (spec.extraSubCategories == null) {
	    return new ArrayList<>(0);
	}
	ArrayList<String[]> out = new ArrayList<>(spec.extraSubCategories.length);
	for (String[] sub : spec.extraSubCategories) {
	    if (sub.length == 2) {
		out.add(sub);
	    }
	}
	return out;
    }
}
