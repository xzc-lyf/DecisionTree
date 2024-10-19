package decisiontree;

import static decisiontree.TreeBuilder.attributes;

public class Condition {
    int col;
    Object val;

    Condition(int col, Object val) {
        this.col = col;
        this.val = val;
    }

    // Check if the value satisfies the condition (numerical or categorical comparison)
    boolean check(Object[] row) {
        return (row[col] instanceof Integer) ? (Integer) row[col] <= (Integer) val : row[col].equals(val);
    }

    @Override
    public String toString() {
        return "Is " + attributes[col] + ((val instanceof Integer) ? " > " : " == ") + val + "?";
    }
}