/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall.gui;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;
import knowitall.Article;
import knowitall.Database;
import knowitall.Debug;
import knowitall.KIASave.Settings;
import knowitall.KnowItAll;
import lev.gui.*;
import lev.gui.resources.LFonts;
import skyproc.gui.SPDefaultGUI;

/**
 *
 * @author Justin Swanson
 */
public class MainPanel extends LPanel {

    LImagePane logo;
    SearchBar search;
    LSwingTree tree;
    LScrollPane treeScroll;
    LScrollPane articleScroll;
    JSplitPane split;
    ContentPanel articleContent;

    MainPanel() throws IOException {
	super();

	search = new SearchBar();
	GUI.search = search;
	search.setLocation(Spacings.mainPanel * 2 + 87, Spacings.mainPanel);
	add(search);

	try {
	    logo = new LImagePane(SPDefaultGUI.class.getResource("SkyProc Logo Small.png"));
	    logo.addMouseListener(new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
		    Database.reloadArticles(new UpdateContent());
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	    });
	    logo.setLocation(Spacings.mainPanel, search.getY() + search.getHeight() / 2 - logo.getHeight() / 2 + 4);
	    add(logo);
	} catch (IOException ex) {
	    Debug.log.logException(ex);
	}

	tree = new LSwingTree();
	tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	tree.addTreeSelectionListener(new TreeSelectionListener() {

	    @Override
	    public void valueChanged(TreeSelectionEvent arg0) {
		LSwingTreeNode node = (LSwingTreeNode) tree.getLastSelectedPathComponent();
		if (node == null) {
		    return;
		}
		if (node instanceof Article) {
		    GUI.loadArticle(node.toString());
		}
	    }
	});
	GUI.tree = tree;

	treeScroll = new LScrollPane(tree);
	treeScroll.setBorder(BorderFactory.createEmptyBorder());

	articleContent = new ContentPanel();
	GUI.contentPanel = articleContent;

	articleScroll = new LScrollPane(articleContent);
	articleScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	articleScroll.setBorder(BorderFactory.createEmptyBorder());

	split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScroll, articleScroll);
	split.setOpaque(false);
	split.setLocation(0, search.getBottom() + Spacings.mainPanel);
	split.setBorder(BorderFactory.createEmptyBorder());
	split.addPropertyChangeListener(new PropertyChangeListener() {

	    @Override
	    public void propertyChange(final PropertyChangeEvent evt) {
		SwingUtilities.invokeLater(new Runnable() {

		    @Override
		    public void run() {
			if ("dividerLocation".equals(evt.getPropertyName())) {
			    remeasureContents();
			}
		    }
		});
	    }
	});
	split.setDividerLocation(KnowItAll.save.getInt(Settings.DividerLocation));
	add(split, 0);
    }

    public class UpdateContent implements Runnable {

	@Override
	public void run() {
	    GUI.updateContentDisplay();
	}
    }

    @Override
    public void remeasure(final Dimension size) {
	super.setSize(size);
	search.setSize(getWidth() - Spacings.mainPanel * 3 - 87, search.getHeight());
	split.setSize(getWidth(), getHeight() - search.getBottom() - Spacings.mainPanel);
	remeasureContents();
    }

    public void remeasureContents() {
	articleContent.setPreferredSize(new Dimension(articleScroll.getWidth(), 50));
	articleContent.remeasure(articleScroll.getSize());
    }
}
