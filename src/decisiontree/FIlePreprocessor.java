package decisiontree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FIlePreprocessor {
    public static List<Object[]> preProcessFiles(String inputFilePath, String outputFilePath) throws IOException {
        removeRegionAndQuestionMark(inputFilePath, outputFilePath);
        return ConvertIncomeToLabel(outputFilePath);
    }

    private static void removeRegionAndQuestionMark(String inputFilePath, String outputFilePath) throws IOException {
        List<String> filteredLines = new ArrayList<>();
        Files.lines(Paths.get(inputFilePath))
                .filter(line -> !line.contains("?"))
                .map(line -> {
                    String[] fields = line.split(",");
                    List<String> filteredFields = new ArrayList<>();
                    for (int i = 0; i < fields.length; i++) {
                        if (i != fields.length - 2) {
                            filteredFields.add(fields[i]);
                        }
                    }
                    return String.join(",", filteredFields);
                })
                .forEach(filteredLines::add);
        Files.write(Paths.get(outputFilePath), filteredLines);
    }

    static List<Object[]> ConvertIncomeToLabel(String filename) throws IOException {
        List<Object[]> data = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.trim().split(", ");
            if (values.length != 15) {  // 期望数据集有15列，收入标签为最后一列
                continue;  // 忽略列数不匹配的行
            }
            Object[] row = new Object[values.length - 1];  // 不包含收入标签
            for (int i = 0; i < values.length - 1; i++) {
                try {
                    row[i] = Integer.parseInt(values[i]);  // 转换为整数
                } catch (NumberFormatException e) {
                    row[i] = values[i];  // 保留非整数值
                }
            }
            // 处理收入标签（最后一列）
            row[row.length - 1] = values[values.length - 1].equals(">50K") || values[values.length - 1].equals(">50K.") ? 1 : -1;
            data.add(row);
        }
        br.close();
        System.out.println(data.size());
        return data;
    }
}
