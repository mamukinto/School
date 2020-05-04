package gui;

public enum Colors {
    MAIN("#2b2b2b"),
    SECONDARY("#3b3b3b"),
    TEXT("#9b9b9b"),
    WARNING_TEXT("#a02e2e"),
    HOVER("#4a4a4a");


    private final String text;

    Colors(String s) {
        this.text = s;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
