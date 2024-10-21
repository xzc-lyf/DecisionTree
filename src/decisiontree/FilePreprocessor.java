package decisiontree;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Preprocessing includes 4 parts basically:
 *   1. Remove rows containing missing values.
 *   2. Remove the attribute "native-country".
 *   3. Convert the value of income from binary to integer 1(<=50K) and -1(>50K).
 *   4. Save the processed files to output directory.
 */
public class FilePreprocessor {
    public static List<Object[]> processAndSaveFile(String inputFilePath, String outputFilePath) throws IOException {
        List<String> processedLines = new ArrayList<String>();
        List<Object[]> finalData = new ArrayList<Object[]>();
        BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
        String line;

        while ((line = reader.readLine()) != null) {
            // Filter rows contain "?".
            if (!line.contains("?")) {
                String[] fields = line.split(", ");
                if (fields.length == 15) {
                    List<String> filteredFields = new ArrayList<String>();
                    for (int i = 0; i < fields.length - 1; i++) {
                        // Delete the second-to-last column
                        if (i != fields.length - 2) {
                            filteredFields.add(fields[i]);
                        }
                    }
                    String incomeLabel = fields[fields.length - 1];
                    // Convert the value of income from binary to integer 1(<=50K) and -1(>50K).
                    int label = incomeLabel.equals(">50K") || incomeLabel.equals(">50K.") ? -1 : 1;
                    filteredFields.add(String.valueOf(label));
                    processedLines.add(join(filteredFields));
                }
            }
        }
        reader.close();

        // Save the pre-processed files to disk.
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
        for (String processedLine : processedLines) {
            writer.write(processedLine);
            writer.newLine();
        }
        writer.close();

        // The processed data is returned as Object[].
        for (String processedLine : processedLines) {
            String[] values = processedLine.split(",");
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
