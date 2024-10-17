package decisiontree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FIlePreprocessor {
    static String inputFilePath = "src/input/adult.data";
    static String outputFilePath = "src/output/adult_preprocessed.csv";
    public static List<Object[]> preProcessFiles() throws IOException {
        removeRegionAndQuestionMark();
        return ConvertIncomeToLabel(outputFilePath);
    }

    private static void removeRegionAndQuestionMark() throws IOException {
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
            Object[] row = new Object[values.length];
            for (int i = 0; i < values.length; i++) {
                // Try to parse as integer, otherwise keep as String
                try {
                    row[i] = Integer.parseInt(values[i]);
                } catch (NumberFormatException e) {
                    row[i] = values[i];
                }
            }
            // Convert income label to 1 or -1
            row[row.length - 1] = values[values.length - 1].equals(">50K") ? 1 : -1;
            data.add(row);
        }
        br.close();
        return data;
    }
}
