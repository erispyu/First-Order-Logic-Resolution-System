import java.util.List;

public class ResolutionHelper {
    private SingleLiteral query;
    private KnowledgeBase knowledgeBase;

    public ResolutionHelper(SingleLiteral query, KnowledgeBase knowledgeBase) {
        this.query = query;
        this.knowledgeBase = knowledgeBase;
    }

    public boolean query() {
        boolean result = true;
        knowledgeBase.addNegatedQuery(query);

        //TODO: actually query

        return result;
    }
}
