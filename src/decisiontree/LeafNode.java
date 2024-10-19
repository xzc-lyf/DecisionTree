package decisiontree;

import java.util.Map;

/**
 * LeafNode of the decision tree
 */
public class LeafNode extends TreeNode{
    int category;
    Map<Integer, Integer> results;

    /**
     * function that constructs a LeafNode with the given results
     * @param results counts of each label, key: label, value: label amount
     */
    LeafNode(Map<Integer, Integer> results) {
        super(null, null, null);
        this.results = results;
        int countOfOne = results.get(1) == null ? 0 : results.get(1);
        int countOfMinusOne = results.get(-1) == null ? 0 : results.get(-1);
        this.category = (countOfOne > countOfMinusOne) ? 1 : -1;
    }
}
