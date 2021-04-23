public class SingleLiteral{

    private Predicate predicate;

    private Term[] terms;

    private boolean isPositive;

    private String str;

    public SingleLiteral(String str) {
        this.str = str;

        String predicateName;
        int predicateNameBeginIndex = -1;
        int predicateNameEndIndex = -1;

        // Parse whether the str starts with a negation operator
        // Get the start position of the predicate's name
        if (str.substring(0, 1).equals(Operator.Negation.denotation)) {
            this.isPositive = false;
            predicateNameBeginIndex = 1;
        } else {
            this.isPositive = true;
            predicateNameBeginIndex = 0;
        }

        // Get the end position of the predicate's name
        for (int i = predicateNameBeginIndex; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '(') {
                predicateNameEndIndex = i;
                break;
            }
        }

        // Get the predicate's name
        predicateName = str.substring(predicateNameBeginIndex, predicateNameEndIndex);

        // Split the args
        int argSize = 0;
        String args = str.substring(predicateNameEndIndex + 1, str.length() - 1);
        String[] termStrArr = args.split(",");
        argSize = termStrArr.length;

        // parse the args into terms
        parseTerms(termStrArr);

        // Construct a Predicate instance
        this.predicate = new Predicate(predicateName, argSize);
    }

    public SingleLiteral(SingleLiteral literal, boolean isNegated) {
        this.predicate = literal.getPredicate();
        this.terms = literal.getTerms();
        this.isPositive = isNegated ? !literal.isPositive : literal.isPositive;
        this.str = literal.str;
        if (!this.isPositive) {
            this.str = Operator.Negation.denotation + this.str;
        } else {
            this.str = this.str.substring(1);
        }
    }

    private void parseTerms(String[] termStrArr) {
        int termSize = termStrArr.length;
        this.terms = new Term[termSize];
        for (int i = 0; i < termSize; i++) {
            terms[i] = new Term(termStrArr[i]);
        }
    }

    public boolean isPositive() {
        return this.isPositive;
    }

    public Predicate getPredicate() {
        return predicate;
    }

    public Term[] getTerms() {
        return terms;
    }

    public void switchPositiveNegative() {
        this.isPositive = !this.isPositive;
        if (!this.isPositive) {
            this.str = Operator.Negation.denotation + this.str;
        } else {
            this.str = this.str.substring(1);
        }
    }

    @Override
    public String toString() {
        return this.str;
    }
}
