package model;

import model.exceptions.InvalidInputException;
import model.exceptions.InvalidMassException;
import model.exceptions.NoIngredientsException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Objects;

//Represents a list of portions, with a recipeName
public class Recipe implements Writable {
    private final ArrayList<Portion> portions;
    private final String recipeName;
    private boolean toSave = true;

    //MODIFIES: this
    //EFFECTS: Initialize name, and scale ingredient serving sizes
    // throws NoIngredientsException if ingredients is empty
    public Recipe(ArrayList<Portion> ingredients, String name) throws NoIngredientsException {
        if (Objects.equals(ingredients, new ArrayList<Portion>())) {
            throw new NoIngredientsException();
        }
        for (Portion p: ingredients) {
            p.scaleIngredient();
        }
        this.portions = ingredients;
        this.recipeName = name;
        if (Objects.equals(name, "Unsaved Recipe")) {
            this.toSave = false;
        }
    }

    //EFFECTS: return recipeName
    public String getName() {
        return recipeName;
    }

    //EFFECTS: returns toSave
    public boolean getToSave() {
        return toSave;
    }

    //EFFECTS: return ingredient list (portions)
    public ArrayList<Portion> getIngredients() {
        return portions;
    }


    //EFFECTS: calculate and total nutrition statistics for a recipe
    public Portion getTotal() throws InvalidMassException {
        int mass = this.massTotal();
        Ingredient sum = new Ingredient(this.recipeName, mass, this.calTotal(), this.proteinTotal(),
                this.carbTotal(), this.fatTotal());
        return new Portion(sum, mass);
    }

    //EFFECTS: returns sum of the masses of all portions
    public int massTotal() {
        int sum = 0;
        for (Portion portion: portions) {
            sum += portion.getIngredient().getServingSize();
        }
        return sum;
    }

    //EFFECTS: returns sum of the calories of all portions
    public int calTotal() {
        int sum = 0;
        for (Portion portion: portions) {
            sum += portion.getIngredient().getCalories();
        }
        return sum;
    }

    //EFFECTS: returns sum of the protein of all portions
    public int proteinTotal() {
        int sum = 0;
        for (Portion portion: portions) {
            sum += portion.getIngredient().getProtein();
        }
        return sum;
    }

    //EFFECTS: returns sum of the carbs of all portions
    public int carbTotal() {
        int sum = 0;
        for (Portion portion: portions) {
            sum += portion.getIngredient().getCarbs();
        }
        return sum;
    }

    //EFFECTS: returns sum of the fat of all portions
    public int fatTotal() {
        int sum = 0;
        for (Portion portion: portions) {
            sum += portion.getIngredient().getFat();
        }
        return sum;
    }

    //EFFECTS: returns recipe as formatted JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        json.put("name", recipeName);

        for (Portion portion: portions) {
            jsonArray.put(portion.toJson());
        }

        json.put("portions", jsonArray);
        return json;
    }


}
