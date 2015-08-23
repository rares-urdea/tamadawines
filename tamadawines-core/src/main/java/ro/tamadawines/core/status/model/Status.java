package ro.tamadawines.core.status.model;

public enum Status {
    SUCCESS(new StatusDescriptor("Products successfully sold", 201)),
    UTTER_FAILURE(new StatusDescriptor("Something failed horribly. Blame Rajesh.", 404)),
    AVAILABILITY_CHANGE(new StatusDescriptor("Availability has changed while you were slacking off. " +
            "Move quick or die fast.", 301)),
    PRODUCT_NOT_FOUND(new StatusDescriptor("Some of the products you're looking for have not been found. " +
            "Check product list to see which ones", 302)),
    OPERATION_SUCCESSFUL(new StatusDescriptor("Operation successful", 200));

    private final StatusDescriptor statusDescriptor;

    Status(StatusDescriptor statusDescriptor) {
        this.statusDescriptor = statusDescriptor;
    }

    public StatusDescriptor getStatusDescriptor() {
        return statusDescriptor;
    }
}
