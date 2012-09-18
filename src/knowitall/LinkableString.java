/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.util.ArrayList;

/**
 *
 * @author Justin Swanson
 */
public class LinkableString {

    ArrayList<LSObject> content = new ArrayList<>();

    public LinkableString(String orig) {
	content.add(new NonLink(orig));
    }

    public boolean addLink(Article a) {
	ArrayList<LSObject> tmp = new ArrayList<>(content);
	content.clear();
	boolean linked = false;
	for (LSObject o : tmp) {
	    if (o instanceof NonLink) {
		NonLink l = (NonLink) o;
		ArrayList<LSObject> links = l.link(a);
		if (links.size() > 1) {
		    linked = true;
		}
		content.addAll(links);
	    } else {
		content.add(o);
	    }
	}
	return linked;
    }

    public abstract class LSObject {
    }

    public class Link extends LSObject {

	Article a;

	Link(Article a) {
	    this.a = a;
	}

	@Override
	public String toString() {
	    return a.getName();
	}
    }

    public class NonLink extends LSObject {

	String str;

	NonLink(String in) {
	    str = in;
	}

	public ArrayList<LSObject> link(Article a) {
	    ArrayList<LSObject> out = new ArrayList<>();
	    ArrayList<Integer> locations = getStringLocations(str, a.getName());
	    for (Integer i : locations) {
		// Make sure the string isn't a suffix of a different word
		if (i != 0 && Character.isAlphabetic(str.charAt(i - 1))) {
		    continue;
		}

		// Pre link text
		if (i > 0) {
		    out.add(new NonLink(str.substring(0, i)));
		}

		// Link
		out.add(new Link(a));

		// Shave string
		str = str.substring(i + a.getName().length());
	    }
	    // If there is text remaining after the last link, add it.
	    if (str.length() > 0) {
		out.add(this);
	    }
	    return out;
	}

	@Override
	public String toString() {
	    return str;
	}
    }

    static public ArrayList<Integer> getStringLocations(String content, String in) {
	in = in.toUpperCase();
	String contentUp = content.toUpperCase();

	ArrayList<Integer> locations = new ArrayList<>();
	int pos = 0;
	int index = contentUp.indexOf(in);
	while (index != -1) {
	    locations.add(index);
	    pos = pos + index + in.length();
	    contentUp = contentUp.substring(index + in.length());
	    index = contentUp.indexOf(in);
	}
	return locations;
    }
}
