/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import com.google.gson.Gson;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Random;
import javax.swing.WindowConstants;
import knowitall.KIASave.Settings;
import knowitall.gui.GUI;
import knowitall.gui.MainFrame;

/**
 *
 * @author Justin Swanson
 */
public class KnowItAll {

    static Random rand = new Random();
    final public static String internalFiles = "Internal Files/";
    public static Gson gson = new Gson();
    public static KIASave save = new KIASave(internalFiles);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
	try {
	    Debug.init();
	    if (handleArgs(args)) {
		Debug.log.close();
		return;
	    }
	    save.init();
	    createFrame();
	    Database.reloadArticles();
	} catch (Exception e) {
	    Debug.log.logException(e);
	    Debug.log.close();
	}
    }

    public static boolean handleArgs(String[] args) {
	String cleanFiles = "CLEANFILES";
	for (String s : args) {
	    if (s.contains(cleanFiles)) {
		Tools.cleanFiles();
		return true;
	    }
	}
	return false;
    }

    public static void createFrame() throws IOException {
	GUI.frame = new MainFrame();
	GUI.frame.createGUI();
	GUI.frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	GUI.frame.addWindowListener(new WindowListener() {
	    @Override
	    public void windowOpened(WindowEvent e) {
	    }

	    @Override
	    public void windowClosing(WindowEvent e) {
		exitProgram();
	    }

	    @Override
	    public void windowClosed(WindowEvent e) {
	    }

	    @Override
	    public void windowIconified(WindowEvent e) {
	    }

	    @Override
	    public void windowDeiconified(WindowEvent e) {
	    }

	    @Override
	    public void windowActivated(WindowEvent e) {
	    }

	    @Override
	    public void windowDeactivated(WindowEvent e) {
	    }
	});
    }

    public static void exitProgram() {
	saveGUIsetup();
	save.saveToFile();
	Debug.log.close();
	System.exit(0);
    }

    public static void saveGUIsetup() {
	Dimension size = GUI.frame.getSize();
	save.setInt(Settings.StartWidth, size.width);
	save.setInt(Settings.StartHeight, size.height);
	save.setInt(Settings.DividerLocation, GUI.dividerLocation());
    }
}