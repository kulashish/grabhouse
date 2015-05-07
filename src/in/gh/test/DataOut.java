package in.gh.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataOut {
	public static final int DIM = 1000;
	public static final int DIM_MISSING = 372028;
	private static String spacedata = "D:\\Personal\\grabhouse\\spacedata.txt";
	private static String pred = "D:\\Personal\\grabhouse\\test_out.csv";
	private static String spaceout = "D:\\Personal\\grabhouse\\spaceout.txt";

	private static String[][] data = new String[DIM][DIM];
	private static String[] labels = new String[DIM_MISSING];

	public static void readData() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(spacedata));
		int i = 0;
		String line = null;
		while ((line = reader.readLine()) != null)
			data[i++] = line.split(" ");
		reader.close();
	}

	public static void readPred() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(pred));
		int i = 0;
		String line = null;
		while ((line = reader.readLine()) != null)
			labels[i++] = line.split(",")[8];
		reader.close();
	}

	public static void fillData() throws IOException {
		int lIndex = 0;
		BufferedWriter writer = new BufferedWriter(new FileWriter(spaceout));
		for (int i = 0; i < DIM; i++) {
			for (int j = 0; j < DIM; j++)
				if (data[i][j].equalsIgnoreCase("0"))
					data[i][j] = labels[lIndex++];
			writer.write(arrTocsv(data[i]));
			writer.newLine();
		}
		writer.flush();
		writer.close();
	}

	public static String arrTocsv(String[] in) {
		StringBuilder csvBuilder = new StringBuilder();
		for (String n : in)
			csvBuilder.append(n).append(" ");
		csvBuilder.deleteCharAt(csvBuilder.length() - 1);
		return csvBuilder.toString();
	}

	public static void main(String[] args) {
		try {
			readData();
			readPred();
			fillData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
