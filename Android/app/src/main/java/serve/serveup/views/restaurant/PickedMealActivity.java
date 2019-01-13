package serve.serveup.views.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import serve.serveup.R;
import serve.serveup.dataholder.MealInfo;
import serve.serveup.dataholder.RestaurantInfo;
import serve.serveup.dataholder.session.SessionContent;
import serve.serveup.utils.ContentStore;
import serve.serveup.utils.Utils;

public class PickedMealActivity extends AppCompatActivity {

    @NonNull
    private MealInfo pickedMeal;
    @NonNull
    private RestaurantInfo pickedRestaurant;
    private float overAllPrice;
    private int factor = 1;

    private TextView pickedMealTitle;
    private TextView pickedMealDescription;
    private TextView pickedMealPrice;
    private ImageView descMealButton;
    private ImageView ascMealButton;
    private ImageView backToPickedMealIcon;
    private TextView factorPriceText;
    private TextView overAllPriceText;
    private View addToBasketButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picked_meal);

        pickedMealTitle = findViewById(R.id.basketMealTitleText);
        pickedMealDescription = findViewById(R.id.pickedMealDescriptionText);
        pickedMealPrice = findViewById(R.id.pickedMealPriceText);
        descMealButton = findViewById(R.id.descMealButton);
        ascMealButton = findViewById(R.id.ascMealButton);
        factorPriceText = findViewById(R.id.factorPriceText);
        overAllPriceText = findViewById(R.id.overallPriceText);
        backToPickedMealIcon = findViewById(R.id.backToPickedMealIcon);
        addToBasketButton = findViewById(R.id.addToBasketButton);


        Intent passedIntent = getIntent();
        pickedMeal = (MealInfo) passedIntent.getSerializableExtra("picked_meal");
        // important to set unique ID name
        pickedMeal.setUniqueName();
        pickedRestaurant = (RestaurantInfo) passedIntent.getSerializableExtra("picked_restaurant");

        pickedMealTitle.setText(pickedMeal.getImeJedi());
        pickedMealDescription.setText(pickedMeal.getOpisJedi());
        pickedMealPrice.setText(
                String.format(getResources().getString(R.string.picked_meal_price_text),
                        pickedMeal.getCena()));

        setIncrAndDecrPrice();
        backToPickedMealIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish(); }
        });

        addToBasketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickedMeal.setKolicina(factor);
                ContentStore myContentStore = new ContentStore(getApplicationContext());
                myContentStore.addToSession(SessionContent.PICKED_MEAL, pickedMeal);
                myContentStore.addToSession(SessionContent.RESTUANRANT, pickedRestaurant);

                // TODO add a success loading screen when meal is added to the basket
                Utils.showToast(getApplicationContext(), "Jed dodana v kosarico!");
                finish();
            }
        });

    }

    private void setIncrAndDecrPrice() {

        overAllPrice = pickedMeal.getCena();
        overAllPriceText.setText(String.format(
                getResources().getString(R.string.picked_meal_overall_price_text),
                overAllPrice));

        descMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                factor = Integer.parseInt(factorPriceText.getText().toString());
                if(factor > 1) {
                    factor--;
                    factorPriceText.setText(String.valueOf(factor));
                    overAllPrice = factor * pickedMeal.getCena();
                    overAllPriceText.setText(String.format(
                            getResources().getString(R.string.picked_meal_overall_price_text),
                            overAllPrice));
                }
            }
        });
        ascMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                factor = Integer.parseInt(factorPriceText.getText().toString());
                if(factor < 20) {
                    factor++;
                    factorPriceText.setText(String.valueOf(factor));
                    overAllPrice = factor * pickedMeal.getCena();
                    overAllPriceText.setText(String.format(
                            getResources().getString(R.string.picked_meal_overall_price_text),
                            overAllPrice));
                }
            }
        });
    }
}
