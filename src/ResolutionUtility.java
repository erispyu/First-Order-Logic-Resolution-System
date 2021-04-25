import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ResolutionUtility {

    /**
     *  API for resolution
     * @param c1
     * @param c2
     * @return
     */
    public static CNFClause resolve(CNFClause c1, CNFClause c2, Predicate predicate) {
        List<SingleLiteral> c1MatchedLiterals = c1.getMatchedLiterals(predicate);
        List<SingleLiteral> c2MatchedLiterals = c2.getMatchedLiterals(predicate);

        for (SingleLiteral matchedLiteral1: c1MatchedLiterals) {
            for (SingleLiteral matchedLiteral2: c2MatchedLiterals) {
                // The two literals must be complementary for binary-resolution
                if (matchedLiteral1.isPositive() == matchedLiteral2.isPositive()) {
                    continue;
                }

                Map<Term, Term> subset = unify(matchedLiteral1, matchedLiteral2);
                if (subset == null) {
                    continue;
                }

                // resolve two clauses
                List<SingleLiteral> resolventLiterals = new LinkedList<>();
                for (SingleLiteral l1: c1.getLiteralList()) {
                    if (!l1.equals(matchedLiteral1)) {
                        resolventLiterals.add(getUnifiedLiteral(l1, subset));
                    }
                }

                for (SingleLiteral l2: c2.getLiteralList()) {
                    if (!l2.equals(matchedLiteral2)) {
                        resolventLiterals.add(getUnifiedLiteral(l2, subset));
                    }
                }

                // return the resolvent clause
                return new CNFClause(resolventLiterals);
            }
        }

        return null;
    }

    private static Map<Term, Term> unify(SingleLiteral l1, SingleLiteral l2) {
        Term[] l1terms = l1.getTerms();
        Term[] l2Terms = l2.getTerms();

        return null;
    }

    public static SingleLiteral getUnifiedLiteral(SingleLiteral l, Map<Term, Term> subset) {
        SingleLiteral unifiedLiteral = new SingleLiteral(l, false);
        Term[] terms = unifiedLiteral.getTerms();
        for (int i = 0; i < terms.length; i++) {
            if (subset.containsKey(terms[i])) {
                terms[i] = subset.get(terms[i]);
            }
        }
        return unifiedLiteral;
    }
}
