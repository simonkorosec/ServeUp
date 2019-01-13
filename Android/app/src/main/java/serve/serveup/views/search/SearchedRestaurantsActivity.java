package serve.serveup.views.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import serve.serveup.R;
import serve.serveup.dataholder.RestaurantInfo;
import serve.serveup.utils.Utils;
import serve.serveup.utils.adapters.DiscoveryRecyclerAdapter;
import serve.serveup.webservices.RestManagement;

public class SearchedRestaurantsActivity extends AppCompatActivity {

    private RecyclerView searchRecyclerView;
    private LinearLayoutManager layoutManager;
    private DiscoveryRecyclerAdapter myAdapter;
    private ImageView backToSearchIcon;
    private ArrayList<RestaurantInfo> filteredRestaurants;
    private LinearLayout progressBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_restaurants);

        if(getIntent() != null) {

            String lokacija = getIntent().getStringExtra("lokacija_text");
            final String tipRestavracije = getIntent().getStringExtra("tip_restavracije_text");

            filteredRestaurants = new ArrayList<>();
            searchRecyclerView = findViewById(R.id.searchedRestaurantsRecycler);
            backToSearchIcon = findViewById(R.id.backToSearchIcon);
            layoutManager = new LinearLayoutManager(getApplicationContext());
            progressBarLayout = findViewById(R.id.loadingSearchRestauransProgressBar);
            progressBarLayout.setVisibility(View.VISIBLE);

            RestManagement.getAllRestaurants(lokacija).
                    enqueue(new Callback<List<RestaurantInfo>>() {
                        @Override
                        public void onResponse(Call<List<RestaurantInfo>> call, Response<List<RestaurantInfo>> response) {
                            if (response.code() == 200) {
                                progressBarLayout.setVisibility(View.GONE);
                                for (RestaurantInfo restaurant : response.body()) {
                                    if (restaurant.getTip().equalsIgnoreCase(tipRestavracije)) {
                                        filteredRestaurants.add(restaurant);
                                    }
                                }
                                myAdapter = new DiscoveryRecyclerAdapter(filteredRestaurants);
                                searchRecyclerView.setAdapter(myAdapter);
                                searchRecyclerView.setLayoutManager(layoutManager);
                            }
                        }
                        @Override
                        public void onFailure(Call<List<RestaurantInfo>> call, Throwable t) {
                            Utils.logInfo("api 'restaurant/home/' failed");
                        }
                    }
            );
        }
        backToSearchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
