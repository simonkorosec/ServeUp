package serve.serveup.dataholder.login;

public enum UserStatusType {

    ERROR(0),
    NEW_USER(1),
    EXISTING_USER(2);

    private int status;

    UserStatusType(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }
}
