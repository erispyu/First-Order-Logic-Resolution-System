import java.util.List;
import java.util.Map;

public class KnowledgeBase {

    private int KBSize;

    private Map<String, Predicate> predicateMap;

    public KnowledgeBase() {
        this.KBSize = 0;
    }

    public KnowledgeBase(int KBSize) {
        this.KBSize = KBSize;
    }

    public void appendPredicate(Predicate predicate) {
        this.predicateMap.put(predicate.getName(), predicate);
    }

    public void appendPredicateList(List<Predicate> predicateList) {
        for (Predicate predicate: predicateList) {
            appendPredicate(predicate);
        }
    }

    public int getKBSize() {
        return KBSize;
    }
}
