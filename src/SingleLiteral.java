public class SingleLiteral{

    private Predicate predicate;

    private Term[] terms;

    private boolean isPositive;

    public SingleLiteral(String str) {
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

        int argSize = 0;
        String args = str.substring(predicateNameEndIndex + 1, str.length() - 1);
        String[] termStrArr = args.split(",");
        argSize = termStrArr.length;

        // Construct a Predicate instance
        this.predicate = new Predicate(predicateName, argSize);
    }

    public boolean isPositive() {
        return this.isPositive;
    }

    private void parseTerms(String[] termStrArr) {
        int termSize = termStrArr.length;
        this.terms = new Term[termSize];
        for (int i = 0; i < termSize; i++) {

        }
    }
}