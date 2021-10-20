package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

//Represents a measured quantity of a food
public class Portion implements Writable {
    private Ingredient ingredient;
    private int mass;
    private final double factor;

    //REQUIRES: mass >= 0, ingredient is non-null
    //MODIFIES: this
    //EFFECTS: parameters are assigned to field of same name, and a conversion factor is calculated
    public Portion(Ingredient ingredient, int mass) {
        this.ingredient = ingredient;
        this.mass = mass;
        this.factor = getConversion();
    }

    //EFFECTS: returns ingredient
    public Ingredient getIngredient() {
        return ingredient;
    }

    //EFFECTS: returns mass
    public int getMass() {
        return mass;
    }

    //EFFECTS: returns the conversion factor for the nutrition statistics of portion mass m of ingredient.servingSize
    public double getConversion() {
        return (double) mass / (double) ingredient.getServingSize();
    }

    //EFFECTS: converts an int by factor, and return as a rounded int
    public int convert(int toConvert, double factor) {
        return (int) Math.round(toConvert * factor);
    }

    //MODIFIES: this.ingredient
    //EFFECTS: scales ingredient to make servingSize = this.mass
    public void scaleIngredient() {
        ingredient = new Ingredient(ingredient.getIngredientName(), mass,
                convert(ingredient.getCalories(), factor), convert(ingredient.getProtein(), factor),
                convert(ingredient.getCarbs(), factor), convert(ingredient.getFat(), factor));
    }

    //REQUIRES: factor>=0
    //MODIFIES: this.ingredient
    //EFFECTS: scales ingredient to provided factor
    public void scaleIngredient(double factor) {
        ingredient = new Ingredient(ingredient.getIngredientName(), convert(ingredient.getServingSize(), factor),
                convert(ingredient.getCalories(), factor), convert(ingredient.getProtein(), factor),
                convert(ingredient.getCarbs(), factor), convert(ingredient.getFat(), factor));
        mass = convert(ingredient.getServingSize(), factor);
    }

    @Override
    public JSONObject toJson() {
        JSONArray jsonArray = new JSONArray();
        JSONObject json = ingredient.toJson();
        json.put("mass", mass);
        return json;
    }
}
