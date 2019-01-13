package serve.serveup.views.navigation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import serve.serveup.R;
import serve.serveup.dataholder.order.ReturnedOrder;
import serve.serveup.dataholder.order.ReturnedOrderApiResponse;
import serve.serveup.utils.ContentStore;
import serve.serveup.utils.Utils;
import serve.serveup.utils.adapters.OrdersAdapter;
import serve.serveup.webservices.RestManagement;

public class OrdersFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ContentStore cntStore;
    private String userID;

    private LinearLayoutManager layoutManager;
    private RecyclerView myRecyclerView;
    private OrdersAdapter ordersAdapter;
    private TextView noOrdersText;
    private ProgressBar ordersProgressBar;

    private ArrayList<ReturnedOrder> orders;

    public OrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_orders, container, false);

        myRecyclerView = root.findViewById(R.id.ordersRecyclerView);
        noOrdersText = root.findViewById(R.id.noOrdersText);
        noOrdersText.setVisibility(View.GONE);
        ordersProgressBar = root.findViewById(R.id.ordersProgressBar);
        ordersProgressBar.setVisibility(View.VISIBLE);
        layoutManager = new LinearLayoutManager(getActivity());

        cntStore = new ContentStore(getActivity().getApplicationContext());
        orders = new ArrayList<>();

        if (cntStore.getSession().userIsSet()) {
            userID = cntStore.getSession().getCurrentUser();

            RestManagement.getUserOrders(userID, 10).enqueue(new Callback<ReturnedOrderApiResponse>() {
                @Override
                public void onResponse(Call<ReturnedOrderApiResponse> call, Response<ReturnedOrderApiResponse> response) {
                    if (response.code() == 200) {
                        ReturnedOrderApiResponse returnedResponse = response.body();

                        if (returnedResponse.getStatus() == 1) {
                            Utils.logInfo("recieved back orders from server for current user");
                            orders = new ArrayList<>(returnedResponse.getOrders());
                            updateOrders();
                        }
                        myRecyclerView.setLayoutManager(layoutManager);
                    } else
                        noOrdersText.setVisibility(View.VISIBLE);
                    ordersProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ReturnedOrderApiResponse> call, Throwable t) {
                    Utils.logInfo("api 'user/get_orders' failed");
                }
            });

        }
        return root;
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

    @Override
    public void onResume() {
        super.onResume();
        updateOrders();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void updateOrders() {
        ordersAdapter = new OrdersAdapter(orders);
        noOrdersText.setVisibility((orders.size() == 0) ? View.VISIBLE : View.GONE);
        myRecyclerView.setAdapter(ordersAdapter);
    }
}
