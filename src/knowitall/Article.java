/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import lev.LFileChannel;
import lev.Ln;

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
}
