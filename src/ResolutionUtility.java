import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ResolutionUtility {

    public static CNFClause resolve(CNFClause c1, CNFClause c2, Map<Term, Term> substitution) {
        List<SingleLiteral> c1_literalList = c1.getLiteralList();
        List<SingleLiteral> c2_literalList = c2.getLiteralList();

        List<SingleLiteral> unifiedLiteralList = new LinkedList<>();

        unifiedLiteralList.addAll(unifyLiteralList(c1_literalList, substitution));
        unifiedLiteralList.addAll(unifyLiteralList(c2_literalList, substitution));

        CNFClause resolvedClause = new CNFClause(unifiedLiteralList);

        return resolvedClause;
    }

    private static List<SingleLiteral> unifyLiteralList(List<SingleLiteral> literalList, Map<Term, Term> substitution) {
        List<SingleLiteral> unifiedLiteralList = new LinkedList<>();

        for (SingleLiteral l1: literalList) {
            SingleLiteral newLiteral = new SingleLiteral(l1, false);
            Term[] terms = newLiteral.getTerms();
            for (int i = 0; i < terms.length; i++) {
                Term term = terms[i];
                if (term.isVariable() && substitution.containsKey(term)) {
                    terms[i] = substitution.get(term);
                }
            }
            newLiteral.setTerms(terms);
            unifiedLiteralList.add(newLiteral);
        }

        return unifiedLiteralList;
    }

    public static CNFClause canUnify(CNFClause c1, CNFClause c2) {
        List<SingleLiteral> c1_literalList = c1.getLiteralList();
        List<SingleLiteral> c2_literalList = c2.getLiteralList();

        List<SingleLiteral> unifiedLiteralList = new LinkedList<>();

        for (SingleLiteral l1: c1_literalList) {
            for (SingleLiteral l2: c2_literalList) {
                if (l1.getPredicate().equals(l2.getPredicate()) && l1.isPositive() != l2.isPositive()) {
                    Map<Term, Term> substitution = new HashMap<>();
                    if (canUnify(l1, l2, substitution)) {

                    }
                }
            }
        }

        return null;
    }

    private static boolean canUnify(SingleLiteral l1, SingleLiteral l2, Map<Term, Term> substitution) {
        Predicate predicate = l1.getPredicate();
        int argSize = predicate.getArgSize();

        Term[] l1Terms = l1.getTerms();
        Term[] l2Terms = l2.getTerms();

        for (int i = 0; i < argSize; i++) {
            Term t1 = l1Terms[i];
            Term t2 = l2Terms[i];

            if (t1.isConstant() && t2.isConstant()) {
                if (t1.equals(t2)) {
                    return false;
                } else {
                    continue;
                }
            }

            if (t1.isVariable() && t2.isVariable()) {
                if ((substitution.containsKey(t1) && substitution.get(t1).equals(t2)) || (substitution.containsKey(t2) && substitution.get(t2).equals(t1))) {
                    return false;
                } else {
                    substitution.put(t1, t2);
                    continue;
                }
            }

            Term constant, variable;
            if (t1.isConstant()) {
                constant = t1;
                variable = t2;
            } else {
                constant = t2;
                variable = t1;
            }

            if (substitution.containsKey(variable)) {
                if (!substitution.get(variable).equals(constant)) {
                    return false;
                }
            } else {
                substitution.put(variable, constant);
                continue;
            }
        }

        return true;
    }
}
