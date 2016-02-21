package ro.tamadawines.core.status.model;

public enum CrudStatus {
    SUCCESS("Operation successful"),
    SUCCESS_IMAGE_EXISTS("Operation successful, but image already exists in S3"),
    FAILURE("Well, shit. I failed. Blame Rajesh."),
    ADD_PRODUCT_FAILURE_ON_IMAGE_UPLOAD("Image upload failed, but product was added to DB");

    private final String status;

    public String getStatus() {
        return status;
    }

    CrudStatus(String status) {
        this.status = status;
    }
}
