package decisiontree;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Classifier {
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

    public static int classify(Object[] row, TreeNode node) {
        return (node instanceof LeafNode) ? ((LeafNode) node).category
                : classify(row, node.predicate.predicate(row) ? node.leftChild : node.rightChild);
    }
}
