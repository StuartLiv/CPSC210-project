package ui;

import model.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Locale;

public class GraphicalUI extends AbstractUI {
    private JFrame frame;

    //EFFECTS: runs tracker
    //Close frame call used from Stack Overflow Comment:
    //Link: https://stackoverflow.com/a/1235994
    public GraphicalUI() {
        super();
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
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
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    protected String chooseProfile() {
        return JOptionPane.showInputDialog("What user profile would you like to load?");
    }

    protected void saveState() {
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
    protected void doCommand() {
        JButton[] buttons = new JButton[5];
        JPanel panel = new JPanel(new GridLayout(6,1));
        panel.add(new JLabel("Select what you would like to do:"));
        String[] b = new String[]{"Ingredients", "Recipes", "Meals", "Statistics", "Quit"};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(new ButtonAction(b[i], this));
            buttons[i].setSize(80, 80);
            panel.add(buttons[i]);
        }
        frame.add(panel);
        frame.setVisible(true);
    }

    //EFFECTS: shows pop up window for actions
    protected String getAction() {
        String[] possibleValues = {"Add", "Edit", "Delete", "Print"};
        int selectedValue = JOptionPane.showOptionDialog(frame, "Select an action", "Action Select",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, possibleValues, possibleValues[0]);
        return possibleValues[selectedValue].toLowerCase(Locale.ROOT).substring(0, 1);
    }


    protected void addIngredients() {

    }

    protected void editIngredient() {

    }

    protected void removeIngredient() {

    }

    protected void showIngredients() {

    }

    protected void addRecipes() {

    }

    protected void editRecipe() {

    }

    protected void removeRecipe() {

    }

    protected void showRecipes() {

    }

    protected void addMeals() {

    }

    protected void editMeal() {

    }

    protected void removeMeal() {

    }

    protected void showMeals() {

    }

    protected void doStats() {

    }
}
