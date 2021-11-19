package ui.panels;

import model.Ingredient;
import model.Recipe;
import ui.GraphicalUI;

import javax.swing.*;
import java.util.ArrayList;

//JComboBox for addMeal
public class RecipeBox extends JComboBox<String> {

    //EFFECTS: creates JComboBox with ingredient names
    public RecipeBox(GraphicalUI ui) {
        super();
        addItems(ui.getProfile().getRecipeBook());
    }

    //MODIFIES: this
    //EFFECTS: adds ingredients to combo box
    private void addItems(ArrayList<Recipe> recipeBook) {
        for (Recipe r: recipeBook) {
            addItem(r.getName());
        }
    }
}
