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
    ContentPanel content;

    public SearchBar(ContentPanel content) {
	this.content = content;
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
	String s = field.getText().toUpperCase();
	if (Database.articles.containsKey(s)) {
	    content.updateContent(Database.articles.get(s));
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
}
