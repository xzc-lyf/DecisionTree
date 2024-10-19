package decisiontree;

import java.util.List;

public class Classifier {
    public static void classify(List<Object[]> testData, TreeNode tree) {
        int CorrectClassifiedNum = 0;
        for (Object[] row : testData) {
            int prediction = categorize(row, tree);
            if (prediction == (Integer) row[row.length - 1]) {
                CorrectClassifiedNum++;
            }
        }
        System.out.println("CorrectClassifiedNum: " + CorrectClassifiedNum + ", Total: " + testData.size());
        System.out.println("Precision: " + ((double) CorrectClassifiedNum / testData.size()));
    }

    public static int categorize(Object[] row, TreeNode node) {
        return (node instanceof LeafNode) ? ((LeafNode) node).category
                : categorize(row, node.condition.check(row) ? node.trueBranch : node.falseBranch);
    }
}
