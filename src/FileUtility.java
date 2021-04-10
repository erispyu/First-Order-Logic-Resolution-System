import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileUtility {
    public static void parseInput(String inputFilePath) {
        try {
            File inputFile = new File(inputFilePath);
            Scanner in = new Scanner(inputFile);

            // TODO: scan
            int querySize = in.nextInt();
            SingleLiteralForm[] queries = new SingleLiteralForm[querySize];
            for (int i = 0; i < querySize; i++) {

            }

            in.close();
        } catch (IOException e) {
            System.out.println("Failed to read from: " + inputFilePath);
            e.printStackTrace();
        }
    }

    public static void generateOutput(String outputFilePath) {
        try {
            FileWriter writer = new FileWriter(outputFilePath);

            // TODO: write

            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to write into: " + outputFilePath);
            e.printStackTrace();
        }
    }
}
