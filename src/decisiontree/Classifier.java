package decisiontree;

import java.util.Map;

public class Classifier {
    public static int classify(Object[] row, TreeNode node) {
        if (node.isLeaf()) {
            Map.Entry<Integer, Integer> maxEntry = null;
            for (Map.Entry<Integer, Integer> entry : node.results.entrySet()) {
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                    maxEntry = entry;
                }
            }
            return maxEntry.getKey();  // 返回最大值对应的键
        }

        if (node.condition.check(row)) {
            return classify(row, node.trueBranch);
        } else {
            return classify(row, node.falseBranch);
        }
    }
}
