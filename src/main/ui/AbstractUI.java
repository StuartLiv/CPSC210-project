package ui;

import model.*;
import model.exceptions.InvalidInputException;
import persistence.JsonWriterIngredient;
import persistence.JsonWriterMeal;
import persistence.JsonWriterRecipe;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;

//Abstract ui class, to decrease coupling in ConsoleUi and GraphicalUI

//AbstractUI, runUI, processCommand modeled after the sample project TellerApp
//Link: https://github.students.cs.ubc.ca/CPSC210/TellerApp
public abstract class AbstractUI {
    protected Profile profile;
    protected String source;
    protected boolean keepGoing;

    //EFFECTS: runs UI
    public AbstractUI() {
        runUI();
    }

    //MODIFIES: this
    //EFFECTS: runs the ui, and program
    protected void runUI() {
        init();
        keepGoing = true;

        while (keepGoing) {
            doCommand();
            maintainSorted();
        }
        saveState();
        printLog();
    }

    //MODIFIES: keepGoing
    //EFFECTS: sets keepGoing to false
    public void setKeepGoingFalse() {
        this.keepGoing = false;
    }

    //MODIFIES: this
    //EFFECTS: initializes account
    protected abstract void init();

    protected abstract String chooseProfile();

    //EFFECTS: saves user state if user chooses to
    protected abstract void saveState();

    //EFFECTS: selects and does user command
    protected abstract void doCommand();

    //MODIFIES: this
    //EFFECTS: keeps data field lists in sorted order
    //Using object comparison from oracle docs:
    //Link: https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html#comparing-java.util.function.Function-
    public void maintainSorted() {
        profile.getIngredientList().sort(Comparator.comparing(Ingredient::getIngredientName));
        profile.getRecipeBook().sort((Comparator.comparing(Recipe::getName)));
        profile.getTracker().sort((Comparator.comparing(Meal::getDateTimeString)));
    }

    //EFFECTS: stores recipeBook, ingredientList, tracker
    protected void saveData() throws FileNotFoundException {
        JsonWriterIngredient writerIngredient = new JsonWriterIngredient(source + "ingredients.json");
        writerIngredient.open();
        writerIngredient.writeIngredients(profile.getIngredientList());
        writerIngredient.close();

        JsonWriterRecipe writerRecipe = new JsonWriterRecipe(source + "recipeBook.json");
        writerRecipe.open();
        writerRecipe.writeRecipes(profile.getRecipeBook());
        writerRecipe.close();

        JsonWriterMeal writerMeal = new JsonWriterMeal(source + "tracker.json");
        writerMeal.open();
        writerMeal.writeMeals(profile.getTracker());
        writerMeal.close();
    }

    //MODIFIES: this
    //EFFECTS: processes user command
    public void processCommand(String command) {
        switch (command) {
            case "i":
                doIngredient(getAction());
                break;
            case "r":
                doRecipe(getAction());
                break;
            case "m":
                doMeal(getAction());
                break;
            case "s":
                doStats();
                break;
        }
    }

    //EFFECTS: returns "a", "e", "r" or "s" from user input
    protected abstract String getAction();

    //MODIFIES: this
    //EFFECTS: add, read, edit, remove ingredients
    private void doIngredient(String action) {
        switch (action) {
            case "a":
                addIngredients();
                break;
            case "e":
                editIngredient();
                break;
            case "d":
                removeIngredient();
                break;
            case "p":
                showIngredients();
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: adds ingredients to profile
    protected abstract void addIngredients();

    //MODIFIES: this
    //EFFECTS: edits ingredients in profile
    protected abstract void editIngredient();

    //MODIFIES: this
    //EFFECTS: removes ingredient from profile
    protected abstract void removeIngredient();

    //MODIFIES: this
    //EFFECTS: shows ingredients in profile
    protected abstract void showIngredients();

    //MODIFIES: this
    //EFFECTS: add, read, edit, remove ingredients
    private void doRecipe(String action) {
        switch (action) {
            case "a":
                addRecipes();
                break;
            case "e":
                editRecipe();
                break;
            case "d":
                removeRecipe();
                break;
            case "p":
                showRecipes();
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: adds recipes to profile
    protected abstract void addRecipes();

    //MODIFIES: this
    //EFFECTS: edits recipes in profile
    protected abstract void editRecipe();

    //MODIFIES: this
    //EFFECTS: removes recipe from profile
    protected abstract void removeRecipe();

    //MODIFIES: this
    //EFFECTS: shows recipes in profile
    protected abstract void showRecipes();

    //MODIFIES: this
    //EFFECTS: adds, edits, removes and renders meals
    private void doMeal(String action) {
        switch (action) {
            case "a":
                addMeals();
                break;
            case "e":
                editMeal();
                break;
            case "d":
                removeMeal();
                break;
            case "p":
                showMeals();
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: adds meals to profile
    protected abstract void addMeals();

    //MODIFIES: this
    //EFFECTS: edits meals in profile
    protected abstract void editMeal();

    //MODIFIES: this
    //EFFECTS: removes meal from profile
    protected abstract void removeMeal();

    //MODIFIES: this
    //EFFECTS: shows meals in profile
    protected abstract void showMeals();

    //EFFECTS: shows statistics about profile
    protected abstract void doStats();

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
        for (Meal meal : profile.getTracker()) {
            date = meal.getDateString();
            for (Meal m : profile.getTracker()) {
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

    //EFFECTS: prints EventLog to Screen
    protected void printLog() {
        EventLog el = EventLog.getInstance();
        for (Event e: el) {
            System.out.println(e.toString());
        }
    }
}
