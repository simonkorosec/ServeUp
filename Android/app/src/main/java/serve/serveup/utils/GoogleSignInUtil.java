package serve.serveup.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import serve.serveup.R;
import serve.serveup.dataholder.UserInfo;

public class GoogleSignInUtil {

    public Context myContext;
    public GoogleSignInClient mGoogleSignInClient;
    public GoogleSignInOptions gso;
    public FirebaseAuth mAuth;
    public GoogleSignInAccount account;


    public GoogleSignInUtil(Context myContext, FirebaseAuth mAuth) {
        this.myContext = myContext;
        this.mAuth = mAuth;
    }

    public void setUp() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(myContext.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(myContext, gso);
    }

    public boolean checkIfAlreadySignedIn() {
        account = GoogleSignIn.getLastSignedInAccount(myContext);
        if(account != null) return true;
        else return false;
    }

    public UserInfo getUserInfo() {
        if(checkIfAlreadySignedIn()) {
            return new UserInfo(account);
        }
        return null;
    }

    public void signOut() {
        mAuth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener((Activity)myContext,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Utils.showToast(myContext, "Signed out!");
                    }
                });
    }

}
