import java.util.LinkedList;
import java.util.List;

public class homework {

    public static void main(String[] args) {
        run("input.txt", "output.txt");
    }

    private static void run(String inputFilePath, String outputFilePath) {
        InputData inputData = FileUtility.parseInput(inputFilePath);

        List<SingleLiteral> queries = inputData.getQueries();
        List<String> sentences = inputData.getSentences();

        KnowledgeBase knowledgeBase = new KnowledgeBase(sentences);

        List<String> results = new LinkedList<>();

        for (SingleLiteral query: queries) {
            ResolutionHelper resolutionHelper = new ResolutionHelper(query, knowledgeBase);
            boolean result = resolutionHelper.query();

            results.add(Boolean.toString(result).toUpperCase());
        }

        FileUtility.generateOutput(outputFilePath, results);
    }
}
