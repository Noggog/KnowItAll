/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JScrollPane;
import knowitall.Database;
import lev.gui.LButton;
import lev.gui.LFrame;
import lev.gui.LList;
import lev.gui.LScrollPane;
import lev.gui.resources.LImages;

/**
 *
 * @author Justin Swanson
 */
public class OpenPackage extends LFrame {

    LList<String> picker;
    JScrollPane scroll;
    LButton cancel;
    LButton open;

    OpenPackage() {
	super("Open Package");
	background.setImage(LImages.multipurpose());

	picker = new LList("Open File Picker");

	scroll = new LScrollPane(picker);
	scroll.setLocation(15, 15);
	background.Add(scroll);

	cancel = new LButton("Cancel");
	cancel.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		setVisible(false);
	    }
	});
	background.Add(cancel);

	open = new LButton("Open");
	open.addActionListener(new ActionListener(){

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		open();
	    }
	});
	background.Add(open);

	setRealSize(300, 300);
    }

    void open() {
	String in = picker.getSelectedElement();
	if (in.isEmpty()) {
	    return;
	}
	for (File packageDir : Database.packages.listFiles()) {
	    if (packageDir.getName().equals(in)) {
		GUI.openPackage(packageDir);
		break;
	    }
	}
	setVisible(false);
    }

    @Override
    public void setSize(int x, int y) {
	super.setSize(x, y);
	Dimension real = getRealSize();
	int buttonWidth = cancel.getWidth() + 15 + open.getWidth();
	cancel.setLocation(real.width / 2 - buttonWidth / 2, real.height - cancel.getHeight() - 15);
	open.setLocation(cancel.getRight() + 15, cancel.getY());
	scroll.setSize(real.width - 30, cancel.getY() - 15 - scroll.getY());
    }

    public void loadPackageList() {
	picker.clear();
	if (Database.packages.isDirectory()) {
	    for (File packageDir : Database.packages.listFiles()) {
		if (packageDir.isDirectory()) {
		    // Check if it has KIA Category Index
		    for (File packageFile : packageDir.listFiles()) {
			if (packageFile.getName().equals(Database.categoryIndexName)) {
			    picker.addElement(packageDir.getName());
			    break;
			}
		    }
		}
	    }
	}
	if (!picker.isEmpty()) {
	    picker.setSelectedElement(0);
	}
    }
}
