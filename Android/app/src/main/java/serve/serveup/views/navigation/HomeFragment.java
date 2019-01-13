package serve.serveup.views.navigation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import serve.serveup.views.search.SearchActivity;
import serve.serveup.webservices.RestManagement;

public class HomeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView discoveryRecyclerView;
    private DiscoveryRecyclerAdapter discoveryRecyclerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout myProgressBarLayout;
    // Contains data of every restaurant home page info
    private List<RestaurantInfo> restaurantHomes;
    private LinearLayout searchButton;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        restaurantHomes = new ArrayList<>();
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        myProgressBarLayout = rootView.findViewById(R.id.loadingProgressBarContainer);
        myProgressBarLayout.setVisibility(View.VISIBLE);
        searchButton = rootView.findViewById(R.id.searchButton);



        /*
         TODO hardcoded refrence to LOCATION, need to change it on the current location of the device
        * */
        RestManagement.getAllRestaurants("Ljubljana").enqueue(new Callback<List<RestaurantInfo>>() {
            @Override
            public void onResponse(Call<List<RestaurantInfo>> call, Response<List<RestaurantInfo>> response) {

                List<RestaurantInfo> myRests = response.body();
                if(getActivity() != null) {
                    restaurantHomes = myRests;
                    myProgressBarLayout.setVisibility(View.GONE);
                }
                // Initialize the view components
                discoveryRecyclerView = rootView.findViewById(R.id.discoveryRecyclerView);
                linearLayoutManager = new LinearLayoutManager(getActivity());
                discoveryRecyclerAdapter = new DiscoveryRecyclerAdapter(restaurantHomes);

                // Set the layout manager and the adapter of the Recycler View
                discoveryRecyclerView.setLayoutManager(linearLayoutManager);
                discoveryRecyclerView.setAdapter(discoveryRecyclerAdapter);
            }

            @Override
            public void onFailure(Call<List<RestaurantInfo>> call, Throwable t) {
                Utils.logInfo("API call 'restaurant/home/' failed!");
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), SearchActivity.class);
                startActivity(myIntent);
            }
        });

        return rootView;
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
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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

}
