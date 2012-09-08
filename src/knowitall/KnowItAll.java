/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.io.IOException;
import java.util.Random;
import knowitall.gui.MainFrame;

/**
 *
 * @author Justin Swanson
 */
public class KnowItAll {

    static MainFrame frame;
    static Random rand = new Random();
    static String internalFiles = "Internal Files/";
    static String source = "Seed Data/";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
	createFrame();
    }

    public static void createFrame() throws IOException {
	frame = new MainFrame();
	frame.createGUI();
    }

}