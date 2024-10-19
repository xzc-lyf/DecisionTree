package decisiontree;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import static decisiontree.Classifier.classify;
import static decisiontree.DecisionTreeSaver.saveTreeToFile;
import static decisiontree.FilePreprocessor.processAndSaveFile;
import static decisiontree.DecisionTreeBuilder.buildTree;

/**
 * Main class is the starting point of executing the decision tree classifier program.
 * 1. Pre-processing the training and test datasets.
 * 2. Building a decision tree from the training data.
 * 3. Classifying the test data and saving the results.
 *
 * The program reads data from input files, processes it, builds a decision tree,
 * and outputs classification results and the decision tree to specified files.
 *
 * File paths:
 * - Training data input: {@code trainingSetInputFilePath}
 * - Training data preprocessed output: {@code trainingSetOutputFilePath}
 * - Test data input: {@code testSetInputFilePath}
 * - Test data preprocessed output: {@code testSetOutputFilePath}
 * - Decision tree output: {@code decisionTreeFilePath}
 * - Test result output: {@code testResultWithLabelFilePath}
 *
 * Time statistics for the entire process are also printed at the end.
 */
public class Main {
    static String trainingSetInputFilePath = "src/input/adult.data";
    static String trainingSetOutputFilePath = "src/output/adult_preprocessed.csv";
    static String testSetInputFilePath = "src/input/adult.test";
    static String testSetOutputFilePath = "src/output/adult_test_preprocessed.csv";
    static String decisionTreeFilePath = "src/output/decision_tree.txt";
    static String testResultWithLabelFilePath = "src/output/test_acc_results.txt";

    /**
     * The main method that orchestrates the pre-processing, decision tree building, and test data classification.
     *
     * @param args Command line arguments.
     * @throws IOException If there are issues with file input/output operations.
     */
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
        classify(testData, decisionTree, new BufferedWriter(new FileWriter(testResultWithLabelFilePath)));
        long endTime = System.currentTimeMillis();
        System.out.println("Total Time: " + (endTime - beginTime) / 1000 + "s");
    }
}