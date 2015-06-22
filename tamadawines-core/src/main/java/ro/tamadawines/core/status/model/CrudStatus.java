package ro.tamadawines.core.status.model;

public enum CrudStatus {
    SUCCESS("Operation successful"),
    FAILURE("Well, shit. I failed. Blame Rajesh.");

    private final String status;

    CrudStatus(String status) {
        this.status = status;
    }
}
