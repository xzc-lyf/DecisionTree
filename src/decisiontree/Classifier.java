package decisiontree;

public class Classifier {
    public static int classify(Object[] row, TreeNode node) {
        return (node instanceof LeafNode) ? ((LeafNode) node).category
                : classify(row, node.condition.check(row) ? node.trueBranch : node.falseBranch);
    }
}
