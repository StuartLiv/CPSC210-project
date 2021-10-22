package ui;

import model.*;
import model.exceptions.InvalidInputException;
import model.exceptions.NoIngredientsException;
import persistence.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
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

    //MODIFIES: this
    //EFFECTS: processes user input
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
            maintainSorted();
        }

        System.out.println("Would you like to save the application?");
        if (getYesNo()) {
            saveData();
        }
        System.out.println("\nGoodbye!");
    }


    //MODIFIES: this
    //EFFECTS: initializes account
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

    //EFFECTS: returns string name of selected user profile
    private String chooseProfile() {
        System.out.println("What user profile would you like to load?");
        return input.next().toLowerCase();
    }

    //MODIFIES: this
    //EFFECTS: initializes recipeBook, ingredientList, tracker
    // throws IOException if user is invalid
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

    //MODIFIES: this
    //EFFECTS: keeps data field lists in sorted order
    //Using object comparison from oracle docs:
    //Link: https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html#comparing-java.util.function.Function-
    private void maintainSorted() {
        ingredientList.sort(Comparator.comparing(Ingredient::getIngredientName));
        recipeBook.sort((Comparator.comparing(Recipe::getName)));
        tracker.sort((Comparator.comparing(Meal::getDateTimeString)));
    }

    //EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ti -> ingredients");
        System.out.println("\tr -> recipes");
        System.out.println("\tm -> meals");
        System.out.println("\ts -> summary stats");
        System.out.println("\tq -> quit");
    }

    //MODIFIES: this
    //EFFECTS: processes user command
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
                editIngredient();
                break;
            case "r":
                removeIngredient();
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
            try {
                addIngredient("a new ingredient");
            } catch (InvalidInputException e) {
                System.out.println("Entered ingredient was invalid");
            }
            System.out.print("Would you like to add another ingredient?");
            repeat = getYesNo();
        }
    }

    //MODIFIES: this
    //EFFECTS: adds entered ingredient to ingredientList
    // throws InvalidInputException if input is improperly formatted
    private void addIngredient(String ingredientID) throws InvalidInputException {
        try {
            System.out.println("Enter " + ingredientID + " in the tab separated form: \nName \t\t\tServing \tCalories "
                    + "\tProtein \tCarbs \tFat");
            ingredientList.add(new Ingredient(input.next().split("\t+")));
        } catch (NumberFormatException e) {
            throw new InvalidInputException();
        }
    }

    //MODIFIES: this
    //EFFECTS: replaces selected ingredient with new declaration
    private void editIngredient() {
        System.out.println("Enter the name of the ingredient to edit:");
        String name = input.next();
        Ingredient toEdit = findIngredient(name);
        if (findIngredient(name) == null) {
            System.out.println("That ingredient is not in the list");
            return;
        }
        System.out.println("Given the current declaration of " + name
                + ": \nName \t\t\tServing \tCalories\tProtein \tCarbs \tFat");
        printIngredient(toEdit);
        System.out.println("\n");
        try {
            addIngredient("your new declaration of " + name);
        } catch (InvalidInputException e) {
            System.out.println("Entered ingredient was invalid");
            return;
        }
        deleteIngredient(name);
    }

    //MODIFIES: this
    //EFFECTS: removes selected ingredient from ingredientList
    private void removeIngredient() {
        System.out.println("Enter the name of the ingredient to delete:");
        String name = input.next();
        Ingredient toDelete = findIngredient(name);
        if (toDelete == null) {
            System.out.println("That ingredient is not in the list");
            return;
        }
        System.out.println("Confirm you would like to delete " + name
                + ": \nName \t\t\tServing \tCalories\tProtein \tCarbs \tFat");
        printIngredient(toDelete);
        System.out.println();
        if (!getYesNo()) {
            return;
        }
        deleteIngredient(name);
    }

    //MODIFIES: this
    //EFFECTS: deletes ingredient of given name from ingredientList
    private void deleteIngredient(String name) {
        for (int i = 0; i < ingredientList.size(); i++) {
            if (ingredientList.get(i).getIngredientName().equals(name)) {
                ingredientList.remove(i);
                break;
            }
        }
    }

    //EFFECTS: prints ingredientList
    private void seeIngredients() {
        System.out.println("These are the current known ingredients (units in grams): \nName \t\t\tServing \tCalories"
                + "\tProtein \tCarbs \tFat");
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
                editRecipe();
                break;
            case "r":
                removeRecipe();
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
            try {
                System.out.println("What is the name of this recipe?");
                String recipeName = input.next();
                addRecipe(recipeName);
            } catch (Exception e) {
                System.out.println("Entered recipe was invalid");
            }
            System.out.print("Would you like to add another recipe?");
            repeat = getYesNo();
        }
    }

    //MODIFIES: this
    //EFFECTS: adds entered recipe to recipe book, if toSave == true then return null, or return recipe
    private Recipe addRecipe(String recipeName) throws Exception {
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

    //EFFECTS: converts recipeString into arrayList<Portion>
    // throws Exception if toPortion threw an exception
    private ArrayList<Portion> recipeProcess(ArrayList<String> recipeString) throws Exception {
        ArrayList<Portion> portions = new ArrayList<>();
        for (String portionString: recipeString) {
            portions.add(toPortion(portionString.split("\t+")));
        }
        return portions;
    }

    //EFFECTS: converts ingredient name and mass to portion
    // throws Exception if mass input is not a string casted integer, or unexpected Exception in new Portion call
    private Portion toPortion(String[] portionStrings) throws Exception {
        String name = portionStrings[0];
        Ingredient ingredient = findIngredient(name);
        while (ingredient == null) {
            try {
                System.out.println(name + " is not a recognized ingredient.");
                addIngredient(name);
                ingredient = findIngredient(name);
            } catch (InvalidInputException e) {
                System.out.println("Entered ingredient was invalid");
            }
        }
        int mass = Integer.parseInt(portionStrings[1]);
        return new Portion(ingredient, mass);
    }

    //MODIFIES: this
    //EFFECTS: replaces selected recipe with new declaration
    private void editRecipe() {
        System.out.println("Enter the name of the recipe to edit:");
        String name = input.next();
        Recipe toEdit = findRecipe(name);
        if (toEdit == null) {
            System.out.println("That recipe is not in the list");
            return;
        }
        System.out.println("Given the current declaration of " + name + ":\n");
        printRecipe(toEdit);
        System.out.println("\nEnter your new declaration of " + name);
        try {
            addRecipe(name);
            deleteRecipe(name);
        } catch (Exception e) {
            System.out.println("Entered recipe was invalid");
        }
    }

    //MODIFIES: this
    //EFFECTS: removes selected recipe from recipeBook
    private void removeRecipe() {
        System.out.println("Enter the name of the recipe to delete:");
        String name = input.next();
        Recipe toDelete = findRecipe(name);
        if (toDelete == null) {
            System.out.println("That recipe is not in the list");
            return;
        }
        System.out.println("Confirm you would like to delete ");
        System.out.println(name + ":");
        printRecipe(toDelete);
        System.out.println();
        if (!getYesNo()) {
            return;
        }
        deleteRecipe(name);
    }

    //MODIFIES: this
    //EFFECTS: deletes toDelete from recipeBook
    private void deleteRecipe(String name) {
        for (int i = 0; i < recipeBook.size(); i++) {
            if (recipeBook.get(i).getName().equals(name)) {
                recipeBook.remove(i);
                break;
            }
        }
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
                editMeal();
                break;
            case "r":
                removeMeal();
                break;
            case "s":
                System.out.println("These are your stored meals:\n");
                seeMeals(tracker, "", false);
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
            try {
                addMeal();
            } catch (Exception e) {
                System.out.println("Entered Meal was invalid");
            }
            System.out.print("Would you like to add another meal? ");
            repeat = getYesNo();
        }
    }

    //MODIFIES: this
    //EFFECTS: adds a meal to tracker
    // throws Exception if mass and time fields are not string casted integers, or unexpected Exception in new Meal call
    private void addMeal() throws Exception {
        String recipeStyle = getRecipeStyle();
        Recipe mealRecipe;
        if (recipeStyle.equals("k")) {
            mealRecipe = knownRecipeMeal();
        } else {
            mealRecipe = newRecipeMeal();
        }
        if (mealRecipe == null) {
            System.out.println("Invalid recipe");
            return;
        }
        System.out.println("What was the mass of your meal");
        int mass = input.nextInt();
        System.out.println("What date did you eat this meal? Enter in YYYY:MM:DD form, or \"today\" if it was today");
        String date = input.next();
        System.out.println("What time did you eat this meal? Enter the time in HH:MM form");
        String time = input.next();
        tracker.add(new Meal(mealRecipe, mass, date, time));
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
        try {
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
        } catch (Exception e) {
            System.out.println("Entered recipe was invalid");
            return null;
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

    //MODIFIES: this
    //EFFECTS: edits selected meal in tracker
    private void editMeal() {
        try {
            Meal toEdit = selectMeal("edit");
            if (toEdit == null) {
                return;
            }
            System.out.println("Given the current declaration of this meal:\n");
            System.out.println("Name \t\t\tServing \tCalories\tProtein \tCarbs \tFat\t\tMeal Mass\tDate\t\tMeal Time");
            printMeal(toEdit);
            addMeal();
            tracker.remove(toEdit);
        } catch (Exception e) {
            System.out.println("Invalid edit choice");
        }
    }

    //MODIFIES: this
    //EFFECTS: removes selected meal in tracker
    private void removeMeal() {
        try {
            Meal toDelete = selectMeal("remove");
            if (toDelete == null) {
                return;
            }
            System.out.println("Confirm you would like to delete:\n");
            System.out.println("Name \t\t\tServing \tCalories\tProtein \tCarbs \tFat\t\tMeal Mass\tDate\t\tMeal Time");
            printMeal(toDelete);
            System.out.println();
            getYesNo();
            tracker.remove(toDelete);
        } catch (Exception e) {
            System.out.println("Invalid index choice");
        }
    }

    //EFFECTS: lets user select a meal in tracker and returns it
    private Meal selectMeal(String callerID) {
        System.out.println("Enter the date the meal to " + callerID + " was eaten on:");
        ArrayList<Meal> mealsOnDate = new ArrayList<>();
        String date = input.next();
        for (Meal m : tracker) {
            if (m.getDateString().equals(date)) {
                mealsOnDate.add(m);
            }
        }
        if (mealsOnDate.size() == 0) {
            System.out.println("No stored meals on that date");
            return null;
        }
        System.out.println("Available Meals to select");
        seeMeals(mealsOnDate, "Index", true);
        System.out.println("Enter the index of the meal to " + callerID);
        return mealsOnDate.get(input.nextInt() - 1);
    }

    //EFFECTS: renders tracker
    private void seeMeals(ArrayList<Meal> mealList, String lineEnd, boolean printIndex) {
        System.out.println("Name \t\t\tServing \tCalories\tProtein \tCarbs \tFat\t\tMeal Mass\tDate\t\tMeal Time \t\t"
                + lineEnd + "\n");
        for (Meal m: mealList) {
            printMeal(m);
            if (printIndex) {
                System.out.println("\t\t" + mealList.indexOf(m) + 1);
            } else {
                System.out.println();
            }
        }
    }

    //EFFECTS: prints a meal
    private void printMeal(Meal meal) {
        printIngredient(meal.getTotal().getIngredient());
        System.out.print("\t\t" + meal.getMass() + "\t\t\t" + meal.getDateString() + "\t" + meal.getTimeString());

    }

    //EFFECTS: prints summary and daily nutritional info
    private void doStats() {
        System.out.println("Total Daily Nutrition\n");
        System.out.println("Date \t\t\tServing \tCalories\tProtein \tCarbs \tFat");
        ArrayList<Portion> dailyTotals = formatTotals(getDailyTotals());
        if (dailyTotals.isEmpty()) {
            System.out.println("No meals to show statistics on");
            return;
        }
        for (Portion p : dailyTotals) {
            printIngredient(p.getIngredient());
            System.out.println();
        }
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
        } catch (NoIngredientsException e) {
            return null;
        }
    }
}
