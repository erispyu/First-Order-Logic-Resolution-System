import java.util.Objects;

public class Term {
    private boolean isConstant;

    private String name;

    public Term(String name) {
        //Constant or Variable?
        char c = name.charAt(0);
        if (c >= 'A' && c <= 'Z') {
            this.isConstant = true;
        } else {
            this.isConstant = false;
        }

        this.name = name;
    }

    public boolean isConstant() {
        return this.isConstant;
    }

    public boolean isVariable() {
        return !this.isConstant;
    }

    public String getName() {
        return this.name;
    }

    public void hashName(int hashCode) {
        this.name = this.name + "_" + hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Term term = (Term) o;
        return isConstant == term.isConstant && Objects.equals(name, term.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isConstant, name);
    }
}
