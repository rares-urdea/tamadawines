package ro.tamadawines.core.model;

public class MessageWrapper {

    private String verboseMessage;
    private Integer code;

    public MessageWrapper() {
    }

    public MessageWrapper(Message message) {
        this.code = message.getCode();
        this.verboseMessage = message.getValue();
    }

    public String getVerboseMessage() {
        return verboseMessage;
    }

    public void setVerboseMessage(String verboseMessage) {
        this.verboseMessage = verboseMessage;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
