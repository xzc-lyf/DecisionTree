package decisiontree;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DecisionTreeSaver {
    // 保存决策树到文件
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
        // 如果是叶子节点，保存分类结果
        if (node.isLeaf()) {
            writer.write(prefix + (isTail ? "└── " : "├── ") + "Leaf: " + node.results + "\n");
        } else {
            // 保存当前节点的检查条件
            writer.write(prefix + (isTail ? "└── " : "├── ") + "Check: " + node.condition + "\n");
            // 保存 true 分支
            saveNode(node.trueBranch, writer, prefix + (isTail ? "    " : "│   "), false);
            // 保存 false 分支
            saveNode(node.falseBranch, writer, prefix + (isTail ? "    " : "│   "), true);
        }
    }
}
