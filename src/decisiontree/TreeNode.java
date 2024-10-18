package decisiontree;

public class TreeNode {
    Check condition;
    TreeNode trueBranch;
    TreeNode falseBranch;

    TreeNode(Check condition, TreeNode trueBranch, TreeNode falseBranch) {
        this.condition = condition;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }
}
