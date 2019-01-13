package serve.serveup.views.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import serve.serveup.R;
import serve.serveup.dataholder.apistatus.ApiStatus;
import serve.serveup.dataholder.UserInfo;
import serve.serveup.dataholder.login.UserStatusType;
import serve.serveup.dataholder.session.SessionContent;
import serve.serveup.utils.ContentStore;
import serve.serveup.utils.Utils;
import serve.serveup.views.NavigationPanelActivity;
import serve.serveup.webservices.RestManagement;

public class LoadingLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_login);

        final Bundle myBundle = getIntent().getExtras();
        final UserInfo myUserInfo = (UserInfo) myBundle.getSerializable("userInfo");

        RestManagement.getLoginStatusCall(myUserInfo.getuID()).enqueue(new Callback<ApiStatus>() {
            @Override
            public void onResponse(Call<ApiStatus> call, Response<ApiStatus> response) {

                ApiStatus myUserLogin = response.body();

                if(myUserLogin != null) {
                    Utils.logInfo(myUserLogin.getDescription() + ", status: " + myUserLogin.getStatus());
                    if(myUserLogin.getStatus() == UserStatusType.EXISTING_USER.getStatus() ||
                            myUserLogin.getStatus() == UserStatusType.NEW_USER.getStatus()) {

                        Utils.logInfo("Session created");
                        ContentStore cntStore = new ContentStore(getApplicationContext());
                        cntStore.addToSession(SessionContent.CURRENT_USER, myUserInfo.getuID());

                        Intent startMainPanel = new Intent(getApplicationContext(), NavigationPanelActivity.class);
                        startMainPanel.putExtras(myBundle);
                        startActivity(startMainPanel);
                        //Utils.showToast(getActivity(), "Signed in!");
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiStatus> call, Throwable t) {
                Utils.logInfo("api 'user/register/' failed!");
            }
        });
    }
}
