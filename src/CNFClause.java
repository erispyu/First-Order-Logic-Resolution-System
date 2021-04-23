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

    private String str;

    public  CNFClause() {

    }

    // Convert Implication sentence to CNF Clause
    public CNFClause(String str) {
        this.str = str;
        this.literals = new LinkedList<>();
        this.positiveLiterals = new LinkedList<>();
        this.negativeLiterals = new LinkedList<>();
        this.predicateMap = new HashMap<>();
        this.constantMap = new HashMap<>();
        this.variableMap = new HashMap<>();

        // 1. Eliminate implications
        List<SingleLiteral> premiseList = new LinkedList<>();
        SingleLiteral conclusion;

        // Split the premises and the conclusion
        str = str.replaceAll(" ", "");
        String[] strParts = str.split(Operator.Implication.denotation);

        conclusion = new SingleLiteral(strParts[1]);

        // Split the premises
        String[] premiseStrArr = strParts[0].split(Operator.Conjunction.denotation);
        for (String premiseStr: premiseStrArr) {
            SingleLiteral premise = new SingleLiteral(premiseStr);
            premiseList.add(premise);
        }

        // 2. Move the negated quantifiers(~) inwards
        for (SingleLiteral premise: premiseList) {
            premise.switchPositiveNegative();
        }

        // 3. Standardized variables (skipped, due to not existence quantifiers this case)

        // 4. Skolemize, skipped

        // 5. Drop universal quantifiers, skipped

        // 6. Distribute V over ^
        for (SingleLiteral premise: premiseList) {
            addLiteral(premise);
        }
        addLiteral(conclusion);

        // Record Predicates, Constants and Variables
        for (SingleLiteral literal: literals) {
            recordLiteral(literal);
        }
    }

    // Use a single literal as a Clause
    public CNFClause(SingleLiteral literal) {
        this.str = literal.toString();
        this.literals = new LinkedList<>();
        this.positiveLiterals = new LinkedList<>();
        this.negativeLiterals = new LinkedList<>();
        this.predicateMap = new HashMap<>();
        this.constantMap = new HashMap<>();
        this.variableMap = new HashMap<>();

        addLiteral(literal);
        recordLiteral(literal);
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

    private void addLiteral(SingleLiteral literal) {
        this.literals.add(literal);
        if (literal.isPositive()) {
            this.positiveLiterals.add(literal);
        } else {
            this.negativeLiterals.add(literal);
        }
    }

    private void addPredicate(Predicate predicate) {
        this.predicateMap.put(predicate.getName(), predicate);
    }

    private void addConstant(Term constant) {
        this.constantMap.put(constant.getName(), constant);
    }

    private void addVariable(Term variable) {
        this.variableMap.put(variable.getName(), variable);
    }

    private void recordLiteral(SingleLiteral literal) {
        addPredicate(literal.getPredicate());

        Term[] terms = literal.getTerms();
        for (Term term: terms) {
            if (term.isConstant()) {
                addConstant(term);
            } else {
                addVariable(term);
            }
        }
    }

    @Override
    public String toString() {
        return this.str;
    }
}
