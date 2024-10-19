package decisiontree;

import java.util.*;

public class TreeBuilder {

    public static final int S_Bar = 100;

    public static TreeNode buildTree(List<Object[]> data, Set<Integer> usedCols) {
        if (data.isEmpty()) {
            return null;
        }

        Set<Object> set = new HashSet<Object>();
        for (Object[] row : data) {
            set.add(row[row.length - 1]);
        }

        if (data.size() < S_Bar || set.size() == 1) {
            return new LeafNode(classCounts(data));
        }

        double bestGini = 0.5;
        Predicate bestPredicate = null;
        List<Object[]>[] bestPartition = null;
        int count = 0;

        for (int col = 0; col < 13; col++) {
            if (usedCols.contains(col)) {
                continue; // 跳过当前已经计算过的属性
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
                List<Object[]>[] partitions = dataDivision(predicate, data);
                List<Object[]> trueList = partitions[0];
                List<Object[]> falseList = partitions[1];

                if (trueList.isEmpty() || falseList.isEmpty()) continue;

                double giniSplit = giniSplit(trueList, falseList);
                if (giniSplit < bestGini) {
                    bestGini = giniSplit;
                    bestPredicate = predicate;
                    bestPartition = partitions;
                }
            }
        }

        if (bestGini == 0.5 || count + usedCols.size() == 13) {
            return new LeafNode(classCounts(data));
        }
        // 记录使用的列
        usedCols.add(bestPredicate.col);

        TreeNode leftChild = buildTree(bestPartition[0], usedCols);
        TreeNode rightChild = buildTree(bestPartition[1], usedCols);
        return new TreeNode(bestPredicate, leftChild, rightChild);
    }

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

    public static double giniSplit(List<Object[]> trueList, List<Object[]> falseList) {
        double p = (double) trueList.size() / (trueList.size() + falseList.size());
        return p * gini(trueList) + (1 - p) * gini(falseList);
    }

    public static double gini(List<Object[]> data) {
        Map<Integer, Integer> counts = classCounts(data);
        double impurity = 1.0;
        for (int label : counts.keySet()) {
            double proportion = counts.get(label) / (double) data.size();
            impurity -= Math.pow(proportion, 2);
        }
        return impurity;
    }

    public static Map<Integer, Integer> classCounts(List<Object[]> data) {
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
