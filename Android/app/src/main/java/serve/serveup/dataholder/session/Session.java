package serve.serveup.dataholder.session;

import java.util.ArrayList;
import serve.serveup.dataholder.MealInfo;
import serve.serveup.dataholder.RestaurantInfo;

/*
    @author: urban.jagodic
*/

public class Session {

    private String currentUser;
    private RestaurantInfo currentRestaurant;
    private ArrayList<MealInfo> orderedMeals;
    private float overAllPrice;
    private String DEFAULT_USER = "default_user";

    public Session() {
        this.orderedMeals = new ArrayList<>();
        this.currentUser = DEFAULT_USER;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
    public String getCurrentUser() {
        return currentUser;
    }
    public void clearCurrentUser() {
        this.currentUser = DEFAULT_USER;
    }

    public void setCurrentRestaurant(RestaurantInfo currentRestaurant) {
        this.currentRestaurant = currentRestaurant;
    }
    public void clearCurrentRestaurant() {
        this.currentRestaurant = null;
    }
    public RestaurantInfo getCurrentRestaurant() {
        return currentRestaurant;
    }

    public void addCurrentMeal(MealInfo meal) {
        this.orderedMeals.add(meal);
    }
    public void deleteMeal(MealInfo removedMeal) {
        if(removedMeal != null) {
            for (MealInfo meal : getAllMeals()) {
                if (meal.getUniqueName().equals(removedMeal.getUniqueName())) {
                    this.orderedMeals.remove(meal);
                    break;
                }
            }
        }
    }
    public void deleteAllMeals() {
        this.orderedMeals = new ArrayList<>();
    }
    public ArrayList<MealInfo> getAllMeals() {
        return this.orderedMeals;
    }

    public float getOverAllPrice() {
        for (MealInfo meal : this.getAllMeals())
            overAllPrice += meal.getCena() * meal.getKolicina();
        return overAllPrice;
    }

    public boolean mealsNotEmpty() {
        return this.getAllMeals().size() != 0;
    }

    public void clearOverAllPrice() {
        overAllPrice = 0;
    }

    public boolean userIsSet() {
        return !this.getCurrentUser().equals(DEFAULT_USER);
    }

    public boolean restaurantIsSet() {
        return this.getCurrentRestaurant() != null;
    }
}
