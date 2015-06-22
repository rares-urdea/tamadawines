package ro.tamadawines.core.status.model;

public class StatusDescriptor {

    private String verboseMessage;
    private Integer handleCode;

    public StatusDescriptor(String verboseMessage, Integer handleCode) {
        this.verboseMessage = verboseMessage;
        this.handleCode = handleCode;
    }

    public Integer getHandleCode() {
        return handleCode;
    }

    public void setHandleCode(Integer handleCode) {
        this.handleCode = handleCode;
    }

    public String getVerboseMessage() {
        return verboseMessage;
    }

    public void setVerboseMessage(String verboseMessage) {
        this.verboseMessage = verboseMessage;
    }

    @Override
    public String toString() {
        return "StatusDescriptor{" +
                "verboseMessage='" + verboseMessage + '\'' +
                ", handleCode=" + handleCode +
                '}';
    }
}
