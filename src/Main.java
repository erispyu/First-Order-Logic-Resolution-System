public class Main {

    public static void main(String[] args) {
        run("input.txt", "output.txt");
    }

    private static void run(String inputFilePath, String outputFilePath) {
        FileUtility.parseInput(inputFilePath);

        FileUtility.generateOutput(outputFilePath, null);
    }
}
