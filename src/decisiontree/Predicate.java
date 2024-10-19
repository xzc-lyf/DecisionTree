package decisiontree;

public class Predicate {
    int attribute;
    Object value;

    Predicate(int attribute, Object value) {
        this.attribute = attribute;
        this.value = value;
    }

    // Predicate if the value satisfies the predicate (numerical or categorical comparison)
    boolean predicate(Object[] row) {
        if (row[attribute] instanceof Integer) {
            return (Integer) row[attribute] <= (Integer) value;
        } else {
            return row[attribute].equals(value);
        }
    }
}