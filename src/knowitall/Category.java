/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;

/**
 *
 * @author Justin Swanson
 */
public class Category implements Comparable {

    CategorySpec spec;

    Category () {
    }

    public boolean load (File categoryDir) {
	File specF = new File(categoryDir.getPath() + "/" + CategorySpec.categorySpecFilename);
	if (specF.isFile()) {
	    try {
		spec = KnowItAll.gson.fromJson(new FileReader(specF), CategorySpec.class);
		if (spec != null && spec.name != null && !spec.name.equals("")) {
		    return true;
		}
	    } catch (FileNotFoundException ex) {
		Debug.log.logException(ex);
	    }
	}
	return false;
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
	if (!Objects.equals(this.getName(), other.getName())) {
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
	return spec.name.compareTo(rhs.spec.name);
    }

}
