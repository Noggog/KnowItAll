/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rpgrandomizer;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import lev.LFileChannel;
import lev.Ln;
import lev.gui.LButton;
import lev.gui.LImagePane;
import lev.gui.Lg;

/**
 *
 * @author Justin Swanson
 */
public class RPGRandomizer {

    static JFrame frame;
    static LImagePane background;
    // Data title, Fields <-> weighting
    static ArrayList<String> fileNames = new ArrayList<>();
    static ArrayList<ArrayList<RPGRandomizer.DataItem>> fileData = new ArrayList<>();
    static DefaultTableModel model;
    static JTable chart;
    static JScrollPane scroll;
    static Random rand = new Random();
    static int numberOfFields = 3;
    static String internalFiles = "Internal Files/";
    static String source = "Seed Data/";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
	createFrame();
	createGUI();
	loadFilesAndDisplay();
    }

    public static void createFrame() {
	frame = new JFrame("RPG Randomizer");
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frame.setResizable(false);
	frame.setLayout(null);
	frame.setVisible(true);
    }

    public static void createGUI() throws IOException {
	LButton reload = new LButton("Reload Seed Files");
	reload.setLocation(15, 15);
	reload.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		try {
		    loadFilesAndDisplay();
		} catch (IOException ex) {
		}
	    }
	});

	LButton go = new LButton("Go!");
	go.setSize(126, 50);
	go.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		rollAndDisplay();
	    }
	});

	background = new LImagePane(internalFiles + "background.jpg");
	frame.add(background);
	resetFrameSize(background.getSize());

	background.add(reload);
	go.setLocation(background.getWidth() / 2 - go.getWidth() / 2, 200);
	background.add(go);

	model = new DefaultTableModel();
	chart = new JTable(model);
	chart.setEnabled(false);
	chart.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

	scroll = new JScrollPane(chart);
	scroll.setSize(background.getWidth() - 50, 300);
	scroll.setLocation(25, background.getHeight() - 50 - scroll.getHeight());
	background.add(scroll);

    }

    public static void loadFilesAndDisplay() throws IOException {
	fileData.clear();
	fileNames.clear();
	loadFiles();
	calculateProbs();
	displayData();
    }

    public static void loadFiles() throws IOException {
	File srcFile = new File(source);

	for (File f : srcFile.listFiles()) {
	    if (f.isFile() && Ln.isFileType(f, "TXT")) {
		LFileChannel in = null;
		try {
		    in = new LFileChannel(f);
		    fileNames.add(f.getName().substring(0, f.getName().length() - 4));
		    ArrayList<RPGRandomizer.DataItem> data = new ArrayList<>();
		    fileData.add(data);
		    while (in.available() > 0) {
			RPGRandomizer.DataItem fileData = new RPGRandomizer.DataItem();
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
	for (ArrayList<RPGRandomizer.DataItem> items : fileData) {
	    int totalWeight = calcTotalWeight(items);

	    for (RPGRandomizer.DataItem i : items) {
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

    public static int calcTotalWeight(ArrayList<RPGRandomizer.DataItem> items) {
	int totalWeight = 0;
	for (RPGRandomizer.DataItem i : items) {
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
	for (ArrayList<RPGRandomizer.DataItem> dataList : fileData) {
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
	    ArrayList<RPGRandomizer.DataItem> dataList = fileData.get(col);
	    for (int row = 0; row < dataList.size(); row++) {
		RPGRandomizer.DataItem item = dataList.get(row);
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
	for (RPGRandomizer.DataItem d : roll()) {
	    s += fileNames.get(i) + ": " + d.name + "\n";
	    i++;
	}

	JOptionPane.showMessageDialog(null, s);
    }

    public static ArrayList<RPGRandomizer.DataItem> roll() {
	ArrayList<RPGRandomizer.DataItem> out = new ArrayList<>(fileNames.size());
	for (ArrayList<RPGRandomizer.DataItem> dataSet : fileData) {
	    int totalWeight = calcTotalWeight(dataSet);
	    int roll = rand.nextInt(totalWeight);
	    int rollingSum = 0;
	    for (RPGRandomizer.DataItem item : dataSet) {
		rollingSum += item.weight;
		if (roll < rollingSum) {
		    out.add(item);
		    break;
		}
	    }
	}
	return out;
    }

    public static void resetFrameSize(Dimension size) {
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

	Dimension maxSize = Lg.calcSize(background.getWidth(), background.getHeight(), screen.width - 5, screen.height - 40);

	background.setMaxSize(maxSize);

	int y = 5;
	int x = screen.width / 2 - maxSize.width / 2;
	if (x < 5) {
	    x = 5;
	}

	frame.setSize(maxSize);
	frame.setLocation(x, y);
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