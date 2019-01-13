package serve.serveup.dataholder.session;

/*
    @author: urban.jagodic
*/
public interface SessionManager {

    // add Session info to object to Shared prefrences
    void addToSession(SessionContent type, Object data);
    // delete data from Session object from Shared prefrences
    void deleteFromSession(SessionContent type, Object... data);
    // erase whole session
    void eraseSession();
    // get session from shared prefrences
    Session getSession();
}
