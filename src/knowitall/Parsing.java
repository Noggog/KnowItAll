/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Justin Swanson
 */
public class Parsing {

    static final HashSet<String> excludes = new HashSet<>();
    static {
	excludes.add("AS");
	excludes.add("HAS");
	excludes.add("CANNOT");
	excludes.add("BUT");
	excludes.add("AT");
	excludes.add("AN");
	excludes.add("ON");
	excludes.add("IS");
	excludes.add("IN");
	excludes.add("HE");
	excludes.add("SHE");
	excludes.add("INSTEAD");
	excludes.add("THE");
	excludes.add("WITH");
	excludes.add("MADE");
	excludes.add("THAT");
	excludes.add("THEY");
	excludes.add("UNTIL");
	excludes.add("WITHIN");
	excludes.add("WITHOUT");
	excludes.add("WHEN");
	excludes.add("SO");
	excludes.add("MAKING");
	excludes.add("NO");
	excludes.add("MAKES");
	excludes.add("MORE");
	excludes.add("HIS");
	excludes.add("ARE");
	excludes.add("BE");
	excludes.add("OR");
	excludes.add("AND");
	excludes.add("IF");
	excludes.add("CAN");
	excludes.add("WHILE");
	excludes.add("BOTH");
	excludes.add("NEXT");
	excludes.add("TO");
	excludes.add("BEFORE");
	excludes.add("OF");
	excludes.add("ALSO");
	excludes.add("MAKE");
	excludes.add("OTHER");
	excludes.add("MUST");
	excludes.add("CERTAIN");
	excludes.add("ONE");
	excludes.add("TWO");
	excludes.add("THREE");
	excludes.add("FOUR");
	excludes.add("FIVE");
	excludes.add("SIX");
	excludes.add("SEVEN");
	excludes.add("EIGHT");
	excludes.add("NINE");
	excludes.add("TEN");
    }

    public static final Set<String> allWords = new TreeSet<>();

    static public Set<String> getWords(String s) {
	HashSet<String> out = new HashSet<>();
	Scanner scan = new Scanner(s);
	scan.useDelimiter("[0-9+., \t'/():-]");
	while (scan.hasNext()) {
	    String t = scan.next().toUpperCase();

	    if (t.length() > 1 && !excludes.contains(t)) {
		out.add(t);
		allWords.add(t);
	    }
	}
	return out;
    }
}
