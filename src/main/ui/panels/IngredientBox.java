package ui.panels;

import model.Ingredient;
import ui.GraphicalUI;

import javax.swing.*;
import java.util.ArrayList;

//JComboBox for AddRecipe
public class IngredientBox extends JComboBox<String> {

    //EFFECTS: creates JComboBox with ingredient names
    public IngredientBox(GraphicalUI ui) {
        super();
        addItems(ui.getProfile().getIngredientList());
    }

    //MODIFIES: this
    //EFFECTS: adds ingredients to combo box
    private void addItems(ArrayList<Ingredient> ingredientList) {
        for (Ingredient i: ingredientList) {
            addItem(i.getIngredientName());
        }
    }

}
