import java.util.*;

public class KnowledgeBase {
    private Map<String, Term> constantMap;

    private Map<String, CNFClause> clauseMap;

    private Map<Predicate, List<CNFClause>> predicateClauseListMap;

    private PriorityQueue<CNFClause> clausePriorityQueue;

    public KnowledgeBase() {
        this.constantMap = new HashMap<>();
        this.clauseMap = new HashMap<>();
        this.predicateClauseListMap = new HashMap<>();
        this.clausePriorityQueue = new PriorityQueue<>();
    }

    // Init the KB
    public KnowledgeBase(List<String> sentences) {
        this();
        for (String sentence: sentences) {
            CNFClause clause = new CNFClause(sentence);
            recordClause(clause);
        }
    }

    public void addClause(CNFClause clause) {
        recordClause(clause);
    }

    private void recordClause(CNFClause clause) {
        this.clauseMap.put(clause.toString(), clause);
        this.clausePriorityQueue.add(clause);

        for (Predicate predicate: clause.getPredicateLiteralListMap().keySet()) {
            mapPredicateClause(predicate, clause);
        }

        addConstantMap(clause.getConstantMap());
    }

    private void mapPredicateClause(Predicate predicate, CNFClause clause) {
        if (this.predicateClauseListMap.containsKey(predicate)) {
            this.predicateClauseListMap.get(predicate).add(clause);
        } else {
            List<CNFClause> clauseList = new LinkedList<>();
            clauseList.add(clause);
            this.predicateClauseListMap.put(predicate, clauseList);
        }
    }

    private void addConstant(Term constant) {
        this.constantMap.put(constant.getName(), constant);
    }

    private void addConstantMap(Map<String, Term> constantMap) {
        this.constantMap.putAll(constantMap);
    }

    public Map<String, CNFClause> getClauseMap() {
        return clauseMap;
    }

    public Map<Predicate, List<CNFClause>> getPredicateClauseListMap() {
        return predicateClauseListMap;
    }

    public PriorityQueue<CNFClause> getClausePriorityQueue() {
        return clausePriorityQueue;
    }

    public KnowledgeBase getDeepCopy() {
        KnowledgeBase copy = new KnowledgeBase();
        for(CNFClause clause: this.clauseMap.values()) {
            copy.addClause(clause.getDeepCopy());
        }
        return copy;
    }
}
