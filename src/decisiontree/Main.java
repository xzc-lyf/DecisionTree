package decisiontree;

import java.io.IOException;
import java.util.List;

import static decisiontree.FIlePreprocessor.preProcessFiles;
import static decisiontree.TreeBuilder.buildTree;
import static decisiontree.Classifier.classify;
public class Main {
    public static void main(String[] args) throws IOException {
        List<Object[]> trainingData = preProcessFiles("src/input/adult.data", "src/output/adult_preprocessed.csv");
        System.out.println("Building decision tree...");
        TreeNode tree = buildTree(trainingData);
        System.out.println("Decision tree built.");

        List<Object[]> testData = preProcessFiles("src/input/adult.test", "src/output/adult_test_preprocessed.csv");
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