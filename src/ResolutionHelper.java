import java.util.*;

public class ResolutionHelper {
    private SingleLiteral query;
    private KnowledgeBase knowledgeBase;

    private Predicate queriedPredicate;
    private Set<Predicate> kbPredicateSet;

    private Map<String, CNFClause> originalClauseMap;

    private PriorityQueue<CNFClause> originClausePQ;
    private Set<CNFClause> generatedClauseSet;
    private PriorityQueue<CNFClause> copiedClausePQ;

    public ResolutionHelper(SingleLiteral query, KnowledgeBase knowledgeBase) {
        this.query = query;
        this.knowledgeBase = knowledgeBase.getDeepCopy();

        this.queriedPredicate = query.getPredicate();
        this.kbPredicateSet = knowledgeBase.getPredicateClausePQMap().keySet();

        this.originalClauseMap = knowledgeBase.getClauseMap();

        this.originClausePQ = new PriorityQueue<>(knowledgeBase.getClausePQ());
        this.generatedClauseSet = new HashSet<>();
        copiedClausePQ = new PriorityQueue<>(knowledgeBase.getClausePQ());
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
        recordClauseToKB(negatedQueryClause);

        while(true) {
            // for each pair of clauses in the KB, do resolution
            Set<CNFClause> visited = new HashSet<>();
            while(!copiedClausePQ.isEmpty()) {
                CNFClause clauseToResolve = copiedClausePQ.poll();
                visited.add(clauseToResolve);
                for (Predicate predicate : clauseToResolve.getPredicateSet()) {
                    PriorityQueue<CNFClause> matchedClauses = new PriorityQueue<>(knowledgeBase.getPredicateClausePQMap().get(predicate));

                    while(!matchedClauses.isEmpty()) {
                        CNFClause matched = matchedClauses.poll();
                        if (visited.contains(matched)) continue;

                        // Try unit resolution
                        if (!clauseToResolve.isUnit() && !matched.isUnit()) continue;

                        CNFClause resolventClause = ResolutionUtility.resolve(clauseToResolve, matched, predicate);
                        if (resolventClause == null) continue;

//                        System.out.println("Resolve: " + clauseToResolve + " and " + matched);
//                        System.out.println("Resolvent: " + resolventClause);
//                        System.out.println("-----------------------------------------------");

                        if (resolventClause.isEmpty()) {
                            // contradiction found!
                            return true;
                        } else {
                            generatedClauseSet.add(resolventClause);
                        }
                    }
                }
            }

            // recover copied clauses
            copiedClausePQ.addAll(knowledgeBase.getClausePQ());

            // handle generated clauses
            boolean hasNewClause = false;
            for(CNFClause generatedClause: generatedClauseSet) {
                if (knowledgeBase.containsClause(generatedClause)) {
                    continue;
                } else {
                    hasNewClause = true;
                    recordClauseToKB(generatedClause);
                }
            }
            if (!hasNewClause) {
                return false;
            }
        }
    }

    private void recordClauseToKB(CNFClause clause) {
        knowledgeBase.recordClause(clause);
        copiedClausePQ.add(clause);
    }
}
