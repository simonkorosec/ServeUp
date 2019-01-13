package serve.serveup.utils.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import serve.serveup.R;
import serve.serveup.dataholder.MealInfo;
import serve.serveup.dataholder.RestaurantInfo;
import serve.serveup.utils.Utils;
import serve.serveup.views.restaurant.PickedMealActivity;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealsHolder> {

    private ArrayList<MealInfo> meals;
    private MealInfo pickedMeal;
    private RestaurantInfo pickedRestaurant;

    // Define the View Holder
    static class MealsHolder extends RecyclerView.ViewHolder {
        LinearLayout cardMealContainer;
        TextView cardMealTitle;
        TextView cardMealDescription;
        TextView cardMealPrice;

        MealsHolder(final View itemView) {
            super(itemView);
            // Initialize the parameters based on the Card layout names
            cardMealContainer = itemView.findViewById(R.id.cardMealContainer);
            cardMealTitle = itemView.findViewById(R.id.mealTitleText);
            cardMealDescription = itemView.findViewById(R.id.mealDescriptionText);
            cardMealPrice = itemView.findViewById(R.id.mealPriceText);
        }
    }

    public MealsAdapter(ArrayList<MealInfo> meals, RestaurantInfo pickedRestaurant) {
        this.meals = meals;
        this.pickedRestaurant = pickedRestaurant;
    }

    @NonNull
    @Override
    public MealsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Find and inflate the appropriate card layout.
        View cardMeal = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_meal, parent, false);
        return new MealsHolder(cardMeal);
    }

    @Override
    public void onBindViewHolder(@NonNull final MealsHolder holder, int position) {
        MealInfo meal = this.meals.get(position);
        holder.cardMealTitle.setText(meal.getImeJedi());
        holder.cardMealDescription.setText(meal.getOpisJedi());
        holder.cardMealPrice.setText(String.valueOf(meal.getCena()) + " €");

        holder.cardMealContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickedMeal = meals.get(holder.getAdapterPosition());
                if (pickedMeal != null) {
                    Intent myIntent = new Intent(view.getContext(), PickedMealActivity.class);
                    myIntent.putExtra("picked_meal", pickedMeal);
                    myIntent.putExtra("picked_restaurant", pickedRestaurant);
                    view.getContext().startActivity(myIntent);
                    //((Activity) view.getContext()).finish();
                }
                else
                    Utils.logInfo("Picked meal doesnt exist");
            }
        });
    }
    @Override
    public int getItemCount() {
        return meals.size();
    }
}
