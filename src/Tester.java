public class Tester {

    public static void main(String[] args) {
        run("input.txt", "output.txt");
    }

    private static void run(String inputFilePath, String outputFilePath) {
        InputData inputData = FileUtility.parseInput(inputFilePath);

        SingleLiteral[] queries = inputData.getQueries();

        Sentence[] sentences = inputData.getSentences();

        KnowledgeBase knowledgeBase = new KnowledgeBase();

        //TODO: init the kb

        //TODO: query
        String[] results = new String[queries.length];
        for (int i = 0; i < queries.length; i++) {
            if (ResolutionUtility.query(queries[i], knowledgeBase)) {
                results[i] = "TRUE";
            } else {
                results[i] = "FALSE";
            }
        }

        FileUtility.generateOutput(outputFilePath, results);
    }

}
