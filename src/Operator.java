public enum Operator {
    Conjunction("&"),
    Disjunction("|"),
    Implication("=>"),
    Negation("~");

    public String denotation;

    private Operator(String denotation) {
        this.denotation = denotation;
    }
}
