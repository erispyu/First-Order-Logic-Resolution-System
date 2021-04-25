import java.util.*;

public class ResolutionHelper {
    private SingleLiteral query;
    private KnowledgeBase knowledgeBase;

    private Predicate queriedPredicate;
    private Set<Predicate> kbPredicateSet;

    private Map<String, CNFClause> originalClauseMap;

    private PriorityQueue<CNFClause> copiedClausePQ;

    public ResolutionHelper(SingleLiteral query, KnowledgeBase knowledgeBase) {
        this.query = query;
        this.knowledgeBase = knowledgeBase.getDeepCopy();

        this.queriedPredicate = query.getPredicate();
        this.kbPredicateSet = knowledgeBase.getPredicateClausePQMap().keySet();

        this.originalClauseMap = knowledgeBase.getClauseMap();

        this.copiedClausePQ = new PriorityQueue<>(knowledgeBase.getClausePQ());
    }

    public boolean query() {
        // Check the predicate
        if (!kbPredicateSet.contains(queriedPredicate)) {
            return false;
        }

        // Generate the negated query
        CNFClause negatedQueryClause = new CNFClause(query.getNegation());
        if (originalClauseMap.containsKey(negatedQueryClause.toString())) {
            return false;
        }

        // Add the negated query to the KB
        recordGeneratedClause(negatedQueryClause);

        // input solution
        while(!copiedClausePQ.isEmpty()) {
            CNFClause clauseToResolve = copiedClausePQ.poll();
            for (Predicate predicate: clauseToResolve.getPredicateSet()) {
                PriorityQueue<CNFClause> matchedClauses = new PriorityQueue<>(knowledgeBase.getPredicateClausePQMap().get(predicate));

                while(!matchedClauses.isEmpty()) {
                    CNFClause matched = matchedClauses.poll();
                    if (clauseToResolve == matched) continue;

                    CNFClause resolventClause = ResolutionUtility.resolve(clauseToResolve, matched, predicate);
                    if (resolventClause == null) continue;

                    System.out.println("Resolve: " + clauseToResolve + " and " + matched);
                    System.out.println("Resolvent: " + resolventClause);
                    System.out.println("-----------------------------------------------");

                    if (knowledgeBase.containsClause(resolventClause)) {
                        // loop proof, return false
                        return false;
                    } else if (resolventClause.isEmpty()) {
                        // contradiction found!
                        return true;
                    } else {
                        recordGeneratedClause(resolventClause);
                    }
                }
            }
        }
        return false;
    }

    private void recordGeneratedClause(CNFClause clause) {
        knowledgeBase.recordClause(clause);
        this.copiedClausePQ.add(clause);
    }
}
