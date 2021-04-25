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
                    // cannot unify
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
        Map<Term, Term> subset = new HashMap<>();

        Term[] l1terms = l1.getTerms();
        Term[] l2Terms = l2.getTerms();

        int argSize = l1terms.length;

        for (int i = 0; i < argSize; i++) {
            Term t1 = l1terms[i];
            Term t2 = l2Terms[i];

            // both constant
            if (t1.isConstant() && t2.isConstant()) {
                if (t1.equals(t2)) {
                    continue;
                } else {
                    return null;
                }
            }

            // both variable
            else if (t1.isVariable() && t2.isVariable()) {
                if (subset.containsKey(t1)) {
                    if (t2.equals(subset.get(t1))) {
                        continue;
                    } else {
                        return null;
                    }
                } else {
                    subset.put(t1, t2);
                }
            }

            // t1 is variable, t2 is constant
            else if (t1.isVariable()) {
                if (subset.containsKey(t1)) {
                    if (t2.equals(subset.get(t1))) {
                        continue;
                    } else {
                        return null;
                    }
                } else {
                    subset.put(t1, t2);
                }
            }

            // t2 is variable, t1 is constant
            else {
                if (subset.containsKey(t2)) {
                    if (t1.equals(subset.get(t2))) {
                        continue;
                    } else {
                        return null;
                    }
                } else {
                    subset.put(t2, t1);
                }
            }
        }

        return subset;
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
