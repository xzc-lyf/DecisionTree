package decisiontree;

import java.util.Map;

public class LeafNode extends TreeNode{
    int category;
    Map<Integer, Integer> results;

    LeafNode(Map<Integer, Integer> results) {
        super(null, null, null);
        this.results = results;
        int countOfOne = results.get(1) == null ? 0 : results.get(1);
        int countOfMinusOne = results.get(-1) == null ? 0 : results.get(-1);

        if (countOfOne > countOfMinusOne) {
            this.category = 1;
        } else {
            this.category = -1;
        }
    }
}
