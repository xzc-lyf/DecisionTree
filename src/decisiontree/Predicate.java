package decisiontree;

public class Predicate {
    int col;
    Object val;

    Predicate(int col, Object val) {
        this.col = col;
        this.val = val;
    }

    // Predicate if the value satisfies the predicate (numerical or categorical comparison)
    boolean predicate(Object[] row) {
        if (row[col] instanceof Integer) {
            return (Integer) row[col] > (Integer) val;
        } else {
            return row[col].equals(val);
        }
    }
}