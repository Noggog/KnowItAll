/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import knowitall.Database;
import lev.gui.LComponent;
import lev.gui.resources.LFonts;

/**
 *
 * @author Justin Swanson
 */
public class SearchBar extends LComponent {

    JTextField field;
    boolean fireSearches = true;

    public SearchBar() {
	init();
    }

    final void init() {
	field = new JTextField();
	field.setFont(LFonts.MyriadProBold(20));
	addDocumentListener(new DocumentListener() {
	    @Override
	    public void insertUpdate(DocumentEvent e) {
		updateContent();
	    }

	    @Override
	    public void removeUpdate(DocumentEvent e) {
		updateContent();
	    }

	    @Override
	    public void changedUpdate(DocumentEvent e) {
		updateContent();
	    }
	});
	add(field);
	setSize(5, 30);
	field.setVisible(true);
	setVisible(true);
    }

    void updateContent() {
	if (!fireSearches) {
	    return;
	}
	String s = field.getText().toUpperCase();
	if (Database.hasArticle(s)) {
	    GUI.setArticle(Database.getArticle(s));
	}
//	else if (s.equals("")) {
//	    content.updateContent(null);
//	}
    }

    @Override
    final public void setSize(int x, int y) {
	super.setSize(x, y);
	field.setSize(x, y);
    }

    public void addDocumentListener(DocumentListener d) {
	field.getDocument().addDocumentListener(d);
    }

    public String getText() {
	return field.getText();
    }

    public void setText(String s) {
	fireSearches = false;
	field.setText(s);
	fireSearches = true;
    }

    @Override
    public void setFocusable(boolean in) {
	field.setFocusable(in);
    }

    @Override
    public void requestFocus() {
	field.requestFocus();
    }
}
