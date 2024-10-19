package decisiontree;

public class Classifier {
    public static int classify(Object[] row, TreeNode node) {
        if (node instanceof LeafNode) {
            LeafNode leaf = (LeafNode) node;
            return leaf.category;
        }

        if (node.predicate.predicate(row)) {
            return classify(row, node.leftChild);
        } else {
            return classify(row, node.rightChild);
        }
    }
}
