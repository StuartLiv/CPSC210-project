package ui;

import model.*;
import persistence.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//Calorie and Macronutrient tracker app

//TrackerApp, runTracker, processCommand, displayMenu getAction modeled after the sample project TellerApp
//Link: https://github.students.cs.ubc.ca/CPSC210/TellerApp
//NOTE: some input handlers are non-robust, this will be improved once construction materials are covered in course
public class TrackerApp {
    private ArrayList<Ingredient> ingredientList;
    private ArrayList<Recipe> recipeBook;
    private ArrayList<Meal> tracker;


    private Scanner input;
    private boolean keepGoing = true;
    private String source;

    //EFFECTS: runs the tracker application
    public TrackerApp() {
        runTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTracker() {
        String command;
        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
            //maintainSorted(); future data persistence method
        }

        System.out.println("Would you like to save the application?");
        if (getYesNo()) {
            saveData();
        }
        System.out.println("\nGoodbye!");
    }


    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");

        try {
            loadData(chooseProfile());
        } catch (IOException e) {
            System.out.println("Invalid profile choice");
            init();
        }
    }

    // EFFECTS: returns string name of selected user profile
    private String chooseProfile() {
        System.out.println("What user profile would you like to load?");
        return input.next().toLowerCase();
    }

    //MODIFIES: this
    //EFFECTS: initializes recipeBook, ingredientList, tracker
    private void loadData(String user) throws IOException {
        source = "./data/" + user + "/";
        ingredientList = new JsonReaderIngredient(source + "ingredients.json").readIngredient();
        recipeBook = new JsonReaderRecipe(source + "recipeBook.json").readRecipe();
        tracker = new JsonReaderMeal(source + "tracker.json").readMeal();
    }

    //EFFECTS: stores recipeBook, ingredientList, tracker
    private void saveData() {
        try {
            JsonWriterIngredient writerIngredient = new JsonWriterIngredient(source + "ingredients.json");
            writerIngredient.open();
            writerIngredient.writeIngredients(ingredientList);
            writerIngredient.close();

            JsonWriterRecipe writerRecipe = new JsonWriterRecipe(source + "recipeBook.json");
            writerRecipe.open();
            writerRecipe.writeRecipes(recipeBook);
            writerRecipe.close();

            JsonWriterMeal writerMeal = new JsonWriterMeal(source + "tracker.json");
            writerMeal.open();
            writerMeal.writeMeals(tracker);
            writerMeal.close();
        } catch (IOException e) {
            System.out.println("Unexpected file name error, data could not be saved");
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ti -> ingredients");
        System.out.println("\tr -> recipes");
        System.out.println("\tm -> meals");
        System.out.println("\ts -> summary stats");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
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
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    //EFFECTS: returns action from add, edit, remove, see
    //NOTE: edit and remove will be implemented in phase 2 data persistence
    private String getAction() {
        String selection = "";
        System.out.println("\nWhat would you like to do?");
        while (!(selection.equals("a") || selection.equals("e") || selection.equals("r") || selection.equals("s"))) {
            System.out.println("\ta -> add");
            System.out.println("\te -> edit");
            System.out.println("\tr -> remove");
            System.out.println("\ts -> see");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        return selection;
    }

    //EFFECTS: returns user input to yes or no question
    private boolean getYesNo() {
        String selection = "";
        while (!(selection.equals("y") || selection.equals("n"))) {
            System.out.println("Enter y/n");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        return selection.equals("y");
    }

    //MODIFIES: this
    //EFFECTS: add, read, edit, remove ingredients
    private void doIngredient(String action) {
        switch (action) {
            case "a":
                addIngredients();
                break;
            case "e":
            case "r":
                System.out.println("This feature will be implemented in Phase 2");
                break;
            case "s":
                seeIngredients();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: looping function for addIngredient: adds some number of ingredients to the list
    private void addIngredients() {
        boolean repeat = true;
        while (repeat) {
            addIngredient("a new ingredient");
            System.out.print("Would you like to add another ingredient?");
            repeat = getYesNo();
        }
    }

    //MODIFIES: this
    //EFFECTS: adds entered ingredient to ingredientList
    //Method is non-robust, will be improved with exception throwing in phase 2
    private void addIngredient(String ingredientID) {
        System.out.println("Enter " + ingredientID + " in the tab separated form: \nName \t\t\tServing \tCalories "
                + "\tProtein \tCarbs \tFat");
        ingredientList.add(new Ingredient(input.next().split("\t+")));
    }

    //EFFECTS: prints ingredientList
    private void seeIngredients() {
        System.out.println(("These are the current known ingredients (units in grams): \nName \t\t\tServing \tCalories"
                + "\tProtein \tCarbs \tFat"));
        for (Ingredient ingredient: ingredientList) {
            printIngredient(ingredient);
            System.out.println();
        }
    }

    //EFFECTS: prints ingredient in standard form
    private void printIngredient(Ingredient ingredient) {
        ArrayList<String> fields = ingredient.getFields();
        formattedIngredientField(fields.get(0), 16);
        formattedIngredientField(fields.get(1), 12);
        formattedIngredientField(fields.get(2), 12);
        System.out.print(fields.get(3) + "\t\t\t");
        System.out.print(fields.get(4) + "\t\t");
        System.out.print(fields.get(5));
    }

    //EFFECTS: prints formatted ingredient name
    private void formattedIngredientField(String name, int dist) {
        int nameLength = name.length();
        double tabsNeeded = Math.ceil(((double) (dist - nameLength) / 4));
        System.out.print(name);
        for (int i = 0; i < tabsNeeded; i++) {
            System.out.print("\t");
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


    //MODIFIES: this
    //EFFECTS: add, read, edit, remove ingredients
    private void doRecipe(String action) {
        switch (action) {
            case "a":
                addRecipes();
                break;
            case "e":
            case "r":
                System.out.println("This feature will be implemented in Phase 2");
                break;
            case "s":
                seeRecipes();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: looping function for addRecipe: adds some number of recipes to the list
    private void addRecipes() {
        boolean repeat = true;
        while (repeat) {
            System.out.println("What is the name of this recipe?");
            String recipeName = input.next();
            addRecipe(recipeName);
            System.out.print("Would you like to add another recipe?");
            repeat = getYesNo();
        }
    }

    //MODIFIES: this
    //EFFECTS: adds entered recipe to recipe book, if toSave == true and return null, or return recipe
    private Recipe addRecipe(String recipeName) {
        ArrayList<String> recipeString = recipeInput();
        ArrayList<Portion> ingredients = recipeProcess(recipeString);
        Recipe newRecipe = new Recipe(ingredients, recipeName);
        if (newRecipe.getToSave()) {
            recipeBook.add(newRecipe);
            return null;
        } else {
            return newRecipe;
        }
    }

    //EFFECTS: gets input for a recipe
    private ArrayList<String> recipeInput() {
        ArrayList<String> recipeString = new ArrayList<>();
        recipeString.add("");
        System.out.println("Enter the recipe ingredients in the tab separated form, and Done when you are done:"
                + "\nIngredient Name \tMass of Ingredient");
        while (!(recipeString.get(recipeString.size() - 1).equals("Done"))) {
            recipeString.add(input.next());
        }
        recipeString.remove(recipeString.size() - 1);
        recipeString.remove(0);
        return recipeString;
    }

    //REQUIRES: recipeString elements are properly formatted, as toPortion call is not robust
    //EFFECTS: converts recipeString into arrayList<Portion>
    private ArrayList<Portion> recipeProcess(ArrayList<String> recipeString) {
        ArrayList<Portion> portions = new ArrayList<>();
        for (String portionString: recipeString) {
            portions.add(toPortion(portionString.split("\t+")));
        }
        return portions;
    }

    //REQUIRES: portionStrings[1] is a string casted int
    //EFFECTS: converts ingredient name and mass to portion
    private Portion toPortion(String[] portionStrings) {
        String name = portionStrings[0];
        int mass = Integer.parseInt(portionStrings[1]);
        Ingredient ingredient = findIngredient(name);
        if (ingredient == null) {
            System.out.println(name + " is not a recognized ingredient.");
            addIngredient(name);
            ingredient = findIngredient(name);
        }
        return new Portion(ingredient, mass);
    }

    //EFFECTS: prints recipeBook
    private void seeRecipes() {
        System.out.println("These are your stored recipes:\n");
        for (Recipe r: recipeBook) {
            System.out.println(r.getName() + ":");
            printRecipe(r);
            System.out.println();
        }
    }

    //EFFECTS: prints a formatted recipe
    private void printRecipe(Recipe recipe) {
        System.out.println("Name \t\t\tServing \tCalories\tProtein \tCarbs \tFat\t\tIngredient Mass");
        for (Portion p: recipe.getIngredients()) {
            printPortion(p);
        }
        System.out.println();
        printPortion(recipe.getTotal());
    }

    //EFFECTS: prints a portion
    private void printPortion(Portion portion) {
        printIngredient(portion.getIngredient());
        System.out.println("\t\t" + portion.getMass());
    }

    //EFFECTS: returns recipe with search name or null if not found
    //Method is not robust, handler must be robust
    private Recipe findRecipe(String name) {
        for (Recipe r: recipeBook) {
            if (name.equals(r.getName())) {
                return r;
            }
        }
        return null;
    }

    //MODIFIES: this
    //EFFECTS: adds, edits, removes and renders meals
    private void doMeal(String action) {
        switch (action) {
            case "a":
                addMeals();
                break;
            case "e":
            case "r":
                System.out.println("This feature will be implemented in Phase 2");
                break;
            case "s":
                seeMeals();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: looping function for addMeal: adds a certain amount of meals
    private void addMeals() {
        boolean repeat = true;
        while (repeat) {
            addMeal();
            System.out.print("Would you like to add another meal?");
            repeat = getYesNo();
        }
    }

    //MODIFIES: this
    //EFFECTS: adds a meal to tracker
    //input handlers are non-robust, improved functionality in phase 2
    private void addMeal() {
        String recipeStyle = getRecipeStyle();
        Recipe mealRecipe;
        if (recipeStyle.equals("k")) {
            mealRecipe = knownRecipeMeal();
            if (mealRecipe == null) {
                System.out.println("Sorry, I don't recognize that recipe");
                return;
            }
        } else {
            mealRecipe = newRecipeMeal();
        }
        System.out.println("What was the mass of your meal");
        int mass = Integer.parseInt(input.next());
        System.out.println("What time did you eat this meal? Enter the hour in 24 hour form");
        int time = Integer.parseInt(input.next());
        tracker.add(new Meal(mealRecipe, mass, time));
    }

    //EFFECTS: finds recipe of ui input name and return it
    private Recipe knownRecipeMeal() {
        String recipeName;
        System.out.println("What is the recipe name?");
        recipeName = input.next();
        return findRecipe(recipeName);
    }

    //EFFECTS: takes new user recipe input and saves, or not
    private Recipe newRecipeMeal() {
        String recipeName;
        System.out.print("Would you like to save the new recipe?");
        if (getYesNo()) {
            System.out.println("What is the recipe name?");
            recipeName = input.next();
            addRecipe(recipeName);
            return findRecipe(recipeName);
        } else {
            return addRecipe("Unsaved Recipe");
        }
    }

    //EFFECTS: returns type of recipe to add as meal
    private String getRecipeStyle() {
        String selection = "";
        System.out.println("\nWhat type of meal would you like to add?");
        while (!(selection.equals("k") || selection.equals("n"))) {
            System.out.println("\tk -> known recipe");
            System.out.println("\tn -> new recipe");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        return selection;
    }

    //EFFECTS: renders tracker
    private void seeMeals() {
        System.out.println("These are your stored meals:\n");
        System.out.println("Name \t\t\tServing \tCalories\tProtein \tCarbs \tFat\t\tMeal Mass\tMeal Time\n");
        for (Meal m: tracker) {
            printMeal(m);
        }
    }

    //EFFECTS: prints a meal
    private void printMeal(Meal meal) {
        printIngredient(meal.getTotal().getIngredient());
        System.out.println("\t\t" + meal.getMass() + "\t\t\t" + meal.getTime());

    }

    //EFFECTS: prints summary and daily nutritional info
    //NOTE: improved functionality in phase 2, with improved time implementation
    private void doStats() {
        String date = "Oct 13, 2021"; //update in phase 2
        System.out.println("Total Daily Nutrition\n");
        System.out.println("Date \t\t\tServing \tCalories\tProtein \tCarbs \tFat");
        ArrayList<Portion> mealPortions = new ArrayList<>();
        for (Meal m : tracker) {
            mealPortions.add(m.getTotal());
        }
        Recipe dailyTotal = new Recipe(mealPortions, date);
        printIngredient(dailyTotal.getTotal().getIngredient());
        System.out.println();
    }
}
