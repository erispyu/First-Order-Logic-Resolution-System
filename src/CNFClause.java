import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CNFClause {
    private List<SingleLiteral> literals;
    private List<SingleLiteral> positiveLiterals;
    private List<SingleLiteral> negativeLiterals;

    private Map<String, Predicate> predicateMap;

    private Map<String, Term> constantMap;

    private Map<String, Term> variableMap;

    // Convert Implication sentence to CNF Clause
    public CNFClause(String str) {
        this.literals = new LinkedList<>();
        this.positiveLiterals = new LinkedList<>();
        this.negativeLiterals = new LinkedList<>();
        this.predicateMap = new HashMap<>();
        this.constantMap = new HashMap<>();
        this.variableMap = new HashMap<>();

        List<SingleLiteral> premiseList = new LinkedList<>();
        SingleLiteral conclusion;

        str = str.replaceAll(" ", "");
        String[] strParts = str.split(Operator.Implication.denotation);

        conclusion = new SingleLiteral(strParts[1]);
        addPredicateAndConstantsFromLiteral(conclusion);

        String[] premiseStrArr = strParts[0].split(Operator.Conjunction.denotation);
        for (String premiseStr: premiseStrArr) {
            SingleLiteral premise = new SingleLiteral(premiseStr);
            premiseList.add(premise);
            addPredicateAndConstantsFromLiteral(premise);
        }

        // TODO: Add predicates, Add Constants

        // TODO: convert! variable!
    }

    public List<SingleLiteral> getLiterals() {
        return literals;
    }

    public List<SingleLiteral> getPositiveLiterals() {
        return positiveLiterals;
    }

    public List<SingleLiteral> getNegativeLiterals() {
        return negativeLiterals;
    }

    public Map<String, Predicate> getPredicateMap() {
        return predicateMap;
    }

    public Map<String, Term> getConstantMap() {
        return constantMap;
    }

    private void addPredicate(Predicate predicate) {
        this.predicateMap.put(predicate.getName(), predicate);
    }

    private void addConstant(Term constant) {
        this.constantMap.put(constant.getName(), constant);
    }

    private void addPredicateAndConstantsFromLiteral(SingleLiteral literal) {
        addPredicate(literal.getPredicate());

        Term[] terms = literal.getTerms();
        for (Term term: terms) {
            if (term.isConstant()) {
                addConstant(term);
            }
        }
    }
}
