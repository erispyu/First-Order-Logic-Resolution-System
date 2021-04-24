import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class KnowledgeBase {
    private Map<String, Term> constantMap;

    private List<CNFClause> CFNClauseList;

    private Map<Predicate, List<CNFClause>> predicateCNFClauseListMap;

    // Init the KB
    public KnowledgeBase(List<String> sentences) {
        this.constantMap = new HashMap<>();
        this.CFNClauseList = new LinkedList<>();

        this.predicateCNFClauseListMap = new HashMap<>();

        for (String sentence: sentences) {
            if (sentence.contains(Operator.Implication.denotation)) {
                parseImplication(sentence);
            } else {
                parseSingleLiteral(sentence);
            }
        }
    }

    // Add the negation of the Query
    public void addNegatedQuery(SingleLiteral query) {
        SingleLiteral negatedQuery = new SingleLiteral(query, true);
        this.CFNClauseList.add(new CNFClause(negatedQuery));
    }

    private void parseSingleLiteral(String str) {
        SingleLiteral literal = new SingleLiteral(str);
        Predicate predicate = literal.getPredicate();
        CNFClause clause = new CNFClause(literal);
        this.CFNClauseList.add(clause);

        mapPredicateCNFClause(predicate, clause);

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

    private void addCNFClause(CNFClause clause) {
        this.CFNClauseList.add(clause);
    }

    public List<CNFClause> getCFNClauseList() {
        return CFNClauseList;
    }
}
