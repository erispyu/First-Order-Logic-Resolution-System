import java.util.*;

public class KnowledgeBase {
    private Map<String, Term> constantMap;

    private Map<String, CNFClause> clauseMap;

    private Map<Predicate, PriorityQueue<CNFClause>> predicateClausePQMap;

    private PriorityQueue<CNFClause> clausePQ;

    public KnowledgeBase() {
        this.constantMap = new HashMap<>();
        this.clauseMap = new HashMap<>();
        this.predicateClausePQMap = new HashMap<>();
        this.clausePQ = new PriorityQueue<>();
    }

    // Init the KB
    public KnowledgeBase(List<String> sentences) {
        this();
        for (String sentence: sentences) {
            CNFClause clause = new CNFClause(sentence);
            recordClause(clause);
        }
    }

    public void recordClause(CNFClause clause) {
        this.clauseMap.put(clause.toString(), clause);
        this.clausePQ.add(clause);

        for (Predicate predicate: clause.getPredicateLiteralListMap().keySet()) {
            mapPredicateClause(predicate, clause);
        }

        addConstantMap(clause.getConstantMap());
    }

    private void mapPredicateClause(Predicate predicate, CNFClause clause) {
        if (this.predicateClausePQMap.containsKey(predicate)) {
            this.predicateClausePQMap.get(predicate).add(clause);
        } else {
            PriorityQueue<CNFClause> clausePriorityQueue = new PriorityQueue<>();
            clausePriorityQueue.add(clause);
            this.predicateClausePQMap.put(predicate, clausePriorityQueue);
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

    public Map<Predicate, PriorityQueue<CNFClause>> getPredicateClausePQMap() {
        return predicateClausePQMap;
    }

    public PriorityQueue<CNFClause> getClausePQ() {
        return clausePQ;
    }

    public KnowledgeBase getDeepCopy() {
        KnowledgeBase copy = new KnowledgeBase();
        for(CNFClause clause: this.clauseMap.values()) {
            copy.recordClause(clause.getDeepCopy());
        }
        return copy;
    }

    public boolean containsClause(CNFClause clause) {
        return this.clauseMap.containsKey(clause.toString());
    }
}
