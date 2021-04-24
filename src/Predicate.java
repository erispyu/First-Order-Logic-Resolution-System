import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Predicate predicate = (Predicate) o;
        return argSize == predicate.argSize && Objects.equals(name, predicate.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, argSize);
    }

    @Override
    public String toString() {
        return this.name + "(), argSize=" + this.argSize;
    }
}
