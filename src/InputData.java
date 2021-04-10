public class InputData {
    private int querySize;
    private SingleLiteral[] queries;
    private int sentenceSize;
    private Sentence[] sentences;

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
        this.sentences = new Sentence[sentenceSize];
    }

    public void setSentence(Sentence sentence, int index) {
        this.sentences[index] = sentence;
    }

    public SingleLiteral[] getQueries() {
        return queries;
    }

    public Sentence[] getSentences() {
        return sentences;
    }
}
