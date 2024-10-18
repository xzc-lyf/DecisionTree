package decisiontree;

public class Classifier {
    public static int classify(Object[] row, TreeNode node) {
        if (node instanceof LeafNode) {
            LeafNode leaf = (LeafNode) node;
            return leaf.category;
        }

        if (node.condition.check(row)) {
            return classify(row, node.trueBranch);
        } else {
            return classify(row, node.falseBranch);
        }
    }
}
