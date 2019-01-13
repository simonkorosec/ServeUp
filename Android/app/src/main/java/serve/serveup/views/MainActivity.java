package serve.serveup.views;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import serve.serveup.R;
import serve.serveup.views.login.LoginFragment;
import serve.serveup.views.login.RegistrationFragment;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener,
        RegistrationFragment.OnFragmentInteractionListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_login);

        LoginFragment myLoginFrag = new LoginFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, myLoginFrag).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // fragment interaction
    }

    @Override
    public void onBackPressed() {
        int isH = findViewById(R.id.login_container).getVisibility();
        if(isH == View.INVISIBLE)
            findViewById(R.id.login_container).setVisibility(View.VISIBLE);
        super.onBackPressed();
    }
}
