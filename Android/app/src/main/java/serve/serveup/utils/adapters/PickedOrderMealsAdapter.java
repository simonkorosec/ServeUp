package serve.serveup.utils.adapters;

import android.content.Context;
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

public class PickedOrderMealsAdapter extends
        RecyclerView.Adapter<PickedOrderMealsAdapter.PickedOrderMealsHolder> {

    private ArrayList<MealInfo> meals;

    // Define the View Holder
    static class PickedOrderMealsHolder extends RecyclerView.ViewHolder {
        LinearLayout cardMealContainer;
        TextView cardMealTitleName;
        TextView cardMealDescription;
        TextView cardMealQuantity;
        TextView cardMealPrice;

        PickedOrderMealsHolder(final View itemView) {
            super(itemView);
            cardMealContainer = itemView.findViewById(R.id.cardPickedOrderMealContainer);
            cardMealTitleName = itemView.findViewById(R.id.pickedOrderMealTitleText);
            cardMealDescription = itemView.findViewById(R.id.pickedOrderMealDescriptionText);
            cardMealQuantity = itemView.findViewById(R.id.pickedOrderMealKolicinaText);
            cardMealPrice = itemView.findViewById(R.id.pickedOrderMealPriceText);
        }
    }

    public PickedOrderMealsAdapter(ArrayList<MealInfo> meals) {
        this.meals = meals;
    }

    @NonNull
    @Override
    public PickedOrderMealsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardPickedOrderMeal = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_picked_order_meal, parent, false);
        return new PickedOrderMealsHolder(cardPickedOrderMeal);
    }

    @Override
    public void onBindViewHolder(@NonNull final PickedOrderMealsHolder holder, int position) {
        final MealInfo meal = this.meals.get(position);

        final Context myContext = holder.cardMealContainer.getContext();
        holder.cardMealTitleName.setText(meal.getImeJedi());
        holder.cardMealDescription.setText(meal.getOpisJedi());
        holder.cardMealQuantity.setText(meal.getKolicina() + "x");
        holder.cardMealPrice.setText(String.format("%.2f €", meal.getCena()));
    }
    @Override
    public int getItemCount() {
        return meals.size();
    }
}
