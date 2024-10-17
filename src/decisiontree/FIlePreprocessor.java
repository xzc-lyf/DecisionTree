package decisiontree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FIlePreprocessor {
    static String inputFilePath = "src/input/adult.data";
    static String outputFilePath = "src/output/adult_preprocessed.csv";
    public static void preProcessFiles() throws IOException {
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
}
