package decisiontree;

/**
 * internal treenode of the decision tree
 * including predicate leftchild and rightchild
 */
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
