package decisiontree;

import java.util.Map;

public class Classifier {
    static int classify(Object[] row, TreeNode node) {
        if (node.isLeaf()) {
            return node.results.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .get()
                    .getKey();
        }
        if (node.condition.check(row)) {
            return classify(row, node.trueBranch);
        } else {
            return classify(row, node.falseBranch);
        }
    }
}
