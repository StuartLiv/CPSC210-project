package model;

//Represents a meal with recipe, summary total, serving size, and time of meal in hours
public class Meal {
    private Recipe recipe;
    private Portion total;
    private int serving;
    private int time;
    //Implement more complex field for time in phase 2

    //REQUIRES: 0 <= time <= 23
    //MODIFIES: this
    //EFFECTS: fields are set, and total nutrition for meal is calculated
    public Meal(Recipe recipe, int mass, int time) {
        this.recipe = recipe;
        this.serving = mass;
        this.time = time;
        this.total = recipe.getTotal();
        this.total = getScaledTotal();
    }

    //EFFECTS: returns Recipe
    public Recipe getRecipe() {
        return recipe;
    }

    //Functionality will be expanded in phase 2
    //EFFECTS: returns time
    public int getTime() {
        return time;
    }

    //EFFECTS: returns nutritional Total
    public Portion getTotal() {
        return total;
    }

    //EFFECTS: returns nutritional total scaled to this.serving
    public Portion getScaledTotal() {
        double factor = (double) serving / recipe.massTotal();
        total.scaleIngredient(factor);
        return total;
    }

    //EFFECTS: returns mass of meal
    public int getMass() {
        return serving;
    }

    //More methods to come, for ui integration
}
