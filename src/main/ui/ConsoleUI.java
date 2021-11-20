package ui;

import model.*;
import model.exceptions.InvalidInputException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//Console UI for TrackerApp

//ConsoleUI, showMenu, getAction modeled after the sample project TellerApp
//Link: https://github.students.cs.ubc.ca/CPSC210/TellerApp
public class ConsoleUI  extends AbstractUI {
    private Scanner input;

    //EFFECTS: runs the tracker application
    public ConsoleUI() {
        super();
    }

    //MODIFIES: this
    //EFFECTS: initializes account
    protected void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        source = "./data/" + chooseProfile() + "/";
        try {
            profile = new Profile(source);
        } catch (IOException | RuntimeException e) {
            System.out.println("Invalid profile choice");
            init();
        }
    }

    //EFFECTS: returns string name of selected user profile
    protected String chooseProfile() {
        System.out.println("What user profile would you like to load?");
        return input.next().toLowerCase();
    }

    //EFFECTS: returns selected menu option
    protected String getCommand() {
        return input.next().toLowerCase();
    }

    @Override
    //EFFECTS: stores data, if user chooses
    protected void saveState() {
        System.out.println("Would you like to save the application?");
        if (getYesNo()) {
            try {
                saveData();
            } catch (IOException e) {
                System.out.println("Unexpected file name error, data could not be saved");
            }
        }
        System.out.println("\nGoodbye!");
    }

    //EFFECTS: shows menu, gets command and handles command
    protected void doCommand() {
        showMenu();
        String command = getCommand();

        if (command.equals("q")) {
            setKeepGoingFalse();
        } else {
            processCommand(command);
        }
    }


    //EFFECTS: displays menu of options to user
    protected void showMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ti -> ingredients");
        System.out.println("\tr -> recipes");
        System.out.println("\tm -> meals");
        System.out.println("\ts -> summary stats");
        System.out.println("\tq -> quit");
    }

    //EFFECTS: returns action from add, edit, remove, see
    protected String getAction() {
        String selection = "";
        System.out.println("\nWhat would you like to do?");
        while (!(selection.equals("a") || selection.equals("e") || selection.equals("d") || selection.equals("p"))) {
            System.out.println("\ta -> add");
            System.out.println("\te -> edit");
            System.out.println("\td -> delete");
            System.out.println("\tp -> print");
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
    //EFFECTS: looping function for addIngredient: adds some number of ingredients to the list
    protected void addIngredients() {
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
    //EFFECTS: adds entered ingredient to profile
    // throws InvalidInputException if input is improperly formatted
    private void addIngredient(String ingredientID) throws InvalidInputException {
        try {
            System.out.println("Enter " + ingredientID + " in the tab separated form: \nName \t\t\tServing \tCalories "
                    + "\tProtein \tCarbs \tFat");
            profile.addIngredient(new Ingredient(input.next().split("\t+")));
        } catch (NumberFormatException e) {
            throw new InvalidInputException();
        }
    }

    //MODIFIES: this
    //EFFECTS: replaces selected ingredient with new declaration
    protected void editIngredient() {
        System.out.println("Enter the name of the ingredient to edit:");
        String name = input.next();
        Ingredient toEdit = profile.findIngredient(name);
        if (toEdit == null) {
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
        profile.deleteIngredient(name);
    }

    //MODIFIES: this
    //EFFECTS: removes selected ingredient from ingredientList
    protected void removeIngredient() {
        System.out.println("Enter the name of the ingredient to delete:");
        String name = input.next();
        Ingredient toDelete = profile.findIngredient(name);
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
        profile.deleteIngredient(name);
    }

    //EFFECTS: prints ingredientList
    protected void showIngredients() {
        System.out.println("These are the current known ingredients (units in grams): \nName \t\t\tServing \tCalories"
                + "\tProtein \tCarbs \tFat");
        for (Ingredient ingredient: profile.getIngredientList()) {
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

    //MODIFIES: this
    //EFFECTS: looping function for addRecipe: adds some number of recipes to the list
    protected void addRecipes() {
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
            profile.addRecipe(newRecipe);
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
        Ingredient ingredient = profile.findIngredient(name);
        while (ingredient == null) {
            try {
                System.out.println(name + " is not a recognized ingredient.");
                addIngredient(name);
                ingredient = profile.findIngredient(name);
            } catch (InvalidInputException e) {
                System.out.println("Entered ingredient was invalid");
            }
        }
        int mass = Integer.parseInt(portionStrings[1]);
        return new Portion(ingredient, mass);
    }

    //MODIFIES: this
    //EFFECTS: replaces selected recipe with new declaration
    protected void editRecipe() {
        System.out.println("Enter the name of the recipe to edit:");
        String name = input.next();
        Recipe toEdit = profile.findRecipe(name);
        if (toEdit == null) {
            System.out.println("That recipe is not in the list");
            return;
        }
        System.out.println("Given the current declaration of " + name + ":\n");
        printRecipe(toEdit);
        System.out.println("\nEnter your new declaration of " + name);
        try {
            addRecipe(name);
            profile.deleteRecipe(name);
        } catch (Exception e) {
            System.out.println("Entered recipe was invalid");
        }
    }

    //MODIFIES: this
    //EFFECTS: removes selected recipe from recipeBook
    protected void removeRecipe() {
        System.out.println("Enter the name of the recipe to delete:");
        String name = input.next();
        Recipe toDelete = profile.findRecipe(name);
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
        profile.deleteRecipe(name);
    }

    //EFFECTS: prints recipeBook
    protected void showRecipes() {
        System.out.println("These are your stored recipes:\n");
        for (Recipe r: profile.getRecipeBook()) {
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
        try {
            printPortion(recipe.getTotal());
        } catch (InvalidInputException e) {
            System.out.println("Recipe has invalid ingredients");
        }
    }

    //EFFECTS: prints a portion
    private void printPortion(Portion portion) {
        printIngredient(portion.getIngredient());
        System.out.println("\t\t" + portion.getMass());
    }

    //MODIFIES: this
    //EFFECTS: looping function for addMeal: adds a certain amount of meals
    protected void addMeals() {
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
        profile.addMeal(new Meal(mealRecipe, mass, date, time));
    }

    //EFFECTS: finds recipe of ui input name and return it
    private Recipe knownRecipeMeal() {
        String recipeName;
        System.out.println("What is the recipe name?");
        recipeName = input.next();
        return profile.findRecipe(recipeName);
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
                return profile.findRecipe(recipeName);
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
    protected void editMeal() {
        try {
            Meal toEdit = selectMeal("edit");
            if (toEdit == null) {
                return;
            }
            System.out.println("Given the current declaration of this meal:\n");
            System.out.println("Name \t\t\tServing \tCalories\tProtein \tCarbs \tFat\t\tMeal Mass\tDate\t\tMeal Time");
            printMeal(toEdit);
            addMeal();
            profile.deleteMeal(toEdit);
        } catch (Exception e) {
            System.out.println("Invalid edit choice");
        }
    }

    //MODIFIES: this
    //EFFECTS: removes selected meal in tracker
    protected void removeMeal() {
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
            profile.deleteMeal(toDelete);
        } catch (Exception e) {
            System.out.println("Invalid index choice");
        }
    }

    //EFFECTS: lets user select a meal in tracker and returns it
    private Meal selectMeal(String callerID) {
        System.out.println("Enter the date the meal to " + callerID + " was eaten on:");
        ArrayList<Meal> mealsOnDate = new ArrayList<>();
        String date = input.next();
        for (Meal m : profile.getTracker()) {
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

    //EFFECTS: prints meals
    protected void showMeals() {
        System.out.println("These are your stored meals:\n");
        seeMeals(profile.getTracker(), "", false);
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
    protected void doStats() {
        System.out.println("Total Daily Nutrition\n");
        System.out.println("Date \t\t\tServing \tCalories\tProtein \tCarbs \tFat");
        ArrayList<Portion> dailyTotals = getFormattedTotals();
        if (dailyTotals.isEmpty()) {
            System.out.println("No meals to show statistics on");
            return;
        }
        for (Portion p : dailyTotals) {
            printIngredient(p.getIngredient());
            System.out.println();
        }
    }
}
