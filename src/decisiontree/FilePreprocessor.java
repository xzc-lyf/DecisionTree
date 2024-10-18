package decisiontree;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Preprocessing includes 4 parts basically:
 *   1. Remove rows containing missing values.
 *   2. Remove the attribute "native-country".
 *   3. Converting the value of income from binary to integer 1(>50K) and -1(<=50K).
 *   4. Save the processed files to output directory.
 */
public class FilePreprocessor {
    public static List<Object[]> processAndSaveFile(String inputFilePath, String outputFilePath) throws IOException {
        List<String> processedLines = new ArrayList<String>();
        List<Object[]> finalData = new ArrayList<Object[]>();

        // 使用BufferedReader读取输入文件并处理
        BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.contains("?")) {  // 过滤掉包含 ? 的行
                String[] fields = line.split(", ");  // 分割字段
                if (fields.length == 15) {  // 确保行有正确的列数
                    List<String> filteredFields = new ArrayList<String>();
                    for (int i = 0; i < fields.length - 1; i++) {
                        if (i != fields.length - 2) {  // 删除倒数第二列
                            filteredFields.add(fields[i]);
                        }
                    }
                    // 处理收入标签
                    String incomeLabel = fields[fields.length - 1];
                    int label = incomeLabel.equals(">50K") || incomeLabel.equals(">50K.") ? 1 : -1;
                    filteredFields.add(String.valueOf(label));  // 将标签转换为数字形式
                    processedLines.add(join(filteredFields));  // 拼接成字符串并添加到列表
                }
            }
        }
        reader.close();

        // 将处理后的结果写入输出文件
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
        for (String processedLine : processedLines) {
            writer.write(processedLine);
            writer.newLine();
        }
        writer.close();

        // 可选：如果需要返回处理后的数据，可以解析为最终的Object[]形式
        for (String processedLine : processedLines) {
            String[] values = processedLine.split(",");
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

    private static String join(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
