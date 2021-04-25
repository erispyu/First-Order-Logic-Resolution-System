import java.util.*;

public class QueryHelper {
    private SingleLiteral query;
    private KnowledgeBase knowledgeBase;

    private Predicate queriedPredicate;
    private Set<Predicate> kbPredicateSet;

    public QueryHelper(SingleLiteral query, KnowledgeBase knowledgeBase) {
        this.query = query;
        this.knowledgeBase = knowledgeBase.getDeepCopy();
        this.queriedPredicate = query.getPredicate();
        this.kbPredicateSet = knowledgeBase.getPredicateClausePQMap().keySet();
    }

    public boolean query() {
        if (!kbPredicateSet.contains(queriedPredicate)) {
            return false;
        }

        CNFClause negatedQueryClause = new CNFClause(query.getNegation());

        knowledgeBase.recordClause(negatedQueryClause);

        // The negated query first
        // TODO


//        // Then other facts
//        while(!clausePriorityQueue.isEmpty()) {
//            CNFClause clause = clausePriorityQueue.poll();
//            Map<Predicate, List<SingleLiteral>> predicateLiteralListMap = clause.getPredicateLiteralListMap();
//            for (Predicate predicate: predicateClausePriorityQueueMap.keySet()) {
//                PriorityQueue<CNFClause> predicateClausePriorityQueue = predicateClausePriorityQueueMap.get(predicate);
//                for (CNFClause predicateClause: predicateClausePriorityQueue) {
//                    CNFClause resolvedClause = ResolutionUtility.resolve(clause, predicateClause, new HashMap<Term, Term>());
//                    if (clauseMap.containsKey(resolvedClause.toString())) {
//                        return false;
//                    }
//                    if (resolvedClause.isEmpty()) {
//                        return true;
//                    }
//                    this.knowledgeBase.recordClause(resolvedClause);
//                }
//            }
//        }

        return false;
    }
}
