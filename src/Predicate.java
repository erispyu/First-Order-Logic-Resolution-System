public class Predicate {
    private String name;
    private int argSize;

    public Predicate(String name, int argSize) {
        this.name = name;
        this.argSize = argSize;
    }

    public String getName() {
        return name;
    }

    public int getArgSize() {
        return argSize;
    }
}
