package model;

import model.exceptions.InvalidInputException;
import persistence.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

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


    /*
    Movable functions:
    Purely:
    maintainSorted //
    findIngredient, recipe methods //
    getDayTotal, getDailyTotals, formatTotals

    Adapted:
    loadData, saveData //

    New Implementation:
    add/delete all fields //
    some edit handler maybe
     */

    //MODIFIES: this
    //EFFECTS: keeps data field lists in sorted order
    //Using object comparison from oracle docs:
    //Link: https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html#comparing-java.util.function.Function-
    public void maintainSorted() {
        ingredientList.sort(Comparator.comparing(Ingredient::getIngredientName));
        recipeBook.sort((Comparator.comparing(Recipe::getName)));
        tracker.sort((Comparator.comparing(Meal::getDateTimeString)));
    }

    //EFFECTS: returns formatted daily nutrition totals
    public ArrayList<Portion> getFormattedTotals() {
        return formatTotals(getDailyTotals());
    }

    //EFFECTS: removes duplicates and null portions from dailyTotals
    private ArrayList<Portion> formatTotals(ArrayList<Portion> dailyTotals) {
        ArrayList<Portion> formattedTotals = new ArrayList<>();
        boolean notInFormattedTools;
        for (Portion total : dailyTotals) {
            if (total != null) {
                notInFormattedTools = true;
                for (Portion p : formattedTotals) {
                    notInFormattedTools = notInFormattedTools
                            && !total.getIngredient().getIngredientName().equals(p.getIngredient().getIngredientName());
                }
                if (notInFormattedTools) {
                    formattedTotals.add(total);
                }
            }
        }
        return formattedTotals;
    }

    //EFFECTS: returns daily totals of each day in tracker
    //NOTE: list contains duplicates, and null portions
    private ArrayList<Portion> getDailyTotals() {
        ArrayList<Portion> totalsPerDay = new ArrayList<>();
        ArrayList<Portion> dailyTotal = new ArrayList<>();
        String date;
        for (Meal meal : tracker) {
            date = meal.getDateString();
            for (Meal m : tracker) {
                if (m.getDateString().equals(date)) {
                    totalsPerDay.add(m.getTotal());
                }
            }
            dailyTotal.add(getDayTotal(totalsPerDay, date));
            totalsPerDay.clear();
        }
        return dailyTotal;
    }

    //EFFECTS: returns single day total from given meal totals that day
    private Portion getDayTotal(ArrayList<Portion> totalsPerDay, String date) {
        try {
            return (new Recipe(totalsPerDay, date)).getTotal();
        } catch (InvalidInputException e) {
            return null;
        }
    }

}
