package model;

//Represents a measured quantity of a food
public class Portion  {
    private Ingredient ingredient;
    private int mass;
    private double factor;

    //REQUIRES: mass >= 0, ingredient is initialized
    //MODIFIES: this
    //EFFECTS: parameters are assigned to fields of same name, and a conversion factor is calculated
    public Portion(Ingredient ingredient, int mass) {
        this.ingredient = ingredient;
        this.mass = mass;
        this.factor = getConversion();
    }

    //EFFECTS: returns ingredient
    public Ingredient getIngredient() {
        return ingredient;
    }

    //EFFECTS: returns the conversion factor for the nutrition statistics of portion mass m of ingredient.servingSize
    public double getConversion() {
        return (double) mass / (double) ingredient.getServingSize();
    }

    public int convert(int toConvert) {
        return (int) Math.round(toConvert * factor);
    }

    //MODIFIES: this.ingredient
    //EFFECTS: scales ingredient to make servingSize = this.mass
    public void scaleIngredient() {
        ingredient = new Ingredient(ingredient.getIngredientName(), mass,
                convert(ingredient.getCalories()), convert(ingredient.getProtein()),
                convert(ingredient.getCarbs()), convert(ingredient.getFat()));
    }
}
