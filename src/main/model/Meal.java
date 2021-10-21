package model;

import model.exceptions.InvalidInputException;
import model.exceptions.InvalidMassException;
import model.exceptions.NoRecipeException;

import org.json.JSONObject;
import persistence.Writable;

//Represents a meal with recipe, summary total, serving size, and time of meal in hours
public class Meal implements Writable {
    private final Recipe recipe;
    private Portion total;
    private final int serving;
    private final int time;
//    private LocalDate date;
//    private LocalTime mealTime;
    //Implement more complex field for time in phase 2
    
    //MODIFIES: this
    //EFFECTS: fields are set, and total nutrition for meal is calculated
    // throws NoRecipeException if recipe is null, throws InvalidMassException if mass is negative,
    // both of supertype InvalidInputException
    public Meal(Recipe recipe, int mass, int time) throws InvalidInputException {
        if (recipe == null) {
            throw new NoRecipeException();
        } else if (mass < 0) {
            throw new InvalidMassException();
        }
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

    //EFFECTS: converts returns meal as formatted JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Recipe", recipe.toJson());
        json.put("mass", serving);
        json.put("time", time);
        return json;
    }

}
