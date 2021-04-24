import java.util.*;

public class CNFClause {
    private List<SingleLiteral> literalList;
    private List<SingleLiteral> positiveLiteralList;
    private List<SingleLiteral> negativeLiteralList;

    private Map<String, Predicate> predicateMap;

    private Map<String, Term> constantMap;

    private Map<String, Term> variableMap;

    private Map<Predicate, List<SingleLiteral>> predicateLiteralMap;

    public CNFClause() {
        this.literalList = new LinkedList<>();
        this.positiveLiteralList = new LinkedList<>();
        this.negativeLiteralList = new LinkedList<>();
        this.predicateMap = new HashMap<>();
        this.constantMap = new HashMap<>();
        this.variableMap = new HashMap<>();

        this.predicateLiteralMap = new HashMap<>();
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
        for (SingleLiteral premise: premiseList) {
            addLiteral(premise);
        }
        addLiteral(conclusion);

        // Record Predicates, Constants and Variables
        for (SingleLiteral literal: literalList) {
            recordLiteral(literal);
        }
    }

    // Use a single literal as a Clause
    public CNFClause(SingleLiteral literal) {
        this();

        addLiteral(literal);
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
            addLiteral(literal);
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

    public Map<String, Predicate> getPredicateMap() {
        return predicateMap;
    }

    public Map<String, Term> getConstantMap() {
        return constantMap;
    }

    private void addLiteral(SingleLiteral literal) {
        this.literalList.add(literal);
        if (literal.isPositive()) {
            this.positiveLiteralList.add(literal);
        } else {
            this.negativeLiteralList.add(literal);
        }

        Predicate predicate = literal.getPredicate();

        if(this.predicateLiteralMap.containsKey(predicate)) {
            this.predicateLiteralMap.get(predicate).add(literal);
        } else {
            List<SingleLiteral> literalList = new LinkedList<>();
            literalList.add(literal);
            this.predicateLiteralMap.put(predicate, literalList);
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
        CNFClause cnfClause = (CNFClause) o;
        return Objects.equals(literalList, cnfClause.literalList) && Objects.equals(positiveLiteralList, cnfClause.positiveLiteralList) && Objects.equals(negativeLiteralList, cnfClause.negativeLiteralList) && Objects.equals(predicateMap, cnfClause.predicateMap) && Objects.equals(constantMap, cnfClause.constantMap) && Objects.equals(variableMap, cnfClause.variableMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(literalList, positiveLiteralList, negativeLiteralList, this.toString());
    }
}
