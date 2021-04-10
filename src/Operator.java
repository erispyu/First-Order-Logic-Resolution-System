public enum Operator {
    Conjunction("&"),
    Disjunction("|"),
    Implication("=>"),
    Negation("~");

    private String denotation;

    private Operator(String denotation) {
        this.denotation = denotation;
    }

    public String getDenotation() {
        return denotation;
    }
}
