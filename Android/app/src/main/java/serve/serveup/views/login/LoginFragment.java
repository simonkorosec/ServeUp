package serve.serveup.views.login;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import serve.serveup.R;
import serve.serveup.dataholder.UserInfo;
import serve.serveup.utils.GoogleSignInUtil;
import serve.serveup.utils.Utils;


public class LoginFragment extends Fragment  {
    private static int RC_SIGN_IN = 100;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private SignInButton googleButton;
    private View signInButton;
    private View signUp;
    private GoogleSignInUtil myGoogleUtil;
    private OnFragmentInteractionListener mListener;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Utils.logInfo("GOOGLE SIGN IN CLIENT: " + myGoogleUtil.checkIfAlreadySignedIn());
        Utils.logInfo("START current user: " + currentUser);
        if(currentUser == null && myGoogleUtil.checkIfAlreadySignedIn())
            myGoogleUtil.signOut();
        else if (currentUser != null && myGoogleUtil.checkIfAlreadySignedIn())
            automaticSingIn();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myLayout = inflater.inflate(R.layout.fragment_login, container, false);
        googleButton = myLayout.findViewById(R.id.google_button);


        myGoogleUtil = new GoogleSignInUtil(getContext(), mAuth);
        myGoogleUtil.setUp();

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignIn();
            }
        });
        return myLayout;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately

                Utils.showToast(getActivity(), "Google authentication failed :(");
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            automaticSingIn();
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Utils.logInfo("Sing in failed!");
                        }
                    }
                });
    }

    private void checkValidation() {
        EditText password = getActivity().findViewById(R.id.passwordField);
        EditText email = getActivity().findViewById(R.id.emailField);
        if (Utils.isEmailValid(email.getText().toString()) && Utils.isPasswordValid(password.getText().toString())) {
            // continue with signup
        }
        else {
            Utils.showToast(getContext(), "Invalid email or password!");
        }
    }

    private void googleSignIn() {
        Intent signInIntent = myGoogleUtil.mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // not being used
    private void signUp() {
        View loginContainer = getActivity().findViewById(R.id.login_container);
        loginContainer.setVisibility(View.INVISIBLE);
        RegistrationFragment nextFrag = new RegistrationFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, nextFrag)
                .addToBackStack(null)
                .commit();
    }

    public void automaticSingIn() {
        final Bundle myBundle = new Bundle();
        final UserInfo myUserInfo = myGoogleUtil.getUserInfo();
        myBundle.putSerializable("userInfo", myUserInfo);
        Intent startMainPanel = new Intent(getActivity(), LoadingLoginActivity.class);
        startMainPanel.putExtras(myBundle);
        startActivity(startMainPanel);
    }

}
