import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class KnowledgeBase {
    private Map<String, Term> constantMap;

    private Map<String, CNFClause> CFNClauseMap;

    private Map<Predicate, List<CNFClause>> predicateCNFClauseListMap;

    public KnowledgeBase() {
        this.constantMap = new HashMap<>();
        this.CFNClauseMap = new HashMap<>();
        this.predicateCNFClauseListMap = new HashMap<>();
    }

    // Init the KB
    public KnowledgeBase(List<String> sentences) {
        this();
        for (String sentence: sentences) {
            if (sentence.contains(Operator.Implication.denotation)) {
                parseImplication(sentence);
            } else {
                parseSingleLiteral(sentence);
            }
        }
    }

    public void addClause(CNFClause clause) {
        recordClause(clause);
    }

    private void parseSingleLiteral(String str) {
        SingleLiteral literal = new SingleLiteral(str);
        CNFClause clause = new CNFClause(literal);
        recordClause(clause);
    }

    private void parseImplication(String str) {
        CNFClause clause = new CNFClause(str);
        recordClause(clause);
    }

    private void recordClause(CNFClause clause) {
        this.CFNClauseMap.put(clause.toString(), clause);

        for (Predicate predicate: clause.getPredicateLiteralListMap().keySet()) {
            mapPredicateCNFClause(predicate, clause);
        }

        addConstantMap(clause.getConstantMap());
    }

    private void mapPredicateCNFClause(Predicate predicate, CNFClause clause) {
        if (this.predicateCNFClauseListMap.containsKey(predicate)) {
            this.predicateCNFClauseListMap.get(predicate).add(clause);
        } else {
            List<CNFClause> clauseList = new LinkedList<>();
            clauseList.add(clause);
            this.predicateCNFClauseListMap.put(predicate, clauseList);
        }
    }

    private void addConstant(Term constant) {
        this.constantMap.put(constant.getName(), constant);
    }

    private void addConstantMap(Map<String, Term> constantMap) {
        this.constantMap.putAll(constantMap);
    }

    public Map<String, CNFClause> getCFNClauseMap() {
        return CFNClauseMap;
    }

    public Map<Predicate, List<CNFClause>> getPredicateCNFClauseListMap() {
        return predicateCNFClauseListMap;
    }

    public KnowledgeBase getDeepCopy() {
        KnowledgeBase copy = new KnowledgeBase();
        for(CNFClause clause: this.CFNClauseMap.values()) {
            copy.addClause(clause.getDeepCopy());
        }
        return copy;
    }
}
