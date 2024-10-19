package decisiontree;

import static decisiontree.DecisionTreeBuilder.attributes;

/**
 * Condition of the tree node
 */
public class Predicate {
    int attribute;
    Object value;

    Predicate(int attribute, Object value) {
        this.attribute = attribute;
        this.value = value;
    }

    /**
     * function that predicate if the value satisfies the predicate
     * @param row one row of dataset
     * @return true if the attribute meet the condition, false otherwise
     */
    boolean predicate(Object[] row) {
        if (row[attribute] instanceof Integer) {
            return (Integer) row[attribute] <= (Integer) value;
        } else {
            return row[attribute].equals(value);
        }
    }

    @Override
    public String toString() {
        return "Is " + attributes[attribute] + ((value instanceof Integer) ? " > " : " == ") + value + "?";

    }
}