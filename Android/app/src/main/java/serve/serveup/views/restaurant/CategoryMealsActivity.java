package serve.serveup.views.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import serve.serveup.R;
import serve.serveup.dataholder.MealInfo;
import serve.serveup.dataholder.RestaurantInfo;
import serve.serveup.utils.ContentStore;
import serve.serveup.utils.Utils;
import serve.serveup.utils.adapters.MealsAdapter;

public class CategoryMealsActivity extends AppCompatActivity {

    private List<MealInfo> categoryMeals;
    private String categoryName;
    private RestaurantInfo pickedRestaurant;

    private LinearLayoutManager linearLayoutManager;
    private MealsAdapter mealsAdapter;
    private RecyclerView mealsRecyclerView;
    private TextView imeRestavracijeText;
    private TextView categoryText;
    private ImageView backButton;
    private TextView cenaCategoryText;
    private LinearLayout cenaCategoryContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_meals);

        imeRestavracijeText = findViewById(R.id.imeRestravracijeCatgoryText);
        categoryText = findViewById(R.id.categoryText);
        mealsRecyclerView = findViewById(R.id.mealsRecyclerView);
        backButton = findViewById(R.id.backToRestaurantIcon);
        cenaCategoryText = findViewById(R.id.cenaTextCategory);
        cenaCategoryContainer = findViewById(R.id.cenaCategoryContainer);

        Intent passedIntent = getIntent();
        categoryMeals = (List<MealInfo>) passedIntent.getSerializableExtra("picked_restaurant_meals");
        pickedRestaurant = (RestaurantInfo) passedIntent.getSerializableExtra("restaurant");
        categoryName = passedIntent.getStringExtra("category_name");

        Utils.logMultipleData("category name", categoryName,
                "picked restaurant", pickedRestaurant);


        // Initialize the view components
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mealsAdapter = new MealsAdapter((ArrayList<MealInfo>) categoryMeals, pickedRestaurant);
        // Set the layout manager and the adapter of the Recycler View
        mealsRecyclerView.setLayoutManager(linearLayoutManager);
        mealsRecyclerView.setAdapter(mealsAdapter);

        imeRestavracijeText.setText(pickedRestaurant.getImeRestavracije());
        categoryText.setText(categoryName);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateOverallCenaText();
    }

    private void updateOverallCenaText() {
        ContentStore cntStore = new ContentStore(getApplicationContext());
        float overAllPrice = cntStore.getSession().getOverAllPrice();
        cenaCategoryText.setText(String.format("%.2f €", overAllPrice));
    }

}
