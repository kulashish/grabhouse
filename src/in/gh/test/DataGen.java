package in.gh.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataGen {
	public static final int DIM = 1000;
	private static String filename = "D:\\Personal\\grabhouse\\spacedata.txt";
	private static String outFile = "D:\\Personal\\grabhouse\\feature.csv";
	private static String[][] data = new String[DIM][DIM];
	private static String[][] featureData = new String[DIM * DIM][8];
	private static String[] labelData = new String[DIM * DIM];

	public static void readData() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		int i = 0;
		String line = null;
		while ((line = reader.readLine()) != null)
			data[i++] = line.split(" ");
		reader.close();
	}

	public static void genData() throws IOException {
		int dataIndex = 0;
		BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
		for (int i = 0; i < DIM; i++) {
			for (int j = 0; j < DIM; j++) {
				featureData[dataIndex][0] = j - 1 >= 0 ? data[i][j - 1] : "-1"; // west
				featureData[dataIndex][1] = j + 1 < DIM ? data[i][j + 1] : "-1"; // east
				featureData[dataIndex][2] = i - 1 >= 0 ? data[i - 1][j] : "-1"; // north
				featureData[dataIndex][3] = i + 1 < DIM ? data[i + 1][j] : "-1"; // south
				featureData[dataIndex][4] = i - 1 >= 0 && j - 1 >= 0 ? data[i - 1][j - 1]
						: "-1"; // nw
				featureData[dataIndex][5] = i - 1 >= 0 && j + 1 < DIM ? data[i - 1][j + 1]
						: "-1"; // ne
				featureData[dataIndex][6] = i + 1 < DIM && j - 1 >= 0 ? data[i + 1][j - 1]
						: "-1"; // sw
				featureData[dataIndex][7] = i + 1 < DIM && j + 1 < DIM ? data[i + 1][j + 1]
						: "-1"; // se
				labelData[dataIndex] = data[i][j];
				writeOut(writer, featureData[dataIndex], labelData[dataIndex]);
				dataIndex++;
			}
		}
		writer.flush();
		writer.close();
	}

	private static void writeOut(BufferedWriter writer, String[] features,
			String label) throws IOException {
		StringBuilder builder = new StringBuilder();
		for (String f : features)
			builder.append(f).append(',');
		builder.append(label);
		writer.write(builder.toString());
		writer.newLine();
	}

	public static void main(String[] args) {
		try {
			readData();
			System.out.println("Read the in data. Generating features");
			genData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
