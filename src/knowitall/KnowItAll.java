/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import com.google.gson.Gson;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.WindowConstants;
import knowitall.KIASave.Settings;
import knowitall.gui.GUI;
import knowitall.gui.MainFrame;
import lev.Ln;

/**
 *
 * @author Justin Swanson
 */
public class KnowItAll {

    static Random rand = new Random();
    final public static String internalFiles = "Internal Files/";
    public static Gson gson = new Gson();
    public static KIASave save;

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
	    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		if ("Nimbus".equals(info.getName())) {
		    UIManager.setLookAndFeel(info.getClassName());
		    break;
		}
	    }
	    File saveF = Ln.getMyDocuments();
	    save = new KIASave(saveF.getPath() + "/Know It All");
	    save.init();
	    createFrame();
	    if (save.getBool(Settings.OpenLastOnStartup)) {
		if (save.getStr(Settings.LastPackage).equals(".")
			&& Database.packages.isDirectory()
			&& Database.packages.listFiles().length > 0) {
		    save.setStr(Settings.LastPackage, Database.packages.listFiles()[0].getPath());
		}
		GUI.loadPackages();
	    }
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
		GUI.mainFrameFocus = true;
	    }

	    @Override
	    public void windowDeactivated(WindowEvent e) {
		GUI.mainFrameFocus = false;
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