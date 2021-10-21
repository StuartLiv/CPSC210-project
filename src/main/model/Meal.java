package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.time.*;


//Represents a meal with recipe, summary total, serving size, and time of meal in hours
public class Meal implements Writable {
    private Recipe recipe;
    private Portion total;
    private int serving;
    private int time;
//    private LocalDate date;
//    private LocalTime mealTime;
    //Implement more complex field for time in phase 2

    //REQUIRES: 0 <= time <= 23, recipe is non-null, mass >=0
    //MODIFIES: this
    //EFFECTS: fields are set, and total nutrition for meal is calculated
    public Meal(Recipe recipe, int mass, int time) {
        this.recipe = recipe;
        this.serving = mass;
        this.time = time;
        this.total = recipe.getTotal();
        this.total = getScaledTotal();
    }

    //EFFECTS: returns Recipe
    public Recipe getRecipe() {
        return recipe;
    }

    //Functionality will be expanded in phase 2
    //EFFECTS: returns time
    public int getTime() {
        return time;
    }

    //EFFECTS: returns nutritional Total
    public Portion getTotal() {
        return total;
    }

    //EFFECTS: returns nutritional total scaled to this.serving
    private Portion getScaledTotal() {
        double factor = (double) serving / recipe.massTotal();
        total.scaleIngredient(factor);
        return total;
    }

    //EFFECTS: returns mass of meal
    public int getMass() {
        return serving;
    }

    //More methods to come, for ui integration

    @Override
    //EFFECTS: converts meal to json
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Recipe", recipe.toJson());
        json.put("mass", serving);
        json.put("time", time);
        return json;
    }

}
