package decisiontree;

import java.io.IOException;
import java.util.List;
import static decisiontree.Classifier.classify;
import static decisiontree.FIlePreprocessor.processAndSaveFile;
import static decisiontree.TreeBuilder.buildTree;
public class Main {
    public static void main(String[] args) throws IOException {
        List<Object[]> trainingData = processAndSaveFile("src/input/adult.data", "src/output/adult_preprocessed.csv");
        System.out.println("Building decision tree...");
        TreeNode tree = buildTree(trainingData);
        System.out.println("Decision tree built.");

        List<Object[]> testData = processAndSaveFile("src/input/adult.test", "src/output/adult_test_preprocessed.csv");
        System.out.println("Classifying test data...");
        int correct = 0;
        for (Object[] row : testData) {
            int prediction = classify(row, tree);
            if (prediction == (int) row[row.length - 1]) {
                correct++;
            }
        }
        System.out.println(correct + " " + testData.size());
        double accuracy = (double) correct / testData.size();
        System.out.println(accuracy);
    }
}