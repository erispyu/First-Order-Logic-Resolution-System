import java.util.List;

public class ResolutionHelper {
    private SingleLiteral query;
    private KnowledgeBase knowledgeBase;

    public ResolutionHelper(SingleLiteral query, KnowledgeBase knowledgeBase) {
        this.query = query;
        this.knowledgeBase = knowledgeBase.getDeepCopy();
    }

    public boolean query() {
        boolean result = false;

        SingleLiteral negatedQuery = query.getNegation();

        knowledgeBase.addClause(new CNFClause(negatedQuery));

        //TODO: do the resolution


        return result;
    }
}
