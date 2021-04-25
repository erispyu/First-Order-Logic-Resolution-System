import java.util.*;

public class Tester {

    public static void main(String[] args) {
        run("TestCases/test_input_1.txt", "TestOutputs/my_output4.txt");

//        testClauseInitByList();

//        testClauseInitByString();

//        testClauseWeight("TestCases/input4.txt");
    }

    private static void run(String inputFilePath, String outputFilePath) {
        InputData inputData = FileUtility.parseInput(inputFilePath);

        List<SingleLiteral> queries = inputData.getQueries();
        List<String> sentences = inputData.getSentences();

        KnowledgeBase knowledgeBase = new KnowledgeBase(sentences);

        List<String> results = new LinkedList<>();

        for (SingleLiteral query: queries) {
            QueryHelper queryHelper = new QueryHelper(query, knowledgeBase);
            boolean result = queryHelper.query();
            results.add(Boolean.toString(result).toUpperCase());
        }

        FileUtility.generateOutput(outputFilePath, results);
    }

    private static void testClauseInitByList() {
        SingleLiteral l1 = new SingleLiteral("Healthy(Teddy)");
        SingleLiteral l2 = new SingleLiteral("~Healthy(x)");
        SingleLiteral l3 = new SingleLiteral("Healthy(Alice)");

        List<SingleLiteral> lList = new LinkedList<>();
//        lList.add(l1);
//        lList.add(l2);
//        lList.add(l3);

        CNFClause clause = new CNFClause(lList);
        System.out.println(clause);
    }

    private static void testClauseInitByString() {
        String str = "~Play(x,y) & Fun(x) => Happy(y)";
        CNFClause clause = new CNFClause(str);
        System.out.println(clause);
    }

    private static void testClauseWeight(String inputFilePath) {
        InputData inputData = FileUtility.parseInput(inputFilePath);

        List<String> sentences = inputData.getSentences();

        KnowledgeBase knowledgeBase = new KnowledgeBase(sentences);

        PriorityQueue<CNFClause> clausePriorityQueue = knowledgeBase.getClausePQ();

        while(!clausePriorityQueue.isEmpty()) {
            System.out.println(clausePriorityQueue.poll().size());
        }
    }
}
