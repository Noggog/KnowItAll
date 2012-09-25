/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import knowitall.KnowItAll;

/**
 *
 * @author Justin Swanson
 */
public class KIAMenu extends JMenuBar {

    KIAMenu () {
	JMenu file = new JMenu("File");
	add(file);

	JMenuItem open = new JMenuItem("Open Package");
	open.addActionListener(new ActionListener(){

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		GUI.openPackagePicker();
	    }
	});
	file.add(open);

	JMenuItem quit = new JMenuItem("Quit");
	quit.addActionListener(new ActionListener(){

	    @Override
	    public void actionPerformed(ActionEvent e) {
		KnowItAll.exitProgram();
	    }
	});
	file.add(quit);

	JMenu view = new JMenu("View");
	add(view);
    }
}
