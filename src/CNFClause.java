import java.util.*;

public class CNFClause {
    private List<SingleLiteral> literalList;
    private List<SingleLiteral> positiveLiteralList;
    private List<SingleLiteral> negativeLiteralList;

    private Map<String, Term> constantMap;

    private Map<String, Term> variableMap;

    private Map<Predicate, List<SingleLiteral>> predicateLiteralListMap;

    public CNFClause() {
        this.literalList = new LinkedList<>();
        this.positiveLiteralList = new LinkedList<>();
        this.negativeLiteralList = new LinkedList<>();
        this.constantMap = new HashMap<>();
        this.variableMap = new HashMap<>();

        this.predicateLiteralListMap = new HashMap<>();
    }

    // Convert Implication sentence to CNF Clause
    public CNFClause(String str) {
        this();

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

        // 2. Move the getNegation quantifiers(~) inwards
        for (SingleLiteral premise: premiseList) {
            premise.switchPositiveNegative();
        }

        // 3. Standardized variables (skipped, due to not existence quantifiers this case)

        // 4. Skolemize, skipped

        // 5. Drop universal quantifiers, skipped

        // 6. Distribute V over ^

        // Record Predicates, Constants and Variables in each literal
        for (SingleLiteral premise: premiseList) {
            recordLiteral(premise);
        }
        recordLiteral(conclusion);
    }

    // Use a single literal as a Clause
    public CNFClause(SingleLiteral literal) {
        this();
        recordLiteral(literal);
    }

    public CNFClause(List<SingleLiteral> lList) {
        this();

        Map<String, SingleLiteral> literalMap = new HashMap<>();

        for (SingleLiteral literal: lList) {
            SingleLiteral negation = literal.getNegation();
            if (literalMap.containsKey(negation.toString())) {
                continue;
            }
            literalMap.put(literal.toString(), literal);
            recordLiteral(literal);
        }
    }

    public List<SingleLiteral> getLiteralList() {
        return literalList;
    }

    public List<SingleLiteral> getPositiveLiteralList() {
        return positiveLiteralList;
    }

    public List<SingleLiteral> getNegativeLiteralList() {
        return negativeLiteralList;
    }

    public Map<Predicate, List<SingleLiteral>> getPredicateLiteralListMap() {
        return predicateLiteralListMap;
    }

    public Map<String, Term> getConstantMap() {
        return constantMap;
    }

    private void mapPredicateLiteral(Predicate predicate, SingleLiteral literal) {
        if(this.predicateLiteralListMap.containsKey(predicate)) {
            this.predicateLiteralListMap.get(predicate).add(literal);
        } else {
            List<SingleLiteral> literalList = new LinkedList<>();
            literalList.add(literal);
            this.predicateLiteralListMap.put(predicate, literalList);
        }
    }

    private void addConstant(Term constant) {
        this.constantMap.put(constant.getName(), constant);
    }

    private void addVariable(Term variable) {
        this.variableMap.put(variable.getName(), variable);
    }

    private void recordLiteral(SingleLiteral literal) {
        this.literalList.add(literal);
        if (literal.isPositive()) {
            this.positiveLiteralList.add(literal);
        } else {
            this.negativeLiteralList.add(literal);
        }

        mapPredicateLiteral(literal.getPredicate(), literal);

        Term[] terms = literal.getTerms();
        for (Term term: terms) {
            if (term.isConstant()) {
                addConstant(term);
            } else {
                term.hashName(this.hashCode());
                addVariable(term);
            }
        }
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "Empty";
        }

        StringBuffer sb = new StringBuffer();
        for (SingleLiteral literal: literalList) {
            sb.append(literal.toString());
            sb.append(Operator.Disjunction.denotation);
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public int size() {
        return this.literalList.size();
    }

    public boolean isEmpty() {
        return this.literalList.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CNFClause clause = (CNFClause) o;
        return Objects.equals(this.toString(), clause.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.toString());
    }
}
