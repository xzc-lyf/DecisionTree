package decisiontree;

import java.io.IOException;
import java.util.List;
import static decisiontree.Classifier.classify;
import static decisiontree.DecisionTreeSaver.saveTreeToFile;
import static decisiontree.FilePreprocessor.processAndSaveFile;
import static decisiontree.TreeBuilder.buildTree;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1. Pre-process training set, change the file path params if necessary.
        List<Object[]> trainingData = processAndSaveFile("src/input/adult.data", "src/output/adult_preprocessed.csv");

        // 2. Build decision tree based on training set data.
        System.out.println("Building decision tree will take several minutes...");
        TreeNode tree = buildTree(trainingData);
        saveTreeToFile(tree, "src/output/decision_tree.txt");
        System.out.println("Decision tree built, please check output directory.");

        // 3. Pre-process test set, change the file path params if necessary.
        List<Object[]> testData = processAndSaveFile("src/input/adult.test", "src/output/adult_test_preprocessed.csv");

        // 4. Classify test data.
        System.out.println("Classifying test data...");
        int correct = 0;
        for (Object[] row : testData) {
            int prediction = classify(row, tree);
            if (prediction == (Integer) row[row.length - 1]) {
                correct++;
            }
        }

        // 5. Print out precision rate.
        System.out.println("Correct: " + correct + ", Total: " + testData.size());
        double accuracy = (double) correct / testData.size();
        System.out.println("Precision: " + accuracy);
    }
}