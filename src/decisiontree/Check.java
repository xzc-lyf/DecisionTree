package decisiontree;

public class Check {
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
}