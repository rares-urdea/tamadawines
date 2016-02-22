package ro.tamadawines.core.model;

public enum Message {
    SUCCESS("Operation successful", 200),
    UTTER_FAILURE("Something failed horribly. Blame Rajesh.", 401),
    EMAIL_EXCEPTION("Email Service Error - IOException || MessagingException", 402),
    AVAILABILITY_CHANGE("Availability has changed while you were slacking off. Move quick or die fast.", 301),
    PRODUCT_NOT_FOUND("Some of the products you're looking for have not been found. Check product list to see which ones", 302),
    JSON_AND_IMAGE_REQUIRED("Both image and json form data parts are required", 303),
    IMAGE_UPLOAD_FAILED("Image upload failed due to a weird exception. Blame Rajesh.", 403);

    private final String value;
    private final Integer code;

    Message(String value, Integer code) {
        this.value = value;
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public Integer getCode() {
        return code;
    }
}