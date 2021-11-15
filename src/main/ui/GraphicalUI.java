package ui;

import model.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Locale;

import static java.lang.Thread.sleep;

public class GraphicalUI extends AbstractUI {
    private JFrame frame;
    private JPanel panel;

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
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        panel = new JPanel(new CardLayout());
        frame.setVisible(true);
    }

    //EFFECTS: uses pop up to get file source
    protected String chooseProfile() {
        return JOptionPane.showInputDialog("What user profile would you like to load?");
    }

    //EFFECTS: saves data based on yes/no pop up
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
        JPanel commandPanel = new JPanel(new GridLayout(6,1));
        commandPanel.add(new JLabel("Select what you would like to do:", JLabel.CENTER));
        String[] b = new String[]{"Ingredients", "Recipes", "Meals", "Statistics", "Quit"};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(new ButtonAction(b[i], this));
            buttons[i].setSize(80, 200);
            commandPanel.add(buttons[i]);
        }
//        panel.removeAll();
//        panel.repaint();
//        panel.revalidate();

        panel.add(commandPanel);
        panel.repaint();
        panel.revalidate();
        frame.add(panel);
//        try {
//            sleep(100000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
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

        JPanel addIngredientPanel = new JPanel(new GridLayout());

        JLabel label = new JLabel("Enter ingredients in the tab separated form: \nName \t\t\tServing \tCalories "
                + "\tProtein \tCarbs \tFat", JLabel.CENTER);
        addIngredientPanel.add(label, Component.BOTTOM_ALIGNMENT);

        panel.removeAll();
        panel.repaint();
        panel.revalidate();
        panel.add(addIngredientPanel);
        panel.repaint();
        panel.revalidate();

//        try {
//            sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }



    protected void removeIngredient() {

    }

    protected void showIngredients() {

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
