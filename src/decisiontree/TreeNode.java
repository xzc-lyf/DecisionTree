package decisiontree;

import java.util.Map;

public class TreeNode {
    static String[] attributes = {"age", "workclass", "fnlwgt", "education", "education-num", "marital-status", "occupation",
            "relationship", "race", "sex", "capital-gain", "capital-loss", "hours-per-week"};
    Check condition;
    TreeNode trueBranch;
    TreeNode falseBranch;
    Map<Integer, Integer> results; // Leaf node stores the count of each label

    TreeNode(Check condition, TreeNode trueBranch, TreeNode falseBranch) {
        this.condition = condition;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    TreeNode(Map<Integer, Integer> results) {
        this.results = results;
    }

    boolean isLeaf() {
        return results != null;
    }

    static class Check {
        int col;
        Object val;

        Check(int col, Object val) {
            this.col = col;
            this.val = val;
        }

        // Check if the value satisfies the condition (numerical or categorical comparison)
        boolean check(Object[] row) {
            if (row[col] instanceof Integer) {
                return (Integer) row[col] > (Integer) val;
            } else {
                return row[col].equals(val);
            }
        }

        @Override
        public String toString() {
            String condition = (val instanceof Integer) ? ">" : "==";
            return "Is " + attributes[col] + " " + condition + " " + val.toString() + "?";
        }
    }
}
