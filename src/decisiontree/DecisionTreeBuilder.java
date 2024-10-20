package decisiontree;

import java.util.*;

/**
 * DecisionTreeBuilder class for building a decision tree based on a given dataset.
 * This class implements methods for tree construction, data division, and Gini index calculation.
 */
public class DecisionTreeBuilder {
    public static final int SET_THRESHOLD = 100;
    public static String[] attributes = {"age", "workclass", "fnlwgt", "education", "education-num", "marital-status",
            "occupation", "relationship", "race", "sex", "capital-gain", "capital-loss", "hours-per-week"};

    /**
     * Builds a decision tree recursively based on the dataset.
     *
     * @param data The dataset used for building the tree.
     * @param usedCols A set of columns already used to avoid redundant splits.
     * @return The root node of the decision tree.
     */
    public static TreeNode buildTree(List<Object[]> data, Set<Integer> usedCols) {
        if (data.isEmpty()) {
            return null;
        }

        Set<Object> set = new HashSet<Object>();
        for (Object[] row : data) {
            set.add(row[row.length - 1]);
        }

        // If data size is below threshold or labels are identical, create a leaf node directly
        // to avoid overfitting and excessive recursion.
        if (data.size() < SET_THRESHOLD || set.size() == 1) {
            return new LeafNode(classCounts(data));
        }

        double optGiniSplit = 0.5;
        Predicate optPredicate = null;
        List<Object[]>[] optDivision = null;
        int count = 0;

        // Iterate through attributes and find the best split based on Gini impurity.
        for (int col = 0; col < attributes.length; col++) {
            if (usedCols.contains(col)) {
                continue;
            }
            Set<Object> uniqueValues = new HashSet<Object>();
            for (Object[] row : data) {
                uniqueValues.add(row[col]);
            }
            if (uniqueValues.size() == 1) {
                count++;
            }

            for (Object value : uniqueValues) {
                Predicate predicate = new Predicate(col, value);
                List<Object[]>[] divisions = dataDivision(predicate, data);
                List<Object[]> trueList = divisions[0];
                List<Object[]> falseList = divisions[1];

                double giniSplit = giniSplit(trueList, falseList);
                if (!trueList.isEmpty() && !falseList.isEmpty() && giniSplit < optGiniSplit) {
                    optGiniSplit = giniSplit;
                    optPredicate = predicate;
                    optDivision = divisions;
                }
            }
        }

        // If no better split is found or all attributes unused have the same value , return a leaf node.
        if (optGiniSplit == 0.5 || count + usedCols.size() == 13) {
            return new LeafNode(classCounts(data));
        }
        usedCols.add(optPredicate.attribute);

        TreeNode leftChild = buildTree(optDivision[0], usedCols);
        TreeNode rightChild = buildTree(optDivision[1], usedCols);
        return new TreeNode(optPredicate, leftChild, rightChild);
    }

    /**
     * Divides the data into two subsets based on the given predicate.
     *
     * @param predicate The condition used to split the data.
     * @param data The dataset to be split.
     * @return An array of two lists: one for data that matches the predicate and one for data that does not.
     */
    public static List<Object[]>[] dataDivision(Predicate predicate, List<Object[]> data) {
        List<Object[]> trueList = new ArrayList<Object[]>();
        List<Object[]> falseList = new ArrayList<Object[]>();
        for (Object[] row : data) {
            if (predicate.predicate(row)) {
                trueList.add(row);
            } else {
                falseList.add(row);
            }
        }
        return new List[]{trueList, falseList};
    }

    /**
     * Calculates the Gini impurity of a split into two subsets.
     *
     * @param trueList The subset of data that matches the predicate.
     * @param falseList The subset of data that does not match the predicate.
     * @return The Gini impurity of the split.
     */
    public static double giniSplit(List<Object[]> trueList, List<Object[]> falseList) {
        double p = (double) trueList.size() / (trueList.size() + falseList.size());
        return p * gini(trueList) + (1 - p) * gini(falseList);
    }

    /**
     * Calculates the Gini impurity for a given subset of data.
     *
     * @param data The subset of data.
     * @return The Gini impurity.
     */
    public static double gini(List<Object[]> data) {
        Map<Integer, Integer> counts = classCounts(data);
        double impurity = 1.0;
        for (int label : counts.keySet()) {
            double proportion = counts.get(label) / (double) data.size();
            impurity -= Math.pow(proportion, 2);
        }
        return impurity;
    }

    /**
     * Counts the occurrences of each class label in the data.
     *
     * @param data The dataset to count class labels in.
     * @return A map with class labels as keys and their frequencies as values.
     */
    public static Map<Integer, Integer> classCounts(List<Object[]> data) {
        Map<Integer, Integer> classesMap = new HashMap<Integer, Integer>();
        for (Object[] row : data) {
            int label = (Integer) row[row.length - 1];
            if (classesMap.containsKey(label)) {
                classesMap.put(label, classesMap.get(label) + 1);
            } else {
                classesMap.put(label, 1);
            }
        }
        return classesMap;
    }
}
