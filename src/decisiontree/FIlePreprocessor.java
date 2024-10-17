package decisiontree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FIlePreprocessor {

    public static List<Object[]> processAndSaveFile(String inputFilePath, String outputFilePath) throws IOException {
        List<String> processedLines = new ArrayList<>();
        List<Object[]> finalData = new ArrayList<>();

        // 读取输入文件并处理
        Files.lines(Paths.get(inputFilePath))
                .filter(line -> !line.contains("?"))  // 过滤掉包含 ? 的行
                .map(line -> {
                    String[] fields = line.split(", ");  // 分割字段
                    if (fields.length != 15) return null; // 确保行有正确的列数
                    List<String> filteredFields = new ArrayList<>();
                    for (int i = 0; i < fields.length - 1; i++) {
                        if (i != fields.length - 2) {  // 删除倒数第二列
                            filteredFields.add(fields[i]);
                        }
                    }
                    // 处理收入标签
                    String incomeLabel = fields[fields.length - 1];
                    int label = incomeLabel.equals(">50K") || incomeLabel.equals(">50K.") ? 1 : -1;
                    filteredFields.add(String.valueOf(label));  // 将标签转换为数字形式
                    return String.join(",", filteredFields);  // 拼接成字符串
                })
                .filter(Objects::nonNull)  // 过滤掉无效行
                .forEach(processedLines::add);

        // 将处理后的结果写入输出文件
        Files.write(Paths.get(outputFilePath), processedLines);

        // 可选：如果需要返回处理后的数据，可以解析为最终的Object[]形式
        for (String line : processedLines) {
            String[] values = line.split(",");
            Object[] row = new Object[values.length];
            for (int i = 0; i < values.length - 1; i++) {
                try {
                    row[i] = Integer.parseInt(values[i]);  // 尝试转换为整数
                } catch (NumberFormatException e) {
                    row[i] = values[i];  // 处理非整数值
                }
            }
            row[values.length - 1] = Integer.parseInt(values[values.length - 1]);  // 最后一列是标签，已为数字
            finalData.add(row);
        }
        return finalData;
    }
}
