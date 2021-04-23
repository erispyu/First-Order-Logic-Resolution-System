import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class KnowledgeBase {

    private Map<String, Predicate> predicateMap;

    private Map<String, Term> constantMap;

    private List<CNFClause> CFNClauseList;

    public KnowledgeBase(List<String> sentences) {
        this.predicateMap = new HashMap<>();
        this.constantMap = new HashMap<>();
        this.CFNClauseList = new LinkedList<>();

        for (String sentence: sentences) {
            if (sentence.contains(Operator.Implication.denotation)) {
                parseImplication(sentence);
            } else {
                parseSingleLiteral(sentence);
            }
        }
    }

    private void parseSingleLiteral(String str) {
        SingleLiteral literal = new SingleLiteral(str);
        this.CFNClauseList.add(new CNFClause(literal));

        addPredicate(literal.getPredicate());

        Term[] terms = literal.getTerms();
        for (Term term: terms) {
            if (term.isConstant()) {
                addConstant(term);
            }
        }
    }

    private void parseImplication(String str) {
        CNFClause clause = new CNFClause(str);
        this.CFNClauseList.add(clause);
        addPredicateMap(clause.getPredicateMap());
        addConstantMap(clause.getConstantMap());
    }

    private void addPredicate(Predicate predicate) {
        this.predicateMap.put(predicate.getName(), predicate);
    }

    private void addPredicateMap(Map<String, Predicate> predicateMap) {
        this.predicateMap.putAll(predicateMap);
    }

    private void addConstant(Term constant) {
        this.constantMap.put(constant.getName(), constant);
    }

    private void addConstantMap(Map<String, Term> constantMap) {
        this.constantMap.putAll(constantMap);
    }

    private void addCNFClause(CNFClause clause) {
        this.CFNClauseList.add(clause);
    }
}
