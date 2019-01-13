package serve.serveup.utils.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import serve.serveup.R;
import serve.serveup.dataholder.MealInfo;
import serve.serveup.dataholder.RestaurantInfo;
import serve.serveup.utils.Utils;
import serve.serveup.views.restaurant.CategoryMealsActivity;
import serve.serveup.webservices.RestManagement;

public class FoodTypesAdapter extends RecyclerView.Adapter<FoodTypesAdapter.FoodTypesHolder> {

    private ArrayList<String> foodTypes;
    private RestaurantInfo pickedRestaurant;

    // Define the View Holder
    static class FoodTypesHolder extends RecyclerView.ViewHolder {
        LinearLayout cardFoodTypeContainer;
        TextView cardFoodTypeTitle;

        FoodTypesHolder(final View itemView) {
            super(itemView);
            // Initialize the parameters based on the Card layout names
            cardFoodTypeContainer = itemView.findViewById(R.id.cardFoodType);
            cardFoodTypeTitle = itemView.findViewById(R.id.foodTypeText);
        }
    }

    public FoodTypesAdapter(ArrayList<String> foodTypes, RestaurantInfo pickedRestaurant) {
        this.foodTypes = foodTypes;
        this.pickedRestaurant = pickedRestaurant;
    }

    @NonNull
    @Override
    public FoodTypesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Find and inflate the appropriate card layout.
        View cardFoodType = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_food_type, parent, false);
        return new FoodTypesHolder(cardFoodType);
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodTypesHolder holder, int position) {
        // Get the restaurant home info for the appropriate restaurant
        String foodType = this.foodTypes.get(position);
        holder.cardFoodTypeTitle.setText(foodType);

        holder.cardFoodTypeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                RestManagement.getRestraurantMeals(pickedRestaurant.getIdRestavracija()).enqueue(new Callback<Map<String, List<MealInfo>>>() {
                    @Override
                    public void onResponse(Call<Map<String, List<MealInfo>>> call, Response<Map<String, List<MealInfo>>> response) {

                        Map<String, List<MealInfo>> pickedRestaurantMeals = response.body();
                        String category = getCurrentItemCategory(view);
                        List<MealInfo> categoryMeals = pickedRestaurantMeals.get(category);

                        if(categoryMeals != null) {
                            Intent myIntent = new Intent(view.getContext(), CategoryMealsActivity.class);
                            myIntent.putExtra("picked_restaurant_meals", (Serializable) categoryMeals);
                            myIntent.putExtra("category_name", category);
                            myIntent.putExtra("restaurant", pickedRestaurant);
                            view.getContext().startActivity(myIntent);
                        }
                        else {
                            Utils.showToast(view.getContext(),
                                    String.format(view.getContext()
                                            .getResources()
                                            .getString(R.string.meals_empty_category_message), category));
                        }
                    }
                    @Override
                    public void onFailure(Call<Map<String, List<MealInfo>>> call, Throwable t) {
                        Utils.logInfo("API call '/meals/id_restavracije=id/'  failed");
                    }
                });
            }
        });
    }
    @Override
    public int getItemCount() {
        return foodTypes.size();
    }


    @NonNull
    private String getCurrentItemCategory(View view) {
        LinearLayout currentItemLayout = (LinearLayout) view;
        TextView categoryText = (TextView) currentItemLayout.getChildAt(0);
        return categoryText.getText().toString();
    }
}
