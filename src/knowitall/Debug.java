/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import lev.debug.LLogger;

/**
 *
 * @author Justin Swanson
 */
public class Debug {
    
    public static LLogger log;

    public static void init() {
	log = new LLogger("Debug/");
    }
}
