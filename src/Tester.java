import java.util.List;

public class Tester {

    public static void main(String[] args) {
        run("TestCases/input3.txt", "TestOutputs/myOutput3.txt");
    }

    private static void run(String inputFilePath, String outputFilePath) {
        InputData inputData = FileUtility.parseInput(inputFilePath);

        List<SingleLiteral> queries = inputData.getQueries();
        List<String> sentences = inputData.getSentences();

        KnowledgeBase knowledgeBase = new KnowledgeBase(sentences);

        FileUtility.generateOutput(outputFilePath, sentences);
    }

}
