public class Term {
    private boolean isConstant;

    private String name;

    public Term(String str) {
        //Constant or Variable?
        char c = str.charAt(0);
        if (c >= 'A' && c <= 'Z') {
            this.isConstant = true;
        } else {
            this.isConstant = false;
        }

        this.name = str;
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

}
