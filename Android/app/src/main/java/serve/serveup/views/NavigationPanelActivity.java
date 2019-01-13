package serve.serveup.views;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import serve.serveup.R;
import serve.serveup.utils.ContentStore;
import serve.serveup.utils.Utils;
import serve.serveup.views.navigation.BasketFragment;
import serve.serveup.views.navigation.HomeFragment;
import serve.serveup.views.navigation.OrdersFragment;
import serve.serveup.views.navigation.ProfileFragment;

public class NavigationPanelActivity extends AppCompatActivity implements
        HomeFragment.OnFragmentInteractionListener,
        OrdersFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,
        BasketFragment.OnFragmentInteractionListener{

    private FrameLayout frameLayout;
    private HomeFragment homeFragment = new HomeFragment();
    private OrdersFragment ordersFragment = new OrdersFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    private BasketFragment basketFragment = new BasketFragment();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragment(homeFragment);
                    return true;
                case R.id.navigation_basket:
                    setFragment(basketFragment);
                    return true;
                case R.id.navigation_orders:
                    setFragment(ordersFragment);
                    return true;
                case R.id.navigation_profile:
                    setFragment(profileFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_panel);
        setFragment(homeFragment);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        frameLayout = findViewById(R.id.frame_container);

        ContentStore mc = new ContentStore(getApplicationContext());
        Utils.logInfo("Current session user: " + mc.getSession().getCurrentUser());
        Utils.logInfo("Current meals in basket session: " + mc.getSession().getAllMeals());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }

    private void setFragment(Fragment myFragment) {
        getSupportFragmentManager().beginTransaction().
                replace(R.id.frame_container, myFragment).
                commit();
    }


}
