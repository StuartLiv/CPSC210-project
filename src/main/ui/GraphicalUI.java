package ui;

import model.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class GraphicalUI extends AbstractUI {
    private JFrame frame;

    //EFFECTS: runs tracker
    public GraphicalUI() {
        super();
    }

    //MODIFIES: this
    //EFFECTS: initializes gui
    protected void init() {
        frame = new JFrame("Macronutrient Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
        source = "./data/" + chooseProfile() + "/";
        try {
            profile = new Profile(source);
        } catch (IOException | RuntimeException e) {
            init();
        }
    }

    protected String chooseProfile() {
        return "stuart";
        //TODO
    }

    protected void saveState() {

    }

    //EFFECTS: shows command window, and sets up button listeners
    //button list initializer inspired by alphabetical selector
    //Link: https://www.roseindia.net/java/example/java/swing/create_multiple_buttons_using_ja.shtml
    protected void doCommand() {
        JButton[] buttons = new JButton[4];
        JPanel panel = new JPanel(new GridLayout(5,1));
        panel.add(new JLabel("Select what you would like to do:"));
        String[] b = new String[]{"Ingredients", "Recipes", "Meals", "Statistics"};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(new ButtonAction(b[i], this));
            buttons[i].setSize(80, 80);
            panel.add(buttons[i]);
        }
        frame.add(panel);
        frame.setVisible(true);
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
