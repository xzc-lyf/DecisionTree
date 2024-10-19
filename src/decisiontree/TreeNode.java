package decisiontree;

public class TreeNode {
    Condition condition;
    TreeNode leftChild;
    TreeNode rightChild;

    TreeNode(Condition condition, TreeNode leftChild, TreeNode rightChild) {
        this.condition = condition;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }
}
