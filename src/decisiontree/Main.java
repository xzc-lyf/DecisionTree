package decisiontree;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import static decisiontree.Classifier.classify;
import static decisiontree.DecisionTreeSaver.saveTreeToFile;
import static decisiontree.FilePreprocessor.processAndSaveFile;
import static decisiontree.TreeBuilder.buildTree;

public class Main {
    static String trainingSetInputFilePath = "src/input/adult.data";
    static String trainingSetOutputFilePath = "src/output/adult_preprocessed.csv";
    static String testSetInputFilePath = "src/input/adult.test";
    static String testSetOutputFilePath = "src/output/adult_test_preprocessed.csv";
    static String decisionTreeFilePath = "src/output/decision_tree.txt";

    public static void main(String[] args) throws IOException {
        // 1. Pre-process training set.
        long beginTime = System.currentTimeMillis();
        List<Object[]> trainingData = processAndSaveFile(trainingSetInputFilePath, trainingSetOutputFilePath);

        // 2. Build decision tree based on training set data.
        System.out.println("Building decision tree will take several minutes...");
        TreeNode decisionTree = buildTree(trainingData, new HashSet<Integer>());
        saveTreeToFile(decisionTree, decisionTreeFilePath);
        System.out.println("Decision tree built, please check the output directory.");

        // 3. Pre-process test set, change the file path params if necessary.
        List<Object[]> testData = processAndSaveFile(testSetInputFilePath, testSetOutputFilePath);

        // 4. Classify test data.
        System.out.println("Classifying test data...");
        classify(testData, decisionTree);
        long endTime = System.currentTimeMillis();
        System.out.println("Total Time: " + (endTime - beginTime) / 1000 + "s");
    }
}