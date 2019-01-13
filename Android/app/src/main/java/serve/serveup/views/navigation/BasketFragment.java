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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import serve.serveup.R;
import serve.serveup.utils.ContentStore;
import serve.serveup.utils.Utils;
import serve.serveup.utils.adapters.ShoppingBasketItemAdapter;
import serve.serveup.views.order.PaymentOptionActivity;

public class BasketFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView shoppingBasketRecycleView;
    private ShoppingBasketItemAdapter shoppingBasketItemAdapter;
    private LinearLayoutManager linearLayoutManager;
    private TextView emptyBasketText;
    private LinearLayout makeOrderButton;

    private ImageView incMinutesButton;
    private ImageView decMinutesButton;
    private TextView totalMinutesText;
    private int totalMinutes = 30;
    private int minutes = 10;
    private ContentStore cntStore;

    public BasketFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_basket, container, false);

        emptyBasketText = rootView.findViewById(R.id.emptyBasketText);
        makeOrderButton = rootView.findViewById(R.id.makeOrderButton);

        // Initialize the view components
        shoppingBasketRecycleView = rootView.findViewById(R.id.shoppingBasketRecyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        shoppingBasketRecycleView.setLayoutManager(linearLayoutManager);

        // Initialize view components for expected arrival time
        incMinutesButton = rootView.findViewById(R.id.incMinutes);
        decMinutesButton = rootView.findViewById(R.id.decMinutes);
        totalMinutesText = rootView.findViewById(R.id.minute);

        cntStore = new ContentStore(getActivity());

        setIncrAndDecrMinutes();
        updateCurrentBasket();

        emptyBasketText.setVisibility(shoppingBasketItemAdapter.getItemCount() < 1 ? View.VISIBLE : View.GONE);
        
        makeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cntStore.getSession().mealsNotEmpty()) {
                    String casPrevzema = Utils.createDateTimeString(String.valueOf(totalMinutes));
                    Intent myIntent = new Intent(getActivity(), PaymentOptionActivity.class);
                    myIntent.putExtra("order_pickup_time", casPrevzema);
                    startActivity(myIntent);
                }
                else
                    Utils.showToast(getActivity(), "Vaša košarica je prazna");
            }
        });

        return rootView;
    }

    private void setIncrAndDecrMinutes() {

        totalMinutesText.setText(R.string.minutes_text);

        incMinutesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalMinutes < 90) {
                    totalMinutes += minutes;
                    totalMinutesText.setText(String.valueOf(totalMinutes));
                }
            }
        });

        decMinutesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalMinutes > 30) {
                    totalMinutes -= minutes;
                    totalMinutesText.setText(String.valueOf(totalMinutes));
                }
            }
        });
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


    @Override
    public void onResume() {
        super.onResume();
        updateCurrentBasket();
    }


    public void updateCurrentBasket() {
        shoppingBasketItemAdapter = new ShoppingBasketItemAdapter(getActivity());
        shoppingBasketRecycleView.setAdapter(shoppingBasketItemAdapter);
        emptyBasketText.setVisibility(shoppingBasketItemAdapter.getItemCount() < 1 ? View.VISIBLE : View.GONE);
    }

}
