import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class FileUtility {
    public static InputData parseInput(String inputFilePath) {
        InputData inputData = new InputData();

        try {
            File inputFile = new File(inputFilePath);
            Scanner in = new Scanner(inputFile);

            // Set the queries
            int querySize = Integer.parseInt(in.nextLine());
            inputData.initQueries(querySize);
            for (int i = 0; i < querySize; i++) {
                SingleLiteral query = new SingleLiteral(in.nextLine());
                inputData.addQuery(query);
            }

            // Set the sentences
            int sentenceSize = Integer.parseInt(in.nextLine());
            inputData.initSentences(sentenceSize);
            for (int i = 0; i < sentenceSize; i++) {
                String sentence = in.nextLine();
                inputData.addSentence(sentence);
            }

            in.close();
        } catch (IOException e) {
            System.out.println("Failed to read from: " + inputFilePath);
            e.printStackTrace();
        }

        return inputData;
    }

    public static void generateOutput(String filePath, List<String> results) {
        try {
            FileWriter writer = new FileWriter(filePath);
            int size = results.size();
            for (int i = 0; i < size; i++) {
                writer.write(results.get(i));
                if (i != size - 1) {
                    writer.write(System.lineSeparator());
                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to write into: " + filePath);
            e.printStackTrace();
        }
    }
}
