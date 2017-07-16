/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JProgressBar;
import lev.gui.LComponent;
import lev.gui.LLabel;
import lev.gui.resources.LFonts;

/**
 *
 * @author Justin Swanson
 */
public class KIAProgressPane extends LComponent {

    LLabel loading;
    JProgressBar progress;
    LLabel processed;
    static int progressMargin = 75;

    public KIAProgressPane () {

	loading = new LLabel("Loading...", LFonts.MyriadProBold(20), Color.GRAY);
	add(loading);

	progress = new JProgressBar();
	progress.setLocation(0, loading.getBottom() + 15);
	progress.setSize(getWidth(), 30);
	add(progress);

	processed = new LLabel("Starting", LFonts.MyriadProBold(16), Color.GRAY);
	processed.setLocation(0, progress.getY() + progress.getHeight() + 15);
	add(processed);
    }

    public void remeasure(Dimension size) {
	setSize(size.width - 2 * progressMargin, processed.getBottom());
	progress.setSize(getWidth(), 30);
	loading.centerIn(this, loading.getY());
	processed.centerIn(this, processed.getY());
	setLocation(progressMargin, size.height / 2 - getHeight() / 2);
    }

    public void setMax(int max) {
	progress.setMaximum(max);
    }

    public void setValue(int value) {
	progress.setValue(value);
    }

    public void setTitle(String str) {
	loading.setText(str);
	loading.centerIn(this, loading.getY());
    }

    public void processed(String str) {
	processed.setText(str);
	processed.centerIn(this, processed.getY());
    }

    public void increment(String title) {
	processed(title);
	increment();
    }

    public void increment() {
	progress.setValue(progress.getValue() + 1);
    }

    public void reset() {
	progress.setValue(0);
	loading.setText("Loading...");
	processed.setText("Starting");
    }
}
