package decisiontree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Preprocessing includes 4 parts basically:
 *   1. Remove rows containing missing values.
 *   2. Remove the attribute "native-country".
 *   3. Converting the value of income from binary to integer 1(>50K) and -1(<=50K).
 *   4. Save the processed files to output directory.
 */
public class FilePreprocessor {
    public static List<Object[]> processAndSaveFile(String inputFilePath, String outputFilePath) throws IOException {
        List<String> processedLines = new ArrayList<>();
        List<Object[]> finalData = new ArrayList<>();

        Files.lines(Paths.get(inputFilePath))
                // Filter the rows contains "?".
                .filter(line -> !line.contains("?"))
                .map(line -> {
                    String[] fields = line.split(", ");
                    if (fields.length != 15) return null;
                    List<String> filteredFields = new ArrayList<>();
                    for (int i = 0; i < fields.length - 1; i++) {
                        // Delete the second-to-last column.
                        if (i != fields.length - 2) {
                            filteredFields.add(fields[i]);
                        }
                    }
                    String incomeLabel = fields[fields.length - 1];
                    // Converting the value of income from binary to integer 1(>50K) and -1(<=50K).
                    int label = incomeLabel.equals(">50K") || incomeLabel.equals(">50K.")? 1 : -1;
                    filteredFields.add(String.valueOf(label));
                    return String.join(",", filteredFields);
                })
                .filter(Objects::nonNull)
                .forEach(processedLines::add);

        // Writes the processed result to the output file.
        Files.write(Paths.get(outputFilePath), processedLines);

        for (String line : processedLines) {
            String[] values = line.split(",");
            Object[] row = new Object[values.length];
            for (int i = 0; i < values.length - 1; i++) {
                try {
                    row[i] = Integer.parseInt(values[i]);
                } catch (NumberFormatException e) {
                    row[i] = values[i];
                }
            }
            row[values.length - 1] = Integer.parseInt(values[values.length - 1]);
            finalData.add(row);
        }
        return finalData;
    }
}
