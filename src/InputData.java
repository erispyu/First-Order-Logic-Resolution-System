public class InputData {
    private int querySize;
    private SingleLiteral[] queries;
    private int sentenceSize;

    public InputData() {

    }

    public void initQueries(int querySize) {
        this.querySize = querySize;
        this.queries = new SingleLiteral[querySize];
    }

    public void setQuery(SingleLiteral query, int index) {
        this.queries[index] = query;
    }

    public void initSentences(int sentenceSize) {
        this.sentenceSize = sentenceSize;
    }
}
