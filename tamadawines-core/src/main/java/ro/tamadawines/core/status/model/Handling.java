package ro.tamadawines.core.status.model;

public enum Handling {
    RECOVERABLE("Recoverable"),
    CRITICAL("Critical");

    private final String value;

    private Handling(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
