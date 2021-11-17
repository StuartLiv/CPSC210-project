package ui;

import model.Ingredient;
import model.Profile;

import javax.swing.*;

import java.io.IOException;
import java.util.Locale;

import static java.lang.Thread.sleep;

public class GraphicalUI extends AbstractUI {
    JFrame frame;
    private MainPanel panel;

    //EFFECTS: runs tracker
    //Close frame call used from Stack Overflow Comment:
    //Link: https://stackoverflow.com/a/1235994
    public GraphicalUI() {
        init();
        doCommand();
    }

    //MODIFIES: this
    //EFFECTS: initializes gui
    protected void init() {
        source = "./data/" + chooseProfile() + "/";
        try {
            profile = new Profile(source);
        } catch (IOException | RuntimeException e) {
            init();
        }
        frame = new JFrame("Macronutrient Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        panel = new MainPanel(this);
        frame.add(panel);
        frame.setVisible(true);
    }

    //EFFECTS: uses pop up to get file source
    protected String chooseProfile() {
        return JOptionPane.showInputDialog("What user profile would you like to load?");
    }

    //EFFECTS: returns profile
    public Profile getProfile() {
        return profile;
    }

    //EFFECTS: returns frame
    public JFrame getFrame() {
        return frame;
    }

    //EFFECTS: saves data based on yes/no pop up
    public void saveState() {
        if (JOptionPane.showConfirmDialog(frame,
                "Would you like to save the application?", "Save application?", JOptionPane.YES_NO_OPTION) == 0) {
            try {
                saveData();
            } catch (IOException e) {
                System.out.println("Unexpected file name error, data could not be saved");
            }
        }
    }

    //EFFECTS: shows command window, and sets up button listeners
    //button list initializer inspired by alphabetical selector
    //Link: https://www.roseindia.net/java/example/java/swing/create_multiple_buttons_using_ja.shtml
    public void doCommand() {
        frame.revalidate();
        frame.repaint();
        panel.setPanel(this, "command");
    }

    //EFFECTS: shows pop up window for actions
    protected String getAction() {
        String[] possibleValues = {"Add", "Delete", "Print"};
        int selectedValue = JOptionPane.showOptionDialog(frame, "Select an action", "Action Select",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, possibleValues, possibleValues[0]);
        return possibleValues[selectedValue].toLowerCase(Locale.ROOT).substring(0, 1);
    }

    //MODIFIES: this
    //EFFECTS: addIngredients entered by user as JTextFields
    protected void addIngredients() {
        panel.setPanel(this, "add ingredient");
    }

    protected void removeIngredient() {
        Object[] ingredients = profile.getIngredientList().stream()
                .map(Ingredient::getIngredientName).toArray();
        String toDelete = (String) JOptionPane.showInputDialog(frame, "Choose an ingredient to delete",
                "Remove Ingredient", JOptionPane.INFORMATION_MESSAGE, null, ingredients, ingredients[0]);
        profile.deleteIngredient(toDelete);
    }

    protected void showIngredients() {
        panel.setPanel(this, "show ingredient");
    }

    protected void addRecipes() {

    }

    protected void removeRecipe() {

    }

    protected void showRecipes() {

    }

    protected void addMeals() {

    }

    protected void removeMeal() {

    }

    protected void showMeals() {

    }

    protected void doStats() {

    }

    protected void editIngredient() {
        //method is inactive
    }

    protected void editRecipe() {
        //method is inactive
    }

    protected void editMeal() {
        //method is inactive
    }
}
