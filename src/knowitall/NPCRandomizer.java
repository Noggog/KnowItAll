/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package knowitall;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import lev.LFileChannel;
import lev.Ln;
import lev.gui.LButton;

/**
 *
 * @author Justin Swanson
 */
public class NPCRandomizer {

    // Data title, Fields <-> weighting
    static ArrayList<String> fileNames = new ArrayList<>();
    static ArrayList<ArrayList<DataItem>> fileData = new ArrayList<>();
    static int numberOfFields = 3;
    static DefaultTableModel model;
    static JTable chart;
    static JScrollPane scroll;

    public static void createGUI() {
//	LButton reload = new LButton("Reload Seed Files");
//	reload.setLocation(15, 15);
//	reload.addActionListener(new ActionListener() {
//	    @Override
//	    public void actionPerformed(ActionEvent arg0) {
//		try {
//		    NPCRandomizer.loadFilesAndDisplay();
//		} catch (IOException ex) {
//		}
//	    }
//	});
//
//	LButton go = new LButton("Go!");
//	go.setSize(126, 50);
//	go.addActionListener(new ActionListener() {
//	    @Override
//	    public void actionPerformed(ActionEvent arg0) {
//		NPCRandomizer.rollAndDisplay();
//	    }
//	});
//
//	KnowItAll.background.add(reload);
//	go.setLocation(KnowItAll.background.getWidth() / 2 - go.getWidth() / 2, 200);
//	KnowItAll.background.add(go);
//
//	model = new DefaultTableModel();
//	chart = new JTable(model);
//	chart.setEnabled(false);
//	chart.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//
//	scroll = new JScrollPane(chart);
//	scroll.setSize(KnowItAll.background.getWidth() - 50, 300);
//	scroll.setLocation(25, KnowItAll.background.getHeight() - 50 - scroll.getHeight());
//	KnowItAll.background.add(scroll);

    }

    public static void loadFilesAndDisplay() throws IOException {
	fileData.clear();
	fileNames.clear();
	loadFiles();
	calculateProbs();
	displayData();
    }

    public static void loadFiles() throws IOException {
	File srcFile = new File(Database.source);

	for (File f : srcFile.listFiles()) {
	    if (f.isFile() && Ln.isFileType(f, "TXT")) {
		LFileChannel in = null;
		try {
		    in = new LFileChannel(f);
		    fileNames.add(f.getName().substring(0, f.getName().length() - 4));
		    ArrayList<DataItem> data = new ArrayList<>();
		    fileData.add(data);
		    while (in.available() > 0) {
			DataItem fileData = new DataItem();
			String line = in.extractLine();
			String[] split = line.split(",");
			fileData.name = split[0].trim();
			fileData.weight = Integer.valueOf(split[1].trim());
			data.add(fileData);
		    }
		} catch (IOException ex) {
		    if (in != null) {
			in.close();
		    }
		}
	    }
	}
    }

    public static void calculateProbs() {
	for (ArrayList<DataItem> items : fileData) {
	    int totalWeight = calcTotalWeight(items);

	    for (DataItem i : items) {
		Double d = 1.0 * i.weight / totalWeight * 100;
		String s = d.toString();
		if (s.length() > 5) {
		    s = s.substring(0, 5);
		}
		if (s.indexOf(".") == 1) {
		    s = "0" + s.substring(0, s.length() - 1);

		}
		i.probability = s + "%";
	    }
	}
    }

    public static int calcTotalWeight(ArrayList<DataItem> items) {
	int totalWeight = 0;
	for (DataItem i : items) {
	    totalWeight += i.weight;
	}
	return totalWeight;
    }

    public static void displayData() {

	Vector cols = new Vector();
	for (String fileName : fileNames) {
	    cols.add(fileName);
	    cols.add(" ");
	    cols.add(" ");
	}

	int largestCol = 0;
	for (ArrayList<DataItem> dataList : fileData) {
	    if (largestCol < dataList.size()) {
		largestCol = dataList.size();
	    }
	}

	Vector<Vector> rows = new Vector<>();
	for (int i = 0; i < largestCol; i++) {
	    Vector v = new Vector();
	    for (int j = 0; j < fileNames.size() * numberOfFields; j++) {
		v.add(null);
	    }
	    rows.add(v);
	}
	for (int col = 0; col < fileData.size(); col++) {
	    ArrayList<DataItem> dataList = fileData.get(col);
	    for (int row = 0; row < dataList.size(); row++) {
		DataItem item = dataList.get(row);
		Vector v = rows.get(row);
		v.setElementAt(item.name, col * numberOfFields);
		v.setElementAt(item.weight, col * numberOfFields + 1);
		v.setElementAt(item.probability, col * numberOfFields + 2);
	    }
	}

	model.setDataVector(rows, cols);
    }

    public static void rollAndDisplay() {
	String s = "The Fates have decreed:\n\n";
	int i = 0;
	for (DataItem d : roll()) {
	    s += fileNames.get(i) + ": " + d.name + "\n";
	    i++;
	}

	JOptionPane.showMessageDialog(null, s);
    }

    public static ArrayList<DataItem> roll() {
	ArrayList<DataItem> out = new ArrayList<>(fileNames.size());
	for (ArrayList<DataItem> dataSet : fileData) {
	    int totalWeight = calcTotalWeight(dataSet);
	    int roll = KnowItAll.rand.nextInt(totalWeight);
	    int rollingSum = 0;
	    for (DataItem item : dataSet) {
		rollingSum += item.weight;
		if (roll < rollingSum) {
		    out.add(item);
		    break;
		}
	    }
	}
	return out;
    }

    public static class DataItem {

	String name;
	Integer weight;
	String probability;

	@Override
	public String toString() {
	    return name;
	}
    }
}