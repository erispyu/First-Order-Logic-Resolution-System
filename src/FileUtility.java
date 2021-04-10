import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileUtility {
    public static InputData parseInput(String inputFilePath) {
        InputData inputData = new InputData();

        try {
            File inputFile = new File(inputFilePath);
            Scanner in = new Scanner(inputFile);

            // Set the queries
            int querySize = in.nextInt();
            inputData.initQueries(querySize);
            for (int i = 0; i < querySize; i++) {
                SingleLiteral query = new SingleLiteral(in.nextLine());
                inputData.setQuery(null, i);
            }

            // Set the sentences
            int sentenceSize = in.nextInt();
            inputData.initSentences(sentenceSize);
            for (int i = 0; i < sentenceSize; i++) {
                Sentence sentence = new Sentence(in.nextLine());
                inputData.setSentence(sentence, i);
            }

            in.close();
        } catch (IOException e) {
            System.out.println("Failed to read from: " + inputFilePath);
            e.printStackTrace();
        }

        return inputData;
    }

    public static void generateOutput(String filePath, String[] results) {
        try {
            FileWriter writer = new FileWriter(filePath);
            for (int i = 0; i < results.length; i++) {
                writer.write(results[i]);
                if (i != results.length - 1) {
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
