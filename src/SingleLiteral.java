import java.util.List;

public class SingleLiteral extends Sentence{

    private Predicate predicate;

    private Term[] terms;

    private boolean isTrue;

    public SingleLiteral(String str) {
        String predicateName;
        int predicateBeginIndex = -1;
        int predicateEndIndex = -1;

        if (str.substring(0, 1).equals(Operator.Negation.denotation)) {
            this.isTrue = false;
            predicateBeginIndex = 0;
        } else {
            this.isTrue = true;
            predicateBeginIndex = 1;
        }

        for (int i = predicateBeginIndex; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '(') {
                predicateEndIndex = i;
                predicateName = str.substring(predicateBeginIndex, predicateEndIndex);
                break;
            }
        }


    }

}
