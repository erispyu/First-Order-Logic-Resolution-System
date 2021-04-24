import java.util.LinkedList;
import java.util.List;

public class Tester {

    public static void main(String[] args) {
//        run("TestCases/input4.txt", "TestOutputs/myOutput4.txt");
        SingleLiteral l1 = new SingleLiteral("Healthy(Teddy)");
        SingleLiteral l2 = new SingleLiteral("~Healthy(x)");
        SingleLiteral l3 = new SingleLiteral("Healthy(Alice)");

        List<SingleLiteral> lList = new LinkedList<>();
        lList.add(l1);
        lList.add(l2);
        lList.add(l3);

        CNFClause clause = new CNFClause(lList);
        System.out.println(clause);
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
