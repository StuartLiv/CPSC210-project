package model;

import model.exceptions.InvalidMassException;

import model.exceptions.InvalidNutritionException;
import org.json.JSONObject;
import persistence.Writable;

//Represents a measured quantity of a food
public class Portion implements Writable {
    private Ingredient ingredient;
    private int mass;
    private final double factor;

    //MODIFIES: this
    //EFFECTS: parameters are assigned to field of same name, and a conversion factor is calculated
    // throws InvalidMassException if mass is negative
    public Portion(Ingredient ingredient, int mass) throws InvalidMassException {
        if (mass < 0) {
            throw new InvalidMassException();
        }
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
    public void scaleIngredient() throws InvalidNutritionException {
        ingredient = new Ingredient(ingredient.getIngredientName(), mass,
                convert(ingredient.getCalories(), factor), convert(ingredient.getProtein(), factor),
                convert(ingredient.getCarbs(), factor), convert(ingredient.getFat(), factor));
    }

    //MODIFIES: this.ingredient
    //EFFECTS: scales ingredient to provided factor
    public void scaleIngredient(double factor) throws InvalidNutritionException {
        ingredient = new Ingredient(ingredient.getIngredientName(), convert(ingredient.getServingSize(), factor),
                convert(ingredient.getCalories(), factor), convert(ingredient.getProtein(), factor),
                convert(ingredient.getCarbs(), factor), convert(ingredient.getFat(), factor));
        mass = convert(ingredient.getServingSize(), factor);
    }

    //EFFECTS: returns portion as formatted JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = ingredient.toJson();
        json.put("mass", mass);
        return json;
    }
}
