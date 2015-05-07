package in.gh.test;

import java.io.File;
import java.io.IOException;

import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVSaver;
import weka.core.converters.ConverterUtils.DataSource;

public class ElementClassifier {
	public static final String TRAIN_FILE = "D:\\Personal\\grabhouse\\train.arff";
	public static final String TEST_FILE = "D:\\Personal\\grabhouse\\test.arff";
	public static final String OUT_FILE = "D:\\Personal\\grabhouse\\test_out.csv";

	private Instances trainSet;
	private Instances testSet;

	public ElementClassifier() throws Exception {
		DataSource trainSource = new DataSource(TRAIN_FILE);
		trainSet = trainSource.getDataSet();
		if (trainSet.classIndex() == -1)
			trainSet.setClassIndex(trainSet.numAttributes() - 1);
		DataSource testSource = new DataSource(TEST_FILE);
		testSet = testSource.getDataSet();
		if (testSet.classIndex() == -1)
			testSet.setClassIndex(testSet.numAttributes() - 1);
	}

	public double[] dtreeClassify() throws Exception {
		J48 dtree = new J48();
		dtree.buildClassifier(trainSet);
		double[] labels = new double[testSet.numInstances()];
		int i = 0;
		for (Instance inst : testSet) {
			labels[i] = dtree.classifyInstance(inst);
			inst.setClassValue(testSet.classAttribute().value((int) labels[i]));
			i++;
		}
		return labels;
	}

	public void writeOut() throws IOException {
		CSVSaver saver = new CSVSaver();
		saver.setInstances(testSet);
		saver.setFile(new File(OUT_FILE));
		saver.writeBatch();
	}

	public static void main(String[] args) {
		double[] result = null;
		try {
			System.out.println("Loading data...");
			ElementClassifier classifier = new ElementClassifier();
			System.out.println("Classifying...");
			result = classifier.dtreeClassify();
			System.out.println("Writing Result...");
			classifier.writeOut();
			System.out.println("Done!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// for (double d : result) {
		// System.out.println(d);
		// }

		System.out.println(result[7436]);
		System.out.println(result[15493]);

	}

}
