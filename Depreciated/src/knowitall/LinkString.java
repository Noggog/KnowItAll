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
public class LinkString {

    ArrayList<LSObject> content = new ArrayList<>();

    public LinkString(String orig) {
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

    public void removeLink(Article a) {
	for (LSObject o : content) {
	    if (o instanceof Link) {
		Link l = (Link) o;
		if (l.a.equals(a)) {
		    l.On(false);
		}
	    }
	}
    }
    
    public boolean hasLink() {
	return content.size() > 1;
    }

    public abstract class LSObject {
    }

    public class Link extends LSObject {

	boolean on = true;
	Article a;
	String s;

	Link(Article a, String s) {
	    this.a = a;
	    this.s = s;
	}

	@Override
	public String toString() {
	    if (on) {
		return ArticleHTML.linkTo(a, s);
	    } else {
		return s;
	    }
	}

	public void On(boolean on) {
	    this.on = on;
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
	    int offset = 0;
	    for (Integer i : locations) {

		i += offset;

		// Make sure the string isn't a suffix of a different word
		if (i != 0 && Character.isAlphabetic(str.charAt(i - 1))) {
		    offset += i + a.getName().length();
		    continue;
		}

		// Pre link text
		if (i > 0) {
		    out.add(new NonLink(str.substring(0, i)));
		}

		// Link
		out.add(new Link(a, str.substring(i, i + a.getName().length())));

		// Shave string
		str = str.substring(i + a.getName().length());

		offset = 0;
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

    @Override
    public String toString() {
	String out = "";
	for (LSObject o : content) {
	    out += o.toString();
	}
	return out;
    }

    public boolean isEmpty() {
	return "".equals(toString());
    }

    public boolean contains(String in) {
	String all = "";
	for (LSObject o : content) {
	    if (o instanceof NonLink) {
		all += o.toString().toUpperCase();
	    }
	}
	return all.contains(in.toUpperCase());
    }
}
