/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import lev.LExporter;
import lev.LFileChannel;
import lev.Ln;

/**
 *
 * @author Justin Swanson
 */
public class Tools {

    public static void deleteAllWithName(Collection<Article> articles, String name) {
	for (Article a : articles) {
	    if (a.getName().equalsIgnoreCase(name)) {
		if (a.specFile.isFile()) {
		    a.specFile.delete();
		}
	    }
	}
    }

    public static void cleanFiles() {
	ArrayList<File> files = Ln.generateFileList(Database.packages, -1, -1, false);
	for (File f : files) {
	    try {
		LFileChannel in = new LFileChannel(f);
		byte[] file = in.extract(in.available());
		in.close();
		LExporter out = new LExporter(f);
		for (byte b : file) {
		    switch (b) {
			// Bad '-' character
			case (byte) 0x96:
			    b = (byte) 0x2D;
			    break;
			case (byte) 0x92:
			    b = (byte) 0x27;
			    break;
		    }
		    out.write((int) b, 1);
		}
		out.close();
	    } catch (IOException ex) {
		Debug.log.logException(ex);
	    }
	}
    }
}