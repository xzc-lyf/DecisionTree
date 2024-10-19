package decisiontree;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DecisionTreeSaver {
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

    private static void saveNode(TreeNode node, BufferedWriter writer, String prefix, boolean isTail) throws IOException {
        if (node instanceof LeafNode) {
            LeafNode leaf = (LeafNode) node;
            writer.write(prefix + (isTail ? "└── " : "├── ") + "Label: " + leaf.category  + ", LeafNode: " + leaf.results + "\n");
        } else {
            writer.write(prefix + (isTail ? "└── " : "├── ") + "TreeNode: " + node.condition + "\n");
            saveNode(node.leftChild, writer, prefix + (isTail ? "    " : "│   "), false);
            saveNode(node.rightChild, writer, prefix + (isTail ? "    " : "│   "), true);
        }
    }
}
