import java.util.*;

public class Tester {

    public static void main(String[] args) {
        run("TestCases/input4.txt", "TestOutputs/my_output4.txt");

//        testClauseInitByList();

//        testClauseInitByString();

//        testClauseWeight();
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

    private static void testClauseWeight() {
        CNFClause c1 = new CNFClause("H(x)");
        CNFClause c2 = new CNFClause("X(x) => Y(y)");
        CNFClause c3 = new CNFClause("A(x) & B(y) => C(z)");

        Set< CNFClause> clauseMap = new TreeSet<>();
        clauseMap.add(c3);
        clauseMap.add(c2);
        clauseMap.add(c1);

        for (CNFClause clause: clauseMap) {
            System.out.println(clause);
        }
    }
}
