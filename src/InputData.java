import java.util.LinkedList;
import java.util.List;

public class InputData {
    private int querySize;
    private List<SingleLiteral> queries;
    private int sentenceSize;
    private List<String> sentences;

    public InputData() {

    }

    public void initQueries(int querySize) {
        this.querySize = querySize;
        this.queries = new LinkedList<>();
    }

    public void addQuery(SingleLiteral query) {
        this.queries.add(query);
    }

    public void initSentences(int sentenceSize) {
        this.sentenceSize = sentenceSize;
        this.sentences = new LinkedList<>();
    }

    public void addSentence(String sentence) {
        this.sentences.add(sentence);
    }

    public int getQuerySize() {
        return querySize;
    }

    public int getSentenceSize() {
        return sentenceSize;
    }

    public List<SingleLiteral> getQueries() {
        return this.queries;
    }

    public List<String> getSentences() {
        return this.sentences;
    }
}
