package decisiontree;

import java.util.List;

public class Classifier {
    public static void classify(List<Object[]> testData, TreeNode tree) {
        int CorrectClassifiedNum = 0;
        for (Object[] row : testData) {
            int prediction = classify(row, tree);
            if (prediction == (Integer) row[row.length - 1]) {
                CorrectClassifiedNum++;
            }
        }
        System.out.println("CorrectClassifiedNum: " + CorrectClassifiedNum + ", Total: " + testData.size());
        System.out.println(String.format("Precision: %.5f%%", ((double) CorrectClassifiedNum / testData.size()) * 100));
    }

    public static int classify(Object[] row, TreeNode node) {
        return (node instanceof LeafNode) ? ((LeafNode) node).category
                : classify(row, node.condition.check(row) ? node.leftChild : node.rightChild);
    }
}
