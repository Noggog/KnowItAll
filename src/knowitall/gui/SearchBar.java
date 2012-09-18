/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import knowitall.Article;
import knowitall.Database;
import lev.gui.JAutoTextField;
import lev.gui.resources.LFonts;

/**
 *
 * @author Justin Swanson
 */
public class SearchBar extends JAutoTextField {

    boolean fireSearches = true;
    int caretListener = 0;

    public SearchBar() {
	super(new ArrayList<>(0));
	setCaseSensitive(false);
	setStrict(false);
	setFont(LFonts.MyriadProBold(20));
	setDocument(new SearchDocument());
	addCaretListener(new CaretListener() {

	    @Override
	    public void caretUpdate(CaretEvent arg0) {
		if (caretListener == 0) {
		    updateContent();
		}
	    }
	});
	addFocusListener(new FocusListener() {

	    @Override
	    public void focusGained(FocusEvent arg0) {
	    }

	    @Override
	    public void focusLost(FocusEvent arg0) {
		setCaretPosition(getText().length());
		updateContent();
		transferFocusUpCycle();
	    }
	});
	setSize(5, 30);
    }

    @Override
    public void replaceSelection(String s) {
	caretListener++;
	super.replaceSelection(s);
	caretListener--;
    }

    void updateContent() {
	if (!fireSearches) {
	    return;
	}
	String s = getTypedString().toUpperCase();
	if (Database.hasArticle(s)) {
	    GUI.setArticle(Database.getArticle(s));
	}
//	else if (s.equals("")) {
//	    content.updateContent(null);
//	}
    }

    public String getTypedString() {
	String text = getText();
	int start = getSelectionStart();
	int end = getSelectionEnd();
	int length = text.length();
	if (getSelectionEnd() == text.length()) {
	    return text.substring(0, getSelectionStart());
	}
	return text;
    }

    @Override
    public void setText(String s) {
	fireSearches = false;
	super.setText(s);
	fireSearches = true;
    }

    public void suggestions(Collection<Article> articles) {
	ArrayList<String> list = new ArrayList<>(articles.size());
	for (Article a : articles) {
	    list.add(a.getName());
	}
	setDataList(list);
    }

    class SearchDocument extends AutoDocument {

	@Override
	public void insertString(int i, String s, AttributeSet attributeset) throws BadLocationException {
	    caretListener++;
	    super.insertString(i, s, attributeset);
	    updateContent();
	    caretListener--;
	}

	@Override
	public void remove(int i, int j) throws BadLocationException {
	    caretListener++;
	    super.remove(i, j);
	    updateContent();
	    caretListener--;
	}
    }
}
