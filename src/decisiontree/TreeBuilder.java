package decisiontree;

import java.util.*;

public class TreeBuilder {
    public static final int S_Bar = 100;
    public static String[] attributes = {"age", "workclass", "fnlwgt", "education", "education-num", "marital-status",
            "occupation", "relationship", "race", "sex", "capital-gain", "capital-loss", "hours-per-week"};

    public static TreeNode buildTree(List<Object[]> data, Set<Integer> usedCols) {
        if (data.isEmpty()) {
            return null;
        }

        Set<Object> set = new HashSet<Object>();
        for (Object[] row : data) {
            set.add(row[row.length - 1]);
        }

        if (data.size() < S_Bar || set.size() == 1) {
            return new LeafNode(labelClasses(data));
        }

        double bestGini = 0.5;
        Check bestCheck = null;
        List<Object[]>[] bestPartition = null;
        int count = 0;

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
                Check check = new Check(col, value);
                List<Object[]>[] partitions = partition(check, data);
                List<Object[]> leftChild = partitions[0];
                List<Object[]> rightChild = partitions[1];

                double giniSplit = giniSplit(leftChild, rightChild);
                if (!leftChild.isEmpty() && !rightChild.isEmpty() && giniSplit < bestGini) {
                    bestGini = giniSplit;
                    bestCheck = check;
                    bestPartition = partitions;
                }
            }
        }

        if (bestGini == 0.5 || count + usedCols.size() == 13) {
            return new LeafNode(labelClasses(data));
        }

        usedCols.add(bestCheck.col);

        TreeNode leftChild = buildTree(bestPartition[0], usedCols);
        TreeNode rightChild = buildTree(bestPartition[1], usedCols);
        return new TreeNode(bestCheck, leftChild, rightChild);
    }

    public static List<Object[]>[] partition(Check condition, List<Object[]> data) {
        List<Object[]> trueSet = new ArrayList<Object[]>();
        List<Object[]> falseSet = new ArrayList<Object[]>();
        for (Object[] row : data) {
            if (condition.check(row)) {
                trueSet.add(row);
            } else {
                falseSet.add(row);
            }
        }
        return new List[]{trueSet, falseSet};
    }

    public static double giniSplit(List<Object[]> left, List<Object[]> right) {
        double p = (double) left.size() / (left.size() + right.size());
        return p * gini(left) + (1 - p) * gini(right);
    }

    public static double gini(List<Object[]> data) {
        Map<Integer, Integer> counts = labelClasses(data);
        double impurity = 1.0;
        for (int count : counts.values()) {
            double prob = count / (double) data.size();
            impurity -= prob * prob;
        }
        return impurity;
    }

    public static Map<Integer, Integer> labelClasses(List<Object[]> data) {
        Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
        for (Object[] row : data) {
            int label = (Integer) row[row.length - 1];
            if (counts.containsKey(label)) {
                counts.put(label, counts.get(label) + 1);
            } else {
                counts.put(label, 1);
            }
        }
        return counts;
    }
}
