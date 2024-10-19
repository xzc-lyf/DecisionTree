package decisiontree;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * The Classifier class provides two main functionalities:
 * 1. Classifying a list of test data using a trained decision tree.
 * 2. Writing the classification results, including whether the prediction was correct, to an external file.
 */
public class Classifier {

    /**
     * Classifies the test data using the given decision tree and writes the results to a file.
     *
     * @param testData List of test data rows.
     * @param tree The decision tree used for classification.
     * @param writer BufferedWriter to write classification results to a file.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public static void classify(List<Object[]> testData, TreeNode tree, BufferedWriter writer) throws IOException {
        System.out.println("Classifying test data...");
        int CorrectClassifiedNum = 0;
        for (Object[] row : testData) {
            int prediction = classify(row, tree);
            boolean flag = prediction == (Integer) row[row.length - 1];
            if (flag) {
                CorrectClassifiedNum++;
            }
            writer.write("Prediction Result:" + flag + ", Row: " + Arrays.toString(row) + ", Prediction label: " + prediction + ", Data label: " + row[row.length - 1]);
            writer.newLine();
        }
        writer.close();
        System.out.println("CorrectClassifiedNum: " + CorrectClassifiedNum + ", Total: " + testData.size());
        System.out.println(String.format("Precision: %.5f%%", ((double) CorrectClassifiedNum / testData.size()) * 100));
    }

    /**
     * Recursively classifies a single row based on the decision tree.
     *
     * @param row Data row to classify.
     * @param node Current node in the decision tree.
     * @return The predicted category for the row.
     */
    public static int classify(Object[] row, TreeNode node) {
        return (node instanceof LeafNode) ? ((LeafNode) node).category
                : classify(row, node.predicate.predicate(row) ? node.leftChild : node.rightChild);
    }
}
