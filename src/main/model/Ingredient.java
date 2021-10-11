package model;

import java.util.Collections;
import java.util.ArrayList;

//Represents an ingredient with name, serving size and nutritional information3
public class Ingredient {
    private final String ingredientName;
    private final int servingSize;
    private final int calories;
    private final int protein;
    private final int carbs;
    private final int fat;


    //REQUIRES: name has a non-zero length
    //MODIFIES: this
    //EFFECTS: All parameters are assigned to field of the same name
    public Ingredient(String ingredientName, int mass, int calories, int protein, int carbs, int fat) {
        this.ingredientName = ingredientName;
        this.servingSize = mass;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }
    //constructor from inputs
    //serving size, calories, protein, carb, fat

    //Field Getters

    //EFFECTS: return ingredientName
    public String getIngredientName() {
        return this.ingredientName;
    }

    //EFFECTS: return servingSize
    public int getServingSize() {
        return servingSize;
    }

    //EFFECTS: returns calories per servingSize
    public int getCalories() {
        return calories;
    }

    //EFFECTS: return protein per servingSize
    public int getProtein() {
        return protein;
    }

    //EFFECTS: return carbs per servingSize
    public int getCarbs() {
        return carbs;
    }

    //EFFECTS: return fat per servingSize
    public int getFat() {
        return fat;
    }

    //EFFECTS: return list of fields
    public ArrayList<String> getFields() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add(getIngredientName());
        fields.add(Integer.toString(getServingSize()));
        fields.add(Integer.toString(getCalories()));
        fields.add(Integer.toString(getProtein()));
        fields.add(Integer.toString(getCarbs()));
        fields.add(Integer.toString(getFat()));
        return fields;
    }
}
