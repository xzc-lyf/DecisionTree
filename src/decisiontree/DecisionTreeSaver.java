package decisiontree;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * print and save decision tree to a file
 */
public class DecisionTreeSaver {
    /**
     * function that save and print the decision tree to the file
     * @param node root of decision tree
     * @param filePath path of file saving decision tree
     * @throws IOException
     */
    public static void saveTreeToFile(TreeNode node, String filePath) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filePath));
            saveNode(node, writer, "", true);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * function that save and print treenode to the file
     * @param node the current tree node being processed
     * @param writer the BufferedWriter used for writing information
     * @param prefix a prefix string used for formatting output
     * @param isTail whether the current node is the last child of its parent
     * @throws IOException
     */
    private static void saveNode(TreeNode node, BufferedWriter writer, String prefix, boolean isTail) throws IOException {
        if (node instanceof LeafNode) {
            LeafNode leaf = (LeafNode) node;
            writer.write(prefix + (isTail ? "└── " : "├── ") + "Label: " + leaf.category  + ", LeafNode: " + leaf.results + "\n");
        } else {
            writer.write(prefix + (isTail ? "└── " : "├── ") + "Predicate: " + node.predicate + "\n");
            saveNode(node.leftChild, writer, prefix + (isTail ? "    " : "│   "), false);
            saveNode(node.rightChild, writer, prefix + (isTail ? "    " : "│   "), true);
        }
    }
}
