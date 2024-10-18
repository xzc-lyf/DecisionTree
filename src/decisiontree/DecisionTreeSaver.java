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
            saveNode(node, writer, 0);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    private static void saveNode(TreeNode node, BufferedWriter writer, int depth) throws IOException {
        // 如果是叶子节点，保存分类结果
        if (node.isLeaf()) {
            writer.write(indent(depth) + "Leaf: " + node.results + "\n");  // 输出叶子节点中的分类结果
        } else {
            // 保存当前节点的检查条件
            writer.write(indent(depth) + "Check: " + node.condition + "\n");  // 输出条件描述
            // 保存 true 分支
            writer.write(indent(depth) + "--> True Branch:\n");
            saveNode(node.trueBranch, writer, depth + 1);
            // 保存 false 分支
            writer.write(indent(depth) + "--> False Branch:\n");
            saveNode(node.falseBranch, writer, depth + 1);
        }
    }

    private static String indent(int depth) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append("  ");
        }
        return indent.toString();
    }
}
