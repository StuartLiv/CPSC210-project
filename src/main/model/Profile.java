package model;

import persistence.*;

import java.io.IOException;
import java.util.ArrayList;

//Represents User data for nutrition tracker
public class Profile {
    private final ArrayList<Ingredient> ingredientList;
    private final ArrayList<Recipe> recipeBook;
    private final ArrayList<Meal> tracker;

    //MODIFIES: this
    //EFFECTS: initializes recipeBook, ingredientList, tracker
    // throws IOException if user is invalid
    public Profile(String source) throws IOException {
        ingredientList = new JsonReaderIngredient(source + "ingredients.json").readIngredient();
        recipeBook = new JsonReaderRecipe(source + "recipeBook.json").readRecipe();
        tracker = new JsonReaderMeal(source + "tracker.json").readMeal();
    }

    //EFFECTS: returns ingredientList
    public ArrayList<Ingredient> getIngredientList() {
        return ingredientList;
    }

    //EFFECTS: returns recipeBook
    public ArrayList<Recipe> getRecipeBook() {
        return recipeBook;
    }

    //EFFECTS: returns tracker
    public ArrayList<Meal> getTracker() {
        return tracker;
    }

    //MODIFIES: this.ingredientList
    //EFFECTS: adds ingredient to ingredientList
    public void addIngredient(Ingredient ingredient) {
        ingredientList.add(ingredient);
    }

    //MODIFIES: this
    //EFFECTS: deletes ingredient of given name from ingredientList
    public void deleteIngredient(String name) {
        for (int i = 0; i < ingredientList.size(); i++) {
            if (ingredientList.get(i).getIngredientName().equals(name)) {
                ingredientList.remove(i);
                break;
            }
        }
    }

    //EFFECTS: returns ingredient with search name or null if not found
    public Ingredient findIngredient(String name) {
        for (Ingredient ingredient: ingredientList) {
            if (name.equals(ingredient.getIngredientName())) {
                return ingredient;
            }
        }
        return null;
    }

    //MODIFIES: this.recipeBook
    //EFFECTS: adds recipe to recipeBook
    public void addRecipe(Recipe recipe) {
        recipeBook.add(recipe);
    }

    //MODIFIES: this
    //EFFECTS: deletes ingredient of given name from recipeBook
    public void deleteRecipe(String name) {
        for (int i = 0; i < recipeBook.size(); i++) {
            if (recipeBook.get(i).getName().equals(name)) {
                recipeBook.remove(i);
                break;
            }
        }
    }

    //EFFECTS: returns recipe with search name or null if not found
    public Recipe findRecipe(String name) {
        for (Recipe r: recipeBook) {
            if (name.equals(r.getName())) {
                return r;
            }
        }
        return null;
    }

    //MODIFIES: this
    //EFFECTS: adds meal to tracker
    public void addMeal(Meal meal) {
        tracker.add(meal);
    }

    //MODIFIES: this
    //EFFECTS: deletes meal from tracker
    public void deleteMeal(Meal meal) {
        tracker.remove(meal);
    }
}
