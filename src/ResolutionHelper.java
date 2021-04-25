import java.util.*;

public class ResolutionHelper {
    private SingleLiteral query;
    private KnowledgeBase knowledgeBase;

    private Predicate queriedPredicate;
    private Set<Predicate> kbPredicateSet;

    private Map<String, CNFClause> originalClauseMap;
    private PriorityQueue<CNFClause> originalClausePQ;

    private Map<String, CNFClause> generatedClauseMap;
    private PriorityQueue<CNFClause> generatedClausePQ;

    public ResolutionHelper(SingleLiteral query, KnowledgeBase knowledgeBase) {
        this.query = query;
        this.knowledgeBase = knowledgeBase.getDeepCopy();

        this.queriedPredicate = query.getPredicate();
        this.kbPredicateSet = knowledgeBase.getPredicateClausePQMap().keySet();

        this.originalClauseMap = knowledgeBase.getClauseMap();
        this.originalClausePQ = knowledgeBase.getClausePQ();

        this.generatedClauseMap = new HashMap<>();
        this.generatedClausePQ = new PriorityQueue<>();
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

        // TODO
        // linear resolution: either one clause is in the original jb, or the other is newly generated.
        while(!generatedClausePQ.isEmpty()) {
            CNFClause c1 = generatedClausePQ.poll();
            for (Predicate predicate: c1.getPredicateSet()) {
                PriorityQueue<CNFClause> matchedClauses = new PriorityQueue<>(knowledgeBase.getPredicateClausePQMap().get(predicate));
                while(!matchedClauses.isEmpty()) {
                    CNFClause resolventClause = ResolutionUtility.resolve(c1, matchedClauses.poll(), predicate);
                    if (resolventClause == null) {
                        continue;
                    } else if (knowledgeBase.containsClause(resolventClause)) {
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
        this.generatedClauseMap.put(clause.toString(), clause);
        this.generatedClausePQ.add(clause);
    }
}
