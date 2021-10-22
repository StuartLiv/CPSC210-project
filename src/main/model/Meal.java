package model;

import model.exceptions.InvalidInputException;
import model.exceptions.InvalidMassException;
import model.exceptions.NoRecipeException;
import org.json.JSONObject;
import persistence.Writable;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

//Represents a meal with recipe, summary total, serving size, and time of meal in hours
public class Meal implements Writable {
    private final Recipe recipe;
    private Portion total;
    private final int serving;
    private final LocalDate date;
    private final LocalTime mealTime;
    //Implement more complex field for time in phase 2
    
    //MODIFIES: this
    //EFFECTS: fields are set, and total nutrition for meal is calculated
    // throws NoRecipeException if recipe is null, throws InvalidMassException if mass is negative,
    // both of supertype InvalidInputException
    public Meal(Recipe recipe, int mass, String date, String time)
            throws InvalidInputException, DateTimeException {
        if (mass < 0) {
            throw new InvalidMassException();
        } else if (recipe == null) {
            throw new NoRecipeException();
        }
        this.recipe = recipe;
        this.serving = mass;
        this.date = setDate(date);
        this.mealTime = LocalTime.parse(time);
        this.total = recipe.getTotal();
        this.total = getScaledTotal();
    }

    //EFFECTS: returns LocalDate based on date string for constructor
    private LocalDate setDate(String date) {
        if (Objects.equals(date, "today")) {
            return LocalDate.now();
        } else {
            return LocalDate.parse(date);
        }
    }

    //EFFECTS: returns Recipe
    public Recipe getRecipe() {
        return recipe;
    }

    //EFFECTS: returns mass of meal
    public int getMass() {
        return serving;
    }

    //EFFECTS: returns time string in format HH:mm
    public String getTimeString() {
        return mealTime.toString();
    }

    //EFFECTS: returns date string in format
    public String getDateString() {
        return date.toString();
    }

    //EFFECTS: returns localDateTime
    public String getDateTimeString() {
        return date.atTime(mealTime).toString();
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


    //EFFECTS: converts returns meal as formatted JSONObject
    //NOTE UPDATE JSON
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Recipe", recipe.toJson());
        json.put("mass", serving);
        json.put("date", getDateString());
        json.put("time", getTimeString());
        return json;
    }

}
