package decisiontree;

public class TreeNode {
    Predicate predicate;
    TreeNode leftChild;
    TreeNode rightChild;

    TreeNode(Predicate predicate, TreeNode leftChild, TreeNode rightChild) {
        this.predicate = predicate;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }
}
