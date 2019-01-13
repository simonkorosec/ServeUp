package serve.serveup.dataholder;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import java.io.Serializable;

public class UserInfo implements Serializable {

    private String email;
    private String displayName;
    private String uID;
    private String photoUrl;


    public UserInfo() {

    }

    public UserInfo(GoogleSignInAccount user) {
        this.email = user.getEmail();
        this.displayName = user.getDisplayName();
        this.uID = user.getId();
        this.photoUrl = user.getPhotoUrl().toString();
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getuID() {
        return uID;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }


    @Override
    public String toString() {
        return "DN: " + getDisplayName() + " UID: " + getuID()
                + " EMAIL: " + getEmail() + " PHOTO_URL: " + getPhotoUrl();
    }
}
