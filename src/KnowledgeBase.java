import java.util.List;
import java.util.Map;

public class KnowledgeBase {
    private List<Sentence> sentenceList;

    private int KBSize;

    private Map<String, Predicate> predicateMap;

    public KnowledgeBase () {
        this.KBSize = 0;
    }

    public void appendSentence(Sentence sentence) {
        this.sentenceList.add(sentence);
        this.KBSize++;
    }

    public void appendPredicate(Predicate predicate) {
        this.predicateMap.put(predicate.getName(), predicate);
    }

    public int getKBSize() {
        return KBSize;
    }
}
