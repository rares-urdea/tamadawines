package ro.tamadawines.core.status.model;

public class CrudResponse {

    private CrudStatus status;

    public CrudResponse(CrudStatus status) {
        this.status = status;
    }

    public CrudStatus getStatus() {
        return status;
    }

    public void setStatus(CrudStatus status) {
        this.status = status;
    }
}
