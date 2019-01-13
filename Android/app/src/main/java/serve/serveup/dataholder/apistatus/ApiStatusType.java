package serve.serveup.dataholder.apistatus;

public enum ApiStatusType {

    // error status type
    ERROR_STATUS(0),
    // ok status
    OK_STATUS(1);

    private int status;

    ApiStatusType(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

}
