package decisiontree;

import java.io.IOException;
import java.util.List;

import static decisiontree.FIlePreprocessor.preProcessFiles;
public class Main {
    public static void main(String[] args) throws IOException {
        List<Object[]> trainingData = preProcessFiles();

    }
}