import java.util.*;

public class Tester {

    public static void main(String[] args) {
//        run("TestCases/test_input_1.txt", "TestOutputs/my_output1.txt");

//        testClauseInitByList();

//        testClauseInitByString();

//        testClauseWeight("TestCases/input4.txt");

//        testResolve();

//        testGetUnifiedLiteral();

        testStandardize();
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

            System.out.println(Boolean.toString(result).toUpperCase());

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

        PriorityQueue<CNFClause> newPQ = new PriorityQueue<>(clausePriorityQueue);

        while(!newPQ.isEmpty()) {
            System.out.println(newPQ.poll().size());
        }

        System.out.println(clausePriorityQueue);
    }

    private static void testResolve() {
        Predicate predicate = new Predicate("Ready", 1);
        CNFClause c1 = new CNFClause("Ready(Ted)");
        CNFClause c2 = new CNFClause("~Ready(Ted)");

        ResolutionUtility.resolve(c1, c2, predicate);
    }

    private static void testGetUnifiedLiteral() {
        SingleLiteral l = new SingleLiteral("~Play(x, y, Cindy)");
        Map<Term, Term> subset = new HashMap<>();
        Term x = new Term("x");
        Term y = new Term("y");
        Term z = new Term("z");
        Term alice = new Term("Alice");
        Term bob = new Term("Bob");
        Term cindy = new Term("Cindy");

        subset.put(x, alice);
        subset.put(y, bob);
        subset.put(z, cindy);

        SingleLiteral unifiedLiteral = ResolutionUtility.getUnifiedLiteral(l, subset);
        System.out.println(unifiedLiteral);
    }

    private static void testStandardize() {
        CNFClause c1 = new CNFClause("A(x) & B(y) => C(z)");
        c1.standardize();
        CNFClause c2 = new CNFClause("A(x)");
        c2.standardize();

        System.out.println(c1);
        System.out.println(c2);
    }
}
