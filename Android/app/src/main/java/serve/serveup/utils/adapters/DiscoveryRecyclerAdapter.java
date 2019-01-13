package serve.serveup.utils.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import serve.serveup.R;
import serve.serveup.dataholder.RestaurantInfo;
import serve.serveup.utils.ContentStore;
import serve.serveup.utils.Utils;
import serve.serveup.views.restaurant.RestaurantActivity;

public class DiscoveryRecyclerAdapter
        extends RecyclerView.Adapter<DiscoveryRecyclerAdapter.DiscoveryRecyclerHolder> {

    // Contains the data from which the adapter will build the cards
    private List<RestaurantInfo> restaurantsHome;
    @NonNull
    private RestaurantInfo currentRestaurant;

    // Define the View Holder
    static class DiscoveryRecyclerHolder extends RecyclerView.ViewHolder {
        // Parameters based on the layout of the Card that will be used in the Adapter
        // In this case layout/card_discovery.xml
        int restaurantID;
        CardView cardDiscoveryView;
        ImageView cardDiscoveryImage;
        TextView cardDiscoveryTitle;
        TextView cardDiscoveryType;
        RatingBar cardDiscoveryRating;

        DiscoveryRecyclerHolder(final View itemView) {
            super(itemView);
            // Initialize the parameters based on the Card layout names
            cardDiscoveryView = itemView.findViewById(R.id.cardDiscoveryView);
            cardDiscoveryImage = itemView.findViewById(R.id.cardDiscoveryImage);
            cardDiscoveryTitle = itemView.findViewById(R.id.cardDiscoveryTitle);
            cardDiscoveryType = itemView.findViewById(R.id.cardDiscoveryType);
            cardDiscoveryRating = itemView.findViewById(R.id.cardDiscoveryRating);
        }
    }

    // Adapter constructor
    public DiscoveryRecyclerAdapter(List<RestaurantInfo> restaurantsHome) {
        this.restaurantsHome = restaurantsHome;
    }
    /*
    onCreateViewHolder, onBindViewHolder and getItemCount() HAVE to be defined in order to get rid
    of the errors!
    */

    @NonNull
    @Override
    public DiscoveryRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Find and inflate the appropriate card layout.
        // In this case layout/card_discovery.xml
        View cardDiscoveryView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_discovery, parent, false);

        return new DiscoveryRecyclerHolder(cardDiscoveryView);
    }

    @Override
    public void onBindViewHolder(@NonNull final DiscoveryRecyclerHolder holder, int position) {
        // Get the restaurant home info for the appropriate restaurant
        RestaurantInfo restInfo = this.restaurantsHome.get(position);
        Context myContext = holder.cardDiscoveryTitle.getContext();
        ContentStore cntStore = new ContentStore(myContext);

        // Set the Holder (card) values based on the values from the data list
        holder.restaurantID = restInfo.getIdRestavracija();
        holder.cardDiscoveryTitle.setText(restInfo.getImeRestavracije());
        holder.cardDiscoveryType.setText(restInfo.getTip());
        holder.cardDiscoveryRating.setRating(restInfo.getOcena());

        if (myContext != null) {
            Bitmap restBitmap = Utils.parseBitmapFromBase64(myContext, restInfo.getSlika());
            holder.cardDiscoveryImage.setImageBitmap(restBitmap);
        }

        holder.cardDiscoveryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle myBundle = new Bundle();
                currentRestaurant = restaurantsHome.get(holder.getAdapterPosition());
                Utils.logInfo("current restaurant position: " + holder.getAdapterPosition());
                myBundle.putSerializable("restaurant_info", currentRestaurant);
                if (view.getContext() != null) {
                    Intent myIntent = new Intent(view.getContext(), RestaurantActivity.class);
                    myIntent.putExtras(myBundle);
                    view.getContext().startActivity(myIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantsHome.size();
    }

    // In case the adapter needs to be refreshed. A simple implementation
    public void refreshAdapter(List<RestaurantInfo> RestaurantInfosNew) {
        this.restaurantsHome.clear();
        this.restaurantsHome.addAll(RestaurantInfosNew);
        notifyDataSetChanged();
    }

    private void enableView(View myView) {
        myView.setEnabled(true);
        myView.setAlpha(1f);
    }

    private void disableView(View myView) {
        myView.setEnabled(false);
        myView.setAlpha(.5f);
    }
}
