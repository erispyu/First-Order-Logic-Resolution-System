public class Predicate {
    private String name;
    private int argSize;
    private Term[] args;

    public Predicate(String name, int argSize) {
        this.name = name;
        this.argSize = argSize;
        this.args = new Term[argSize];
    }

    public String getName() {
        return name;
    }
}
