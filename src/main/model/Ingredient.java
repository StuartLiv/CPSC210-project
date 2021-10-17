package model;

import java.util.ArrayList;

//Represents an ingredient with name, serving size and nutritional information3
public class Ingredient {
    private final String ingredientName;
    private final int servingSize;
    private final int calories;
    private final int protein;
    private final int carbs;
    private final int fat;


    //REQUIRES: 0 < ingredientName.length() < 16
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

    //REQUIRES: 0 < ingredientName.length() < 16, parameter[1:5] are string casted integers
    //MODIFIES: this
    //EFFECTS: All parameters are assigned to field of the same name
    public Ingredient(String[] parameters) {
        this.ingredientName = parameters[0];
        this.servingSize = Integer.parseInt(parameters[1]);
        this.calories = Integer.parseInt(parameters[2]);
        this.protein = Integer.parseInt(parameters[3]);
        this.carbs = Integer.parseInt(parameters[4]);
        this.fat = Integer.parseInt(parameters[5]);
    }

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